package org.group1.back_end.response.skills;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContextFreeGrammar {
        private static final Pattern PATTERN = Pattern.compile("<[^>]+>");
        private static final String OR = ".*\\|.*";

        public static HashMap<String, String> REAL_DATA;

        public static List<String[]> REAL_REAL_DATA = new ArrayList<>();
        //TODO: FOR NATALIA
        public List<String> beforeKleeneStar = new ArrayList<>();
        public List<List<String[]>> slotsUsed = new ArrayList<>();
        public List<String> actionsToSlots = new ArrayList<>();
        static List<String> questions;

        // each row, which contains terminals and non-terminals
        public List<String[]> rightHandSide = new ArrayList<>();
        // maps non-terminals to the values
        private HashMap<String,String> mapping= new HashMap<>();
        // list of each non-terminals
        public List<String> leftHandSide = new ArrayList<>();


        public static Set<String> terminals = new HashSet<>();

        public FormatTree formatTree;

        public ContextFreeGrammar(List<String> data) {
            REAL_DATA = new HashMap<>();
            SplitRuleAction(data);

            createListData();
        }

        public void  createListData(){
            for (Map.Entry<String, String> entrada : REAL_DATA.entrySet()) {
                REAL_REAL_DATA.add(new String[]{entrada.getKey(), entrada.getValue()});
            }
        }

        public List<String[]> getRealData(){
            return REAL_REAL_DATA;
        }

        public void SplitRuleAction(List<String> data){
            List<String> rules = new ArrayList<>();
            List<String> actions = new ArrayList<>();
            for (String row : data) {
                String[] ruleParts = row.split(" ", 2);
                if(ruleParts[0].contains("Rule")){
                    rules.add(ruleParts[1]);
                }else{
                    actions.add(ruleParts[1]);
                }
            }

            processRules(rules);
            formatTree = new FormatTree(leftHandSide, rightHandSide);
            processActions(actions);
        }

        public void processRules(List<String> data){
            List<String[]> strippedMapping = new ArrayList<>();
            for (String row : data) {
                String[] ruleParts = row.split(" ", 2);
                String LHS = ruleParts[0].trim();
                leftHandSide.add(LHS);
                String RHS = ruleParts[1].trim();
                createVocabulary(RHS);
                mapping.put(LHS, RHS);
                strippedMapping.add(getRealMapping(LHS));
            }
            rightHandSide = addEmptySpace(strippedMapping);
        }

        public List<String[]> addEmptySpace(List<String[]> input){
            List<String[]> output = new ArrayList<>();
            for (int i = 0; i < input.size(); i++) {
                String[] row = input.get(i);
                boolean hasNonTerminals = true;
                for (int j = 0; j < row.length; j++) {
                    String rowComponent = row[j];
                    if(isPlaceHolder(rowComponent)) {
                        hasNonTerminals = false;
                    }
                }
                if(hasNonTerminals) {
                    String[] temp = new String[input.get(i).length + 1];
                    temp[0] = ""; /// THE EMPTY SPACE SHOULD BE \s
                    for (int j = 0; j < row.length; j++) {
                        temp[j + 1] = row[j];
                    }
                    output.add(temp);
                }else {
                    String[] temp = new String[input.get(i).length];
                    for (int j = 0; j < row.length; j++) {
                        temp[j] = row[j];
                    }
                    output.add(temp);
                }
            }
            return output;
        }


        public void processActions(List<String> data){
            List<String> LHS = new ArrayList<>();
            List<String> RHS = new ArrayList<>();
            for(String row: data){
                String[] ruleParts = row.split("\\*");
                if(ruleParts.length>1) {
                    // cases that * is present
                    // getting which rule is going to be substituted
                    ruleParts[0] = ruleParts[0].trim();
                    LHS.add(ruleParts[0]);

                    // RHS is the product
                    ruleParts[1] = ruleParts[1].trim();
                    RHS.add(ruleParts[1]);
                }else{
                    //I have no idea case
                    RHS.add(ruleParts[0]);
                }

            }
            questions = formatTree.findSentences("<S>");
            populate();

            for (int i = 0; i < LHS.size(); i++) {
                createAction(LHS.get(i), RHS.get(i));
            }
        }

        public void populate(){
            for (String a : questions) {
                REAL_DATA.put(a, "I have no idea");
            }
        }

        public void createAction(String LHS, String RHS){

            Set<String> variables = new HashSet<>();
            List<String[]> action = new ArrayList<>();
            while (isPlaceHolder(RHS)){
                //Removing placeholder
                String placeHolder = getFirstPlaceHolder(RHS);
                RHS = RHS.replaceFirst(placeHolder, "").trim();

                // getting placeholder variable
                String variable = removeVariable(RHS);
                variables.add(variable);
                RHS = RHS.replaceFirst(variable, "").trim();
                String[] slotsUsed = new String[]{placeHolder,variable};
                action.add(slotsUsed);
            }
            //variables.forEach(System.out::println);
            beforeKleeneStar.add(LHS);
            slotsUsed.add(action);
            actionsToSlots.add(RHS);
            mapCorrectActions(findPositives(LHS, variables), RHS);

        }

        public void mapCorrectActions(List<String> keys, String value){
            for(String key: keys){
                REAL_DATA.put(key,value);
            }

        }

        public List<String> findPositives(String start, Set<String> placeholders){
            Set<String> UNIVERSE = new HashSet<>(terminals);
            UNIVERSE.removeAll(placeholders);
            return formatTree.findSentences(start, new ArrayList<>(UNIVERSE), placeholders);
        }

        public String removeVariable(String RHS){
            RHS = RHS.trim();
            String key = "";
            int counter = 0;
            terminals.remove("");
            do{
                key += RHS.charAt(counter++);
            } while (!terminals.contains(key));
            return key;
        }

        public String getFirstPlaceHolder(String placeHolder){
            Pattern pattern = Pattern.compile("<([^>]+)>");
            Matcher matcher = pattern.matcher(placeHolder);
            if (matcher.find()) {
                return matcher.group();
            }
            return "";
        }
        public String[] getRealMapping(String key){
            String[] output;
            String value = mapping.get(key);
            if (value.matches(OR)){
                output = value.split("\\|");
                for (int i=0; i< output.length; i++) {
                    output[i] = output[i].trim();
                }

            }else {
                output = new String[]{value};
            }
            return output;
        }
        public void createVocabulary(String row){
            String[] rowComponent = row.split("\\|");
            for (String component : rowComponent){
                component = component.trim();
                if(!isPlaceHolder(component)){
                    terminals.add(component);
                }else {


                }
            }
            // terminals.add("");
        }


        public boolean isPlaceHolder(String word) {
            String[] components = word.split(" ");
            for (String component : components) {
                if(PATTERN.matcher(component).matches()){
                    return true;
                }
            }
            return false;
        }

        public static void main(String[] args) {
            String text = "Rule <S> <action>\n" +
                    "Rule <action> <weather>\n" +
                    "Rule <weather> How is the weather in <location> | <pro> <verb> in <location> . What is the weather?\n" +
                    "Rule <location> <city> <time>\n" +
                    "Rule <city> New York | Berlin\n" +
                    "Rule <time> tomorrow | today\n" +
                    "Rule <pro> I | she | he| my mother\n" +
                    "Rule <verb> am| is\n" +
                    "Action <weather> * <city> New York <time> tomorrow It will be sunny.\n" +
                    "Action <weather> * <city> Berlin It is rainy.\n" +
                    "Action <weather> * <pro> my mother <verb> is <city> New York <time> today It is stormy today.\n" +
                    "Action I have no idea";

            String input1 =
                    "Rule <S> <ACTION>\n" +
                    "Rule <ACTION> <LOCATION> | <SCHEDULE>\n" +
                    "Rule <SCHEDULE> Which lectures are there <TIMEEXPRESSION> | <TIMEEXPRESSION> which lectu\n" +
                    "Rule <TIMEEXPRESSION> on <DAY> at <TIME> | at <TIME> on <DAY>\n" +
                    "Rule <TIME> 9 | 12\n" +
                    "Rule <LOCATION> Where is <ROOM> | How do <PRO> get to <ROOM> | Where is <ROOM> located\n" +
                    "Rule <PRO> I | you | he | she\n" +
                    "Rule <ROOM> DeepSpace | SpaceBox\n" +
                    "Rule <DAY> Monday | Tuesday | Wednesday | Thursday | Friday | Saturday | Sunday\n" +
                    "Action <SCHEDULE> * <DAY> Saturday There are no lectures on Saturday\n" +
                    "Action <SCHEDULE> * <DAY> Monday <TIME> 9 We start the week with math\n" +
                    "Action <SCHEDULE> * <DAY> Monday <TIME> 12 On Monday noon we have Theoratical Computer S\n" +
                    "Action <LOCATION> * <ROOM> DeepSpace DeepSpace is the first room after the entrance\n" +
                    "Action <LOCATION> * <ROOM> SpaceBox is in the first floor\n" +
                    "Action I have no idea";

            String[] text2 = input1.split("\n");
            List<String> input = new ArrayList<>();
            for (String row: text2) {
                input.add(row);
            }

            ContextFreeGrammar a = new ContextFreeGrammar(input);

            List<String> c = new ArrayList<>();
            c.add("Berlin");
            c.add("today");
            c.add("New York");


        }
}
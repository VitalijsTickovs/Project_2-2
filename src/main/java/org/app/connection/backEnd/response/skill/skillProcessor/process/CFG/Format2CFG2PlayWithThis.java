package org.app.connection.backEnd.response.skill.skillProcessor.process.CFG;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format2CFG2PlayWithThis {

    private static final Pattern PATTERN = Pattern.compile("<[^>]+>");
    private static final String OR = ".*\\|.*";

    public static List<String[]> REAL_REAL_DATA = new ArrayList<>();

    static List<String> questions;

    // each row, which contains terminals and non-terminals
    public List<String[]> rightHandSide = new ArrayList<>();
    // maps non-terminals to the values
    private HashMap<String,String> mapping= new HashMap<>();
    // list of each non-terminals
    public List<String> leftHandSide = new ArrayList<>();


    public static Set<String> terminals = new HashSet<>();

    static FormatTree2PlayWithThis formatTree;

    public Format2CFG2PlayWithThis(List<String> data) {

        SplitRuleAction(data);
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
        formatTree = new FormatTree2PlayWithThis(leftHandSide, rightHandSide);
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

        for (int i = 0; i < LHS.size(); i++) {
            createAction(LHS.get(i), RHS.get(i));
        }
        //for (int i = 0; i < LHS.size(); i++) {
        //    System.out.println(LHS.get(i) + " \t " + RHS.get(i));
        //}
    }

    public static void createAction(String LHS, String RHS){

        Set<String> variables = new HashSet<>();
        while (isPlaceHolder(RHS)){
            //Removing placeholder
            String placeHolder = getFirstPlaceHolder(RHS);
            RHS = RHS.replace(placeHolder, "").trim();

            // getting placeholder variable
            String variable = removeVariable(RHS);
            variables.add(variable);
            RHS = RHS.replace(variable, "").trim();
        }
        System.out.println(RHS+"\n\n\n\n");
        //variables.forEach(System.out::println);
        findPositives(LHS, variables);

    }

    public static void findPositives(String start, Set<String> placeholders){
        Set<String> UNIVERSE = new HashSet<>(terminals);
        //UNIVERSE.add("");
        UNIVERSE.removeAll(placeholders);
        List<String> CORRECT_QUESTIONS = formatTree.findSentences(start, new ArrayList<>(UNIVERSE), placeholders);
        CORRECT_QUESTIONS.forEach(System.out::println);
    }

    public static String removeVariable(String RHS){
        RHS = RHS.trim();
        String key = "";
        int counter = 0;
        terminals.remove("");
        do{
            key += RHS.charAt(counter++);
        } while (!terminals.contains(key));
        return key;
    }

    public static String getFirstPlaceHolder(String placeHolder){
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


    public static boolean isPlaceHolder(String word) {
        String[] components = word.split(" ");
        for (String component : components) {
            if(PATTERN.matcher(component).matches()){
                return true;
            }
        }
        return false;
    }

    public static boolean containsAllPlaceHolders(Set<String> set, String text) {
        for (String element : set) {
            if (!text.contains(element)) {
                return false;
            }
        }
        terminals.remove("");
        Set<String> differenceSet = new HashSet<>(terminals);
        differenceSet.removeAll(set);
        for(String element: differenceSet){
            if(text.contains(element)){
                return false;
            }
        }
        System.out.println("Found element");
        return true;
    }

    public static void main(String[] args) {
        String text = "Rule <S> <ACTION>\n" +
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
                "Action <LOCATION> * <ROOM> SpaceBox SpaceBox is in the first floor\n" +
                "Action I have no idea";

        String[] text2 = text.split("\n");
        List<String> input = new ArrayList<>();
        for (String row: text2) {
            input.add(row);
        }

        Format2CFG2PlayWithThis a = new Format2CFG2PlayWithThis(input);

        List<String> c = new ArrayList<>();
        c.add("Berlin");
        c.add("today");
        c.add("New York");


    }
}

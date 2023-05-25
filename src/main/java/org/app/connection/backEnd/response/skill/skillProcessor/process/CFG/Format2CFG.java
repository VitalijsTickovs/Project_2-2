package org.app.connection.backEnd.response.skill.skillProcessor.process.CFG;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format2CFG {

    private static final Pattern PATTERN = Pattern.compile("<[^>]+>");
    private static final String OR = ".*\\|.*";
    public static HashMap<String, String> REAL_DATA;
    public static List<String[]> REAL_REAL_DATA = new ArrayList<>();

    static List<String> questions;

    // each row, which contains terminals and non-terminals
    public List<String[]> rightHandSide = new ArrayList<>();
    // maps non-terminals to the values
    private HashMap<String,String> mapping= new HashMap<>();
    // list of each non-terminals
    public List<String> leftHandSide = new ArrayList<>();


    public static Set<String> terminals = new HashSet<>();

    static FormatTreeTesting formatTreeTesting;

    public Format2CFG(List<String> data) {
        REAL_DATA = new HashMap<>();
        SplitRuleAction(data);

        createListData();
        for(String[] map: REAL_REAL_DATA){
            System.out.println(map[0] + " -----> "+ map[1]);
        }
    }

    public static void  createListData(){

        for (Map.Entry<String, String> entrada : REAL_DATA.entrySet()) {
            REAL_REAL_DATA.add(new String[]{entrada.getKey(), entrada.getValue()});
        }
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
        formatTreeTesting = new FormatTreeTesting(leftHandSide, rightHandSide);
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
                temp[0] = "";
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
        questions = formatTreeTesting.findSentences("<S>");
        populate();
        for (int i = 0; i < LHS.size(); i++) {
            createAction(LHS.get(i), RHS.get(i));
        }
    }

    public static void populate(){
        for (String a : questions) {
            REAL_DATA.put(a, "I have no idea");
        }
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

        for (String key : questions) {
            if(containsAllPlaceHolders(variables, key)){
                REAL_DATA.put(key, RHS);
            }else REAL_DATA.putIfAbsent(key, "I have no idea");
        }
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
        terminals.add("");
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

        return true;
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
        String[] text2 = text.split("\n");
        List<String> input = new ArrayList<>();
        for (String row: text2) {
            input.add(row);
        }

        Format2CFG a = new Format2CFG(input);


    }
}

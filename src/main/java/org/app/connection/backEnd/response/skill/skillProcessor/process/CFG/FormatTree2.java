package org.app.connection.backEnd.response.skill.skillProcessor.process.CFG;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatTree2 {

    Map<String, String[]> PRODUCTIONS = new HashMap<>();

    public FormatTree2(List<String> LHS, List<String[]> RHS) {
        generateProductions(LHS, RHS);

    }


    public void generateProductions(List<String> LHS, List<String[]> RHS){
        for (int i = 0; i < LHS.size(); i++) {
            PRODUCTIONS.put(LHS.get(i), RHS.get(i));
        }

    }

    public List<String> findSentences(String start, List<String> targetWords){
        sentences = new ArrayList<>();
        List<String> stack = new LinkedList<>();
        stack.add(start);
        recursiveFind(stack, targetWords);
        return sentences;
    }

    private static List<String> sentences = new ArrayList<>();

    public void recursiveFind(List<String> medusa, List<String> excludeWords){

        // base case
        // no terminals
        int nonTerminalPosition = findNonTerminalIndex(medusa);
        if(nonTerminalPosition == -1){
            String sentence = String.join(" ", medusa)
                    .replaceAll("\\s+", " ")
                    .trim();

            // Check if the sentence contains any of the exclude words
            boolean anyFound = false;
            for (String word : excludeWords) {
                if (sentence.contains(word)) {
                    anyFound = true;
                    break;
                }
            }

            // Only add the sentence if it does not contain any of the exclude words
            if (!anyFound) {
                //System.out.println(sentence);
                sentences.add(sentence);
            }
            return;
        }

        String firstNonTerminal = medusa.get(nonTerminalPosition);


        List<List<String>> values = division(PRODUCTIONS.get(firstNonTerminal));

        // Now we expand the nodes and of course you use recursion because
        // that is what medusa goes in the direction.
        for (List<String> value : values) {

            List<String> newMedusa = new ArrayList<>(medusa);
            newMedusa.remove(nonTerminalPosition);
            newMedusa.addAll(nonTerminalPosition, value);

            recursiveFind(newMedusa, excludeWords);
        }
    }


    public List<List<String>>  division(String[] production){

        List<List<String>> output = new ArrayList<>();

        for (int i = 0; i < production.length; i++) {
            output.add(convertToList(production[i]));
        }
        return output;
    }

    private List<String> convertToList(String input){
        List<String> output = new ArrayList<>();
//        String[] splittedInput = input.split(" ");
//        List<String> normalTexts = new ArrayList<>();
        Pattern pattern = Pattern.compile("<.*?>");
        Matcher matcher = pattern.matcher(input);

//        for(String splitInput: splittedInput){
//            if(!splitInput.matches("<.*?>")){
//                normalTexts.add(splitInput);
//            }else{
//                String normalText = String.join(" ", normalTexts);
//                output.add(normalText);
//                output.add(splitInput);
//                normalTexts = new ArrayList<>();
//            }
//        }

        int start = 0;
        while (matcher.find()) {
            if (matcher.start() != 0) {
                output.add(input.substring(start, matcher.start()).trim());
            }
            output.add(input.substring(matcher.start(), matcher.end()));
            start = matcher.end();
        }
        if (start != input.length()) {
            output.add(input.substring(start).trim());
        }
        return output;
    }

    private int findNonTerminalIndex(List<String> symbols) {

        for (int i = 0; i < symbols.size(); i++) {
            // non-terminal found
            if (PRODUCTIONS.containsKey(symbols.get(i))) {
                return i;
            }
        }
        return -1;
    }

}

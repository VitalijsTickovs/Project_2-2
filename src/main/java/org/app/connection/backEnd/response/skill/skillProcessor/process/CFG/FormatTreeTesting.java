package org.app.connection.backEnd.response.skill.skillProcessor.process.CFG;

import java.util.*;

public class FormatTreeTesting {

    Map<String, String[]> PRODUCTIONS = new HashMap<>();

    public FormatTreeTesting(List<String> LHS, List<String[]> RHS) {
        generateProductions(LHS, RHS);

    }


    public void generateProductions(List<String> LHS, List<String[]> RHS){
        for (int i = 0; i < LHS.size(); i++) {
            PRODUCTIONS.put(LHS.get(i), RHS.get(i));
        }

    }

    public List<String> findSentences(String start){
        sentences = new ArrayList<>();
        List<String> stack = new LinkedList<>();
        stack.add(start);
        recursiveExpansion(stack);
        return sentences;
    }

    private List<String> sentences = new ArrayList<>();

    public void recursiveExpansion(List<String> medusa){
        // base case
        // no terminals
        int nonTerminalPosition = findNonTerminalIndex(medusa);
        if(nonTerminalPosition == -1){
            String sentence = String.join(" ", medusa)
                    .replaceAll("\\s+", " ")
                    .trim();

            //System.out.println(sentence);
            sentences.add(sentence);
            return;
        }
        String firstNonTerminal = medusa.get(nonTerminalPosition);
        String[] getProduction = PRODUCTIONS.get(firstNonTerminal);
        List<List<String>> values = division(getProduction);

        // Now we expand the nodes and of course you use recursion because
        // that is what medusa goes in the direction.
        for (List<String> value : values) {

            List<String> newMedusa = new ArrayList<>(medusa);
            newMedusa.remove(nonTerminalPosition);
            newMedusa.addAll(nonTerminalPosition, value);

            recursiveExpansion(newMedusa);
        }
    }

    public List<List<String>>  division(String[] production){

        List<List<String>> output = new ArrayList<>();

        for (int i = 0; i < production.length; i++) {
            output.add(convertToList(production[i]));
        }
        return output;
    }

    private List<String> convertToList(String productions){
        String[] production = productions.split(" ");
        return Arrays.asList(production);
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

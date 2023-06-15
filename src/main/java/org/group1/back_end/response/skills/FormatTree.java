package org.group1.back_end.response.skills;

import java.util.*;

public class FormatTree {
    //TODO: TO GET SLOTS FOR CFG (in for loop ignore placeholders)
    Map<String, String[]> PRODUCTIONS = new HashMap<>();
    boolean flag = false;
    public Map<String, String[]> getCFGSlots(){
        Map<String, String[]> temp = new HashMap<>();
        for (String keys: PRODUCTIONS.keySet()) {
            //System.out.println("Productions: " + Arrays.toString(PRODUCTIONS.get(keys)));
            //Looping through list containing all products
            for (int i = 0; i < PRODUCTIONS.get(keys).length; i++) {
                //looping through each individual string
                for (int j = 0; j < PRODUCTIONS.get(keys)[i].length(); j++) {
                    //if we find this symbol: '<' we break from the loop otherwise we will add this list to array
                    if (PRODUCTIONS.get(keys)[i].charAt(j)=='<'){
                        break;
                    }else if (j==PRODUCTIONS.get(keys)[i].length()-1){
                        flag=true;
                        break;
                    }
                }
            }
            if (flag==true){
//                System.out.println("Chosen: " + Arrays.toString(PRODUCTIONS.get(keys)));
//                System.out.println("Corresponding Key: " + getKeyByValue(PRODUCTIONS.get(keys),PRODUCTIONS));
                temp.put(getKeyByValue(PRODUCTIONS.get(keys),PRODUCTIONS),PRODUCTIONS.get(keys));
                flag = false;
            }
        }
        System.out.println(" ");
        return temp;
    }
    public String getKeyByValue(String[] value, Map<String, String[]> hashmap){
        for (String key : hashmap.keySet()) {
            if(hashmap.get(key).equals(value)){
                return key;
            }
        }
        return null;
    }


    public FormatTree(List<String> LHS, List<String[]> RHS) {
        generateProductions(LHS, RHS);
        getCFGSlots();
    }

    public void generateProductions(List<String> LHS, List<String[]> RHS){
        for (int i = 0; i < LHS.size(); i++) {
            PRODUCTIONS.put(LHS.get(i), RHS.get(i));
        }

    }

    public List<String> findSentences(String start, List<String> targetWords, Set<String> placeholders){
        sentences = new ArrayList<>();

        List<String> stack = new LinkedList<>();
        stack.add(start);
        recursiveFind(stack, targetWords, placeholders);
        return sentences;
    }

    public void recursiveFind(List<String> medusa, List<String> excludeWords, Set<String> placeHolders){

        // base case
        // no terminals
        int nonTerminalPosition = findNonTerminalIndex(medusa);

        if(nonTerminalPosition == -1){


            boolean MedusahasEmptyString = true;
            for (String word : medusa) {
                if(word.equals("")){
                    MedusahasEmptyString = false;
                    break;
                }
            }

            if(MedusahasEmptyString){
                excludeWords.add("");
            }else{
                while(excludeWords.remove("")){
                }
            }
            boolean anyFound = false;

            String MYCHECKER = String.join(" ", medusa);

            List<Boolean> hasExcluded = new ArrayList<>();

            for(String element : medusa){
                //boolean
                for(String component: excludeWords) {
                    if (component.equals(element)) {
                        hasExcluded.add(true);
                        break;
                    }
                }
            }

            if(hasExcluded.size() != 0){
                anyFound=true;
            }


            ///Vitaly comment
            List<Boolean> hasPlaceholder = new ArrayList<>();
            for(String placeholder: placeHolders){
                for(String component: medusa){
                    if (placeholder.equals(component)) {
                        hasPlaceholder.add(true);
                        break;
                    }
                }
            }

            if(hasPlaceholder.size()!= placeHolders.size()){
                anyFound=true;
            }
            //false



            String sentence = String.join(" ", medusa)
                    .replaceAll("\\s+", " ")
                    .trim();


            // Check if the sentence contains any of the exclude words

            // Only add the sentence if it does not contain any of the exclude words
            if (!anyFound) {
                //System.out.println(sentence);
                sentences.add(sentence);
            }
            return;
        }

        String firstNonTerminal = medusa.get(nonTerminalPosition);

        /// HAZLO TU BRO POR QU ETE V A IR MEJOR ME REFIERO A LO DE TANTA LISTA AGURPA

        String[] keyOfProduction = PRODUCTIONS.get(firstNonTerminal);
        List<List<String>> values = division(keyOfProduction);

        // Now we expand the nodes and of course you use recursion because
        // that is what medusa goes in the direction.
        for (List<String> value : values) {

            List<String> newMedusa = new ArrayList<>(medusa);
            newMedusa.remove(nonTerminalPosition);
            newMedusa.addAll(nonTerminalPosition, value);

            recursiveFind(newMedusa, excludeWords, placeHolders);
        }
    }

    public List<String> findSentences(String start){
        sentences = new ArrayList<>();
        List<String> stack = new LinkedList<>();
        stack.add(start);
        recursiveExpansion(stack);
        return sentences;
    }

    private static List<String> sentences = new ArrayList<>();

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

        List<List<String>> values = division(PRODUCTIONS.get(firstNonTerminal));

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

        for(int i = 0; i < production.length; i++) {
            output.add(convertToList(production[i]));
        }
        return output;
    }

    private List<String> convertToList(String input){
        List<String> output = new ArrayList<>();
        String[] splittedInput = input.split(" ");
        List<String> normalTexts = new ArrayList<>();

        for(String splitInput: splittedInput){
            if(!splitInput.matches("<.*?>")){
//                if(splitInput.equals(""))
                normalTexts.add(splitInput); // CHECK THIS LINE
            }else{
                if(normalTexts.size()>0) {
                    String normalText = String.join(" ", normalTexts).trim();
                    output.add(normalText);
                    normalTexts = new ArrayList<>();
                }
                output.add(splitInput);
            }
        }
        if(normalTexts.size()>0){
            output.add(String.join(" ", normalTexts).trim());
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

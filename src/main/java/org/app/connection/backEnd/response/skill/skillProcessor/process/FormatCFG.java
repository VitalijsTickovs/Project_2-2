package org.app.connection.backEnd.response.skill.skillProcessor.process;

import java.util.ArrayList;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FormatCFG {

    private List<String[]> REAL_DATA;

    public FormatCFG(List<String> data) {
        REAL_DATA = new ArrayList<>();
        processAll(data);
    }

    public FormatCFG(String data) {
        REAL_DATA = new ArrayList<>();
        List<String> b = new ArrayList<>();
        b.add(data);
        processAll(b);
    }

    public void processAll(List<String> data) {
        for (String text : data) {
            process(text);
        }
    }
    public void process(String data) {
        ContextFreeGrammar context_free_grammar = new ContextFreeGrammar(data);
        REAL_DATA = context_free_grammar.getREAL_DATA();
    }

    public List<String[]> getData() {
        return REAL_DATA;
    }

    static class ContextFreeGrammar {
        private static final Pattern PATTERN = Pattern.compile("<[^>]+>");
        private final Map<String, List<List<String>>> rules;
        private final Map<String, Map<List<String>, String>> actions;

        Map<String, String> DATABASE = new HashMap<>();
        private final Map<String, Set<String>> validCombinations;

        private static Set<String> terminals = new HashSet<>();
        private final String[] lines;
        private final Set<String> sentences = new HashSet<>();

        private static final Map<String, Set<String>> grammar = new HashMap<>();

        public ContextFreeGrammar(String grammar) {
            rules = new HashMap<>();
            actions = new HashMap<>();
            validCombinations = new HashMap<>();
            lines = grammar.split("\\n");
            generateVocabulary();
            generateProductionRules();
            generateSentences("<S>");
            generateAnswers();
            miMetoido();
        }

        public void generateVocabulary(){
            for(int i=0; i<lines.length; i++){
                String[] productions = lines[i].split(" ", 3);
                String RHS = productions[2];
                String[] RHS_Components = RHS.split("\\|");
                for (String a : RHS_Components) {
                    if(!hasFormat(a)){
                        terminals.add(a.trim());
                    }
                }
            }
        }


        public static boolean hasFormat(String word) {
            String[] components = word.split(" ");
            for (String component : components) {
                if(PATTERN.matcher(component).matches()){
                    return true;
                }
            }
            return false;
        }

        public void generateAnswers(){

            for (String line : lines){
                String[] parts = line.split(" ", 2);

                //parts = Action , lo de mas
                if(parts[0].equals("Action")){

                    //quitamos la * nos quedamos formato <D> --> algo
                    String[] placeHolder_action  = parts[1].split("\\*");

                    // si existe <D>
                    if(placeHolder_action.length>1){

                        //devuelveme las variables despues de <D>
                        String[] palabritas = placeHolder_action[1].split(" ");
                        List<String> variables = new ArrayList<>();
                        //Si tiene formato devuelve la siguente
                        for (int i = 0; i < palabritas.length; i++) {

                            if(hasFormat(palabritas[i])){
                                String variable = "";
                                int count = i+1;
                                boolean trick1;
                                boolean trick2;
                                boolean trick3;
                                boolean trick4;
                                do{
                                    variable += palabritas[count];
                                    count++;
                                    if(count >= palabritas.length) break;
                                    trick1 = terminals.contains(variable.trim());
                                    trick2 = terminals.contains(" " +variable);
                                    trick4 = terminals.contains(variable + " ");
                                    trick3 = trick1 || trick2 || trick4;
                                    variable = variable + " ";
                                }while(!trick3);
                                variables.add(variable);
                            }
                        }

                        //System.out.println(variables.toString());
                        String sentence = findPhraseWithKeywords(new ArrayList<>(sentences), variables);

                        DATABASE.put(sentence, placeHolder_action[1]);
                    }

                }
            }


            for (String line : lines){
                String[] parts = line.split(" ", 2);
                String type = parts[0].trim();

                if(type.equals("Action")){

                    String[] placeHolder_action  = parts[1].split("\\*");
                    if(placeHolder_action.length>1){
                        String placeHolder = placeHolder_action[0];
                        String action = placeHolder_action[1].split(" ")[2];
                        ArrayList<String> placeHolders = extractTags(placeHolder_action[1]);

                        List<List<String>> variables = rules.get(action);

                        String sentence = findPhraseWithKeywords(new ArrayList<>(sentences), placeHolders);
                        DATABASE.put(sentence, action);
                    }
                }
            }
        }

        private static ArrayList<String> extractTags(String input) {
            ArrayList<String> tags = new ArrayList<>();
            Pattern pattern = Pattern.compile("<(.*?)>");
            Matcher matcher = pattern.matcher(input);

            while (matcher.find()) {
                tags.add("<" + matcher.group(1) + ">");
            }

            return tags;
        }

        public static String findPhraseWithKeywords(List<String> phrases, List<String> keywords) {
            for (String phrase : phrases) {
                boolean allKeywordsPresent = true;

                for (String keyword : keywords) {
                    if (!phrase.trim().contains(keyword.trim())) {
                        allKeywordsPresent = false;
                        break;
                    }
                }

                if (allKeywordsPresent) {
                    return phrase;
                }
            }

            return "I have no idea";
        }

        public void generateProductionRules(){

            for (String line : lines) {
                String[] parts = line.split(" ", 2);
                String type = parts[0].trim();

                if (type.equals("Rule")) {
                    String[] ruleParts = parts[1].split(" ", 2);
                    String nonTerminal = ruleParts[0].trim();
                    String[] productions = ruleParts[1].split("\\|");

                    List<List<String>> productionList = new ArrayList<>();
                    for (String production : productions) {
                        List<String> symbols = Arrays.asList(production.trim().split(" "));
                        productionList.add(symbols);
                    }

                    rules.put(nonTerminal, productionList);

                    //System.out.println(nonTerminal + "____" + productionList);
                }
            }
        }

        List<String[]> REAL_DATA = new ArrayList<>();
        public void miMetoido(){
            List<String> mapping = new ArrayList<>(sentences);
            for (int i = 0; i < mapping.size(); i++) {
                String value = DATABASE.getOrDefault(mapping.get(i), "I have no idea");

                String[] keys = {mapping.get(i), value};

                REAL_DATA.add(keys);

            }
        }

        public List<String[]> getREAL_DATA() {
            return REAL_DATA;
        }

        public void generateSentences(String startSymbol) {
            List<String> stack = new LinkedList<>();
            stack.add(startSymbol);
            generateSentencesHelper(stack);
        }



        // EYOOOOO
        public static  int  nonTerminals = 0;
        public static  int  deep = 0;
        public static  int  size = 0;
        //Start with <S>
        private void generateSentencesHelper(List<String> stack) {

            deep++;
            int firstNonTerminalIndex = findFirstNonTerminalIndex(stack);

            if (firstNonTerminalIndex == -1) {
                //String response = findResponse(stack);
                //System.out.println("Frase: " + String.join(" ", stack));
                sentences.add(String.join(" ", stack));
                //System.out.println("Respuesta: " + response);
                return;
            }

            String nonTerminal = stack.get(firstNonTerminalIndex);
            List<List<String>> productions = rules.get(nonTerminal);

            // Here apears the beautiful STUFFF
            for (List<String> production : productions) {
                size++;
                List<String> newStack = new ArrayList<>(stack);
                newStack.remove(firstNonTerminalIndex);
                newStack.addAll(firstNonTerminalIndex, production);

                // MORE BAUTIFUL STUFF
                generateSentencesHelper(newStack);
            }
        }



        private int findFirstNonTerminalIndex(List<String> symbols) {

            // N3 found
            for (int i = 0; i < symbols.size(); i++) {
                nonTerminals++;
                if (rules.containsKey(symbols.get(i))) {
                    return i;
                }
            }
            return -1;
        }

        private String findResponse(List<String> sentence) {
            String joinedSentence = String.join(" ", sentence);

            for (String nonTerminal : validCombinations.keySet()) {
                Set<String> nonTerminalCombinations = validCombinations.get(nonTerminal);

                for (String combination : nonTerminalCombinations) {
                    if (joinedSentence.contains(combination)) {
                        return "Found valid combination: " + combination;
                    }
                }
            }

            return "I have no idea";
        }


    }
}


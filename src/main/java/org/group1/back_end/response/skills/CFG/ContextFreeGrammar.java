package org.group1.back_end.response.skills.CFG;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ContextFreeGrammar {
    private final Map<String, List<List<String>>> rules;
    private final Map<String, Map<List<String>, String>> actions;

    Map<String, String> DATABASE = new HashMap<>();
    private final Map<String, Set<String>> validCombinations;

    private final List<String[]> result;

    private final String[] lines;
    private final Set<String> sentences = new HashSet<>();

    private static final Map<String, Set<String>> grammar = new HashMap<>();

    public ContextFreeGrammar(String grammar) {
        rules = new HashMap<>();
        actions = new HashMap<>();
        validCombinations = new HashMap<>();
        lines = grammar.split("\\n");
        result = new ArrayList<>();
        generateProductionRules();

        generateSentences("<s>");
        generateAnswers();

        miMetoido();


    }
    private static final Pattern PATTERN = Pattern.compile("<[^>]+>");

    public static boolean hasFormat(String word) {
        return PATTERN.matcher(word).matches();
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
                            variables.add(palabritas[i+1]);
                        }
                    }

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

   /*
                ArrayList<String> placeHolders = extractTags(action);
                String sentence = findPhraseWithKeywords(new ArrayList<>(sentences), placeHolders);

                DATABASE.put(sentence, action);

    */

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
                if (!phrase.contains(keyword)) {
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

    public void miMetoido(){
        List<String> eyo = new ArrayList<>(sentences);

        for (int i = 0; i < eyo.size(); i++) {

            String value = DATABASE.getOrDefault(eyo.get(i), "I have no idea");
            String[] resultao = new String[2];
            resultao[0] = eyo.get(i);
            resultao[1] = value;
            result.add(resultao);
            System.out.println(eyo.get(i) + " ---> " + value);
        }

    }


    public void generateSentences(String startSymbol) {
        List<String> stack = new LinkedList<>();
        stack.add(startSymbol);
        generateSentencesHelper(stack);
    }

    private void generateSentencesHelper(List<String> stack) {

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

        for (List<String> production : productions) {
            List<String> newStack = new ArrayList<>(stack);
            newStack.remove(firstNonTerminalIndex);
            newStack.addAll(firstNonTerminalIndex, production);
            System.out.println(newStack);
            generateSentencesHelper(newStack);
        }
    }



    private int findFirstNonTerminalIndex(List<String> symbols) {
        for (int i = 0; i < symbols.size(); i++) {
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

    public List<String[]> getResult() {
        return result;
    }

    public static void main(String[] args) {

        String grammar1 = "Rule <S> <ACTION>\n" +
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

        String grammar2 = "Rule <S> <EXPR>" +
                "Rule <EXPR> <TERM> | <EXPR> <ADD_OP> <TERM>\n" +
                "Rule <TERM> <FACTOR> | <TERM> <MUL_OP> <FACTOR>\n" +
                "Rule <FACTOR> <NUMBER> | <LPAREN> <EXPR> <RPAREN>\n" +
                "Rule <ADD_OP> + | -\n" +
                "Rule <MUL_OP> * | /\n" +
                "Rule <LPAREN> (\n" +
                "Rule <RPAREN> )\n" +
                "Rule <NUMBER> <DIGIT> | <NUMBER> <DIGIT>\n" +
                "Rule <DIGIT> 0 | 1 | 2 \n" +
                "Action <EXPR> * <TERM> <ADD_OP> <TERM> eval_add_sub\n" +
                "Action <TERM> * <FACTOR> <MUL_OP> <FACTOR> eval_mul_div\n" +
                "Action <FACTOR> * <LPAREN> <EXPR> <RPAREN> eval_parentheses\n" +
                "Action <NUMBER> * <DIGIT> append_digit\n" +
                "Action <NUMBER> * <NUMBER> <DIGIT> append_digit" +
                "Action I have no idea";

        String grammar3 = "Rule <s> <action>\n" +
                "Rule <action> <weather> \n" +
                "Rule <weather> How is the weather in <location> | <pro> <verb> in <location> What is the weather?\n" +
                "Rule <location> in <city> <time>\n" +
                "Rule <city> New York | Berlin\n" +
                "Rule <time> tomorrow | today\n" +
                "Rule <pro> I | she | he| my mother\n" +
                "Rule <verb> am| is\n" +
                "Action <weather> * <city> New York <time> tomorrow It will be sunny.\n" +
                "Action <weather> * <city> Berlin It is rainy.\n" +
                "Action <weather> * <pro> my mother <verb> is  <city> New York <time> today It is stormy today. \n" +
                "Action I have no idea\n";


        //ContextFreeGrammar cfg1 = new ContextFreeGrammar(grammar1);
        //ContextFreeGrammar cfg2 = new ContextFreeGrammar(grammar2);
        ContextFreeGrammar cfg1 = new ContextFreeGrammar(grammar3);


        // cfg.generateSentences("<SCHEDULE>");
        /*
        boolean chechSentence(String sentence){
            for (ContextFreeGrammar cfg : allGrammars) {
                boolean belongs = cfg.pase(myword);
                if (belongs) return true;
            }
            return false;
        }

         */


    }
}
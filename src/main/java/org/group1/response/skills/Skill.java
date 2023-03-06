package org.group1.response.skills;

import java.util.*;
import java.util.function.Predicate;

import org.group1.processor.PreProcessor;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Skill {


    // Skill Attributes
    public ArrayList<Rule> rule;
    public Slot slot;
    public Action action;
    private Question question;

    // Keyword
    public List<String> keywords;


    /**
     * Used to load skills.
     * The text file would be the rule file with all the slots
     *      (e.g. Rule_1, Rule_2, etc.)
     */
    public Skill(String text) throws Exception {
        if(text == null) throw new Exception("Text is null in insertion");
        this.rule = new ArrayList<>();
        this.action = new Action();
        generateSkills(text);
    }

    //TODO: use some sort of pattern matching to find the best match i.e. a certain accuracy against a threshold ('distance')
    /**
     * Used to check if a question matches a skill
     * @param question The question
     * @return True if the question matches the skill
     */
    public boolean isMatch(String question) throws Exception{
        List<String> text = PreProcessor.preprocess(question);
        if(question == null || question.equals("")) return false;
        return text.containsAll(keywords);
    }



    /**
     * Used to generate keywords from a question
     * @param text The question
     * @return The keywords
     */
    private static List<String> generateKeyWords(String text) throws Exception {

        Pattern pattern = Pattern.compile("<\\w+>");
        Matcher matcher = pattern.matcher(text);

        List<String> toRemove = new ArrayList<>();
        while (matcher.find()) {
            toRemove.add(matcher.group());
        }
        for(String s: toRemove){
            text = text.replace(s, "");
        }
        return PreProcessor.preprocess(text);
    }

    // TODO: fix for lowercase letters...

    /**
     * Used to generate a skill from a text
     * @param text, the text
     * @throws FileNotFoundException, if the file is not found
     */
    public void generateSkills(String text) throws Exception {

        boolean check = Arrays
                .stream(text
                        .split("\n"))
                .toList()
                .get(0)
                .contains("Question");

        // check if it contains a question
        if(check) processQuestion(text);
    }

    /**
     * Used to process a question
     * @param text, the text
     * @throws FileNotFoundException, if the file is not found
     */
    public void processQuestion(String text) throws Exception {
        List<String> words = Arrays.stream(Arrays
                .stream(text
                        .split("\n"))
                .toList()
                .get(0)
                .split(" ")).toList();

        List<String> names = new ArrayList<>();

        words.forEach((w)-> {if(isPlaceHolder(w)) names.add(w);});
        this.setQuestion(new Question(String.join(" ", Arrays.copyOfRange(words.toArray(new String[0]),1,words.size())), names));
        processSlot(text, names);
    }

    /**
     * Used to process a slot
     * @param text, the text
     * @param entries, the entries
     */
    public void processSlot(String text, List<String> entries) {
        List<Slot> listSlot = new ArrayList<>();

        for (String entry : entries){

            List<String> lines =  Arrays
                    .stream(text
                            .split("\n"))
                    .toList()
                    .stream()
                    .filter((s)-> s.contains("Slot") && s.contains(entry))
                    .toList();
            listSlot.add(getSlot(lines));
        }
        processAction(text);
    }

    /**
     * Used to process an action
     * @param text, the text
     */
    public void processAction(String text){
        List<String> lines =  Arrays
                .stream(text
                        .split("\n"))
                .toList()
                .stream()
                .filter((s)-> s.contains("Action"))
                .toList();
        lines.forEach((l)-> action.add(l));
        addRules(lines);
    }

    /**
     * Used to add rules to a skill
     * @param lines, the lines
     */
    private void addRules(List<String> lines){
        for(String line: lines){
            try {
                this.rule.add(getRule(line));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }

    /**
     * Used to get the slot from a list of lines
     * @param lines The lines
     * @return The slot
     */
    private static Slot getSlot(List<String> lines){
        Slot slot = new Slot();
        String[] key;
        String toadd="";
        for(String line: lines){
            key = line.split(" ");
            if(line.contains("Slot")){
                for(String s : Arrays.copyOfRange(key, 2, key.length)){
                    toadd += s + " ";
                }
                slot.add(toadd);
            }
        }
        return slot;
    }

    /**
     * Used to get the rule from a line
     * @param line The line
     * @return The rule
     */
    private static Rule getRule(String line) throws Exception{
        return new Rule(getPlaceHolders(line), getAction(line));
    }

    // TODO: instead of stemming do preprocessing... however, do this in rule, separation of concern

    /**
     * Used to check if a string is a placeholder
     * @param line, The string
     * @return True if the string is a placeholder
     */
    private static List<String> getPlaceHolders(String line){
        List<String> placeHolders = new ArrayList<>();
        String[] tocheck = line.split(" ");
        for(int i = tocheck.length-1; i >=0; i--){
            if(isPlaceHolder(tocheck[i])){
                placeHolders.add(tocheck[i+1]);
            }
        }
        return placeHolders;
    }

    /**
     * Used to get the action from a line
     * @param line The line
     * @return The action
     */
    private static Action getAction(String line) {
        String[] tocheck = line.split(" ");
        Action action = new Action();
        for(int i = tocheck.length-1; i >=0; i--){
            if(isPlaceHolder(tocheck[i])){
                action.add(String.join(" ", Arrays.copyOfRange(tocheck, i+2, tocheck.length)));
                break;
            }
        }
        return action;
    }

    /**
     * Used to check if a string is a placeholder
     * @param text The text
     * @return True if it is a placeholder
     */
    private static boolean isPlaceHolder(String text){
        return text.matches("^<[A-Z]+>.*$");
    }

    // Setters
    public void setQuestion(Question question){
        try{
            this.keywords = generateKeyWords(question.getQuestion());
        }catch(Exception e){
            System.out.println(e);
        }
        this.question = question;

    }
    public void setAction(Action action) {
        this.action = action;
    }


    // Override
    @Override
    public String toString(){
        String toRet = "";
        toRet += "question: " + question.getQuestion() + " slot size: " + this.slot.getSlot().size();
        return toRet;
    }



    // ====================================
    /**
     * Used to get the answer to a question
     * @param question, the question
     * @return The answer to the question
     * @throws Exception
     */
    public String getAnswer(String question) throws Exception{
        Predicate<String> isKeyWord = word -> keywords.contains(word);
        if(!isMatch(question)) {
            throw new Exception("Faulty Call on Skill...");
        }

        List<String> tocheck = PreProcessor.preprocess(question);

        tocheck.removeIf(isKeyWord);
        for(Rule rule: this.rule){
            Collections.sort(tocheck);
            Collections.sort(rule.pairs);
            boolean containsArray = tocheck.equals(rule.pairs);
            if(containsArray) return rule.action.toString();
        }
        return "No answer found";
    }

    public List<Rule> getRules(){
        return this.rule;
    }


}

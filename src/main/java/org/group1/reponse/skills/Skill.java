package org.group1.reponse.skills;

import java.util.*;
import java.util.function.Predicate;
import org.group1.reponse.FileService;
import org.group1.exception.NullTextException;
import org.group1.reponse.procesor.PreProcessor;
import org.group1.reponse.procesor.Stemming;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Skill implements Comparable{

    static Pattern pattern;
    static Matcher matcher;
    private Predicate<String> isKeyWord;
    public ArrayList<Rule> rule;
    public Slot slot;
    public Action action;
    private Question question;
    public static FileService fileService;
    public List<String> keywords;
    /**
     * Used to load skills
     */

    public Skill(String text) throws Exception {
        if(text == null) throw new Exception("text is null in insertion");
        this.isKeyWord = word -> keywords.contains(word);
        this.action = new Action();
        fileService = new FileService();
        this.rule = new ArrayList<>();
        generateSkills(text);
    }

    public Skill(Question question){
        this.isKeyWord = word -> keywords.contains(word);
        setQuestion(question);
        try{
            this.setQuestion(question);
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    public Skill(){}

    //TODO: use some sort of pattern matching to find the best match i.e. a certain accuracy against a threshold
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
     * Used to get the answer to a question
     * @param question, the question
     * @return The answer to the question
     * @throws Exception
     */
    public String getAnswer(String question) throws Exception{
        if(!isMatch(question)) return "faulty call on skill";
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

    /**
     * Used to generate keywords from a question
     * @param text The question
     * @return The keywords
     */
    private static List<String> generateKeyWords(String text) throws Exception {
        pattern = Pattern.compile("<\\w+>");
        matcher = pattern.matcher(text);

        List<String> toRemove = new ArrayList<>();
        while (matcher.find()) {
            toRemove.add(matcher.group());
        }
        for(String s: toRemove){
            text = text.replace(s, "");
        }
        return PreProcessor.preprocess(text);
    }

    /**
     * Used to generate a skill from a text
     * @param text, the text
     * @throws FileNotFoundException, if the file is not found
     */
    public void generateSkills(String text) throws FileNotFoundException {

        boolean check = Arrays
                .stream(text
                        .split("\n"))
                .toList()
                .get(0)
                .contains("Question");

        if(check) processQuestion(text);
    }

    /**
     * Used to process a question
     * @param text, the text
     * @throws FileNotFoundException, if the file is not found
     */
    public void processQuestion(String text) throws FileNotFoundException {
        List<String> words = Arrays.stream(Arrays
                .stream(text
                        .split("\n"))
                .toList()
                .get(0)
                .split(" ")).toList();

        List<String> names = new ArrayList<>();

        words.forEach((w)-> {if(w.matches("^<[A-Z]+>$")) names.add(w);});
        this.setQuestion(new Question(String.join(" ", Arrays.copyOfRange(words.toArray(new String[0]),1,words.size()))));
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
     * @throws NullTextException If the text is null
     */
    private static Rule getRule(String line) throws Exception {
        return new Rule(getPlaceHolders(line), getAction(line));
    }

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
                placeHolders.add(Stemming.Steam(tocheck[i+1].toLowerCase()));
            }
        }
        return placeHolders;
    }

    /**
     * Used to get the action from a line
     * @param line The line
     * @return The action
     * @throws NullTextException If the text is null
     */
    private static Action getAction(String line) throws Exception{
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
        return text.matches("^<[A-Z]+>$");
    }

    public String toString(){
        String toRet = "";
        toRet += "question: " + question.getQuestion() + " slot size: " + this.slot.getSlot().size();
        return toRet;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

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
}

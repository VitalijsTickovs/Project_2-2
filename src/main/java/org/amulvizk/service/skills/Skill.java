package org.amulvizk.service.skills;

import java.util.*;
import java.util.function.Predicate;
import org.amulvizk.service.FileService;
import org.group1.collections.Delim;
import org.group1.exception.NullTextException;
import org.group1.helpers.TXTReader;
import org.group1.reponse.procesor.PreProcessor;
import org.group1.reponse.procesor.Stemming;
import org.group1.reponse.procesor.Tokenization;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Skill implements Comparable{

    static Pattern pattern;
    static Matcher matcher;

    private Predicate<String> isKeyWord;

    public static void main(String[] args) throws Exception{
        String test1 = "Action <DAY> Monday <TIME> 11 On Monday noon we have Theoretical Computer Science";
        String test2 = "Action <DAY> Saturday There are no lectures on Saturday";

        Skill test = new Skill(new Question("Which lectures are there on <DAY> at <TIME>"));
        System.out.println(test.keywords);
    }

    public ArrayList<Rule> rule;
    public Slot slot;
    public Action action;
    private Question question;
    public static FileService fileService;
    public List<String> keywords;
    /**
     * Used to load skills
     */
    public Skill(Question question, Slot slot, Action action){
        this.slot = slot;
        this.action = action;
        this.question = question;
        try{
            this.setQuestion(question);
        }catch (Exception e) {
            System.out.println(e);
        }

    }

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

    //TODO: use some sort of pattern matching to find the best match i.e. a certain accuracy against a treshold
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
        return "couldn't find match";
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

    public void generateSkills(String text) throws FileNotFoundException {

        boolean check = Arrays
                .stream(text
                        .split("\n"))
                .toList()
                .get(0)
                .contains("Question");

        if(check) processQuestion(text);
    }

    public void processQuestion(String text) throws FileNotFoundException {
        List<String> words = Arrays.stream(Arrays
                .stream(text
                        .split("\n"))
                .toList()
                .get(0)
                .split(" ")).toList();

        List<String> names = new ArrayList<>();

        words.forEach((w)-> {if(w.matches("^<[A-Z]+>$")) names.add(w);});

        Question question1 = new Question(String.join(" ", Arrays.copyOfRange(words.toArray(new String[0]),1,words.size())));
        this.setQuestion(question1);


        processSlot(text, names);

    }

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

            String[] key;
            String toadd="";
            Slot slot = new Slot();
            for(String line: lines){
                key = line.split(" ");
                if(line.contains(entry)){
                    for(int i=2; i<key.length;i++){
                         toadd += key[i];
                    }
                    slot.add(toadd);
                    toadd="";
                }
            }
            listSlot.add(slot);
        }
        processAction(text);
    }


    public void processAction(String text){
        List<String> lines =  Arrays
                .stream(text
                        .split("\n"))
                .toList()
                .stream()
                .filter((s)-> s.contains("Action"))
                .toList();

        lines.forEach((l)-> action.add(l));
        lines.forEach((l)-> System.out.println("process action: " + l));

        for(String line: lines){
            try {
                Rule rule = createPair(line);
               this.rule.add(rule);
            }catch(NullTextException e){
                e.printStackTrace();
            }
        }
    }


    private static Rule createPair(String line) throws NullTextException {
        boolean firstHolder = true;
        String[] tocheck = line.split(" ");
        List<String> placeHolders = new ArrayList<>();
        Action action = new Action();
        for(int i = tocheck.length-1; i >=0; i--){
            if(isPlaceHolder(tocheck[i])){
                List<String> unprocessed = Tokenization.tokenize(tocheck[i+1], Delim.SPACE);
                unprocessed = Stemming.exctract(unprocessed);
                List<String> processed = new ArrayList<>();
                for(String text : unprocessed){
                    processed.add(text.toLowerCase());
                }
                String process = String.join(" ", processed);
                placeHolders.add(process);
                if(firstHolder){
                    action.add(String.join(" ", Arrays.copyOfRange(tocheck, i+2, tocheck.length)));
                    firstHolder = false;
                }
            }
        }
        return new Rule(placeHolders, action);
    }

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
            System.out.println("this should hit");
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

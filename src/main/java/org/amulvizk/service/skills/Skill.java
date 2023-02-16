package org.amulvizk.service.skills;

import org.amulvizk.service.FileService;
import org.group1.collections.Delim;
import org.group1.reponse.procesor.PreProcessor;
import org.group1.reponse.procesor.Tokenization;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Skill implements Comparable{

    static Pattern pattern;
    static Matcher matcher;

    public static void main(String[] args) throws Exception{
        Question question = new Question("What lecture do we have on <DAY> at <TIME>?");
        Skill test = new Skill(question, new Slot[]{new Slot(), new Slot()}, new Action());
        System.out.println(generateKeyWords(question.getQuestion()));
        System.out.println(question.getQuestion());
        System.out.println(test.isMatch("What lecture do we have on Monday at 10:00?"));
        System.out.println(test.isMatch("Do we have a lecture on Monday at 10:00?"));
        System.out.println(test.isMatch("What lecture do we have on Monday at 10:00?"));
        System.out.println(test.isMatch("On Monday at 10:00 do we have a lecture?"));
        System.out.println(test.isMatch("What are you doing today?"));

    }

    public Rule rule;
    public Slot[] slot;
    public Action action;
    public Question question;
    public static FileService fileService;
    List<String> keywords;

    /**
     * Used to load skills
     */
    public Skill(Question question, Slot[] slot, Action action){
        this.slot = slot;
        this.action = action;
        this.question = question;
        try{
            this.keywords = generateKeyWords(question.getQuestion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO: use some sort of pattern matching to find the best match i.e. a certain accuracy against a treshold
    /**
     * Used to check if a question matches a skill
     * @param question The question
     * @return True if the question matches the skill
     */
    public boolean isMatch(String question) throws Exception{
        List<String> text = PreProcessor.preprocess(question);
        for(String s: text){
            if(keywords.contains(s)) return true;
        }
        return false;
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

        processSlot(text, names);

    }

    public void processSlot(String text, List<String> variables) {

        for (String variable : variables){

            List<String> lines =  Arrays
                    .stream(text
                            .split("\n"))
                    .toList()
                    .stream()
                    .filter((s)-> s.contains("Slot") && s.contains(variable))
                    .toList();

            //lines.forEach(System.out::println);
            //System.out.println();
            //slot.add(lines.toArray(new String[0]));
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

    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public void setSlot(Slot[] slot) {
        this.slot = slot;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}

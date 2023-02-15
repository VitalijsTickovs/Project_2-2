package org.amulvizk.service.skills;

import org.amulvizk.service.FileService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Skill implements Comparable{

    public Rule rule;
    public Slot[] slot;
    public Action action;
    public Question question;
    public static FileService fileService;

    /**
     * Used to load skills
     */
    public Skill(Question question, Slot[] slot, Action action){
        this.slot = slot;
        this.action = action;
        this.question = question;
    }

//    public Skill(String text) throws IOException {
//
//        fileService = new FileService();
//        this.rule = new Rule();
//        this.question = new Question();
//        slot = new Slot();
//        this.action = new Action();
//
//        generateSkills(text);
//    }

    public void generateSkills(String text) throws FileNotFoundException {

        boolean check = Arrays
                .stream(text
                        .split("\n"))
                .toList()
                .get(0)
                .contains("Question");

        if(check) processQuestion(text);
    }

    public static void main(String[] args) throws IOException {

        /*
        FileService service = new FileService();
        Skill b = new Skill(service.read(1));

         */

        String[] day = {"monday", "Wednesday dhen hug", "Thursday", "Friday", "Saturday"};

        String time = "14";

        for (int i = 0; i < day.length; i++) {
            String regex = "Question Which lectures are there on " +  day[i] + " at " + time;
            System.out.println(
                    regex.matches("^Question Which lectures are there on ([A-z]{1}[a-z]*+[ ]?){1,3} at [0-9]*$"));
        }

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

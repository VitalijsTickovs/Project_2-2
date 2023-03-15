package org.group1.response;

import java.util.*;
import java.util.regex.Pattern;

import static org.group1.utilities.RegexUtilities.*;


public class SkillProcessor {

    private String originalQuestion;
    private String deafault;
    private Set<String> slotSet;
    private List<String> questions;
    private List<String> actions;

    public SkillProcessor(String text) throws Exception {
        this.slotSet = new HashSet<>();
        questions = new LinkedList<>();
        actions = new LinkedList<>();
        processText(text);

    }

    public void processText(String text) throws Exception {
        processQuestion(text);
        processSlot(text);
        processAction(text);
    }

    public void processQuestion(String text){
        originalQuestion = filterLineByRegex(text, "Question")
                .get(0)
                .replace("Question", "")
                .trim();
    }

    public void processSlot(String text){

        // HERE I FILTER THE TEXT IN LINES I ONLY WANT THE LINES THAT START WITH SLOT
        List<String> slotList = filterLineByRegex(text, "Slot");

        // HERE I DELETE EVERYTHING AND I LEAVE THE WORD.
        for (int i = 0; i < slotList.size(); i++) {
            String temp = slotList
                    .get(i)
                    .replace("Slot", "")
                    .replaceAll("<.*?>", "")
                    .trim();

            // PREPROCESS THE TEMP
            slotSet.add(temp);
            // SYNONYMS
        }
    }

    public void processAction(String text) throws Exception {

        List<String> actionList = filterLineByRegex(text, "Action");

        for (int i = 0; i < actionList.size(); i++) {

            int n = countRegexOccurrences(actionList.get(i),"<.*?>");

            if(n == 0) deafault =
                    replaceRegex(
                            actionList
                            .get(i)
                            , "Action"
                            , "");


            String[][] data = new String[n][2];
            String line = replaceRegex(actionList.get(i), "Action", "");

            for (int j = 0; j < n; j++) {

                String variable = getOriginalFormatFromRegex(
                        line
                        ,"<.*?>");

                String newLine = replaceRegex(line, variable, "");
                String slot = "";
                int count = 0;

                do{
                    slot += newLine.charAt(count++);
                } while (!this.slotSet.contains(slot));


                newLine = replaceFirstRegex(newLine, slot, "");
                line = newLine;
                data[j][1] = variable;
                data[j][0] = slot;
            }
            makeDataFrame(data, line);
        }
    }

    public void makeDataFrame(String[][] data, String action) throws Exception {
        String question = originalQuestion;

        for (int i = 0; i < data.length; i++) {
            question = replaceRegex(question, data[i][1], data[i][0]);
        }

        if(data.length < countRegexOccurrences(originalQuestion,"<.*?>")){
            String temp = getOriginalFormatFromRegex(question, "<.*?>");
            question = replaceRegex(question, temp, "");
        }

        questions.add(question);
        actions.add(action);

    }


    public String getDeafault() {
        return deafault;
    }

    public String getDeafaultKey() {
        return Pattern.compile("<.*?>").matcher(originalQuestion).replaceAll("");
    }

    public List<String> getQuestions() {
        return questions;
    }

    public List<String> getActions() {
        return actions;
    }

}

package org.app.connection.backEnd.response.skill.skillProcessor.process;

import java.util.ArrayList;
import java.util.List;

import org.app.connection.backEnd.response.skill.skillProcessor.process.processUtility.RegexUtilities;

public class FormatTemplate {
    private List<String[]> REAL_DATA;

    public FormatTemplate(List<String> skils){
        REAL_DATA = new ArrayList<>();
        processAll(skils);
    }

    public void processAll(List<String> data){
        for(String text: data){
            process(text);
        }
    }

    public void process(String skill){

        // OG Question is kind of a back-up for creating all posible questions.
        String originalQuestion = RegexUtilities.filterLineByRegex(skill, "Question")
                .get(0)
                .replace("Question", "")
                .trim();

        // We need de exact slots for security
        List<String> slotList = RegexUtilities.filterLineByRegex(skill, "Slot");

        // Combinations are Placeholders mapped to question
        // i.e Action <DAY> Saturday There are no lectures on Saturday
        // Comb will have DAY Saturday
        List<String> comb = new ArrayList<>();
        List<String> slotSet = new ArrayList<>();

        for (int i = 0; i < slotList.size(); i++) {
            String temp = slotList
                    .get(i)
                    .replace("Slot", "")
                    .replaceAll("<.*?>", "")
                    .trim();

            slotSet.add(temp);
        }

        for (String s : slotList) {
            comb.add(s
                    .replaceAll(" ", "")
                    .replaceAll("Slot", ""));
        }
        comb.add(" ");

        comb = RegexUtilities
                .concatenate(comb,
                        RegexUtilities.countRegexOccurrences(originalQuestion,"<.*?>"));


        // filter line by action
        List<String> actionList = RegexUtilities.filterLineByRegex(skill, "Action");

        // Process each action
        for (int i = 0; i < actionList.size(); i++) {
            // Gets number of placeholders
            int n = RegexUtilities.countRegexOccurrences(actionList.get(i),"<.*?>");

            // stores the slot name and the value of it
            String[][] data = new String[n][2];
            String line = RegexUtilities.replaceRegex(actionList.get(i), "Action", "");

            for (int j = 0; j < n; j++) {

                String variable = RegexUtilities.getOriginalFormatFromRegex(
                        line
                        ,"<.*?>");

                String newLine = RegexUtilities.replaceRegex(line, variable, "");
                String slot = "";
                int count = 0;


                do{
                    slot += newLine.charAt(count++);
                } while (!slotSet.contains(slot));


                newLine = RegexUtilities.replaceFirstRegex(newLine, slot, "");

                line = newLine;
                data[j][1] = variable;
                data[j][0] = slot;
            }

            String question = originalQuestion;

            for (int y = 0; y < data.length; y++) {
                question = RegexUtilities.replaceRegex(question, data[y][1], data[y][0]);
            }

            if(data.length < RegexUtilities.countRegexOccurrences(originalQuestion,"<.*?>")){
                String temp = RegexUtilities.getOriginalFormatFromRegex(question, "<.*?>");
                question = RegexUtilities.replaceRegex(question, temp, "");
            }

            question = question.replaceAll("<.*?>", "").trim();

            String[] key_value = new String[2];
            key_value[0] = question;
            key_value[1] = line;

            REAL_DATA.add(key_value);
        }
    }
    public List<String[]> getData(){
        return REAL_DATA;
    }
}

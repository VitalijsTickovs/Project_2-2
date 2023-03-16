package org.group1.response;

import org.bytedeco.opencv.opencv_core.IplROI;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.group1.utilities.RegexUtilities.*;


public class SkillProcessor {

    private String originalQuestion;
    private String deafault;
    private Set<String> slotSet;
    private ArrayList<String> slotTypes;
    private List<String> questions;
    private List<String> actions;
    private HashMap<String, String> slotMapping;

    public SkillProcessor(String text) throws Exception {
        this.slotSet = new HashSet<>();
        questions = new LinkedList<>();
        actions = new LinkedList<>();
        this.slotTypes = new ArrayList<>();
        this.slotMapping = new HashMap<>();
        processText(text);

    }



    public void processText(String text) throws Exception {
        processQuestion(text);
        processSlot(text);
        processAction(text);
        //TODO Link with database

        TxtToSQL ts = new TxtToSQL();
        String tableName = "rule";
        ArrayList<String> slot = new ArrayList<>();
        slot.add("Slot Type"); slot.add("Slot Value");
        slotTypes.add("Action");
        ts.createTable(tableName, slotTypes);
        ts.createTable("Slots" ,slot);

        for(String key: slotMapping.keySet()){
            ts.insertRecord("Slots",
                    new String[]{"Slot Type", "Slot Value"},
                    new String[]{key, slotMapping.get(key)});
        }
    }



    public void processQuestion(String text){
        originalQuestion = filterLineByRegex(text, "Question")
                .get(0)
                .replace("Question", "")
                .trim();

        // Slot types
        String pattern = "<([^>]*)>";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(originalQuestion);

        while (m.find()) {
            slotTypes.add(m.group(1));
        }
    }

    public void processSlot(String text){

        // HERE I FILTER THE TEXT IN LINES I ONLY WANT THE LINES THAT START WITH SLOT
        List<String> slotList = filterLineByRegex(text, "Slot");
        // HERE I DELETE EVERYTHING AND I LEAVE THE WORD.
        for (int i = 0; i < slotList.size(); i++) {
            String[] mapping = slotList
                    .get(i)
                    .replace("<", "")
                    .replace(">", "")
                    .replace("Slot ", "")
                    .split(" ");

            slotMapping.put(myToString(Arrays.copyOfRange(mapping,1,mapping.length)),mapping[0]);

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

    public static String myToString(Object[] a) {
        if (a == null)
            return "null";

        int iMax = a.length - 1;
        if (iMax == -1)
            return "";

        StringBuilder b = new StringBuilder();
        if(a.length>1) {
            for (int i = 0; i < a.length; i++) {
                b.append(String.valueOf(a[i]));
                b.append(" ");
            }
        }else{
            b.append(String.valueOf(a[0]));
        }
        return b.toString();
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

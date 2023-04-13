package org.group1.back_end.response.skills;

import org.group1.back_end.response.skills.dataframe.Cell;
import org.group1.back_end.response.skills.dataframe.DataFrame;
import org.group1.back_end.response.skills.dataframe.Rows;
import org.group1.back_end.utilities.strings.RegexUtilities;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.group1.back_end.utilities.strings.RegexUtilities.*;


public class SkillGenerator {

    private String originalQuestion = "";
    private String deafault;
    private Set<String> slotSet;
    private List<String> questions;
    private List<List<String>> SQL_Formating;
    private Set<String> vitalySlot;

    List<String> combinations = new ArrayList<>();

    private List<String> actions;
    SkillFormat format;
    boolean problem = false;
    String problemCause;


    SkillData skillData = new SkillData();

    public SkillGenerator(String text) throws Exception {
        this();
        processText(text);
        if(format.hasProblemFormat()){
            this.problem = true;
            this.problemCause = format.getProblem();
        }
    }

    public SkillGenerator(){
        format = new SkillFormat();
        this.slotSet = new HashSet<>();
        questions = new LinkedList<>();
        actions = new LinkedList<>();
        this.vitalySlot = new HashSet<>();
        this.SQL_Formating = new LinkedList<>();
    }


    public void processText(String text) throws Exception {
        processQuestion(text);
        processSlot(text);
        processAction(text);
        System.out.println(this.skillData);
        this.skillData.actions.display();
        System.out.println(this.skillData);
    }

    public void processQuestion(String text){
        originalQuestion = filterLineByRegex(text, "Question")
                .get(0)
                .replace("Question", "")
                .trim();

        System.out.println("Original Question: " + originalQuestion);
        this.skillData.setQuestion(originalQuestion);
    }


    private void insertSkillSlot(List<String> slotlist){
        List<String> temp = new ArrayList<>();
        for (String s : slotlist) {
            temp.add(s
                    .trim().
                    replaceAll("^(?!.*\\bSlot\\b).*$\n", ""));
        }


        //splitting the string to take a set of the placeholders
        List<String> placeholders = new ArrayList<>();
        Pattern pattern = Pattern.compile("<(.*?)>");
        for (String str : slotlist) {
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                placeholders.add(matcher.group());
            }
        }
        Set<String> tableHeads = new HashSet<>(placeholders);

        //Creating individual dataframes form the sets
        List<DataFrame> dataFrames = new ArrayList<>();
        for (String s : tableHeads) {
            DataFrame toadd = new DataFrame(Arrays.asList(s));

            for(String slots : slotlist){
                if(slots.contains(s)){
                    toadd.insertCell(0, slots.replace("Slot", "")
                            .replaceAll("<.*?>", "")
                            .trim());
                }
            }

            dataFrames.add(toadd);
        }

        DataFrame slotDataFrame = DataFrame.mergeDataFrames(dataFrames);

        this.skillData.setSlots(slotDataFrame);
        System.out.println("Slot Dataframe: \n" + slotDataFrame);

    }

    public void processSlot(String text){


        // HERE I FILTER THE TEXT IN LINES I ONLY WANT THE LINES THAT START WITH SLOT
        List<String> slotList = filterLineByRegex(text, "Slot");
        List<String> comb = new ArrayList<>();

        System.out.println("Slot List: ");
        insertSkillSlot(slotList);

        for (String s : slotList) {
            comb.add(s
                    .replaceAll(" ", "").
                    replaceAll("Slot", ""));
        }
        vitalySlot.addAll(comb);
        comb = RegexUtilities.concatenate(
                        comb
                        ,RegexUtilities.countRegexOccurrences(
                                        originalQuestion
                                        ,"<.*?>"));

        for (String slot : comb){

            String[] variables = slot.split(" ");

            String a = originalQuestion;
            for (int i = 0; i < variables.length; i++) {
                String temp = getOriginalFormatFromRegex(variables[i], "<.*?>");
                if(temp == null) temp = "";
                variables[i] = variables[i].replace(temp, "");
                a = a.replace(temp, variables[i]);
            }
            combinations.add(a);
        }

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
        System.out.println("Slot Set: " + slotSet);

    }

    public void processAction(String text) throws Exception {

        int lineNumber = -1;

        List<String> actionList = filterLineByRegex(text, "Action");

        System.out.println("Action List: " + actionList);
        insertSkillAction(actionList);

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
            System.out.println("Line: " + line);
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

                System.out.println("Newline: " + newLine);
                line = newLine;
                data[j][1] = variable;
                data[j][0] = slot;
            }


            makeDataFrame(data, line);
            vitalyMethod(data,line);
        }


        format.setProblem_action(false);


    }


    private void insertSkillAction(List<String> actionList){

        DataFrame shitstickape = this.skillData.slots;
        List<String> filters = new ArrayList<>();
        for(Rows r : shitstickape.getData()){
            int count = 0;
            for(Cell s : r.getCells()){
                if(s == null) {
                    count++;
                    continue;
                }
                filters.add(this.skillData.slots.getColumnNames().get(count) + " " + s);
                count++;
            }
        }
        System.out.println("Filters: " + filters);

        for(String action: new ArrayList<>(actionList)){
            String sentence = action.replace("Action", "").trim();
            System.out.println("Sentence: " + sentence);
            List<String> matchedRegexes = new ArrayList<>();
            for (String regex : filters) {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(sentence);
                if (matcher.find()) {
                    String matchedRegex = matcher.group();
                    matchedRegexes.add(matchedRegex);
                    sentence = sentence.replace(matchedRegex, "").trim();
                }
            }
            System.out.println("Matched regex: " + matchedRegexes);
            System.out.println("Remaining text: " + sentence);
            this.skillData.insertAction(matchedRegexes, sentence);

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


        question = question.replaceAll("<.*?>", "").trim();
        questions.add(question);
        actions.add(action);
    }

    public void vitalyMethod(String[][] data, String action) {

        String question = originalQuestion;

        List<String> solution = new ArrayList<>();

        for (int i = 0; i < data.length; i++) {
            solution.add(data[i][1]);
            solution.add(data[i][0]);
        }
        solution.add(action);
        SQL_Formating.add(solution);
    }

    /** GETTERS */

    public List<List<String>> getSQL_Formating() {
        return SQL_Formating;
    }

    public Set<String> getVitalySlot(){
        return this.vitalySlot;
    }

    public String getDeafault() {
        return deafault;
    }

    public String getDeafaultKey() {
        return Pattern.compile("<.*?>").matcher(originalQuestion).replaceAll("").trim();
    }

    public List<String> getQuestions() {
        return questions;
    }

    public String getOriginalQuestion(){
        return originalQuestion;
    }

    public List<String> getActions() {
        return actions;
    }

}

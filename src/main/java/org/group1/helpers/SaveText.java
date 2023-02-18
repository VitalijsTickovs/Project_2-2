package org.group1.helpers;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.group1.reponse.skills.Action;
import org.group1.reponse.skills.Question;
import org.group1.reponse.skills.Skill;

import java.io.*;
import java.util.ArrayList;


public class SaveText {
    private final static String DIR = "/src/main/resources/skills";
    private ArrayList<String> skills;

    private static int skillNum;

    private int placeHolderEnd = 0;

    public SaveText(){
        skills = loadSkills();
        skillNum = skills.size()+1;
    }

    private ArrayList<String> loadSkills(){
        ArrayList<String> skills = new ArrayList<>();
        File file = new File(DIR);
        File[] skillFiles = file.listFiles();
        for(File skillFile: skillFiles){
            skills.add(skillFile.getName());
        }
        return skills;
    }

    /**
     * Method to save data/skill in to text
     * @param text data
     */
    public static void addSkill(String... text){
        try {
            String filePath = DIR +"/"+ skillNum +".txt";
            skillNum++;
            FileWriter fileWriter = new FileWriter(new File(filePath));
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(String str: text) {
                bufferedWriter.write(str);
                bufferedWriter.newLine();
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void saveCSV(String fileName, String... data){
        String path = DIR + fileName + ".csv";
        File file = new File(path);
        try {
            FileWriter writer = new FileWriter(file);
            CSVWriter csvWriter = new CSVWriter(writer);


        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Loads saved data/skill into string array
     * finding for the specific question
     * @return data read from the txt file
     */
    public Skill loadSkill(String question){
        Question question1 = new Question(question);
        try{
            Skill loadedSkill = null;
            for(String fileName: skills) {
                boolean isMatching = false;
                loadedSkill = new Skill(question1);
                //Load the file
                //To be changed to load the skills from the database
                BufferedReader bf = new BufferedReader(new FileReader(DIR + "/" + fileName));
                String line;
                ArrayList<ArrayList<String>> loadedSlots = new ArrayList<>();
                String slotName="";
                // Action init
                Action action = new Action();
                while ((line = bf.readLine()) != null) {
                    //Check the first line if the question is exactly matched with the skill question
                    if (!isMatching) {
                        //Matching the question
                        boolean isRightQuestion = loadedSkill.isMatch(line);
                        if (isRightQuestion) {
                            isMatching = true;
                        } else {
                            break;
                        }
                    }
                    //Slots
                    if(line.contains("Slot")){
                        String placeHolder = getPlaceHolder(line);
                        if(!slotName.equals(placeHolder)){
                            loadedSlots.add(new ArrayList<>());
                            slotName = placeHolder;
                        }
                    }
                    //Action
                    action.add(line);
                    //TODO: add the action with the slots to the rule

                    bf.close();
                }
                if (isMatching) {
                    return loadedSkill;
                }
            }
            return null;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getSlot(String line){
        return line.substring(this.placeHolderEnd+1);
    }

    /**
     * Retrieves slot placeholder name
     * @param slotLine
     * @return
     */
    private String getPlaceHolder(String slotLine){
        String placeHolder ="";
        boolean foundPlaceHolder = false;
        for(int i=0; i<slotLine.length(); i++){
            placeHolder = placeHolder + slotLine.charAt(i);
            if(slotLine.charAt(i) == '<'){
                    foundPlaceHolder = true;
                }else if(slotLine.charAt(i)=='>'){
                    this.placeHolderEnd = i;
                    return placeHolder;
                }

        }
        return null;
    }

    public static void main(String[] args) {
        SaveText sv = new SaveText();
        System.out.println(sv.skills);
        Skill skill = sv.loadSkill("Question Which lectures are there on Monday at 9");
        System.out.println(skill.slot);
    }
}

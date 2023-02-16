package org.group1.helpers;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.amulvizk.service.skills.Action;
import org.amulvizk.service.skills.Question;
import org.amulvizk.service.skills.Skill;
import org.amulvizk.service.skills.Slot;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class SaveText {
    private final static String DIR = "src/main/resources/skills";
    private ArrayList<String> skills;

    private static int skillNum;

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
            for(String fileName: skills){
                boolean isMatching = false;
                //Load the file
                //To be changed to load the skills from the database
                BufferedReader bf = new BufferedReader(new FileReader(DIR+"/"+fileName));
                CSVReader reader = new CSVReader(bf);
                String[] line;
                ArrayList<ArrayList<String>> loadedSlots = new ArrayList<>();
                // Action init
                Action action = new Action();
                while ((line = reader.readNext()) != null) {
                    //Check the first line if the question is exactly matched with the skill question
                    if (!isMatching){
                        //Matching the question
                        //TODO: process the question
                        boolean isRightQuestion = line[0].equals(question);
                        System.out.println(isRightQuestion);
                        if(isRightQuestion){
                            isMatching = true;
                            //Slot init
                            int slotNum = line.length-2;
                            for(int i=0; i< slotNum; i++){
                                loadedSlots.add(new ArrayList<>());
                            }
                        }else{
                            break;
                        }
                    }
                    if(isMatching) {
                        //Slots
                        for (int i = 1; i < line.length - 1; i++) {
                            loadedSlots.get(i).add(line[i]);
                        }
                        //Action
                        action.add(line[line.length - 1]);
                    }
                    //TODO: add the action with the slots to the rule

                    bf.close();

                }
                if(isMatching) {
                    loadedSkill = new Skill(question1, loadedSlots.toArray(new Slot[0]), action);
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
        }
    }

    public static void main(String[] args) {
        SaveText sv = new SaveText();
        System.out.println(sv.skills);
        sv.loadSkill("Question Which lectures are there on <DAY> at <TIME>");
    }
}

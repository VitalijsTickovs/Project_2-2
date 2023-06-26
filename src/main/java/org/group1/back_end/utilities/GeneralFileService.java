package org.group1.back_end.utilities;

import org.group1.back_end.response.skills.SkillData;
import org.group1.back_end.response.skills.dataframe.Cell;
import org.group1.back_end.response.skills.dataframe.DataFrame;
import org.group1.back_end.response.skills.dataframe.Rows;
import org.group1.back_end.utilities.strings.RegexUtilities;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.group1.back_end.utilities.enums.Paths.DATA_TXT_CFG_SKILLS;
import static org.group1.back_end.utilities.enums.Paths.DATA_TXT_SKILLS;

public class GeneralFileService {
    private static String path;
    private static final String FILES_NAMES = "Rule_";
    private static final  String FILES_EXTENSION = ".txt";

    private static int size = 4;

    static{
        try {
            path = new File(".").getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(String text){
        String id = Integer.toString(new File((path + DATA_TXT_SKILLS.path)).listFiles().length+1);
        String filePath = path + DATA_TXT_SKILLS.path + FILES_NAMES + id + FILES_EXTENSION;

        try{
            File file = new File(filePath);

            FileWriter writer = new FileWriter(file);

            writer.append(text);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCFG(String text){
        System.out.println("Path: "+ path+ DATA_TXT_CFG_SKILLS.path);
        String id = Integer.toString(new File((path +DATA_TXT_CFG_SKILLS.path)).listFiles().length+1);
        String filePath = path + DATA_TXT_CFG_SKILLS.path + "cfg_" + id + FILES_EXTENSION;

        try{
            File file = new File(filePath);

            FileWriter writer = new FileWriter(file);

            writer.append(text);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void overWrite(String id, String text){
        String filePath = path + DATA_TXT_SKILLS.path + FILES_NAMES + id + FILES_EXTENSION;

        String line="";
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            line = reader.readLine();

            File file = new File(filePath);
            FileWriter writer = new FileWriter(file);
            writer.append(line+"\n"+text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void overWriteCFG(List<String> newActions, List<String> modifiedRules, int id){
        String filePath = path + DATA_TXT_CFG_SKILLS.path + "cfg_" + id + FILES_EXTENSION;
        try {
            // Read the contents of the text file
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();

            // Remove existing "Action" lines
            List<String> modifiedLines = new ArrayList<>();
            for (String currentLine : lines) {
                if (!currentLine.startsWith("Action") && RegexUtilities.countRegexOccurrences(currentLine, "<.*?>" )>1) {
                    modifiedLines.add(currentLine);
                }
            }
            modifiedLines.addAll(modifiedRules);
            // Adding new actions to the cfg
            modifiedLines.addAll(newActions);
            modifiedLines.add("Action I have no idea");
            // Write the modified contents back to the text file
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (String modifiedLine : modifiedLines) {
                writer.write(modifiedLine);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void overWrite(SkillData skillData){
        String text ="";
        String question = skillData.getQuestion();
        //Converting DataFrame into text
        //Slots
        //Getting slot DataFrame
        DataFrame slots = skillData.getSlots();
        List<String> slotColumnName = slots.getColumnNames();
        //Going through each column
        for(int i=0; i<slotColumnName.size();i++) {
            DataFrame slotColumn = slots.getColumn(i);
            //Going through each row of the column
            for (int j=0; j<slotColumn.size(); j++) {
                Rows slotRow = slotColumn.get(j);
                Cell value = slotRow.get(0);
                if(value.getValue() !=null)
                    if(!value.getValue().toString().equals(" "))
                        text += "Slot " + slotColumnName.get(i) + " " + slotRow.get(0)+"\n";

            }
        }


        //Action
        //Getting Action dataframe
        DataFrame actions = skillData.getActions();
        System.out.println(actions);
        //Getting placeholders of actions
        List<String> actionColumnNames = actions.getColumnNames();
        //For each row in action
        for(int i=0; i<actions.size(); i++) {
            Rows actionRow = actions.get(i);
            text += "Action ";
            //For each cell of action
            for (int j=0; j<actionRow.size(); j++) {
                Cell cell = actionRow.get(j);
                if(cell.getValue()!=null) {
                    if (cell.getValue().equals("I have no idea")) {
                        text += "I have no idea\n";
                    } else {
                        if (!actionColumnNames.get(j).contains("Action") && !cell.getValue().equals("")) {
                            text += actionColumnNames.get(j) + " " + actionRow.get(j) + " ";
                        }else {
                            text += actionRow.get(j) + "\n";
                        }
                    }
                }
            }
        }

        //Searching for the right question

        System.out.println("Text: " + text);
        for(int i=1; i<size+1; i++) {
            String line = "";
            try {
                String filePath = path + DATA_TXT_SKILLS.path + FILES_NAMES + i + FILES_EXTENSION;
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                line = reader.readLine();
                if(line.contains(question)) {
                    File file = new File(filePath);
                    FileWriter writer = new FileWriter(file);
                    writer.append(line + "\n" + text);
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static int getSize() {
        return size;
    }

    public static void setSize(int size1){
        size = size1;
    }
}

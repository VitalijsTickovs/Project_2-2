package org.group1.back_end.utilities;

import org.group1.back_end.response.skills.SkillData;
import org.group1.back_end.response.skills.dataframe.Cell;
import org.group1.back_end.response.skills.dataframe.DataFrame;
import org.group1.back_end.response.skills.dataframe.Rows;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.List;

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

    public static void overWrite(SkillData skillData){
        String text ="";
        String question = skillData.getQuestion();
//        System.out.println("Question: "+ question);
        //Converting DataFrame into text
        //Slots
        DataFrame slots = skillData.getSlots();
        List<String> slotColumnName = slots.getColumnNames();
        for(int i=0; i<slotColumnName.size();i++) {
            DataFrame slotColumn = slots.getColumn(i);

            for (int j=0; j<slotColumn.size(); j++) {
                Rows slotRow = slotColumn.get(j);
                Cell value = slotRow.get(0);
                if(value.getValue() !=null)
                    if(!value.getValue().toString().equals(" "))
                        text += "Slot " + slotColumnName.get(i) + " " + slotRow.get(0)+"\n";

            }
        }


        //Action
        DataFrame actions = skillData.getActions();
        List<String> actionColumnNames = actions.getColumnNames();
//        System.out.println(actionColumnNames);
        for(int i=0; i<actions.size(); i++) {
            Rows actionRow = actions.get(i);
            text += "Action ";
            for (int j=0; j<actionRow.size(); j++) {
                if(actionRow.get(j).getValue()!=null) {
                    if (actionRow.get(j).getValue().equals("I have no idea")) {
                        text += "I have no idea\n";
                    } else {
                        if (!actionColumnNames.get(j).contains("Action"))
                            text += actionColumnNames.get(j) + " " + actionRow.get(j) + " ";
                        else {
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

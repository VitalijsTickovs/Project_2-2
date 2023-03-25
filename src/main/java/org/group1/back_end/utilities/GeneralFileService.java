package org.group1.back_end.utilities;

import java.io.*;

import static org.group1.back_end.utilities.enums.Paths.DATA_TXT_SKILLS;

public class GeneralFileService {
    private static String path;
    private static final String FILES_NAMES = "Rule_";
    private static final  String FILES_EXTENSION = ".txt";

    static{
        try {
            path = new File(".").getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(String text){
        String id = Integer.toString(new File((path + DATA_TXT_SKILLS.path)).listFiles().length);
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
}

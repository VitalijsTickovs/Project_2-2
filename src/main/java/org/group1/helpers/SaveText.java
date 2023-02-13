package org.group1.helpers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveText {
    private final static String DIR = "src/main/resources/save/";

    /**
     * Method to save data/skill in to text
     * @param fileName skill name
     * @param text data
     */
    public static void save(String fileName, String... text){
        try {
            String filePath = DIR + fileName +".txt";

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

    /**
     * Loads saved data/skill into string array
     * @param fileName skill name
     * @return data read from the txt file
     */
    public static String[] load(String fileName){
        try{
            List<String> listOfStrings = new ArrayList<String>();
            BufferedReader bf = new BufferedReader(new FileReader(fileName+".txt"));
            String line = bf.readLine();
            while (line != null) {
                listOfStrings.add(line);
                line = bf.readLine();
            }
            bf.close();

            String[] data = listOfStrings.toArray(new String[0]);
            return data;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

    }
}

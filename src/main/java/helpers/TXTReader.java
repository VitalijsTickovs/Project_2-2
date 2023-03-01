package org.group1.helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TXTReader {
    static BufferedReader bufferedReader;
    static FileReader fileReader;

    /**
     * Method to read a file
     * @param path, the path of the file
     * @return a list of strings, where each string represents an individual line in the txt file
     */
    public static List<String> read(String path) throws Exception {
        List<String> list = new ArrayList<>();
        if(bufferedReader == null || fileReader == null){
            setupReaders(path);
        }
        try{
            String line = bufferedReader.readLine();
            while(line != null){
                list.add(line);
                line = bufferedReader.readLine();
            }
        }catch (Exception e){
            throw new Exception("Error reading the file");
        }
        return list;
    }

    /**
     * Method to initialise the readers
     * @param path, the path of the file
     */
    private static void setupReaders(String path) throws Exception {
        try{
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}

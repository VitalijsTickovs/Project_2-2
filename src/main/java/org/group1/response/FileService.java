package org.group1.response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.group1.enums.Paths.DATA_TXT_SKILLS;


public class FileService {

    private static int id = 0;
    private static String path;
    private final String FILES_NAMES = "Rule_";
    private final String FILES_EXTENSION = ".txt";

    public FileService() throws IOException {
        path = new File(".").getCanonicalPath();
    }

    public List<String> readAll() throws IOException {

        String[] files = getFilesPath();

        List<String> allFiles = new ArrayList<>();

        for (String file : files) allFiles.add(read(file));

        return allFiles;
    }

    public String read(int n) throws FileNotFoundException {

        StringBuilder content = new StringBuilder();

        Scanner sc = new Scanner(
                new File(
                        path + DATA_TXT_SKILLS.path + FILES_NAMES + n + FILES_EXTENSION
                ));


        while (sc.hasNextLine()) content.append(sc.nextLine()).append("\n");

        return content.toString();
    }

    /**
     * Method for reading .txt files with a given path
     * @param path The path of the .txt file that we want to read.
     * @return all the content line by line of the .txt file
     * @throws FileNotFoundException If the file does not exist.
     */
    public String read(String path) throws FileNotFoundException {

        StringBuilder content = new StringBuilder();

        Scanner sc = new Scanner(new File(path));

        while (sc.hasNextLine()) content.append(sc.nextLine()).append("\n");

        return content.toString();
    }

    /**
     * IS VERY IMPORTANT TO NOT USE THIS METHOD OUTSIDE THE CLASS
     * PLEASE DO NOT CHANGE THE ACCESS MODIFIER.
     * Something like an ID creator of a database but super hardcoded
     * @return the next path name of the rule
     */
    private String getNextName() throws IOException{

        String[] files = getFilesPath();
        System.out.println("File count: " + files.length);

        if (id == 0){
            id = files.length;
        }


        System.out.println("epic: " + path + DATA_TXT_SKILLS.path + FILES_NAMES + id + FILES_EXTENSION);
        return  path + DATA_TXT_SKILLS.path + FILES_NAMES + (++id) + FILES_EXTENSION;
    }

    /**
     * Method to get tha path of the files of the directory.
     * @return Arrary of string with the path names.
     */
    public String[] getFilesPath() throws IOException{

        File[] files = getFiles();

        String[] paths = new String[files.length];

        for (int i = 0; i < files.length; i++) paths[i] = files[i].getCanonicalPath() ;

        return paths;
    }

    /**
     * ESTO ES UNA PUTA LOCURA
     * @return
     */
    public static File[] getFiles(){

        return new File((path + DATA_TXT_SKILLS.path)).listFiles(); }


    /**
     * BE CAREFUL WITH THIS METHOD BECAUSE THE FILE MANAGER GOES IN THE OPPOSITE DIRECTION
     *
     * @param start
     */
    public boolean deleteFiles(int start){ return getFiles()[start].delete(); }


    /**
     * This writes to a new available 'rule_n' file.
     * @param text
     * @throws IOException
     */
    public void write(String text) throws IOException {

        File file = new File(getNextName());

        FileWriter writer = new FileWriter(file);

        writer.append(text);

        writer.close();

    }

    public int getId() {
        return id;
    }
}

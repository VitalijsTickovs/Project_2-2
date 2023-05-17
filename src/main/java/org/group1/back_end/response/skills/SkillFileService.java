package org.group1.back_end.response.skills;

import org.group1.back_end.utilities.enums.Paths;

import java.io.*;
import java.util.*;

import static org.group1.back_end.utilities.enums.Paths.DATA_TXT_CFG_SKILLS;
import static org.group1.back_end.utilities.enums.Paths.DATA_TXT_SKILLS;

public class SkillFileService {

    private static int id = 0;
    private static int size;
    private static String path;
    private final String FILES_NAMES = "Rule_";
    private final String FILES_EXTENSION = ".txt";

    public SkillFileService() throws IOException {
        path = new File(".").getCanonicalPath();
    }

    public List<String> readAll() throws IOException {

        String[] files = getFilesPath();

        List<String> allFiles = new ArrayList<>();

        for (String file : files) {
            if(file!=null) allFiles.add(read(file));
        }

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

    public List<String> readCFG() throws IOException {
        File[] files = new File((path + DATA_TXT_CFG_SKILLS.path)).listFiles();

        String[] paths = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            String path = files[i].getCanonicalPath();
            if(!path.contains("DS_Store"))
                paths[i] = path;
        }

        List<String> allFiles = new ArrayList<>();

        for (String file : paths) {
            if(file!=null) allFiles.add(read(file));
        }

        return allFiles;
    }

    public String read(Paths p) throws FileNotFoundException {

        StringBuilder content = new StringBuilder();

        Scanner sc = new Scanner(new File(path + p.path));

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

        if (id == 0){
            id = Integer.parseInt(files[0].substring(
                    files[files.length - 1].length() - (FILES_EXTENSION.length() + (files.length + "").length()),
                    files[files.length - 1].length() - FILES_EXTENSION.length())
            );
        }

        return  path + DATA_TXT_SKILLS.path + FILES_NAMES + (++id) + FILES_EXTENSION;
    }

    /**
     * Method to get tha path of the files of the directory.
     * @return Arrary of string with the path names.
     */
    public String[] getFilesPath() throws IOException{

        File[] files = getFiles();

        String[] paths = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            String path = files[i].getCanonicalPath();
            if(!path.contains("DS_Store"))
                paths[i] = path;
        }

        return paths;
    }

    /**
     * ESTO ES UNA PUTA LOCURA
     * @return
     */
    public File[] getFiles(){

        return new File((path + DATA_TXT_SKILLS.path)).listFiles(); }


    /**
     * BE CAREFUL WITH THIS METHOD BECAUSE THE FILE MANAGER GOES IN THE OPPOSITE DIRECTION
     *
     * @param start
     */
    public boolean deleteFiles(int start){ return getFiles()[start].delete(); }

    public void edit(String text, String ruleName) throws IOException {
        write(text, ruleName);
    }
    public void add(String text) throws IOException {
        write(text);
    }
    public void add(String text, String ruleName) throws IOException {
        write(text, ruleName);
    }
    public void write(String text, String ruleName) throws IOException {

        String number = ruleName.replace(FILES_NAMES, "");
        number = number.replace(FILES_EXTENSION, "");
        int n = Integer.parseInt(number);

        File file = new File(path + DATA_TXT_SKILLS.path + FILES_NAMES + n + FILES_EXTENSION);

        FileWriter writer = new FileWriter(file);

        writer.append(text);

        writer.close();

    }

    public void write(String text, Paths p) throws IOException {

        File file = new File(path + p.path);

        FileWriter writer = new FileWriter(file);

        writer.append(text);

        writer.close();

    }

    /**
     * will write the rule thet is actual text
     * @param text
     * @throws IOException
     */
    public void write(String text) throws IOException {


        File file = new File(getNextName());

        FileWriter writer = new FileWriter(file);

        writer.append(text);

        writer.close();

    }

    public static int getSize() {
        return size;
    }
}

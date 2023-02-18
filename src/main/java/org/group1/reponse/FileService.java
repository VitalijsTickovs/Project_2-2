package org.group1.reponse;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class FileService {

    private static int id = 0;
    private static String path;

    private final String DIR_TXT = "/src/main/java/org/group1/reponse/data";
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
                        path + DIR_TXT + FILES_NAMES + n + FILES_EXTENSION
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

        if (id == 0){
            id = Integer.parseInt(files[0].substring(
                    files[files.length - 1].length() - (FILES_EXTENSION.length() + (files.length + "").length()),
                    files[files.length - 1].length() - FILES_EXTENSION.length())
            );
        }

        return  path + DIR_TXT + FILES_NAMES + (++id) + FILES_EXTENSION;
    }

    /**
     * Method to get tha path of the files of the directory.
     * @return Arrary of string with the path names.
     */
    public String[] getFilesPath() throws IOException{

        File[] files = getFiles();

        String[] paths = new String[files.length];

        for (int i = 0; i < files.length; i++) paths[i] = files[i].getCanonicalPath();

        return paths;
    }

    /**
     *
     * @return
     */
    public File[] getFiles(){ return new File(path + DIR_TXT).listFiles(); }


    /**
     * BE CAREFUL WITH THIS METHOD BECAUSE THE FILE MANAGER GOES IN THE OPPOSITE DIRECTION
     *
     * @param start
     */
    public boolean deleteFiles(int start){ return getFiles()[start].delete(); }


    /**
     * THIS METHOD SHOULD NOT BE HERE IS NOT WRONG BUT NEEDS ANOTHER CLASS
     * @param text
     * @return
     * @throws FileNotFoundException
     */
    public static List<String> getTextByLine(String text) throws FileNotFoundException {

        List<String> content = new ArrayList<>();

        Scanner sc = new Scanner(new File(path));

        while (sc.hasNextLine()) content.add(sc.nextLine() + "\n");

        return content;
    }

    public void write(String text) throws IOException {

        File archivo = new File(getNextName());

        FileWriter writer = new FileWriter(archivo);

        writer.append(text);

        writer.close();

    }
}

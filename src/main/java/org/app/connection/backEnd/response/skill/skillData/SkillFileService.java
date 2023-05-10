package org.app.connection.backEnd.response.skill.skillData;

import java.io.*;
import java.util.*;

import org.app.connection.backEnd.response.skill.Skill;
import org.app.connection.utils.reader.Paths;

public class SkillFileService {

    private static int id = 0;
    private FilesNaming filesNaming;

    public SkillFileService() {
        filesNaming = FilesNaming.TEMPLATE;
    }

    public void setFilesName(FilesNaming naming) {
        filesNaming = naming;
    }


    public List<String> readAll() throws Exception {

        String[] files = getFilesPath();

        List<String> allFiles = new ArrayList<>();

        for (String file : files) {
            if(file!=null) allFiles.add(read(file));
        }
        return allFiles;
    }

    public static void main(String[] args) {
        SkillFileService fs = new SkillFileService();
        try {
            fs.readAll();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * IS VERY IMPORTANT TO NOT USE THIS METHOD OUTSIDE THE CLASS
     * PLEASE DO NOT CHANGE THE ACCESS MODIFIER.
     * Something like an ID creator of a database but super hardcoded
     * @return the next path name of the rule
     */
    private String getNextName() throws Exception{

        String[] files = getFilesPath();

        if (id == 0){
            id = Integer.parseInt(files[0].substring(
                    files[files.length - 1].length() - (filesNaming.extension.length() + (files.length + "").length()),
                    files[files.length - 1].length() - filesNaming.extension.length())
            );
        }

        return  Paths.getPath(filesNaming.name + (++id) + filesNaming.extension);
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

    private File[] getFiles() throws IOException {
        return new File(new File(".").getCanonicalPath() + filesNaming.directory).listFiles(); }

    /**
     * Method to get tha path of the files of the directory.
     * @return Arrary of string with the path names.
     */
    private String[] getFilesPath() throws Exception{

        File[] files = getFiles();

        String[] paths = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            String path = files[i].getCanonicalPath();
            if(!path.contains("DS_Store"))
                paths[i] = path;
        }

        return paths;
    }

    public void write(String text, String ruleName) throws Exception {
        String number = ruleName.replace(filesNaming.name, "");
        number = number.replace(filesNaming.extension, "");
        int n = Integer.parseInt(number);
        File file = new File(Paths.getPath(filesNaming.name) + n + filesNaming.extension);
        FileWriter writer = new FileWriter(file);
        writer.append(text);
        writer.close();
    }
}

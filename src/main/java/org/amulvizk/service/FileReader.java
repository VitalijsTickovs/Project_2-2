package org.amulvizk.service;

import org.amulvizk.service.skills.Skill;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    List<Skill> skills;
    FileService service;

    public FileReader() throws IOException {
        service = new org.amulvizk.service.FileService();
        skills = new ArrayList<>();
        generateSkills();
    }

    public void generateSkills() throws IOException {
        List<String> text = service.readAll();

        text.forEach((t) -> {
            try {
                skills.add(new Skill(t));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws Exception {

        FileReader file = new FileReader();
        System.out.println(file.skills.get(0).getAnswer("What lecture do we have on monday at 11"));
    }
}

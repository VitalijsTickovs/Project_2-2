package org.group1.response;

import org.group1.response.skills.Skill;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SkillGenerator {

    List<Skill> skills;
    FileService service;

    /**
     * Build a file service and list for skills.
     * This also generates the skills.
     * @throws IOException
     */
    public SkillGenerator() throws IOException {
        service = new FileService();
        skills = new ArrayList<>();
        generateSkills();
    }

    /**
     * Method that actually generates from skills.
     * Reads all files from directory and creates skills.
     * @throws IOException
     */
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
    /**
     * Retrieve the skills
     * @return
     */
    public List<Skill> getSkills(){
        return this.skills;
    }

}

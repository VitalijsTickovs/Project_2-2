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


    // THIS METHOD HAS A CONNECTION TO SKILL
    // NEEDS SOME FORM OF INTERFACE, CABLE
    public String getAnswer(String question) throws Exception {
        for (Skill skill : skills) {
            if(skill.isMatch(question))
                return skill.getAnswer(question);
        }
        return null;
    }


    /**
     * Retrieve the skills
     * @return
     */
    public List<Skill> getSkills(){
        return this.skills;
    }

    //TODO: fix the problem with isMatch

    // TestingGround for Mr. Tom
    public static void main(String[] args) throws Exception {
        SkillGenerator file = new SkillGenerator();
        System.out.println(file.getAnswer("What lecture do we have on monday at 11?"));
        System.out.println(file.getAnswer("On monday at 9 what lecture do we have?"));
        System.out.println(file.getAnswer("What lecture do we have on monday at 9?"));
        System.out.println(file.getAnswer("What will the weather be like on tuesday?"));
        System.out.println("-------------------------------------------------");
        //some test cases from monday to sunday with different formulation
        System.out.println(file.getAnswer("What is the weather on monday?"));
        System.out.println(file.getAnswer("On tuesday what is the weather like?"));
        System.out.println(file.getAnswer("On wednesday what is the weather like?"));
        System.out.println(file.getAnswer("How is the weather on thursday?"));
        System.out.println(file.getAnswer("What is the weather on friday?"));
        System.out.println(file.getAnswer("What is the weather on saturday?"));
        System.out.println(file.getAnswer("What is the weather on sunday?"));

    }
}

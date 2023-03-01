package org.group1.response;

import org.group1.response.skills.Skill;

import java.io.IOException;
import java.util.List;

/**
 * Endpoint to ...
 */
public class Response {

    public Response() throws IOException {

        SkillGenerator sg = new SkillGenerator();
        List<Skill> skills = sg.getSkills();

        // where exactly implement distance



    }


    // self
    //  sort on distance
    //  need numerical value for matching


}

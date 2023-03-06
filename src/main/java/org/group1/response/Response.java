package org.group1.response;

import org.group1.response.matching.RuleMatcher;
import org.group1.response.matching.SkillMatcher;
import org.group1.response.matching.Support.iMatcher;
import org.group1.response.distances.Jaro;
import org.group1.response.distances.iDistance;
import org.group1.response.skills.Rule;
import org.group1.response.skills.Skill;

import java.io.IOException;
import java.util.List;

/**
 * Endpoint to ...
 */
public class Response {
    SkillGenerator sg;
    List<Skill> skills;
    iDistance distance;
    iMatcher<Skill> skillMatcher;
    iMatcher<Rule> ruleMatcher;
    static final String  noAnswerFound = "No answer found (note: this string should be the one from the end of each skill file)";

    public Response(iDistance distance) throws IOException {
       this.distance = distance;
       this.skillMatcher = new SkillMatcher(distance);
       this.ruleMatcher = new RuleMatcher(distance);
    }
    /**
     * Used to get the answer to a question
     * @param question, the question
     * @return The answer to the question
     * @throws Exception
     */
    public String getAnswer(String question) throws Exception{
        if(skills == null || sg == null) sg = new SkillGenerator(); skills = sg.getSkills();
        Skill match = skillMatcher.match(skills, question);
        if(match != null) {
                Rule rule = ruleMatcher.match(match.getRules(), question);
                if(rule != null) return validResponse(rule.action.toString());
        }
        return noAnswerFound;
    }

    /**
     * Checks if the response is valid
     * @param response, the response
     * @return the response if valid, else noAnswerFound
     */
    private static String validResponse(String response) {
        if(response == null) return noAnswerFound;
        if(response.equals("")) return noAnswerFound;
        return response;
    }

//we want to sort a collection of rules

    public static void main(String[] args) throws IOException{
        Response r = new Response(new Jaro());
        try {
            System.out.println(r.getAnswer("what lecture do we has on monday at 9?"));
            System.out.println(r.getAnswer("what lactur do we has on munday at 9?"));
            System.out.println(r.getAnswer("on minday what lcture do we have at 9?"));
            System.out.println(r.getAnswer("what weeter on munday?"));
            System.out.println(r.getAnswer("what lactur do we has on munday at 13?"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
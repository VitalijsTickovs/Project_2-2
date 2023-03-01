package org.group1.response;

import org.group1.processor.PreProcessor;
import org.group1.response.skills.Rule;
import org.group1.response.skills.Skill;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Endpoint to ...
 */
public class Response {
    SkillGenerator sg;
    List<Skill> skills;

    public Response() throws IOException {

        // where exactly implement distance
    }

    /**
     * Used to get the answer to a question
     * @param question, the question
     * @return The answer to the question
     * @throws Exception
     */
    public String getAnswer(String question) throws Exception{
        Predicate<String> isKeyWord;
        if(skills == null || sg == null) sg = new SkillGenerator(); skills = sg.getSkills();

        List<String> tocheck = PreProcessor.preprocess(question);

        for(Skill skill: this.skills) {
            isKeyWord = word -> skill.keywords.contains(word);
            if(!tocheck.stream().anyMatch(isKeyWord)) continue; //

            tocheck.removeIf(isKeyWord);
            for(Rule rule: skill.rule){
                Collections.sort(tocheck);
                Collections.sort(rule.pairs);
                boolean containsArray = tocheck.equals(rule.pairs);
                if(containsArray) return rule.action.toString();
            }
        }
        return "No answer found";
    }

    public static void main(String[] args) throws IOException{
        Response r = new Response();
        try {
            System.out.println(r.getAnswer("what lecture do we has on monday at 9?"));
            System.out.println(r.getAnswer("         monday     9  what    lectures"));
            System.out.println(r.getAnswer("what is the weather on monday?"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // self
    //  sort on distance
    //  need numerical value for matching


}

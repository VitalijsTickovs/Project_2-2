package org.group1.response.matching;
import org.group1.processor.PreProcessor;
import org.group1.response.matching.Support.aMatcher;
import org.group1.response.matching.Support.iMatcher;
import org.group1.response.distances.iDistance;
import org.group1.response.skills.Skill;

import java.util.List;

public class SkillMatcher extends aMatcher implements iMatcher<Skill> {

    public SkillMatcher(iDistance distance) {
       super(distance);
    }

    /**
     * Matches the question to a skill
     * @param skills, the list of skills to match
     * @param question, the question to match
     * @return, the skill that matches the question
     * @throws Exception
     */
    @Override
    public Skill match(List<Skill> skills, String question) throws Exception{
        List<String> preprocessedQuestion = PreProcessor.preprocess(question);
        for(Skill skill: skills) {
            if(isExactMatch(skill.keywords, preprocessedQuestion)) return skill;
            if(isSimilarMatch(skill.keywords, preprocessedQuestion)) return skill;
        }
        return null;
    }

}


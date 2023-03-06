package org.group1.response.matching;

import org.group1.processor.PreProcessor;
import org.group1.response.matching.Support.aMatcher;
import org.group1.response.matching.Support.iMatcher;
import org.group1.response.distances.iDistance;
import org.group1.response.skills.Rule;

import java.util.List;

public class RuleMatcher extends aMatcher implements iMatcher<Rule> {

    public RuleMatcher(iDistance distance) {
       super(distance);
    }

    /**
     * Matches the question to a skill
     * @param rules, the list of rules to match
     * @param question, the question to match
     * @return, the rule that matches the question
     * @throws Exception
     */
    @Override
    public Rule match(List<Rule> rules, String question) throws Exception{
        List<String> preprocessedQuestion = PreProcessor.preprocess(question);
        for(Rule rule: rules) {
            if(isExactMatch(rule.pairs, preprocessedQuestion)) return rule;
            if(isSimilarMatch(rule.pairs, preprocessedQuestion)) return rule;
        }
        return null;
    }


}

package org.group1.response.matching.Support;

import org.group1.response.distances.iDistance;

import java.util.List;

public class aMatcher {

    public static final double THRESHOLD = 0.75;
    iDistance distance;

    public aMatcher(iDistance distance) {
        this.distance = distance;
    }

    /**
     * Checks if the question is similar to the skill
     * @param keywords, the keywords to check
     * @param preprocessedQuestion, the question to check
     * @return, true if the question is similar to the skill
     */
    public boolean isSimilarMatch(List<String> keywords, List<String> preprocessedQuestion) {
        if(preprocessedQuestion == null) return false;
        DistanceSorter sorter;
        int count = 0;
        for (String word : keywords) {
            sorter = new DistanceSorter(word, distance);
            preprocessedQuestion.sort(sorter);
            if (distance.calculate(word, preprocessedQuestion.get(0)) >= THRESHOLD) count++;
        }
        return (count == keywords.size()); //todo: think about this as the count might be too high
    }

    /**
     * Checks if the question contains all the keywords of the skill
     * @param answers, the answers to check i.e. keywords
     * @param preprocessedQuestion, the question to check
     * @return, true if the question contains all the keywords of the skill
     */
    public static boolean isExactMatch(List<String> answers, List<String> preprocessedQuestion) {
        if(preprocessedQuestion == null) return false;
        return preprocessedQuestion.containsAll(answers);
    }
}

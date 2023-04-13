package org.group1.back_end.textprocessing;

import org.group1.back_end.response.skills.Skill;
import org.group1.back_end.utilities.algebra.Distances;
import org.nd4j.common.primitives.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SpellingCorrectProcess {

    public static String correct(String word){
        // might be error this ust be fixed
        List<Pair<String, Integer>> suggestions = new ArrayList<>();
        for(String vocab : Skill.VOCABULARY){
            suggestions.add(new Pair<>(vocab, Distances.levenshtein(word, vocab))); //this should be a list of pairs
        }
        Collections.sort(suggestions, new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
                return o1.getSecond() - o2.getSecond();
            }
        });
        return suggestions.get(0).getFirst();
    }

}

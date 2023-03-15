package org.group1.back_end.ML.model_vectorization;

import org.group1.back_end.response.skills.Skill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Vector {

    public static void main(String[] args) {

    }
    public static double[] setToVector(Set<String> keys){
        List<String> vocabulary = new ArrayList<>(Skill.VOCABULARY);
        double[] vector = new double[Skill.VOCABULARY.size()];

        for (int i = 0; i < vocabulary.size(); i++) {
            if(keys.contains(vocabulary.get(i))){
                vector[i] = 1;
            }
        }
        return vector;
    }
}

package org.group1.back_end.ML.model_vectorization;

import org.group1.back_end.response.skills.Skill;
import java.util.HashMap;
import java.util.List;

public class VectorSIF {

    private final HashMap<String, Integer> frecuency;
    private final double a = 0.00001;

    public VectorSIF() {

        frecuency = new HashMap<>();

    }

    public double[] getVector(List<String> sentence){
        double[] vector = new double[Skill.VOCABULARY.size()];
        int wordPosition = 0;
        for(String word : Skill.VOCABULARY){
            if (sentence.contains(word)){
                vector[wordPosition] = calculateWeight(word);
            }
            wordPosition++;
        }

        return (vector == null)? new double[Skill.VOCABULARY.size()] : vector;
    }

    public double calculateWeight(String word){
        if(frecuency.get(word) == null)
            return a;

        return a / (a + frecuency.get(word));
    }

    public void createFrequencyTable(){

        for (String word : Skill.VOCABULARY) {
            if(frecuency.get(word) == null){
                frecuency.put(word, 0);
            }else{
                Integer update = frecuency.get(word);
                frecuency.put(word, update);
            }
        }

        for (List<String> sentence : Skill.COMPLETE_QUERY){
            for (String word : sentence) {
                Integer update = frecuency.get(word);
                frecuency.put(word, ++update);
            }
        }
    }
}

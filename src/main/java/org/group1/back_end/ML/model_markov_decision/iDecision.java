package org.group1.back_end.ML.model_markov_decision;

import java.io.FileNotFoundException;

public interface iDecision {

    String getNext(String sequence);
    void train();
    void retrain();

    void load();
    void save() throws FileNotFoundException;
}

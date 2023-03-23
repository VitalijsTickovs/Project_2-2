package org.group1.back_end.ML.model_spelling_correction;

import java.util.List;

public class CorrectionPair {

    String original;
    List<String> corrections;

    public CorrectionPair(String original, List<String> corrections){
        this.original = original;
        this.corrections = corrections;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public List<String> getCorrections() {
        return corrections;
    }

    public void setCorrections(List<String> corrections) {
        this.corrections = corrections;
    }

}

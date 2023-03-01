package org.group1.reponse.procesor;

import org.group1.linguistics.Delim;

import java.util.ArrayList;
import java.util.List;

public class PreProcessor {

    /**
     * This method is used to preprocess the text
     * @param text The text input.
     * @return The output is a list of words or set of words
     */
    public static List<String> preprocess(String text) throws Exception{
        List<String> validEntries = new ArrayList<>();
        for(String word : Stemming.extract(StopWordRemover.eliminate(Normalization.normalize(Tokenization.tokenize(text, Delim.SPACE))))){
            if(word.length() >= 1 && word != Delim.SPACE.toString()) validEntries.add(word);
        }
        return validEntries;
    }
}

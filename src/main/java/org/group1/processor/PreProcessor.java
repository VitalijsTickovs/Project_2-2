package org.group1.processor;

import org.group1.linguistics.Delim;
import org.group1.textprocessing.Normalization;
import org.group1.textprocessing.Stemming;
import org.group1.textprocessing.StopWordRemover;
import org.group1.textprocessing.Tokenization;

import java.util.ArrayList;
import java.util.List;

public class PreProcessor {

    /**
     * This method is used to preprocess the text
     * It goes through the pipeline of processing steps
     * It's the main access point to gather processed text
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

package org.group1.textprocessing;

import org.group1.exception.NullTextException;
import org.group1.helpers.TXTReader;

import java.util.List;
import java.util.function.Predicate;

public class StopWordRemover {

    /**
     * Stop word database: https://www.kaggle.com/datasets/heeraldedhia/stop-words-in-28-languages?select=english.txt
     */
    private static String stopwordPath = "src/main/resources/linguistics/StopWords.txt";
    private static List<String> stopwords;
    private static Predicate<String> isStopWord;

    /**
     * This method is used for the elimination of the words.
     * @param tokenizedText Tokenized words.
     * @return List of words on which the elimination process is performed.
     */
    public static List<String> eliminate(List<String> tokenizedText) throws Exception {
        if(tokenizedText == null) throw new NullTextException("Cannot eliminate a null text");
        return removeStopWords(tokenizedText);
    }

    /**
     * This method is used for the elimination of the stop words.
     * @param tokenizedText Tokenized words.
     * @return List of words on which the stop words are removed.
     */
    private static List<String> removeStopWords(List<String> tokenizedText) throws Exception {
        if(tokenizedText == null) throw new NullTextException("Cannot eliminate a null text");
        if(stopwords == null) stopwords = TXTReader.read(stopwordPath);
        if(isStopWord == null) isStopWord = word -> stopwords.contains(word);
        tokenizedText.removeIf(isStopWord);
        return tokenizedText;
    }
}

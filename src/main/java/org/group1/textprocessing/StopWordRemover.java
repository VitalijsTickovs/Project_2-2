package org.group1.textprocessing;

import org.group1.exception.NullTextException;
import org.group1.helpers.TXTReader;
import org.group1.processor.PreProcessor;
import org.w3c.dom.ls.LSOutput;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Predicate;

public class StopWordRemover {

    /**
     * Stop word database: https://www.kaggle.com/datasets/heeraldedhia/stop-words-in-28-languages?select=english.txt
     */
    private static String stopwordPath = "src/main/resources/linguistics/StopWords.txt";
    private static List<String> stopwords;
    private static Map<String, Boolean> stopwordMap;
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
        if(stopwordMap == null) setKeyWordMap(stopwords);
        if(isStopWord == null) isStopWord = word -> stopwordMap.containsKey(word);
        tokenizedText.removeIf(isStopWord);
        return tokenizedText;
    }

    /**
     * This method is used for the creation of the stop word map.
     * @param keywords List of stop words.
     */
    private static void setKeyWordMap(List<String> keywords){
        stopwordMap = new HashMap<>();
        for(String word: keywords){
            stopwordMap.put(word, true);
        }
    }
}

package org.group1.reponse.procesor;

import org.group1.exception.NullTextException;

import java.util.ArrayList;
import java.util.List;

public class Normalization {

    //ASCII codes for the range (a-z)
    public static final int A = 96;
    public static final int Z = 123;

    public static final int ZERO = 48;
    public static final int NINE = 57;

    /**
     * Kind of s Singleton pattern but not really is just that I don´t
     * want that people can create multiple instances of this class.
     */
    private Normalization(){}

    /**
     * This method is used for the normalization of the words.
     * @param tokenizedText Tokenized words.
     * @return List of words normalized.
     */
    public static List<String> normalize(List<String> tokenizedText) throws NullTextException {

        if(tokenizedText == null) throw new NullTextException("Cannot normalize a null text");

        List<String> list = new ArrayList<>();

        for (String token : tokenizedText) {
            list.add(token.toLowerCase());
        }
        return clean(list); //TODO: Check the domain of the function (will work for unique String?)
    }

    /**
     * This method cleans the text and returns only words with the English alphabet.
     * @param text Thue input
     * @return Real words.
     * @throws NullTextException The text must not be null
     */
    private static List<String> clean(List<String> text) {

        List<String> normalizedText = text;
        List<String> list = new ArrayList<>();

        for (String word : normalizedText) {
            list.add(removeSymbols(word));
        }
        return list;
    }

    /**
     * This method cleans word a word the symbols based in the ASCII.
     * @param word input
     * @return clean word
     */
    private static String removeSymbols(String word){

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < word.length(); i++) {
            if((word.charAt(i) > A && word.charAt(i) < Z) || (word.charAt(i) >= ZERO && word.charAt(i) <= NINE)){
                sb.append(word.charAt(i));
            }
        }
        return sb.toString();
    }
}

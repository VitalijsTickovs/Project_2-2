package org.group1.reponse.procesor;

import org.group1.collections.Delim;
import org.group1.exception.NullTextException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Tokenization {

    /**
     * Kind of s Singleton pattern but not really is just that I don´t
     * want that people can create multiple instances of this class.
     */
    private Tokenization(){}

    /**
     * This method is used to tokenize the sentences in words
     * @param text The text input.
     * @param delim The delimitation constant
     * @return The output is a list of words or set of words
     */
    public static List<String> tokenize(String text, Delim delim) throws NullTextException {

        if(text == null) throw new NullTextException("Cannot tokenize a null text");

        StringTokenizer tokens = new StringTokenizer(text, delim.delim);
        List<String> list = new ArrayList<>();

        while (tokens.hasMoreTokens()){
            list.add(tokens.nextToken());
        }
        return list; //TODO: Is possible to return null?
    }
}

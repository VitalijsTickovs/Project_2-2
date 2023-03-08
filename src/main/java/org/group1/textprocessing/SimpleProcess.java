package org.group1.textprocessing;

import java.util.*;
import java.util.regex.Pattern;

public class SimpleProcess {

    public static List<String> process(String text){

        text = Pattern.compile("[^-<>\\w]+").matcher(text).replaceAll(" ").trim();
        List<String> temp = new ArrayList<>();
        StringTokenizer tokens = new StringTokenizer(text, " ");

        while (tokens.hasMoreTokens()){
            temp.add(tokens.nextToken().toLowerCase());
        }
        return temp;
    }

    public static List<String> processForVocabulary(String text){

        text = Pattern.compile("[^\\w]+").matcher(text).replaceAll(" ").trim();
        List<String> temp = new ArrayList<>();
        StringTokenizer tokens = new StringTokenizer(text, " ");

        while (tokens.hasMoreTokens()){
            temp.add(tokens.nextToken().toLowerCase());
        }
        return temp;
    }

}

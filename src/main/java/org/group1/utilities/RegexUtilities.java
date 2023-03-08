package org.group1.utilities;


import java.util.*;
import java.util.regex.*;

public class RegexUtilities {

    public static List<String> filterLineByRegex(String text, String regex){
        return Arrays
                .stream(text
                        .split("\n"))
                .toList()
                .stream()
                .filter((s)-> s.contains(regex))
                .toList();
    }

    public static int countRegexOccurrences(String text, String regex){

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while(matcher.find()) count++;

        return count;
    }


    public static String getOriginalFormatFromRegex(String text, String regex){

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }

    public static String replaceRegex(String text, String oldWord, String newWord){
        return text
                .replace(oldWord, newWord)
                .trim();
    }

    public static String replaceFirstRegex(String text, String oldWord, String newWord){
        return text
                .replaceFirst(oldWord, newWord)
                .trim();
    }

}

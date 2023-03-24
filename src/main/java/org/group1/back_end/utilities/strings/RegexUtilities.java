package org.group1.back_end.utilities.strings;


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

    public static boolean containsNumber(String text) {
        String regex = "\\d+";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(text);

        return matcher.find();
    }

    public static List<String> concatenate(List<String> set, int power) {
        if (power == 1) {
            return set;
        }
        List<String> previousConcatenation = concatenate(set, power - 1);
        List<String> concatenationList = new ArrayList<>();
        for (String str1 : set) {
            for (String str2 : previousConcatenation) {
                String element = str1.concat(" ").concat(str2);
                concatenationList.add(element);
            }
        }
        return concatenationList;
    }

    public static String numberToText(String text){

        String regex = "\\d+";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        String number = "zero";
        if (matcher.find()) {
             number = matcher.group();
        }
        int n = Integer.parseInt(number);
        return numberToWords(n);
    }

    private static String numberToWords(int number) {

        if (number == 0) {
            return "zero";
        }

        String[] units = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
        String[] tens = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

        String text = "";

        if (number >= 1000) {
            text += numberToWords(number / 1000) + " thousand ";
            number %= 1000;
        }

        if (number >= 100) {
            text += units[number / 100] + " hundred ";
            number %= 100;
        }

        if (number >= 20) {
            text += tens[number / 10] + " ";
            number %= 10;
        }

        if (number > 0) {
            text += units[number] + " ";
        }

        return text.trim();
    }

    public static String getOriginalFormatFromRegex(String text, String regex){

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "";
        }
    }

    public static String replaceRegex(String text, String oldWord, String newWord){
        return text
                .replaceFirst(oldWord, newWord)
                .trim();
    }

    public static String replaceRegexAll(String text, String oldWord, String newWord){
        return text
                .replaceAll(oldWord, newWord)
                .trim();
    }

    public static String replaceFirstRegex(String text, String oldWord, String newWord){
        return text
                .replaceFirst(oldWord, newWord)
                .trim();
    }

}

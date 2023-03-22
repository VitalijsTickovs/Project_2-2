package org.group1.back_end.ML.model_markov_decision;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpellingChecker {

    static final int shortestWord = 3;
    static final int mediumWord = 5;

    static final NGrams shortNGrams = new NGrams(2);
    static final NGrams mediumNGrams = new NGrams(3);
    static final NGrams longNGrams = new NGrams(5);

    public SpellingChecker() {
        shortNGrams.load();
        mediumNGrams.load();
        longNGrams.load();
    }

    public double getScore(String word) {
        if(word.length() <= shortestWord) {
            return shortNGrams.computeNGramScore(word);
        } else if(word.length() <= mediumWord) {
            return mediumNGrams.computeNGramScore(word);
        } else if(word.length() > mediumWord) {
            return longNGrams.computeNGramScore(word);
        } else {
            return 0.0;
        }
    }

    private double getShortScore(String word) {
        return shortNGrams.computeNGramScore(word);
    }
    private double getMediumScore(String word) {
        return mediumNGrams.computeNGramScore(word);
    }
    private double getLongScore(String word) {
        return longNGrams.computeNGramScore(word);
    }

    public static String mutateString(String input, int n) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        if (n <= 0) {
            return input;
        }
        if (n >= input.length()) {
            return "";
        }
        char[] chars = input.toCharArray();
        for (int i = 0; i < n; i++) {
            int randomIndex = (int) (Math.random() * chars.length);
            char randomChar = (char) (Math.random() * 26 + 'a');
            chars[randomIndex] = randomChar;
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        int n = 10; // the upper bound of the random integer
        Random rand = new Random();
        String totest = "Monday";
        String mutation;
        SpellingChecker checker = new SpellingChecker();
        List<String> shitstick = new ArrayList<>();
        shitstick.add(totest);
        for(int i = 0; i < 50; i++){
            mutation = mutateString(totest, rand.nextInt(n) + 1);
            if(mutation.matches(".*[a-zA-Z].*")) shitstick.add(mutation);
            else i--;
        }
        shitstick.sort((o1, o2) -> (int) (checker.getScore(o2) - checker.getScore(o1)));
        for (String s : shitstick){
            System.out.println(s + " -->" + checker.getScore(s) + " -->" + checker.getShortScore(s) + " -->" + checker.getMediumScore(s) + " -->" + checker.getLongScore(s));
        }
    }


//    public static void main(String[] args) {
//
//        SpellingChecker checker = new SpellingChecker();
//        ArrayList<String> testCases = new ArrayList<String>();
//        testCases.add("apple");
//        testCases.add("apel");
//        testCases.add("banana");
//        testCases.add("bannana");
//        testCases.add("computer");
//        testCases.add("compuuter");
//        testCases.add("elephant");
//        testCases.add("elephent");
//        testCases.add("guitar");
//        testCases.add("guitaar");
//        testCases.add("happiness");
//        testCases.add("hapiness");
//        testCases.add("internet");
//        testCases.add("internt");
//        testCases.add("language");
//        testCases.add("langage");
//        testCases.add("microphone");
//        testCases.add("miccrophone");
//        testCases.add("notebook");
//        testCases.add("notebok");
//        testCases.add("orange");
//        testCases.add("orage");
//        testCases.add("pizza");
//        testCases.add("piza");
//        testCases.add("quality");
//        testCases.add("qality");
//        testCases.add("restaurant");
//        testCases.add("resturant");
//        testCases.add("sunshine");
//        testCases.add("sunshyne");
//        testCases.add("telephone");
//        testCases.add("telephne");
//        testCases.add("umbrella");
//        testCases.add("umbrela");
//        testCases.add("victory");
//        testCases.add("vistory");
//        testCases.add("watermelon");
//        testCases.add("watermellon");
//        testCases.add("xylophone");
//        testCases.add("xylophon");
//        testCases.add("yellow");
//        testCases.add("yelow");
//
//        boolean init = false;
//        int correctcount = 0;
//        int falsecount = 0;
//       for(int i = 0; i < testCases.size(); i++) {
//           System.out.println("Word: " + testCases.get(i) + " ----> Score: " + checker.getScore(testCases.get(i)));
//              if(init) {
//                  if(checker.getScore(testCases.get(i)) < checker.getScore(testCases.get(i-1))) correctcount++;
//                    else falsecount++;
//              }
//           init = true;
//       }
//        System.out.println("Correct: " + correctcount);
//        System.out.println("False: " + falsecount);
//
//    }

}

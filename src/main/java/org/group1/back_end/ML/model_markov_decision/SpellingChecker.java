package org.group1.back_end.ML.model_markov_decision;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpellingChecker {

    static final int shortestWord = 3;
    static final int mediumWord = 5;
    static final Random rand = new Random();
    static final int mutationRate = 3;
    static final int maxIterations = 100;
    static final NGrams shortNGrams = new NGrams(2);
    static final NGrams mediumNGrams = new NGrams(3);
    static final NGrams longNGrams = new NGrams(5);
    static final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public SpellingChecker() {
        shortNGrams.load();
        mediumNGrams.load();
        longNGrams.load();
    }

    public String correct(String word){
        String bestWord = word;
        int[] cases;
        String mutated;
        double bestScore = getScore(word);
        for(int i = 0; i < maxIterations; i++){
            cases = getMutationCases();
            mutated = word;
            for(int casus : cases){
                mutated = swap(mutated, casus);
                System.out.println("mutated: " + mutated);
                double score = getScore(mutated);
                if(score > bestScore){
                    bestScore = score;
                    bestWord = mutated;
                }
            }
        }
        return bestWord;
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

    public String getCorrected(String word){
        return null;
    }
    private static int[] getMutationCases(){
        int[] cases = new int[mutationRate];
        for(int i = 0; i < 3; i++) cases[i] = rand.nextInt(3) + 1;
        return cases;
    }


    private static String swap(String word, int casus){
        switch (casus){
            case 1:
                return mutate(word);
            case 2:
                return insert(word);
            case 3:
                return delete(word);
            default:
                return word;
        }
    }
    private static String mutate(String word){
        int index = rand.nextInt(word.length());
        return word.substring(0, index) + randomChar() + word.substring(index + 1);
    }

    private static String insert(String word){
        int index = rand.nextInt(word.length());
        return word.substring(0, index) + randomChar() + word.substring(index);
    }

    private static String delete(String word){
        return word.replace(randomChar(), "");
    }

    private static String randomChar(){
        return ""+ alphabet.charAt(rand.nextInt(alphabet.length()));
    }

    private static int randomInt(int start, int end){
        return rand.nextInt(end - start) + start;
    }

    public static void main(String[] args) {
        int n = 10; // the upper bound of the random integer
        Random rand = new Random();
        String totest = "Monday";
        SpellingChecker sc = new SpellingChecker();
        System.out.println(sc.getCorrected("Mfunoday"));
    }


}

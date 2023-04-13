//package org.group1.back_end.ML.model_markov_decision;
//
//import org.group1.back_end.response.skills.Skill;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class SpellingChecker {
//
//    static final int shortestWord = 3;
//    static final int mediumWord = 5;
//    static final Random rand = new Random();
//    static final int mutationRate = 3;
//    static final int maxIterations = 100;
//    static final NGrams shortNGrams = new NGrams(2);
//    static final NGrams mediumNGrams = new NGrams(3);
//    static final NGrams longNGrams = new NGrams(5);
//    static final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//
//    public SpellingChecker() {
//        shortNGrams.load();
//        mediumNGrams.load();
//        longNGrams.load();
//    }
//
//    public String correct(String word){
//        String bestWord = word;
//        int[] cases;
//        String mutated;
//        double bestScore = getScore(word);
//        for(int i = 0; i < maxIterations; i++){
//            cases = getMutationCases();
//            mutated = word;
//            for(int casus : cases){
//                mutated = swap(mutated, casus);
//                System.out.println("mutated: " + mutated);
//                double score = getScore(mutated);
//                if(score > bestScore){
//                    bestScore = score;
//                    bestWord = mutated;
//                }
//            }
//        }
//        return bestWord;
//    }
//    public double getScore(String word) {
//       return getMediumScore(word);
//    }
//
//    private double getShortScore(String word) {
//        return shortNGrams.computeNGramScore(word);
//    }
//    private double getMediumScore(String word) {
//        return mediumNGrams.computeNGramScore(word);
//    }
//    private double getLongScore(String word) {
//        return longNGrams.computeNGramScore(word);
//    }
//
//    public String getCorrected(String word){
//        return null;
//    }
//    private static int[] getMutationCases(){
//        int[] cases = new int[mutationRate];
//        for(int i = 0; i < 3; i++) cases[i] = rand.nextInt(3) + 1;
//        return cases;
//    }
//
//
//    private static String swap(String word, int casus){
//        switch (casus){
//            case 1:
//                return mutate(word);
//            case 2:
//                return insert(word);
//            case 3:
//                return delete(word);
//            default:
//                return word;
//        }
//    }
//    private static String mutate(String word){
//        int index = rand.nextInt(word.length());
//        return word.substring(0, index) + randomChar() + word.substring(index + 1);
//    }
//
//    private static String insert(String word){
//        int index = rand.nextInt(word.length());
//        return word.substring(0, index) + randomChar() + word.substring(index);
//    }
//
//    private static String delete(String word){
//        return word.replace(randomChar(), "");
//    }
//
//    private static String randomChar(){
//        return ""+ alphabet.charAt(rand.nextInt(alphabet.length()));
//    }
//
//    private static int randomInt(int start, int end){
//        return rand.nextInt(end - start) + start;
//    }
//
//
//    public static List<String> generateCandidates(String word, int maxDistance, List<String> dictionary) {
//        List<String> candidates = new ArrayList<>();
//        for (String candidate : dictionary) {
//            int distance = levenshteinDistance(word, candidate);
//            if (distance <= maxDistance) {
//                candidates.add(candidate);
//            }
//        }
//        return candidates;
//    }
//
//    private static int levenshteinDistance(String word1, String word2) {
//        int m = word1.length();
//        int n = word2.length();
//        int[][] dp = new int[m + 1][n + 1];
//        for (int i = 0; i <= m; i++) {
//            dp[i][0] = i;
//        }
//        for (int j = 0; j <= n; j++) {
//            dp[0][j] = j;
//        }
//        for (int i = 1; i <= m; i++) {
//            for (int j = 1; j <= n; j++) {
//                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
//                    dp[i][j] = dp[i - 1][j - 1];
//                } else {
//                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
//                }
//            }
//        }
//        return dp[m][n];
//    }
//    public static void main(String[] args) throws Exception{
//        SpellingChecker spellingChecker = new SpellingChecker();
//        Skill test = new Skill();
//        test.generateSkills();
//        spellingChecker.getScore("Miobnaday");
//    }
//
//
//}

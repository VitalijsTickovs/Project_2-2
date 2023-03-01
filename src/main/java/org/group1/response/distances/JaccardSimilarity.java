package org.group1.response.distances;

import java.util.HashSet;

public class JaccardSimilarity {
    public static double calculate(String s1, String s2) {
        HashSet<String> set1 = new HashSet<>();
        HashSet<String> set2 = new HashSet<>();

        // Split strings into words
        String[] words1 = s1.split("\\s+");
        String[] words2 = s2.split("\\s+");

        // Add words to sets
        for (String word : words1) {
            set1.add(word);
        }
        for (String word : words2) {
            set2.add(word);
        }

        // Calculate Jaccard similarity
        HashSet<String> union = new HashSet<>(set1);
        union.addAll(set2);

        HashSet<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        double similarity = (double) intersection.size() / (double) union.size();
        return similarity;
    }

    public static void main(String[] args) {
        String s1 = "the quick brown fox jumps over the lazy dog";
        String s2 = "the lazy dog jumps over the quick brown fox";

        double similarity = JaccardSimilarity.calculate(s1, s2);
        System.out.println("Jaccard similarity = " + similarity);
    }
}


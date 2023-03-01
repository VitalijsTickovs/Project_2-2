package org.group1.response.distances;

import java.util.HashSet;


public class JaccardSimilarity {
    public static double calculate(String s1, String s2) {



        // create two hashSets to allow for set operations
        HashSet<String> set1 = new HashSet<>();
        HashSet<String> set2 = new HashSet<>();

        // split strings into words
        String[] words1 = s1.split("\\s+");
        String[] words2 = s2.split("\\s+");

        // put words into lower case and add words to sets
        for (String word : words1) {
            word.toLowerCase();
            set1.add(word);
        }
        for (String word : words2) {
            word.toLowerCase();
            set2.add(word);
        }

        // create the union
        HashSet<String> union = new HashSet<>(set1);
        union.addAll(set2);

        // create the intersection set
        HashSet<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        // calculate similarity between the two original strings
        // based on the division of the cardinality of union set and intersection set
        double similarity = (double) intersection.size() / (double) union.size();

        return similarity;
    }

    public static void main(String[] args) {

        String s1 = "Tom likes to eat cake";
        String s2 = "cake and cats for breakfast";

        double similarity = JaccardSimilarity.calculate(s1, s2);
        System.out.println("Jaccard similarity = " + similarity);
    }
}


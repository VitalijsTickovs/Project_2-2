package org.group1.back_end.utilities.algebra;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.*;

public class Distances {

    public static double jaccard(Set<String> set1, Set<String> set2) {
        if(set1 == null) return Double.MAX_VALUE;
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);
        return (double) intersection.size() / union.size();
    }


    public static double INVENTED(Set<String> set1, Set<String> set2){

        double counter = 0;

        for (String word : set1) {
            if(set2.contains(word)){
               counter++;
            }
        }

        return counter / set1.size();
    }


    public static double jaccard(String s1, String s2) {

        // create two hashSets to allow for set operations
        HashSet<String> set1 = new HashSet<>(), set2 = new HashSet<>();

        // split strings into words
        String[] words1 = s1.split("\\s+"), words2 = s2.split("\\s+");

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
        return  (double) intersection.size() / (double) union.size();
    }

    public static double jaro(String a, String b){
        // If the Strings are equal
        if (a.equals(b))
            return 1.0;

        // Length of two Strings
        int aLength = a.length();
        int bLength = b.length();

        // Maximum distance upto which matching
        // is allowed
        int maxDist = (int) (Math.floor(Math.max(aLength, bLength) / 2) - 1);

        // Count of matches
        int match = 0;

        // Hash for matches
        int aHash[] = new int[a.length()];
        int bHash[] = new int[b.length()];

        // Traverse through the first String
        for (int i = 0; i < aLength; i++) {

            // Check if there is any matches
            for (int j = Math.max(0, i - maxDist);
                 j < Math.min(bLength, i + maxDist + 1); j++) {

                // If there is a match
                if (a.charAt(i) == b.charAt(j) && bHash[j] == 0) {
                    aHash[i] = 1;
                    bHash[j] = 1;
                    match++;
                    break;
                }
            }
        }

        // If there is no match
        if (match == 0) return 0.0;

        // Number of transpositions
        double t = 0;

        int point = 0;

        // Count number of occurrences
        // where two characters match but
        // there is a third matched character
        // in between the indices
        for (int i = 0; i < aLength; i++) {
            if (aHash[i] == 1) {
                // Find the next matched character in second string
                while (bHash[point] == 0)
                    point++;

                if (a.charAt(i) != b.charAt(point++))
                    t++;
            }
        }

        t /= 2;

        // Return the Jaro Similarity
        return (    ((double)match) / ((double)aLength)+
                ((double)match) / ((double)bLength)+
                ((double)match - t) / ((double)match)) /3; //Divide by 3 to normalize it
    }

    public static int levenshtein(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + min(dp[i - 1][j - 1], min(dp[i][j - 1], dp[i - 1][j]));
                }
            }
        }
        return dp[a.length()][b.length()];
    }

    public static double cosine(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    public static double euclideanDistance(double[] vector1, double[] vector2) {
        if(vector1 == null || vector2 == null) return Double.MAX_VALUE;
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("Los vectores deben tener la misma longitud");
        }

        double sum = 0.0;
        for (int i = 0; i < vector1.length; i++) {
            sum += Math.pow(vector1[i] - vector2[i], 2);
        }
        return Math.sqrt(sum);
    }

    public static String getSimilarFromSet(String input, Set<String> strings) {
        int minDistance = Integer.MAX_VALUE;
        String closest = null;
        for (String string : strings) {
            int distance = levenshtein(input, string);
            if (distance < minDistance) {
                minDistance = distance;
                closest = string;
            }
        }
        return closest;
    }

    public static int getSimilarDistanceFromSet(String input, Set<String> strings) {
        int minDistance = Integer.MAX_VALUE;
        String closest = null;
        for (String string : strings) {
            int distance = levenshtein(input, string);
            if (distance < minDistance) {
                minDistance = distance;
                closest = string;
            }
        }
        return minDistance;
    }
}

package org.group1.response.distances;

public class Jaro {
    public static double jaroDistance(String a, String b){
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

    public static void main(String[] args)
    {
        String s1 = "hwat lecrure thre monaday", s2 = "Which lectures are there on Monday";

        // Print jaro Similarity of two Strings
        System.out.print(jaroDistance(s1, s2) +"\n");
    }
}

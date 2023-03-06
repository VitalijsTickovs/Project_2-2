package org.group1.response.matching.Support;

import org.group1.response.distances.iDistance;

import java.util.Comparator;

class DistanceSorter implements Comparator<String> {
    String wordToCompare;
    iDistance distance;

    /**
     * Constructor for the DistanceSorter
     * @param wordToCompare, the word to compare to
     * @param distance, the distance to use
     */
    public DistanceSorter(String wordToCompare, iDistance distance) {
        this.wordToCompare = wordToCompare;
        this.distance = distance;
    }

    /**
     * Compares two words
     * @param o1, the first word
     * @param o2, the second word
     * @return, the result of the comparison
     */
    @Override
    public int compare(String o1, String o2) {
        ;
        if (distance.calculate(wordToCompare, o1) < distance.calculate(wordToCompare, o2)) return 1;
        else if (distance.calculate(wordToCompare, o1) > distance.calculate(wordToCompare, o2)) return -1;
        else return 0;
    }
}

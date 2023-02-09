package org.group1.reponse.procesor.helpers;

import org.group1.collections.CV;

import static org.group1.collections.CV.C;
import static org.group1.collections.CV.V;

public class StemBasic {

    /**
     * This method returns the m value of the word
     * @param word The word to evaluate
     * @return the m value.
     */
    public static int getM(String word){ //TODO: Test a lot this method

        int i = 0;
        int j = word.length() - 1;
        int n = 0;
        if(j <= 1) return 0;

        while (true) {
            if (i > j) return n;
            if (vocalAt(word, i) == V) break;
            i++;
        }

        i++;
        while (true) {
            while (true) {
                if (i > j) return n;
                if (vocalAt(word, i) == C) break;
                i++;
            }
            i++;
            n++;
            while (true) {
                if (i > j) return n;
                if (vocalAt(word, i) == V) break;
                i++;
            }
            i++;
        }
    }


    //I KNOW THAT I CAN SIMPLIFY THIS IN ONE METHOD BUT I AM GOING FAST. I WILL CHANGE IT LATER ON

    /**
     * This method checks for an o in the end
     * @param word word to evaluate
     * @return result true || false
     */
    public static boolean _o(String word){
        return ('o' == word.charAt(word.length() - 1));
    }


    /**
     * This method checks for an l in the end
     * @param word word to evaluate
     * @return result true || false
     */
    public static boolean _l(String word){
        return ('l' == word.charAt(word.length() - 1));
    }


    /**
     * This method checks for an d in the end
     * @param word word to evaluate
     * @return result true || false
     */
    public static boolean _d(String word){
        return ('d' == word.charAt(word.length() - 1));
    }


    /**
     * This method checks if there is a vocal between words
     * @param word The input word
     * @return true || false
     */
    public static boolean _v_(String word) {
        if(word.length() < 3) return false;
        for (int i = 1; i <= word.length() - 2; i++){
            if (vocalAt(word, i) == V)
                return true;
        }
        return false; //TODO: Check if does not work
    }

    /**
     * Evaluates if a character in a given position is a vocal
     * @param word the word
     * @param position position of the character in the word
     * @return (C || V)
     */
    public static CV vocalAt(String word, int position){

        return switch (word.charAt(position)) {
            case 'a', 'e', 'i', 'o', 'u' -> V;
            default -> C;
        };
    }
}

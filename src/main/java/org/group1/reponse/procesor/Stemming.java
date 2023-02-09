package org.group1.reponse.procesor;

import org.group1.collections.CV;

import static org.group1.collections.CV.*;

public class Stemming {

    /**
     * Potter Algorithm
     * More info -> (https://vijinimallawaarachchi.com/2017/05/09/porter-stemming-algorithm/)
     */
    private Stemming(){}

    private void Steam(String text){
        int start = 0;
        int end = text.length() - 1;
    }

    /**
     * Evaluates if a character in a given position is a vocal
     * @param word the word
     * @param position position of the character in the word
     * @return (C || V)
     */
    static CV vocalAt(String word, int position){

        return switch (word.charAt(position)) {
            case 'a', 'e', 'i', 'o', 'u' -> V;
            default -> C;
        };
    }

    /**
     * This method returns the m value of the word
     * @param word The word to evaluate
     * @return the m value.
     */
    private static int getM(String word){ //TODO: Test a lot this method

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

    /**
     * This method checks if there is a vocal between words
     * @param word The input word
     * @return true || false
     */
    private static boolean _v_(String word) {
        if(word.length() < 3) return false;
        for (int i = 1; i <= word.length() - 2; i++){
            if (Stemming.vocalAt(word, i) == V)
                return true;
        }
        return false; //TODO: Check if does not work
    }

    /**
     * This is the step 1 a of the algorithm.
     * @param word Input
     * @return output
     */
    public static String step1a(String word){

        if(word.endsWith("sses")) return word.replace("sses", "ss");

        if(word.endsWith("ies")) return word.replace("ies", "i");

        if(word.endsWith("s")) return word.replace("s", "");

        return word;
    }

    /**
     *
     * @param word
     * @return
     */
    public static String step1b(String word){

        if(getM(word) > 0){
            if(word.endsWith("eed")) return word.replace("eed", "ee");
        }

        if(_v_(word)){

            String temp = word;

            if(word.endsWith("ed")){

                temp = word.replace("ed", "");

                return step1b2(word);
            }



            if(word.endsWith("ing")) return word.replace("ing", "");
        }

        return null;
    }

    public static String step1b2(String word){

        if(word.endsWith("at")) return word.replace("at", "ate");

        if(word.endsWith("bl")) return word.replace("bl", "blee");

        if(word.endsWith("iz")) return word.replace("iz", "ize");

        return word;
    }



    public static void main(String[] args) {
        System.out.println(getM("trouble"));
    }

}

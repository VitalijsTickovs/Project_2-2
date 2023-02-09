package org.group1.reponse.procesor;

import org.group1.collections.CV;

import static org.group1.collections.CV.*;

public class Stemming {

    // TODO: the stemming class is almost done probably will have bugs (it will for sure)

    /**
     * Potter Algorithm
     * More info -> (https://vijinimallawaarachchi.com/2017/05/09/porter-stemming-algorithm/)
     */
    private Stemming(){}

    public static void main(String[] args) {
        System.out.println(Steam("emotional"));
    }

    private static String Steam(String word){

        int m = getM(word);
        word = step1a(word);


        if(m > 0){
            word = step1b(word);
        }

        if(_v_(word)){
            word = step1c(word);
        }

        if(m > 0){
            word = step2(word);
        }

        if(m > 0){
            word = step3(word);
        }

        if (m > 1){
            word = step4(word);
        }

        if(m == 1 && !_o(word)){
            word = step5a(word);
        }

        if(m > 1 && _d(word)){
            word = step5a(word);
        }

        if(m > 1 && _l(word)){
            word = step5a(word);
        }
        return word;
    }

    public static boolean _o(String word){
        return ('o' == word.charAt(word.length() - 1));
    }

    public static boolean _l(String word){
        return ('l' == word.charAt(word.length() - 1));
    }

    public static boolean _d(String word){
        return ('d' == word.charAt(word.length() - 1));
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
     * This is the step 1a of the algorithm.
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
     * This is th step 1b
     * @param word input
     * @return output
     */
    public static String step1b(String word){

        if(getM(word) > 0){
            if(word.endsWith("eed")) return word.replace("eed", "ee");
        }

        if(_v_(word)){

            String temp = word;

            if(word.endsWith("ed")){

                temp = word.replace("ed", "");

                return step1b2(temp);
            }

            if(word.endsWith("ing")) return word.replace("ing", "");
        }

        return word;
    }

    public static String step1b2(String word){

        if(word.endsWith("at")) return word.replace("at", "ate");

        if(word.endsWith("bl")) return word.replace("bl", "blee");

        if(word.endsWith("iz")) return word.replace("iz", "ize");

        return word;
    }

    public static String step1c(String word){

        if(_v_(word)){
            if(word.endsWith("y")) return word.replace("y", "i");
        }
        return word;
    }

    private static final String[] STEP2_I = {
            "ational", "tional", "enci", "anci", "izer", "abli",
            "alli", "entli", "eli", "ousli",
            "ization", "ation", "ator", "alism", "iveness",
            "fulness", "ousness", "ality", "ivity", "bility"
    };

    private static final String[] STEP2_O = {
            "ate", "tion", "ence", "ance", "ize", "able",
            "al", "ent", "e", "ous", "ize", "ate", "ate",
            "al", "ive", "ful", "ous", "al", "ive", "ble"
    };

    public static String step2(String word){

        if(getM(word) > 0){
            for (int i = 0; i < STEP2_I.length; i++) {
                if(word.endsWith(STEP2_I[i])) return word.replace(STEP2_I[i], STEP2_O[i]);
            }
        }
        return word;
    }

    private static final String[] STEP3_I = {
            "icative", "ative", "alize", "iciti", "ical", "ful", "ness"
    };

    private static final String[] STEP3_O = {
            "ic", "", "al", "ic", "ic", "ic", ""
    };

    public static String step3(String word){

        if(getM(word) > 0){
            for (int i = 0; i < STEP3_I.length; i++) {
                if(word.endsWith(STEP3_I[i])) return word.replace(STEP3_I[i], STEP3_O[i]);
            }
        }
        return word;
    }

    private static final String[] STEP4_I = {
            "al", "ance", "ence", "er", "ic", "able", "ible",
            "ant", "ement", "ment", "ent", "tion", "sion",
            "ou", "ism", "ate", "iti", "ous", "ive", "ize"
    };

    private static final String STEP4_O = "";


    public static String step4(String word){

        if(getM(word) > 0){
            for (int i = 0; i < STEP4_I.length; i++) {
                if(word.endsWith(STEP4_I[i])) return word.replace(STEP4_I[i], STEP4_O);
            }
        }
        return word;
    }

    private static final String STEP5_I = "e";
    private static final String STEP5_O = "";

    public static String step5a(String word){
        if(word.endsWith(STEP5_I)) return word.replace(STEP5_I, STEP5_O);
        return word;
    }

    public static String step5b(String word){
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < word.length() - 1; i++) {
            temp.append(word.charAt(i));
        }
        return temp.toString();
    }
}

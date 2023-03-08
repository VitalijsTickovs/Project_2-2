package org.group1.textprocessing.no_need_read;

import java.util.ArrayList;
import java.util.List;

import static org.group1.textprocessing.no_need_read.ConsonantsVowels.*;
import static org.group1.textprocessing.no_need_read.StemBasic.*;
import static org.group1.textprocessing.no_need_read.StemConstants.*;

public class Stemming {

    // TODO: the stemming class is almost done probably will have bugs (it will for sure)

    /**
     * Potter Algorithm
     * More info -> (https://vijinimallawaarachchi.com/2017/05/09/porter-stemming-algorithm/)
     */
    private Stemming(){}


    public static List<String> extract(List<String> tokens){
        List<String> steamed = new ArrayList<>();
        for(String token: tokens){
            steamed.add(stem(token));
        }
        return steamed;
    }
    /**
     * This method returns a stemmed word
     * @param word word to stem
     * @return the stemmed word
     */
    public static String stem(String word){

        int m = getM(word);
        word = step1a(word);
        m = getM(word);
        if(m > 0){
            word = step1b(word);
        }

        m = getM(word);
        if(_v_(word)){
            word = step1c(word);
        }

        m = getM(word);
        if(m > 0){
            word = step2(word);
        }

        m = getM(word);
        if(m > 0){
            word = step3(word);
        }

        m = getM(word);
        if (m > 1){
            word = step4(word);
        }

        m = getM(word);
        if(m == 1 && !_o(word)){
            word = step5a(word);
        }

        m = getM(word);
        if(m > 1 && _d(word)){
            word = step5b(word);
        }

        m = getM(word);
        if(m > 1 && _l(word)){
            word = step5b(word);
        }
        return word;
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


    /**
     * This is the second part of the step 1b
     * @param word word for evaluation
     * @return resultant word.
     */
    public static String step1b2(String word){

        if(word.endsWith("at")) return word.replace("at", "ate");

        if(word.endsWith("bl")) return word.replace("bl", "blee");

        if(word.endsWith("iz")) return word.replace("iz", "ize");

        return word;
    }


    /**
     * This is the step 1c
     * @param word word for evaluation
     * @return resultant word.
     */
    public static String step1c(String word){

        if(_v_(word)){
            if(word.endsWith("y")) return word.replace("y", "i");
        }
        return word;
    }


    /**
     * This is the step 2
     * @param word word for evaluation
     * @return resultant word.
     */
    public static String step2(String word){
        if(getM(word) > 0){
            for (int i = 0; i < STEP2_I.length; i++) {
                if(word.endsWith(STEP2_I[i])) return word.replace(STEP2_I[i], STEP2_O[i]);
            }
        }
        return word;
    }


    /**
     * This is the step 3
     * @param word word for evaluation
     * @return resultant word.
     */
    public static String step3(String word){

        if(getM(word) > 0){
            for (int i = 0; i < STEP3_I.length; i++) {
                if(word.endsWith(STEP3_I[i])) return word.replace(STEP3_I[i], STEP3_O[i]);
            }
        }
        return word;
    }


    /**
     * This is the step 4
     * @param word word for evaluation
     * @return resultant word.
     */
    public static String step4(String word){

        if(getM(word) > 1){
            for (int i = 0; i < STEP4_I.length; i++) {
                if(word.endsWith("tion")) return word.replace("tion", "t");
                if(word.endsWith("tion")) return word.replace("sion", "s");
                if(word.endsWith(STEP4_I[i])) return word.replace(STEP4_I[i], STEP4_O);
            }
        }
        return word;
    }


    /**
     * This is the step 5a
     * @param word word for evaluation
     * @return resultant word.
     */
    public static String step5a(String word){
        if(word.endsWith(STEP5_I)) return word.replace(STEP5_I, STEP5_O);
        return word;
    }


    /**
     * This is the step 2
     * @param word word for evaluation
     * @return resultant word.
     */
    public static String step5b(String word){
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < word.length() - 1; i++) {
            temp.append(word.charAt(i));
        }
        return temp.toString();
    }
}











class StemBasic {

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
    public static ConsonantsVowels vocalAt(String word, int position){

        return switch (word.charAt(position)) {
            case 'a', 'e', 'i', 'o', 'u' -> V;
            default -> C;
        };
    }
}












class StemConstants {

    public static final String[] STEP2_I = {
            "ational", "tional", "enci", "anci", "izer", "abli",
            "alli", "entli", "eli", "ousli",
            "ization", "ation", "ator", "alism", "iveness",
            "fulness", "ousness", "ality", "ivity", "bility"
    };

    public static final String[] STEP2_O = {
            "ate", "tion", "ence", "ance", "ize", "able",
            "al", "ent", "e", "ous", "ize", "ate", "ate",
            "al", "ive", "ful", "ous", "al", "ive", "ble"
    };

    public static final String[] STEP3_I = {
            "icative", "ative", "alize", "iciti", "ical", "ful", "ness"
    };

    public static final String[] STEP3_O = {
            "ic", "", "al", "ic", "ic", "ic", ""
    };

    public static final String[] STEP4_I = {
            "al", "ance", "ence", "er", "ic", "able", "ible",
            "ant", "ement", "ment", "ent", "tion", "sion",
            "ou", "ism", "ate", "iti", "ous", "ive", "ize"
    };

    public static final String STEP4_O = "";

    public static final String STEP5_I = "e";

    public static final String STEP5_O = "";
}












/**
 * This class describes the consonants and vocals,
 * these are phonological.
 * It is utilized for Porter Algorithm.
 */
enum ConsonantsVowels {
    C(false),
    V(true);

    private final boolean value;

    ConsonantsVowels(boolean a){
        this.value = a;
    }

    public boolean getValue() {
        return value;
    }
}


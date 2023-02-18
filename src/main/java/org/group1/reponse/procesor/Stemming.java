package org.group1.reponse.procesor;

import java.util.ArrayList;
import java.util.List;

import static org.group1.reponse.procesor.helpers.StemBasic.*;
import static org.group1.reponse.procesor.helpers.StemConstants.*;

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
            steamed.add(Steam(token));
        }
        return steamed;
    }
    /**
     * This method returns a stemmed word
     * @param word word to stem
     * @return the stemmed word
     */
    public static String Steam(String word){

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

    public static void main(String[] args) {
        System.out.println(Steam("going"));
        System.out.println(Steam("adjustable"));
        System.out.println(Steam("accomplished"));
        System.out.println(Steam("crepuscular"));
    }
}

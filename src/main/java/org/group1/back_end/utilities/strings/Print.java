package org.group1.back_end.utilities.strings;

import org.group1.back_end.response.*;
import org.group1.back_end.response.skills.Skill;

import java.util.Arrays;

import static org.group1.back_end.utilities.algebra.Distances.cosine;
import static org.group1.back_end.utilities.algebra.Distances.euclideanDistance;

public class Print {

    static String[] frase1 = {
            "are",
            "cat",
            "saturday",
            "nine",
            "toast",
            "lecture",
            "lessons",
            "weather",
            "had",
            "lectures"
    };

    static String[] frase2 = {

            "has",
            "dog",
            "monday",
            "eleven",
            "true",
            "class",
            "lecture",
            "forecast",
            "have",
            "university"
    };

    static String[] frase3 = {
            "have",
            "dog",
            "monday",
            "eleven",
            "happiness",
            "lectures",
            "weather",
            "rain",
            "has",
            "school"
    };

    public static void printVocabulary(){
        System.out.println("\n----------- VOCABULARY -----------");
        for (String w : Skill.VOCABULARY){
            System.out.println(w);
        }
        System.out.println("-----------------------------------\n");
    }


    public static void printSimilarityTable(){

        System.out.printf("%10s %10s %30s %30s", "WORD 1", "WORD 2", "COSINE      ","EUCLIDEAN      ");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < frase1.length; i++) {

            double[] frase1Vector = ResponseLibrary.WORD2VEC.getWordVectorsMean(Arrays.stream(frase1[i].split(" ")).toList()).toDoubleVector();
            double[] frase2Vector = ResponseLibrary.WORD2VEC.getWordVectorsMean(Arrays.stream(frase2[i].split(" ")).toList()).toDoubleVector();
            double[] frase3Vector = ResponseLibrary.WORD2VEC.getWordVectorsMean(Arrays.stream(frase3[i].split(" ")).toList()).toDoubleVector();


            System.out.format("%10s %10s %30s %30s",
                    frase2[i] , frase1[i]
                    , cosine(frase2Vector, frase1Vector)
                    , euclideanDistance(frase2Vector, frase1Vector) + "\n");

            System.out.format("%10s %10s %30s %30s",
                    frase3[i] , frase1[i]
                    , cosine(frase3Vector, frase1Vector)
                    , euclideanDistance(frase2Vector, frase1Vector) + "\n");
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------");

    }

}

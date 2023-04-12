package org.group1.back_end.response.skills.databases;

import org.group1.back_end.textprocessing.ComplexProcess;
import org.group1.back_end.textprocessing.SimpleProcess;
import org.group1.back_end.response.ResponseLibrary;

import java.util.*;

import static org.group1.back_end.utilities.algebra.Distances.cosine;

public class DB_Vectors extends DB_Manager implements iDataBase<String, String>{

    public static Map<double[], String> QUERY_VECTOR_MATCHING;
    public static Map<double[], String> RETRIEVE_QUERY;

    public DB_Vectors() {
        QUERY_VECTOR_MATCHING = new HashMap<>();
        RETRIEVE_QUERY = new HashMap<>();
    }

    public String process(String text){

        Set<String> clean = ComplexProcess.process(SimpleProcess.process(text));

        String newKey = "";
        for (String w : clean){
            newKey += " " + w;
        }

        return newKey.trim();
    }

    @Override
    public void add(String key, String value) {
        List<String> newKey = Arrays.stream(process(key).split(" ")).toList();
        double[] vector = ResponseLibrary.VECTOR_SIF.getVector(newKey);
        QUERY_VECTOR_MATCHING.put(vector, value);
        RETRIEVE_QUERY.put(vector, newKey.toString());
    }

    @Override
    public String getFirst(String key) {

        double closestKey = 0;

        List<String> newKey = Arrays.stream(process(key).split(" ")).toList();
        double[] tempKey = ResponseLibrary.VECTOR_SIF.getVector(newKey);

        double[] keySet = null;

        for (Map.Entry<double[], String> entry : QUERY_VECTOR_MATCHING.entrySet()){

            List<String> a = newKey;

            keySet = entry.getKey();
            List<String> b = Arrays.stream(process(RETRIEVE_QUERY.get(keySet)).split(" ")).toList();

            double[] tempKeyA = ResponseLibrary.VECTOR_SIF.getVector(a);
            double[] tempKeyB = ResponseLibrary.VECTOR_SIF.getVector(b);

            double distance = cosine(tempKeyA, tempKeyB);

            if (distance > closestKey) {
                closestKey = distance;
                tempKey = keySet;
            }
        }
        return QUERY_VECTOR_MATCHING.getOrDefault(tempKey, "NULL");
    }


    @Override
    public void printKeys() {

        System.out.println("\n---------------------------------- KEYS VECTORS -----------------------------------");
        int count = 0;
        for (double[] key : QUERY_VECTOR_MATCHING.keySet()) {
            count++;
            System.out.println("KEY"+count+" = " + RETRIEVE_QUERY.get(key) + " ----> " + QUERY_VECTOR_MATCHING.get(key));
        }
        System.out.println("------------------------------------------------------------------------------------\n");
    }
}

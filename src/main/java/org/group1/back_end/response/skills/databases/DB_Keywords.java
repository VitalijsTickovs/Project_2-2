package org.group1.back_end.response.skills.databases;

import com.mysql.cj.result.Row;
import org.group1.back_end.ML.model_vectorization.Vector;
import org.group1.back_end.response.skills.dataframe.Cell;
import org.group1.back_end.response.skills.dataframe.DataFrame;
import org.group1.back_end.response.skills.dataframe.Rows;
import org.group1.back_end.textprocessing.ComplexProcess;
import org.group1.back_end.textprocessing.SimpleProcess;
import org.group1.back_end.utilities.algebra.Distances;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DB_Keywords implements iDataBase<String, String>{

    public static Map<Set<String>, String> QUERY_KEYWORD_MATCHING;

    public static DataFrame data;

    public DB_Keywords() {
        QUERY_KEYWORD_MATCHING = new HashMap<>();
        data = new DataFrame(Arrays.asList("KEYWORDS", "RESPONSE"));
        data.isSet(true);
    }

    @Override
    public void add(String key, String value) {
        Set<String> keySet = ComplexProcess.process(SimpleProcess.process(key));
        QUERY_KEYWORD_MATCHING.put(keySet, value);
        data.insert(new Rows(Arrays.asList(new Cell<>(keySet), new Cell<>(value))));
    }

    @Override
    public String getFirst(String key) {

        System.out.println("KEY: " + key);

        double closestKey = 0;

        Set<String> tempKey = ComplexProcess
                .process(
                        SimpleProcess
                                .process(key));

        Set<String> keySet = null;

        for (Map.Entry<Set<String>, String> entry : QUERY_KEYWORD_MATCHING.entrySet()){

            keySet = entry.getKey();

            double distance = Distances.jaccard(keySet, tempKey);
            double[] a = Vector.setToVector(keySet);
            double[] b = Vector.setToVector(tempKey);
            //double distance = Distances.euclideanDistance(a, b);

            if (distance < closestKey) {
                closestKey = distance;
                tempKey = keySet;
            }
        }
        String nextBestResponse = QUERY_KEYWORD_MATCHING.getOrDefault(tempKey, "Sorry I dont understand what you are trying to say");

        return nextBestResponse;

    }


    @Override
    public void printKeys() {

        System.out.println("\n---------------------------------- KEYS KEYWORDS -----------------------------------");
        int count = 0;
        for (Set<String> key : QUERY_KEYWORD_MATCHING.keySet()) {
            count++;
            System.out.println("KEY"+count+" = " + key + " ----> " + QUERY_KEYWORD_MATCHING.get(key));
        }
        System.out.println("------------------------------------------------------------------------------------\n");
        System.out.println("------------------------------------------------------------------------------------\n");
        System.out.println(data);
    }
}

package org.group1.back_end.response.skills.databases;

import org.group1.back_end.response.skills.dataframe.Cell;
import org.group1.back_end.response.skills.dataframe.DataFrame;
import org.group1.back_end.response.skills.dataframe.Rows;
import org.group1.back_end.textprocessing.ComplexProcess;
import org.group1.back_end.textprocessing.SimpleProcess;

import java.util.*;

public class DB_PerfectMatching
        implements
        iDataBase<String, String>,
        iProcess<String, String>,
        iOrdenable<String, String, Direction>
{


    // DATA
    public static Map<String, String> QUERY_PERFECT_MATCHING;
    public static DataFrame data;

    public DB_PerfectMatching() {
        QUERY_PERFECT_MATCHING = new HashMap<>();
        data = new DataFrame(Arrays.asList("PERFECT MATCH", "RESPONSE"));
        data.isSet(true);
    }
    @Override
    public void add(String key, String value) {
        String newKey = process(key);
        System.out.println(newKey);
        QUERY_PERFECT_MATCHING.put(newKey, value);
        data.insert(new Rows(Arrays.asList(new Cell<>(newKey), new Cell<>(value))));
    }

    @Override
    public String getFirst(String key) {

        double closestKey = 0;
        String tempKey = process(key);
        System.out.println(key);

//        for (Map.Entry<String, String> entry : QUERY_PERFECT_MATCHING.entrySet()){
//
//            String keySet = entry.getKey();
//            double distance = Distances.jaccard(keySet, tempKey);
//
//            if (distance > closestKey) {
//                closestKey = distance;
//                tempKey = keySet;
//            }
//        }
        return QUERY_PERFECT_MATCHING.getOrDefault(tempKey, "Sorry I dont understand what you are trying to say");
    }

    @Override
    public void printKeys() {
        int count = 0;
        System.out.println("\n---------------------------------- KEYS PERFECT MATCHING --------------------------");
        for (Map.Entry<String, String> entry : QUERY_PERFECT_MATCHING.entrySet()) {
            count++;
//            System.out.println("KEY "+count+" = " + entry.getKey() + " ----> " + QUERY_PERFECT_MATCHING.get(entry.getKey()));
        }
        System.out.println("------------------------------------------------------------------------------------\n");

//        System.out.println(data);
    }

    @Override
    public String process(String sentence) {

        List<String> a = SimpleProcess.process(sentence);
        a = ComplexProcess.lemmas(a);

        String b = a.toString();
        return b;
    }

    @Override
    public String order(String s, String object, Direction upDown) {
        return null;
    }

    @Override
    public String getFirst(String s, String object, Direction upDown, double threshold) {
        return null;
    }
}

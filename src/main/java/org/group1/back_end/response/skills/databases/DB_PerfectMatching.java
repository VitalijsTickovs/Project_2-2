package org.group1.back_end.response.skills.databases;

import org.group1.back_end.utilities.algebra.Distances;

import java.util.*;

public class DB_PerfectMatching implements iDataBase{

    public static Map<String, String> QUERY_PERFECT_MATCHING;
    public DB_PerfectMatching() {
        QUERY_PERFECT_MATCHING = new HashMap<>();
    }
    @Override
    public void add(String key, String value) {
        QUERY_PERFECT_MATCHING.put(key, value);
    }

    @Override
    public String getFirst(String key) {

        double closestKey = 0;
        String tempKey = key.toLowerCase().trim();

        for (Map.Entry<String, String> entry : QUERY_PERFECT_MATCHING.entrySet()){

            String keySet = entry.getKey();
            double distance = Distances.jaccard(keySet, tempKey);

            if (distance > closestKey) {
                closestKey = distance;
                tempKey = keySet;
            }
        }
        return QUERY_PERFECT_MATCHING.getOrDefault(tempKey, "NULL");
    }

    @Override
    public void printDistances(String key) {
        System.out.println("\n-------------------------- DISTANCES KEYS (JACCARD) -----------------------------------");
        for (String keys : QUERY_PERFECT_MATCHING.keySet()) {
            System.out.println(keys + " ----> " + Distances.jaccard(keys, key));
        }
        System.out.println("------------------------------------------------------------------------------------\n");
    }

    @Override
    public void printKeys() {
        System.out.println("\n---------------------------------- KEYS PERFECT MATCHING --------------------------");
        for (Map.Entry<String, String> entry : QUERY_PERFECT_MATCHING.entrySet()) {
            System.out.println("KEY = " + entry.getKey() + " ----> " + QUERY_PERFECT_MATCHING.get(entry.getKey()));
        }
        System.out.println("------------------------------------------------------------------------------------\n");
    }
}

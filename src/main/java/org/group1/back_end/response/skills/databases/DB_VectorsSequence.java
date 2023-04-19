package org.group1.back_end.response.skills.databases;

import org.group1.back_end.response.ResponseLibrary;
import org.group1.back_end.response.skills.dataframe.Cell;
import org.group1.back_end.response.skills.dataframe.DataFrame;
import org.group1.back_end.response.skills.dataframe.Rows;
import org.group1.back_end.textprocessing.ComplexProcess;
import org.group1.back_end.textprocessing.SimpleProcess;
import org.group1.back_end.utilities.algebra.Distances;
import org.group1.back_end.utilities.algebra.Matrix;
import java.util.*;

import static org.group1.back_end.ML.model_optimization.WordMoversDistance.otra;


public class DB_VectorsSequence
        implements iDataBase<String, String>,
        iProcess<List<String>, String>{

    public static Map<List<String>, String> QUERY_VECTOR_MATCHING;

    public static DataFrame queryData;

    public DB_VectorsSequence() {

        QUERY_VECTOR_MATCHING = new HashMap<>();
        queryData = new DataFrame(Arrays.asList("VECTOR", "QUERY"));
        queryData.isSet(true);
    }

    public List<String> process(String text){
        return ComplexProcess.process(SimpleProcess.process(text)).stream().toList();
    }

    public List<double[]> processVector(List<String> key){
        List<double[]> vectorsSeq = new ArrayList<>();
        for (int i = 0; i < key.size(); i++) {
            vectorsSeq.add(ResponseLibrary.WORD2VEC.getWordVector(key.get(i)));
        }
        return vectorsSeq;
    }

    @Override
    public void add(String key, String value) {

        List<String> a = process(key);
        if(a.size() > 0){
            QUERY_VECTOR_MATCHING.put(a , value);
            queryData.insert(new Rows(Arrays.asList(new Cell<>(a), new Cell<>(value))));
        }
    }

    @Override
    public String getFirst(String key) {

        double closestKey = Double.MAX_VALUE;

        List<String> tempKey = process(key);

        List<String> keySet;
        List<String> selectedKey = new ArrayList<>();

        for (Map.Entry<List<String>, String> entry : QUERY_VECTOR_MATCHING.entrySet()){

            keySet = entry.getKey();

            double distance = otra(tempKey, keySet);

//            System.out.println(tempKey + "|||||" + keySet + "|||||" + distance);
            double setDistance = Distances.jaccard(new HashSet<>(tempKey), new HashSet<>(keySet));

            if (distance < closestKey) {
                closestKey = distance;
                selectedKey = keySet;
            }

        }



        return QUERY_VECTOR_MATCHING.getOrDefault(selectedKey, "NULL");
    }






    @Override
    public void printKeys() {

        System.out.println("\n---------------------------------- KEYS VECTORS SEQUENCE -----------------------------------");
        int count = 0;
        for (List<String> key : QUERY_VECTOR_MATCHING.keySet()) {
            count++;
            System.out.println("KEY"+count+" = " + key + " ----> " + QUERY_VECTOR_MATCHING.get(key));
        }
        System.out.println("------------------------------------------------------------------------------------\n");
        System.out.println(queryData);
    }


    @Override
    public String process(List<String> sentence) {
        return null;
    }
}

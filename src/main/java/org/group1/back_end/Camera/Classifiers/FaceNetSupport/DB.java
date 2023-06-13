package org.group1.back_end.Camera.Classifiers.FaceNetSupport;

import org.group1.back_end.Camera.Util.Distance;

import java.util.HashMap;


public class DB {
    HashMap<float[], String> db;

    static final float threshold = 1f;

    public DB(){
        db = new HashMap<>();
    }

    public void add(float[] embedding, String name){
        db.put(embedding, name);
    }

    public String get(float[] embedding){
        float min = Float.MAX_VALUE;
        String name = "Unknown";
        for(float[] key: db.keySet()){
            float distance = Distance.eucledian(key, embedding);
            if(distance < min){
                min = distance;
                name = db.get(key);
            }
        }
        if(min > threshold) name = "Unknown";
        return name;
    }
}

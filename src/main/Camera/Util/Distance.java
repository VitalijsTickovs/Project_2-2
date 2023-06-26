package org.group1.back_end.Camera.Util;

public class Distance {


    public static float eucledian(float[] one, float[] two){
        float total = 0;
        for(int i = 0; i < one.length; i++){
            total += Math.pow(one[i] - two[i], 2);
        }
        return (float) Math.sqrt(total);
    }
}

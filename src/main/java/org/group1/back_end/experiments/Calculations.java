package org.group1.back_end.experiments;

public class Calculations {

    public static void main(String[] args) {


        double[] a = {
                31.0,// tp
                2.0, // tn
                3.0, // fp
                11.0 // fn
        };


        double total = 0;

        for (int i = 0; i < a.length; i++) {
            total += a[i];
        }

        for (int i = 0; i < a.length; i++) {
            a[i] = a[i] / total;
        }

        System.out.println("Y: " + (a[0] / (a[0] + a[3])));
        System.out.println("X: " + (a[2] / (a[1] + a[2])));


    }
}

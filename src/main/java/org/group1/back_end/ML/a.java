package org.group1.back_end.ML;

import org.group1.back_end.response.ResponseLibrary;
import org.group1.back_end.utilities.algebra.Distances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class a {

    public static void main(String[] args) {
        String a = "I like your dog";
        String b = "I love my cat";

        System.out.println(WMD(a,b));
    }

    private static double WMD(String a, String b){

        List<String> sentenceA = Arrays.stream(a.split(" ")).toList();
        List<String> sentenceB = Arrays.stream(b.split(" ")).toList();

        double[][] distanceMatrix = new double[sentenceA.size()][sentenceB.size()];

        List<double[]> vectorsA = new ArrayList<>();
        List<double[]> vectorsB = new ArrayList<>();
        for (int i = 0; i < sentenceA.size(); i++) {
            for (int j = 0; j < sentenceB.size(); j++) {
//                double[] vectorA = ResponseLibrary.WORD2VEC.getWordVector(sentenceA.get(i));
//                double[] vectorB = ResponseLibrary.WORD2VEC.getWordVector(sentenceB.get(j));
//                distanceMatrix[i][j] = Distances.euclideanDistance(vectorA, vectorB);
//                vectorsA.add(vectorA);
//                vectorsB.add(vectorB);
            }
        }

        double[] weightA = new double[vectorsA.get(0).length];
        for (int i = 0; i < vectorsA.size(); i++) {
            for (int j = 0; j < vectorsA.get(0).length; j++) {
                weightA[i] += vectorsA.get(i)[j];
            }
        }

        double[] weightB = new double[vectorsB.get(0).length];
        for (int i = 0; i < vectorsB.size(); i++) {
            for (int j = 0; j < vectorsB.get(0).length; j++) {
                weightB[i] += vectorsB.get(i)[j];
            }
        }

        //Sinkhorn-knnop
        double[][] transportMatrix = new double[sentenceA.size()][sentenceB.size()];
        double[] source = new double[sentenceA.size()];
        double[] target = new double[sentenceB.size()];
        int iterations = 20;
        double lambda = -1;

        for (int k = 0; k < iterations; k++) {

            for (int i = 0; i < distanceMatrix.length; i++) {
                double sum = 0;
                for (int j = 0; j < distanceMatrix[0].length; j++) {
                    sum += distanceMatrix[i][j];
                }
                source[i] = Math.log(weightA[i] + 1e-8) - lambda * sum;
            }

            for (int i = 0; i < distanceMatrix[0].length; i++) {
                double sum = 0;
                for (int j = 0; j < distanceMatrix.length; j++) {
                    sum += distanceMatrix[j][i];
                }
                target[i] = Math.log(weightB[i] + 1e-8) - lambda * sum;
            }

            for (int i = 0; i < distanceMatrix.length; i++) {
                for (int j = 0; j < distanceMatrix[0].length; j++) {
                    transportMatrix[i][j] = Math.exp(source[i] + target[j] - lambda * distanceMatrix[i][j]);
                }
            }

            double[] rowSums = Arrays.stream(transportMatrix).mapToDouble(row -> Arrays.stream(row).sum()).toArray();
            double[] colSums = Arrays.stream(transportMatrix).mapToDouble(row -> Arrays.stream(row).sum()).toArray();
            double err = Arrays.stream(rowSums).sum() - Arrays.stream(colSums).sum();
            lambda -= err / transportMatrix.length;
        }


        double WMD = 0;
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix[0].length; j++) {
                WMD += transportMatrix[i][j] * distanceMatrix[i][j];
            }
        }


        return WMD;
    }

}

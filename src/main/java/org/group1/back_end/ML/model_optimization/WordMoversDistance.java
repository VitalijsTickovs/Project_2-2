package org.group1.back_end.ML.model_optimization;

import org.group1.back_end.response.ResponseLibrary;
import org.group1.back_end.utilities.algebra.Distances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordMoversDistance {

    public static double otra(List<String> sentenceA, List<String> sentenceB){

        // WE CREATE COST MATRIX
        double[][] costMatrix = new double[sentenceA.size()][sentenceB.size()];

        List<double[]> vectorsA = new ArrayList<>();
        List<double[]> vectorsB = new ArrayList<>();
        for (int i = 0; i < sentenceA.size(); i++) {
            for (int j = 0; j < sentenceB.size(); j++) {

//                double[] vectorA = ResponseLibrary.WORD2VEC.getWordVector(sentenceA.get(i));
//                double[] vectorB = ResponseLibrary.WORD2VEC.getWordVector(sentenceB.get(j));
//                if(vectorA == null){
//                    vectorA = new double[300];
//                }
//                if(vectorB == null){
//                    vectorB = new double[300];
//                }
//
//                costMatrix[i][j] = Distances.euclideanDistance(vectorA, vectorB);
//                vectorsA.add(vectorA);
//                vectorsB.add(vectorB);
            }
        }
        double[] source = new double[sentenceA.size()];
        double[] target = new double[sentenceB.size()];
        int n = source.length;
        int m = target.length;

        double total = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                source[i] += costMatrix[i][j];
            }
            total += source[i];
        }

        for (int i = 0; i < n; i++) {
            source[i] /= total;
        }

        Arrays.fill(target, 1d / m);

        double[][] p = new double[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                p[i][j] = Math.exp(-costMatrix[i][j]);
            }
        }
        double error = 1;
        double[] row = new double[n];
        double[] col = new double[m];
        int breakPoint = 0;
        while (error > 0.001) {
            for (int i = 0; i < n; i++) {
                double sum = 0;
                for (int j = 0; j < m; j++) {
                    sum += p[i][j];
                }
                row[i] = source[i] / sum;
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    p[i][j] *= row[i];
                }
            }
            for (int j = 0; j < m; j++) {
                double sum = 0;
                for (int i = 0; i < n; i++) {
                    sum += p[i][j];
                }
                col[j] = target[j] / sum;
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    p[i][j] *= col[j];
                }
            }
            error = 0;
            for (int i = 0; i < n; i++) {
                double sum = 0;
                for (int j = 0; j < m; j++) {
                    sum += p[i][j];
                }
                error = Math.max(error, Math.abs(sum - source[i]));
            }


            if(breakPoint > 10){
                break;
            }
            breakPoint++;
        }

        double minCost = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                minCost += p[i][j] * costMatrix[i][j];
            }
        }

        return minCost;

    }

}

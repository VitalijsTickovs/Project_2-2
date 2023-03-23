package org.group1.back_end.response.skills.databases;

import org.group1.back_end.response.ResponseLibrary;
import org.group1.back_end.textprocessing.ComplexProcess;
import org.group1.back_end.textprocessing.SimpleProcess;
import org.group1.back_end.utilities.algebra.Distances;
import org.group1.back_end.utilities.algebra.Matrix;
import java.util.*;

public class DB_VectorsSequence implements iDataBase{

    public static Map<List<String>, String> QUERY_VECTOR_MATCHING;

    public DB_VectorsSequence() {

        QUERY_VECTOR_MATCHING = new HashMap<>();

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




    private static double otra(List<String> sentenceA, List<String> sentenceB){

        // WE CREATE COST MATRIX
        double[][] costMatrix = new double[sentenceA.size()][sentenceB.size()];

        List<double[]> vectorsA = new ArrayList<>();
        List<double[]> vectorsB = new ArrayList<>();
        for (int i = 0; i < sentenceA.size(); i++) {
            for (int j = 0; j < sentenceB.size(); j++) {

                double[] vectorA = ResponseLibrary.WORD2VEC.getWordVector(sentenceA.get(i));
                double[] vectorB = ResponseLibrary.WORD2VEC.getWordVector(sentenceB.get(j));
                if(vectorA == null){
                    vectorA = new double[300];
                }
                if(vectorB == null){
                    vectorB = new double[300];
                }

                costMatrix[i][j] = Distances.euclideanDistance(vectorA, vectorB);
                vectorsA.add(vectorA);
                vectorsB.add(vectorB);
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

    private static double wordMoverDistance(List<String> sentenceA, List<String> sentenceB){

        // WE CREATE COST MATRIX
        double[][] costMatrix = new double[sentenceA.size()][sentenceB.size()];
        List<double[]> vectorsA = new ArrayList<>();
        List<double[]> vectorsB = new ArrayList<>();
        for (int i = 0; i < sentenceA.size(); i++) {
            for (int j = 0; j < sentenceB.size(); j++) {
                double[] vectorA = ResponseLibrary.WORD2VEC.getWordVector(sentenceA.get(i));
                double[] vectorB = ResponseLibrary.WORD2VEC.getWordVector(sentenceB.get(j));
                costMatrix[i][j] = Distances.euclideanDistance(vectorA, vectorB);
                vectorsA.add(vectorA);
                vectorsB.add(vectorB);
            }
        }

        double lambda = 1;
        double[][] pMatrix = new double[sentenceA.size()][sentenceB.size()];
        double[] source = new double[sentenceA.size()];
        double[] target = new double[sentenceB.size()];

        // WE CREATE TRANSPORT MATRIX P
        for (int i = 0; i < pMatrix.length; i++) {
            for (int j = 0; j < pMatrix[0].length; j++) {
                pMatrix[i][j] = Math.exp(-costMatrix[i][j] / lambda);
            }
        }

        double sumP = 0;
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < target.length; j++) {
                sumP += pMatrix[i][j];
            }
        }
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < target.length; j++) {
                pMatrix[i][j] /= sumP;
            }
        }

        double[][] sourceMatrix = new double[source.length][1];
        for (int i = 0; i < source.length; i++) {
            sourceMatrix[i][0] = source[i];
        }

        double[][] targetMatrix = new double[1][target.length];
        for (int i = 0; i < target.length; i++) {
            targetMatrix[0][i] = target[i];
        }

        double[][] transport_matrix = new double[costMatrix.length][costMatrix[0].length];

        double err = 1.0;
        double eps = 0.1;
        int breakPoint = 0;



        while (err > eps) {



            double[][] row_ratio = Matrix.divideMatrixByScalar(sourceMatrix, Matrix.sum(pMatrix, 1));

            for (int i = 0; i < transport_matrix.length; i++) {
                for (int j = 0; j < transport_matrix[0].length; j++) {
                    transport_matrix[i][j] = pMatrix[i][j] + row_ratio[i][0];
                }
            }

            double[][] col_ratio = Matrix.divideMatrixByScalar(targetMatrix, Matrix.sum(pMatrix, 0));
            for (int i = 0; i < transport_matrix[0].length; i++) {
                for (int j = 0; j < transport_matrix.length; j++) {
                    transport_matrix[j][i] = pMatrix[j][i] + col_ratio[0][i];
                }
            }


            double[] rowSums = new double[pMatrix.length];
            for (int i = 0; i < pMatrix.length; i++) {
                for (int j = 0; j < pMatrix[0].length; j++) {
                    rowSums[i] += pMatrix[i][j];
                }
            }

            double[] colSums = new double[pMatrix[0].length];
            for (int i = 0; i < pMatrix[0].length; i++) {
                for (int j = 0; j < pMatrix.length; j++) {
                    colSums[i] += pMatrix[j][i];
                }
            }

            double maxDiff = 0;
            for (int i = 0; i < rowSums.length; i++) {
                double diff = Math.abs(rowSums[i] - source[i]);
                if (diff > maxDiff) {
                    maxDiff = diff;
                }
            }
            err = maxDiff;
            System.out.println(err);
            if(breakPoint > 10){
                break;
            }
            breakPoint++;

        }

        double minCost = 0;
        for (int i = 0; i < transport_matrix.length; i++) {
            for (int j = 0; j < transport_matrix[0].length; j++) {
                minCost += transport_matrix[i][j] * costMatrix[i][j];
            }
        }

        return minCost;


    }

    private static double WMD(List<String> sentenceA, List<String> sentenceB){

        double[][] distanceMatrix = new double[sentenceA.size()][sentenceB.size()];

        List<double[]> vectorsA = new ArrayList<>();
        List<double[]> vectorsB = new ArrayList<>();
        for (int i = 0; i < sentenceA.size(); i++) {
            for (int j = 0; j < sentenceB.size(); j++) {
                double[] vectorA = ResponseLibrary.WORD2VEC.getWordVector(sentenceA.get(i));
                double[] vectorB = ResponseLibrary.WORD2VEC.getWordVector(sentenceB.get(j));
                distanceMatrix[i][j] = Distances.cosine(vectorA, vectorB);
                vectorsA.add(vectorA);
                vectorsB.add(vectorB);
            }
        }

        double[] weightA = new double[vectorsA.get(0).length];
        for (int i = 0; i < vectorsA.size(); i++) {
            for (int j = 0; j < vectorsA.get(0).length; j++) {
                weightA[j] += vectorsA.get(i)[j];
            }
        }

        double[] weightB = new double[vectorsB.get(0).length];
        for (int i = 0; i < vectorsB.size(); i++) {
            for (int j = 0; j < vectorsB.get(0).length; j++) {
                weightB[j] += vectorsB.get(i)[j];
            }
        }

        //Sinkhorn-knnop
        double[][] transportMatrix = new double[sentenceA.size()][sentenceB.size()];
        double[] source = new double[sentenceA.size()];
        double[] target = new double[sentenceB.size()];
        int iterations = 50;
        double lambda = 5;

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

            double[] rowSums = new double[transportMatrix.length];
            for (int i = 0; i < transportMatrix.length; i++) {
                for (int j = 0; j < transportMatrix[0].length; j++) {
                    rowSums[i] += transportMatrix[i][j];
                }
            }

            double[] colSums = new double[transportMatrix[0].length];
            for (int i = 0; i < transportMatrix[0].length; i++) {
                for (int j = 0; j < transportMatrix.length; j++) {
                    colSums[i] += transportMatrix[j][i];
                }
            }

            double r = 0;
            for (int i = 0; i < rowSums.length; i++) {
                r += rowSums[i];
            }

            double c = 0;
            for (int i = 0; i < colSums.length; i++) {
                c += colSums[i];
            }

            double err = r - c;
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

    private static double wmd(
            List<double[]> vectorSpaceKey,
            List<double[]> vectorSpaceValue,
            List<String> wordsKey,
            List<String> wordsValue){

        double distance = 0.0;
        double totalWeight = 0.0;
        int i = 0;
        System.out.println(vectorSpaceKey.size());
        System.out.println(wordsKey);

        for (double[] vectorA : vectorSpaceKey) {
            double minDistance = Double.MAX_VALUE;
            for (double[] vectorB : vectorSpaceKey){
                double euclideanDistance = Distances.euclideanDistance(vectorA, vectorB);
                if(euclideanDistance < minDistance){
                    minDistance = euclideanDistance;
                }
            }
            double w = Collections.frequency(wordsKey, wordsKey.get(i));
            i++;
            distance += minDistance * w;
            totalWeight += w;
        }

        i = 0;
        for (double[] vectorB : vectorSpaceValue) {
            double minDistance = Double.MAX_VALUE;
            for (double[] vectorA : vectorSpaceKey){
                double euclideanDistance = Distances.euclideanDistance(vectorA, vectorB);
                if(euclideanDistance < minDistance){
                    minDistance = euclideanDistance;
                }
            }
            double w = Collections.frequency(wordsValue, wordsValue.get(i));
            distance += minDistance * w;
            totalWeight += w;
        }
        return distance / totalWeight;
    }


    @Override
    public void printDistances(String key) {

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
    }
}

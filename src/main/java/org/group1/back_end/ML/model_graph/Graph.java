package org.group1.back_end.ML.model_graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Graph {

    double[] vector;
    int[][] adjMatrix;
    List<GraphNode> nodes;
    public Graph(Set<String> vocabulary){
        nodes = new ArrayList<>();
        createNodes(vocabulary);
        adjMatrix = new int[nodes.size()][nodes.size()];
        vector = new double[nodes.size()];
    }

    public void createNodes(Set<String> vocabulary) {
        for (String word : vocabulary) {
            nodes.add(new GraphNode(word));
        }
    }

    public void connectNodes(GraphNode A, GraphNode B){

        int i = 0;
        for (GraphNode compareA : nodes) {
            int j = 0;
            for (GraphNode compareB : nodes) {
                if (A.equals(compareA) && B.equals(compareB)){

                    compareA.add(B);
                    compareB.add(A);

                    adjMatrix[i][j] += 1;
                    adjMatrix[j][i] += 1;
                }
                j++;
            }
            i++;
        }
    }

    public void iteration2(){
        double[] initial = new double[adjMatrix.length];

        int i = 0;
        for (GraphNode node : nodes) {
            initial[i] = node.getPR_value();
            i++;
        }

        double[] solution = matrixVectorMultiplication(adjMatrix, initial);
        i = 0;
        for (GraphNode node : nodes) {
            node.setPR_value(solution[i]);
            i++;
        }

        vector = solution;

    }

    public static double[] matrixVectorMultiplication(int[][] matrix, double[] vector) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        double[] result = new double[rows];
        for (int i = 0; i < rows; i++) {
            double sum = 0.0;
            for (int j = 0; j < columns; j++) {
                sum += matrix[i][j] * vector[j];
            }
            result[i] = sum;
        }
        return result;
    }

    public void iteration(){
        for (GraphNode node : nodes) {
            node.linearEq();
        }
    }
}



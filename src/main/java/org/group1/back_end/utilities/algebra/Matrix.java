package org.group1.back_end.utilities.algebra;

public class Matrix {

    public static double sum(double[][] arr, int index) {
        double sum = 0.0;
        for (int j = 0; j < arr[index].length; j++) {
            sum += arr[index][j];
        }
        return sum;
    }

    public static double[][] multiply(double[][] a, double[][] b) {
        int aRows = a.length;
        int aColumns = a[0].length;
        int bRows = b.length;
        int bColumns = b[0].length;
        if (aColumns != bRows) {
            throw new IllegalArgumentException("Las matrices no son compatibles para multiplicación");
        }
        double[][] result = new double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                for (int k = 0; k < aColumns; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    public static double[][] divide(double[][] a, double[][] b) {
        int aRows = a.length;
        int aColumns = a[0].length;
        int bRows = b.length;
        int bColumns = b[0].length;
        if (aRows != bRows || aColumns != bColumns) {
            throw new IllegalArgumentException("Las matrices no tienen las mismas dimensiones para la división");
        }
        double[][] result = new double[aRows][aColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < aColumns; j++) {
                result[i][j] = a[i][j] / b[i][j];
            }
        }
        return result;
    }

    public static double[][] divideMatrixByScalar(double[][] matrix, double scalar) {
        int numRows = matrix.length;
        int numCols = matrix[0].length;
        double[][] result = new double[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                result[i][j] = matrix[i][j] / scalar;
            }
        }
        return result;
    }


    public static double[][] subtract(double[][] a, double[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("Matrices must have the same dimensions");
        }
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }

}

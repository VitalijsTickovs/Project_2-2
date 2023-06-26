package org.group1.back_end.Camera.Classifiers.FaceNetSupport;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Credit for this class to: https://www.javatpoint.com/real-time-face-recognition-in-java
 * This class is used to load a pre-trained facenet embedding model
 * and use it to generate embeddings for faces
 * Model from: https://github.com/davidsandberg/facenet, which has training dataset: VGGFace2
 */

public class FaceNetEmbedding {

    public static final String facenetPath = "src/main/resources/FaceNet/FaceNetModel.pb";
    private Graph graph;
    private Session session;

    public FaceNetEmbedding(){}

    public void loadModel(){
        System.out.println("Loading FaceNet model...");
        graph = new Graph();
        byte[] modelBytes = readAllBytesOrExit(Paths.get(facenetPath));
        graph.importGraphDef(modelBytes);
        session = new Session(graph);
    }

    public float[] getEmbedding(Mat face) {
        float[][] embedding = null;
        try (Tensor<Float> tensor = normalizeImage(face)) {
            Tensor<Boolean> phaseTrain = (Tensor<Boolean>) Tensor.create(false);
            Tensor<Float> output = session.runner()
                    .feed("input", tensor)
                    .feed("phase_train", phaseTrain)
                    .fetch("embeddings")
                    .run()
                    .get(0)
                    .expect(Float.class);
            embedding = new float[(int) output.shape()[0]][(int) output.shape()[1]];
            output.copyTo(embedding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return linearize(embedding);

    }

    private float[] linearize(float[][] embedding){
        float[] linearized = new float[embedding.length * embedding[0].length];
        for(int i = 0; i < embedding.length; i++){
            for(int j = 0; j < embedding[0].length; j++){
                linearized[i * embedding[0].length + j] = embedding[i][j];
            }
        }
        return linearized;
    }


    private Tensor<Float> normalizeImage(Mat mat) {
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2RGB);
        mat.convertTo(mat, CvType.CV_32F);
        Core.divide(mat, Scalar.all(255.0f), mat);

        Size size = new Size(160, 160);
        Imgproc.resize(mat, mat, size);

        int w = mat.width();
        int h = mat.height();
        int c = mat.channels();

        // Create a float array
        float[][][][] array = new float[1][h][w][c];

        // Populate the float array from mat data
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                double[] data = mat.get(i, j);
                for (int k = 0; k < c; k++) {
                    array[0][i][j][k] = (float) data[k];
                }
            }
        }

        return (Tensor<Float>) Tensor.create(array);
    }

    private static byte[] readAllBytesOrExit(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

}

package org.group1.back_end.Camera.Support;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.group1.back_end.Camera.Managers.Authenticator;
import org.group1.back_end.Camera.Managers.CascadeManager;
import org.group1.back_end.Camera.Util.Color;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

public class CascadeCamera {
    static final int FPS = 60;

    VideoCapture camera;

    CascadeManager manager;

    Authenticator authenticator;

    /**
     * Constructor for the Camera class
     */
    public CascadeCamera(CascadeManager manager, Authenticator authenticator){
        nu.pattern.OpenCV.loadLocally();
        this.manager = manager;
        this.camera = new VideoCapture(0);
        this.authenticator = authenticator;
        this.turnOn();
    }

    private void turnOn() {
        this.camera.open(0); // open the camera
    }

    private void turnOff() {
        this.camera.release(); // release the camera
    }

    public Mat getImage() {
        Mat framemat = new Mat();
        this.camera.read(framemat);
        return framemat;
    }

    public BufferedImage getBufImage() {
        Mat mat = getImage();
        mat = manager.detect(mat);
        return matToBufferedImage(mat);
    }
    public boolean detect() {
        Mat mat = getImage();
        return this.authenticator.detect(mat);
    }

    public void display() throws Exception {
        Thread displayThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Creating a frame to display the webcam output
                    JFrame frame = new JFrame("Web Cam");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setResizable(false);

                    CanvasFrame canvas = new CanvasFrame("Web Cam");
                    canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;

                    while (true) {
                        Mat mat = getImage();
                        System.out.println(authenticator.detect(mat));
                        mat = manager.detect(mat);
                        canvas.showImage(new OpenCVFrameConverter.ToMat().convert(mat));
                        Thread.sleep(1000 / FPS);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        displayThread.start();
    }

    private static BufferedImage matToBufferedImage(Mat mat) {
        // Convert the Mat to a byte array
        int bufferSize = mat.channels() * mat.cols() * mat.rows();
        byte[] buffer = new byte[bufferSize];
        mat.get(0, 0, buffer);

        // Create a BufferedImage with the same dimensions and type as the Mat
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), BufferedImage.TYPE_3BYTE_BGR);

        // Get the underlying byte array of the BufferedImage
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

        // Copy the data from the Mat to the BufferedImage
        System.arraycopy(buffer, 0, data, 0, buffer.length);

        return image;
    }

}

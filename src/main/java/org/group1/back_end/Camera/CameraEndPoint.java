package org.group1.back_end.Camera;


import org.group1.back_end.Camera.Classifiers.Cascade;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.videoio.VideoCapture;
import org.group1.back_end.Camera.Classifiers.*;
import org.opencv.videoio.Videoio;
import nu.pattern.OpenCV.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import nu.pattern.OpenCV.*;

public class CameraEndPoint{

    FaceNet faceNet;
    Cascade cascade;
    boolean useFaceNet;
    VideoCapture camera;

    public CameraEndPoint() throws Exception {
        this(true);
    }

    public CameraEndPoint(boolean useFaceNet) throws Exception{
        nu.pattern.OpenCV.loadLocally();
        cascade = new Cascade("src/main/resources/classifier/haarcascade_frontalface_alt2.xml");
        if(useFaceNet && !isAppleM1OrM2()) {
            faceNet = new FaceNet(cascade);
            this.useFaceNet = true;
        }else{
            this.useFaceNet = false;
        }

        // Camera setup
        camera = new VideoCapture(0);
        this.camera.open(0); // open the camera
        if (camera.isOpened()) {
            camera.set(Videoio.CAP_PROP_FRAME_WIDTH, Visualizer.WIDTH);
            camera.set(Videoio.CAP_PROP_FRAME_HEIGHT, Visualizer.HEIGHT);
        }
    }

    public static boolean isAppleM1OrM2() {
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "sysctl -n machdep.cpu.brand_string");
        Process process;
        try {
            process = processBuilder.start();

            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Apple M1") || line.contains("Apple M2")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Mat paint(){
        Mat img = new Mat();
        camera.read(img);
        if(img.empty() || img == null) return null;
        return paint(img);
    }

    public boolean authenticate(){
        Mat img = new Mat();
        camera.read(img);
        if(img.empty() || img == null) return false;
        return authenticate(img);
    }

    public void addUser(String name) throws Exception{
        Mat img = new Mat();
        camera.read(img);
        MatOfRect detections = cascade.getBound(img);

        if(img.empty() || img == null) return;
        if(faceNet.detections(img).size() > 1) throw new Exception("Multiple faces detected");
        if(detections.toArray().length > 1) throw new Exception("More then 1 face detected");
        if(detections.toArray().length < 1) throw new Exception("No face detected");
        faceNet.addUser(name, img);
    }

    public String detect(){
        Mat img = new Mat();
        camera.read(img);
        if(img.empty() || img == null) return null;
        return detect(img);
    }

    private Mat paint(Mat img){
        if(useFaceNet) return faceNet.paint(img);
        else return cascade.paint(img);
    }

    private boolean authenticate(Mat img){
        if(useFaceNet) return faceNet.authenticate(img);
        else return cascade.authenticate(img);
    }

    private String detect(Mat img){
        if(useFaceNet) return faceNet.detect(img);
        else return cascade.detect(img);
    }
}

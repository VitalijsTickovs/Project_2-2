package org.group1.back_end.Camera;


import org.group1.back_end.Camera.Classifiers.Cascade;
import org.group1.back_end.Camera.Classifiers.FaceNet;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.videoio.VideoCapture;


public class CameraEndPoint{

    FaceNet faceNet;
    Cascade cascade;
    boolean useFaceNet = true;
    VideoCapture camera;

    public CameraEndPoint() throws Exception {
        this(true);
    }

    public CameraEndPoint(boolean useFaceNet) throws Exception{
        nu.pattern.OpenCV.loadLocally();
        cascade = new Cascade("/Users/tombakker/Documents/Projects/GR/FaceAuthentication/src/main/resources/classifier/haarcascade_frontalface_alt2.xml");
        faceNet = new FaceNet(cascade);
        this.useFaceNet = useFaceNet;

        // Camera setup
        camera = new VideoCapture(0);
        this.camera.open(0); // open the camera
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
        if(detections.toArray().length <= 1) throw new Exception("No face detected");
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

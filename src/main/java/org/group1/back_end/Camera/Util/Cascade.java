package org.group1.back_end.Camera.Util;

import javafx.util.Pair;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.objdetect.CascadeClassifier;

public class Cascade {

    String path;
    CascadeClassifier classifier;

    public Color color;
    public Cascade(String cascadePath){
        this.path = cascadePath;
        this.classifier = new CascadeClassifier(this.path);
        this.color = new Color(0, 0, 0);
    }

    public Cascade(String cascadePath, Color color){
        this.color = color;
        this.path = cascadePath;
        this.classifier = new CascadeClassifier(this.path);
    }

    public Pair<Boolean, MatOfRect> detect(Mat img){
        if(img == null) return null;
        MatOfRect detections = new MatOfRect();
        try{
            classifier.detectMultiScale(img, detections);
            return new Pair<>(detections.toArray().length!=0, detections);
        } catch (Exception e) {
            System.out.println(e);
            return new Pair<>(false, null);
        }
    }
}

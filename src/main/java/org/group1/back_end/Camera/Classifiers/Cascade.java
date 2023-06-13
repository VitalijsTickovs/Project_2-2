package org.group1.back_end.Camera.Classifiers;

import javafx.util.Pair;

import org.group1.back_end.Camera.Util.Color;
import org.group1.back_end.Camera.Util.MicrosoftPaint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.objdetect.CascadeClassifier;

public class Cascade implements Recognition {

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

    @Override
    public Mat paint(Mat img) {
        return MicrosoftPaint.paint(img, getBounds(img).getValue(), this.color);
    }

    @Override
    public boolean authenticate(Mat img) {
        return getBounds(img).getKey();
    }

    @Override
    public String detect(Mat img){
        return "Unknown";
    }

    public MatOfRect getBound(Mat img){
        return getBounds(img).getValue();
    }

    private Pair<Boolean, MatOfRect> getBounds(Mat img){
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

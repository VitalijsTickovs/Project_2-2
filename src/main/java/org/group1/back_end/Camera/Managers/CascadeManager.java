package org.group1.back_end.Camera.Managers;

import javafx.util.Pair;
import org.group1.back_end.Camera.Util.Cascade;
import org.group1.back_end.Camera.Util.Color;
import org.group1.back_end.Camera.Util.MicrosoftPaint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;

import java.util.List;

public class CascadeManager extends DetectionManager{

    public CascadeManager(List<String> paths){
        super(paths);
    }

    public CascadeManager(List<String> paths, List<Color> colors){
        super(paths, colors);
    }

    public CascadeManager(String paths){
        super(paths);
    }

    public Mat detect(Mat img){
        for(Cascade classifier: this.classifiers){
            Pair<Boolean, MatOfRect> detection = classifier.detect(img);
            if(detection.getKey()){
                img =  MicrosoftPaint.paint(img, detection.getValue(), classifier.color);
            }
        }
        return img;
    }
}

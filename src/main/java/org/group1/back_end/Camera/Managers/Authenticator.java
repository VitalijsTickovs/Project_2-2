package org.group1.back_end.Camera.Managers;

import javafx.util.Pair;
import org.group1.back_end.Camera.Util.Cascade;
import org.group1.back_end.Camera.Util.Color;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;

import java.util.List;

public class Authenticator extends DetectionManager{

    public Authenticator(List<String> paths) {
        super(paths);
    }

    public Authenticator(List<String> paths, List<Color> colors) {
        super(paths, colors);
    }

    public Authenticator(String paths) {
        super(paths);
    }

    public boolean detect(Mat img){
        for(Cascade classifier: this.classifiers){
            Pair<Boolean, MatOfRect> detection = classifier.detect(img);
            if(detection.getKey()){
                return true;
            }
        }
        return false;
    }
}

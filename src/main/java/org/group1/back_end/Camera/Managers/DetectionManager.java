package org.group1.back_end.Camera.Managers;


import org.group1.back_end.Camera.Util.Cascade;
import org.group1.back_end.Camera.Util.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class DetectionManager {

    List<Cascade> classifiers;

    public DetectionManager(String paths){
        nu.pattern.OpenCV.loadLocally();
        classifiers = new ArrayList<>();
        addClassifier(paths);
    }

    public DetectionManager(List<String> paths){
        nu.pattern.OpenCV.loadLocally();
        classifiers = new ArrayList<>();
        addClassifier(paths);
    }
    public DetectionManager(List<String> paths, List<Color> colors){
        nu.pattern.OpenCV.loadLocally();
        classifiers = new ArrayList<>();
        addClassifier(paths, colors);
    }


    private void addClassifier(List<String> paths, List<Color> colors){
        int count = 0;
        for(String path: paths){
            if(count < colors.size()) this.classifiers.add(new Cascade(path, colors.get(count)));
            else addClassifier(path);
            count++;
        }
    }

    private void addClassifier(List<String> paths){
        for(String path: paths){
            addClassifier(path);
        }
    }

    private void addClassifier(String path){
        this.classifiers.add(new Cascade(path));
    }
}

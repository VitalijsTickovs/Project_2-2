package org.group1.back_end.Camera.Util;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class ImageReader {

    public static Mat readImage(String path){
        Mat img = Imgcodecs.imread(path);
        return img.empty() ? null : img;
    }

}

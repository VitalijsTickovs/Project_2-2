package org.group1.back_end.Camera.Util;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import org.opencv.core.Scalar;

import java.util.List;

public class MicrosoftPaint {

    public static Mat paint(Mat img, List<MatOfRect> faces){
        Mat toreturn = img;
        for(MatOfRect rect: faces){
            toreturn = paint(toreturn, rect);
        }
        return toreturn;
    }

    public static Mat paint(Mat img, MatOfRect faces){
        // Drawing boxes
        for (Rect rect : faces.toArray()) {
            Imgproc.rectangle(
                    img,
                    new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 0, 0),
                    3
            );
        }
        return img;
    }
    public static Mat paint(Mat img, MatOfRect faces, Color color){
        // Drawing boxes
        for (Rect rect : faces.toArray()) {
            Imgproc.rectangle(
                    img,
                    new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(color.b, color.g, color.r),
                    3
            );
        }
        return img;
    }
}

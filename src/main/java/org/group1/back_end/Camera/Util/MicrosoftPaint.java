package org.group1.back_end.Camera.Util;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import java.util.ArrayList;
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

    public static Mat paint(Mat img, MatOfRect faces, Color color, List<String> id){
        // Drawing boxes
        int i = 0;
        for (Rect rect : faces.toArray()) {
            Imgproc.rectangle(
                    img,
                    new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(color.b, color.g, color.r),
                    3
            );
            Imgproc.putText(
                    img,
                    id.get(i),
                    new Point(rect.x, rect.y),
                    1,
                    3,
                    new Scalar(color.b, color.g, color.r),
                    2
            );
            i++;
        }
        return img;
    }


    public static List<Mat> crop(Mat img, MatOfRect faces){
        List<Mat> croppedFaces = new ArrayList<>();

        // Drawing boxes
        for (Rect rect : faces.toArray()) {
            croppedFaces.add(img.submat(rect));
        }

        return croppedFaces;
    }

}

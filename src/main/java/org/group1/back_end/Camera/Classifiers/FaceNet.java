package org.group1.back_end.Camera.Classifiers;


import org.group1.back_end.Camera.Classifiers.FaceNetSupport.DBManager;
import org.group1.back_end.Camera.Classifiers.FaceNetSupport.FaceNetEmbedding;
import org.group1.back_end.Camera.Util.MicrosoftPaint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import java.util.ArrayList;
import java.util.List;

public class FaceNet implements Recognition{

    static final int FACENET_IMAGE_SIZE = 160;

    DBManager dbManager;
    Cascade cascade;

    public FaceNetEmbedding faceNetEmbedding;
    public FaceNet(Cascade cascade) throws Exception{
        faceNetEmbedding = new FaceNetEmbedding();
        faceNetEmbedding.loadModel();
        dbManager = new DBManager(faceNetEmbedding);
        this.cascade = cascade;
    }
    @Override
    public Mat paint(Mat img) {
        MatOfRect detections = cascade.getBound(img);
        return MicrosoftPaint.paint(img, detections, cascade.color, this.detections(img));
    }

    @Override
    public boolean authenticate(Mat img) {
        return cascade.authenticate(img);
    }

    @Override
    public String detect(Mat img) {
        List<Mat> cropped = MicrosoftPaint.crop(img, cascade.getBound(img));
        if(cropped.size() == 0) return "Unknown";
        if(cropped.size() == 1) return dbManager.getName(faceNetEmbedding.getEmbedding(cropped.get(0)));
        String names = "";
        for(Mat mat: cropped){
            names += dbManager.getName(faceNetEmbedding.getEmbedding(mat)) + "<->";
        }
        names = names.substring(0, names.length()-3);
        return names;
    }

    public List<String> detections(Mat img){
        List<Mat> cropped = MicrosoftPaint.crop(img, cascade.getBound(img));
        List<String> matches = new ArrayList<>();
        for(Mat mat: cropped){
            matches.add(dbManager.getName(faceNetEmbedding.getEmbedding(mat)));
        }
        return matches;
    }

    public void addUser(String name, Mat img) throws Exception{
        Mat cropped = MicrosoftPaint.crop(img, cascade.getBound(img)).get(0);
        if(cropped.width() < FACENET_IMAGE_SIZE || cropped.height() < FACENET_IMAGE_SIZE) throw new IllegalArgumentException("Image is too small");
        dbManager.addUser(name, cropped);
        this.reload();
    }

    public void reload(){
        dbManager.reload();
    }
}

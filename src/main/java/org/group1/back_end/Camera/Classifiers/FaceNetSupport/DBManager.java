package org.group1.back_end.Camera.Classifiers.FaceNetSupport;


import org.group1.back_end.Camera.Util.ImageReader;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import java.io.File;


public class DBManager {
    DB db;
    FaceNetEmbedding faceNetEmbedding;
    public static final String DB_PATH = "src/main/resources/FaceNet/Faces/";

    public DBManager(FaceNetEmbedding embedding) throws Exception {
        this(new DB(), embedding);
    }

    public DBManager(DB db, FaceNetEmbedding embedding) throws Exception {
        this.db = db;
        this.faceNetEmbedding = embedding;
        this.loadDirectory(DB_PATH);
    }

    public String getName(float[] embedding){
        return db.get(embedding);
    }

    public void loadDirectory(String path){
        System.out.println("Loading directory...");
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for(File file: listOfFiles){
            Mat img = ImageReader.readImage(file.getAbsolutePath());
            if(img != null){
                db.add(faceNetEmbedding.getEmbedding(img), filterExtension(file.getName()));
            }
        }
    }

    public void addUser(String name, Mat img){
        Imgcodecs.imwrite(DB_PATH+name+".jpg", img);
        db.add(faceNetEmbedding.getEmbedding(img), name);
    }

    public void reload(){
        db = new DB();
        loadDirectory(DB_PATH);
    }

    private static String filterExtension(String path){
        return path.replaceAll("\\..*", "");
    }

}

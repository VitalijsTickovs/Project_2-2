package org.group1.GUI.stage.scenes;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.bytedeco.javacv.CanvasFrame;
import org.group1.GUI.stage.StageManager;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.*;
import java.io.ByteArrayInputStream;

public class SceneManager extends Scene {
    public static int screenWidth = 900;
    public static int screenHeight = 700;
    protected AnchorPane UIPane;
    protected AnchorPane scrollChat = new AnchorPane();
    int detect=1;

    //Create Image Display
    public void display(int x,int y,int width) throws Exception {
        //Creating the image view
        ImageView imageView = new ImageView();
        //Setting image to the image view
        //Setting the image view parameters
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);

        UIPane.getChildren().add(imageView);
    }

    public Image mat2Image(Mat frame) {
        // create a temporary buffer
        MatOfByte buffer = new MatOfByte();
        // encode the frame in the buffer, according to the PNG format
        Imgcodecs.imencode(".png", frame, buffer);
        // build and return an Image created from the image encoded in the buffer
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }


    public SceneManager(){
        super(new AnchorPane(), screenWidth, screenHeight);
        setFill(Color.rgb(18,64,76));
        try{
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void createSideMenu(){
        Rectangle sideMenu = new Rectangle(0,0,250,screenHeight);
        sideMenu.setFill(Color.rgb(159,182,189));
        UIPane.getChildren().add(sideMenu);
    }

    public Text createText(String title, int posX, int posY){
        Text text = new Text(title);
        text.setFont(Font.font("Impact",40));
        text.setStyle("-fx-font-weight: bold");
        text.setFill(Color.WHITE);
        text.setTranslateX(posX);
        text.setTranslateY(posY);
        UIPane.getChildren().add(text);
        return text;
    }

    public void makeNewPane(){
        UIPane = new AnchorPane();
        setRoot(UIPane);
        UIPane.setStyle("-fx-background-color: transparent");
    }
}

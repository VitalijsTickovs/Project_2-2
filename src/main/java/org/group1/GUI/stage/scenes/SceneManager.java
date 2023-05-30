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
import org.group1.GUI.stage.StageManager;
import org.group1.back_end.Camera.CameraEndPoint;
import org.opencv.core.Mat;

import java.awt.*;

public class SceneManager extends Scene {
    public static int screenWidth = 900;
    public static int screenHeight = 700;
    protected AnchorPane UIPane;
    protected AnchorPane scrollChat = new AnchorPane();
    CameraEndPoint cameraEndPoint = new CameraEndPoint();
    int detect=1;
    public void startCameraAvailabilityCheck() {

        Thread cameraThread = new Thread(() -> {
            int count = 0;
            while (detect == 1 && count <= 20) {
                Mat mat = cameraEndPoint.cam.getImage();
                //System.out.println(cameraEndPoint.authenticator.detect(mat));
                System.out.println(detect);
                if (cameraEndPoint.authenticator.detect(mat)==false) {
                    detect=0;
                    count++;
                }else{
                    count = 0;
                }
            }
            Platform.runLater(() -> {
//                System.out.println(" fudhfsjhfsdkj");
//                SceneLogin sceneLogin = new SceneLogin();
//                StageManager.setScene(sceneLogin);
            });
        });

        cameraThread.setDaemon(true);
        cameraThread.start();
    }
    //Create Image Display
    public void display(int x,int y,int width) throws Exception {
        //Creating the image view
        ImageView imageView = new ImageView();
        //Setting image to the image view
        imageView.setImage(cameraEndPoint.getImage());
        //Setting the image view parameters
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);
        UIPane.getChildren().add(imageView);

        Thread cameraThread = new Thread(() -> {
            while (true) {
                Image image = cameraEndPoint.getImage();
                if (image != null) {
                    Mat mat = cameraEndPoint.cam.getImage();
                    //System.out.println(cameraEndPoint.authenticator.detect(mat));
                    mat = cameraEndPoint.manager.detect(mat);
                    imageView.setImage(image);
                }
            }
        });

        cameraThread.setDaemon(true);
        cameraThread.start();
    }

    public SceneManager(){
        super(new AnchorPane(), screenWidth, screenHeight);
        setFill(Color.rgb(18,64,76));
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

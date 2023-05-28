package org.group1.back_end.Camera;
import javafx.embed.swing.SwingFXUtils;
import org.group1.back_end.Camera.Managers.Authenticator;
import org.group1.back_end.Camera.Managers.CascadeManager;
import org.group1.back_end.Camera.Support.CascadeCamera;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.awt.*;


public class CameraEndPoint {

    static String path = "src/main/resources/CascadeClassifiers/haarcascade_frontalface_alt.xml";
    public static CascadeManager manager = new CascadeManager(path);
    public static Authenticator authenticator = new Authenticator(path);
    public static CascadeCamera cam = new CascadeCamera(manager, authenticator);


    public static boolean authenticate() {
//        return cam.detect();
        return true;
    }

    public static Image convertToJavaFXImage(BufferedImage bufferedImage) {
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    public static Image getImage(){
        return convertToJavaFXImage(cam.getBufImage());
    }

}
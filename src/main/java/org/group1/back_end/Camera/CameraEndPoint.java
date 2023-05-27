package org.group1.back_end.Camera;

import org.group1.back_end.Camera.Managers.Authenticator;
import org.group1.back_end.Camera.Managers.CascadeManager;
import org.group1.back_end.Camera.Support.CascadeCamera;

import java.awt.*;

public class CameraEndPoint {

    static String path = "src/main/resources/CascadeClassifiers/haarcascade_frontalface_alt.xml";
    static CascadeManager manager = new CascadeManager(path);
    static Authenticator authenticator = new Authenticator(path);
    static CascadeCamera cam = new CascadeCamera(manager, authenticator);


    public static boolean authenticate() {
//        return cam.detect();
        return true;
    }

    public static Image getImage(){
        return cam.getBufImage();
    }
}

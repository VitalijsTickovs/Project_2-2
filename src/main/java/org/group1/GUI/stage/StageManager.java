package org.group1.GUI.stage;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group1.GUI.stage.scenes.SceneLogin;
import org.group1.back_end.Camera.CameraEndPoint;
import org.group1.back_end.Camera.Visualizer;
import org.group1.back_end.response.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StageManager {
    protected static Stage UIstage = new Stage();
    protected Stage chatStage = new Stage();
    private static Response connection;

    private static CameraEndPoint endPoint;

    static {
        try {
            if(!isAppleM1OrM2()) {
                endPoint = new CameraEndPoint(false);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isAppleM1OrM2() {
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "sysctl -n machdep.cpu.brand_string");
        Process process;
        try {
            process = processBuilder.start();

            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Apple M1") || line.contains("Apple M2")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    private static Visualizer visualizer;

    static {
        try {
            if(!isAppleM1OrM2()) {
                visualizer = new Visualizer(endPoint);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    Thread faceIdentification;

    public StageManager(){
        try {
            connection = new Response();
            if(!isAppleM1OrM2()) {
                faceIdentification = new Thread(visualizer);
                faceIdentification.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        UIstage.setScene(new SceneLogin());
        UIstage.setResizable(false);
    }

    public void setStage(Stage mainStage){
        this.chatStage=mainStage;
        mainStage.close();
        UIstage.show();
    }

    public static String getResponse(String query){
        return connection.getResponse(query);
    }
    public static String getResponse(String query, boolean isMTD){
        return connection.getResponse(query, isMTD);
    }

    public static void setScene(Scene newScene){
        UIstage.setScene(newScene);
    }

    public static Response getConnection() {
        return connection;
    }

    public static void close(){
        UIstage.close();
    }
    public Stage getUIstage(){
        return UIstage;
    }

}

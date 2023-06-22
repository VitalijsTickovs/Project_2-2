package org.group1.GUI.stage;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group1.GUI.stage.scenes.SceneLogin;
import org.group1.back_end.Camera.CameraEndPoint;
import org.group1.back_end.Camera.Visualizer;
import org.group1.back_end.response.Response;

public class StageManager {
    protected static Stage UIstage = new Stage();
    protected Stage chatStage = new Stage();
    private static Response connection;

    private static CameraEndPoint endPoint;

    static {
        try {
            endPoint = new CameraEndPoint();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Visualizer visualizer;

    static {
        try {
            visualizer = new Visualizer(endPoint);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    Thread faceIdentification;

    public StageManager(){
        try {
            connection = new Response();
            faceIdentification = new Thread(visualizer);
            faceIdentification.start();
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

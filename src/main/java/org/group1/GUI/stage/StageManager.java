package org.group1.GUI.stage;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group1.GUI.stage.scenes.SceneLogin;
import org.group1.back_end.response.Response;

public class StageManager {
    protected static Stage UIstage = new Stage();
    protected Stage chatStage = new Stage();
    private static Response connection;

    public StageManager(){
        try {
            connection = new Response();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        UIstage.setScene(new SceneLogin());
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

package org.app.connection.frontEnd.stage;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.app.connection.Connection;
import org.app.connection.frontEnd.stage.scenes.SceneLogin;

public class StageManager {
    protected static Stage UIstage = new Stage();
    protected Stage chatStage = new Stage();
    private static Connection connection;

    public StageManager(){
        connection = new Connection();
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

    public static void close(){
        UIstage.close();
    }
    public Stage getUIstage(){
        return UIstage;
    }

}

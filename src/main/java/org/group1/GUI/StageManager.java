package org.group1.GUI;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public abstract class StageManager {
    protected int screenWidth = 900;
    protected int screenHeight = 700;
    protected AnchorPane UIpane = new AnchorPane();
    protected AnchorPane scrollChat = new AnchorPane();
    protected Scene UIscene = new Scene(UIpane, screenWidth,screenHeight);
    protected Stage UIstage = new Stage();
    protected Stage chatStage = new Stage();

    protected void initStage(){
        UIscene.setFill(Color.rgb(18,64,76));
        UIpane.setStyle("-fx-background-color: transparent");
        UIstage.setScene(UIscene);
    }

    public void setStage(Stage mainStage){
        this.chatStage=mainStage;
        mainStage.close();
        UIstage.show();
    }
    public void setStage(Stage mainStage,Stage chatStage){
        this.chatStage=chatStage;
        mainStage.close();
        UIstage.show();
    }

    public Stage getUIstage(){
        return UIstage;
    }

}

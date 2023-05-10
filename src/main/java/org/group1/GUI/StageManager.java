package org.group1.GUI;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    public void openChatWindow() {
        // Create the chat window
        ChatWindow chatWindow = null;
        try {
            chatWindow = new ChatWindow();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Set the stage for the chat window
        chatWindow.setStage(chatStage);
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

    public void createSideMenu(){
        Rectangle sideMenu = new Rectangle(0,0,250,screenHeight);
        sideMenu.setFill(Color.rgb(159,182,189));
        UIpane.getChildren().add(sideMenu);
    }

    public Text createText(String title, int posX, int posY){
        Text text = new Text(title);
        text.setFont(Font.font("Impact",40));
        text.setStyle("-fx-font-weight: bold");
        text.setFill(Color.WHITE);
        text.setTranslateX(posX);
        text.setTranslateY(posY);
        UIpane.getChildren().add(text);
        return text;
    }

    public Stage getUIstage(){
        return UIstage;
    }

}

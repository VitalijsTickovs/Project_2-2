package org.app.connection.frontEnd.stage.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SceneManager extends Scene {
    public static int screenWidth = 900;
    public static int screenHeight = 700;
    protected AnchorPane UIPane;
    protected AnchorPane scrollChat = new AnchorPane();
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

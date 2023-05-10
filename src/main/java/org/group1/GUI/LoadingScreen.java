package org.group1.GUI;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoadingScreen extends Stage {
    StackPane loadingScreen;
    public LoadingScreen() {
        loadingScreen = new StackPane();
        Text loading = new Text("Loading...");
        setText(loading,0, 0);
        Scene scene = new Scene(loadingScreen, 900, 700);
        loadingScreen.setStyle("-fx-background-color: transparent");
        scene.setFill(Color.rgb(18,64,76));
        setScene(scene);
    }
    private void setText(Text element, int posX, int posY){
        element.setFont(Font.font("Impact",100));
        element.setStyle("-fx-font-weight: bold");
        element.setFill(Color.WHITE);
        element.setTranslateX(posX);
        element.setTranslateY(posY);
        loadingScreen.getChildren().add(element);
    }
}
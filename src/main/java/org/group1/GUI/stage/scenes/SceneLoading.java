package org.group1.GUI.stage.scenes;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.deeplearning4j.core.util.UIDProvider;
import org.group1.GUI.stage.StageManager;

public class SceneLoading extends SceneManager {
    StackPane loadingScreen;
    public SceneLoading() {
        makeNewPane();
        loadingScreen = new StackPane();
        Text loading = new Text("Loading...");
        setText(loading,0, 0);

//        final Rectangle rect1 = new Rectangle(10, 10, 100, 100);
//        rect1.setArcHeight(20);
//        rect1.setArcWidth(20);
//        rect1.setFill(Color.RED);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), loading);
        ft.setFromValue(1.0);
        ft.setToValue(0.1);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        loadingScreen.setStyle("-fx-background-color: transparent");
        setRoot(loadingScreen);
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
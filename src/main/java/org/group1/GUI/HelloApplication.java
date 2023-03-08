package org.group1.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//TODO:
// - (1) write down the question - need to process it..
// - (2) excel style action input


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
         LoginScreen loginScreen= new LoginScreen();
         stage=loginScreen.getUIstage();
         stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
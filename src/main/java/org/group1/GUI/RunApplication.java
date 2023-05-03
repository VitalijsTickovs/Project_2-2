package org.group1.GUI;

import javafx.application.Application;
import javafx.stage.Stage;

public class RunApplication extends Application {
    @Override
    public void start(Stage stage){
         LoginScreen loginScreen= new LoginScreen();
         stage=loginScreen.getUIstage();
         stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
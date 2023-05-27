package org.group1.GUI;

import javafx.application.Application;
import javafx.stage.Stage;
import org.group1.back_end.response.Response;

public class RunApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        LoginScreen loginScreen= new LoginScreen();
        stage=loginScreen.getUIstage();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
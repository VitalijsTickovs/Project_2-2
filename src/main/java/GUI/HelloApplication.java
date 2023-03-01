package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
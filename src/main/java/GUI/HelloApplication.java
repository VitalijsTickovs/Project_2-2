package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
    // Please dont mind the cursed graphics will make it pretty later <33
    // for now just setting up the functionalities
    // will finish during the weekend :D
         LoginScreen loginScreen= new LoginScreen();
         stage=loginScreen.getUIstage();
         stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
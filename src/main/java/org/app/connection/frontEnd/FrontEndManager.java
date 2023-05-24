package org.app.connection.frontEnd;

import javafx.application.Application;
import javafx.stage.Stage;
import org.app.connection.Connection;
import org.app.connection.frontEnd.stage.scenes.SceneLogin;
import org.app.connection.frontEnd.stage.StageManager;

public class FrontEndManager extends Application {
    private Connection connection;

    public FrontEndManager(){}

    public FrontEndManager(Connection connection){
        this.connection = connection;
    }

    public void run(){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageManager stageManager = new StageManager();

        primaryStage = stageManager.getUIstage();
        primaryStage.show();
    }
}

package org.group1.GUI.stage.scenes;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.group1.GUI.stage.StageManager;
import org.group1.GUI.stage.scenes.utils.ButtonFactory;
import org.group1.back_end.Camera.CameraEndPoint;


public class SceneLogin extends SceneManager implements ICustomScene {
    //VIEW SETTINGS
    public Button loginButton;

    private final TextField loginTextField, passwordTextField;
    public SceneLogin(){
        makeNewPane();
        loginTextField = new TextField();
        passwordTextField = new PasswordField();
        design();
        keyboardHandler();
    }
    private void setText(Text element, int posX, int posY){
        element.setFont(Font.font("Impact",20));
        element.setStyle("-fx-font-weight: bold");
        element.setFill(Color.WHITE);
        element.setTranslateX(posX);
        element.setTranslateY(posY);
        UIPane.getChildren().add(element);
    }
    private void setTextField(TextField textField, int posY){
        textField.setPrefWidth(250);
        textField.setPrefHeight(30);
        textField.setTranslateX(550);
        textField.setTranslateY(posY);
        textField.setStyle("-fx-background-color: transparent;" +"-fx-border-width: 2px;" + "-fx-border-color:rgba(159,182,189,1);"+ "-fx-text-fill: white;");
        UIPane.getChildren().add(textField);
    }

    private void setTextElements(){
        Text username = new Text("Username: ");
        setText(username,550, 270);

        setTextField(loginTextField, 280);


        Text password = new Text("Password: ");
        setText(password, 550, 340);

        setTextField(passwordTextField, 350);

        Text logo1 = new Text("UM ");
        setText(logo1, 160, 340);
        logo1.setFont(Font.font("Impact",40));
        logo1.setFill(Color.rgb(75,105,116));

        Text logo = new Text("Chat ");
        setText(logo, 210, 340);
        logo.setFont(Font.font("Impact",40));
    }
    @Override
    public void design(){

        Line line = new Line(screenWidth/2.0,screenHeight/5.0,screenWidth/2.0,(screenHeight/5.0)*4);
        line.setStyle("-fx-stroke-width: 2px");
        line.setStroke(Color.rgb(159,182,189));
        UIPane.getChildren().add(line);

        Polygon triangle = new Polygon(150,340 ,150,310 ,120,340);
        triangle.setFill(Color.rgb(159,182,189));
        UIPane.getChildren().add(triangle);
        Polygon triangle1 = new Polygon(295,340 ,295,310 ,325,310);
        triangle1.setFill(Color.rgb(159,182,189));
        UIPane.getChildren().add(triangle1);

        setTextElements();

        loginButton = ButtonFactory.createButton("Login", 550, 400);
        loginButton.setStyle("-fx-background-color: rgba(159,182,189,1);");
        UIPane.getChildren().add(loginButton);
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: rgba(42,97,117,1)"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: rgba(159,182,189,1)"));
        loginButton.setOnAction(e -> {
            //close the existing stage
            if(CameraEndPoint.authenticate()) {
                //create and display the loading scree
                SceneLoading loadingScreen = new SceneLoading();
                StageManager.setScene(loadingScreen);

                // Start a separate thread to load the models
                Thread loadingThread = new Thread(() -> {
                    // Delays the loading, so it can display the new stage content
                    try {
                        Thread.sleep(6000); // Adjust the duration as needed
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    // Terminate loading screen and open the chat window
                    Platform.runLater(() -> {
                        SceneChat sceneChat = new SceneChat();
                        StageManager.setScene(sceneChat);
                    });
                });
                loadingThread.start();

            }else{
                loginButton.setText("Wrong Credentials");
                loginButton.setStyle("-fx-background-color: rgba(255,0,0,1);");
            }
        });

    }

    //@Override
    public void keyboardHandler() {
        this.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                StageManager.close();
                ke.consume();
            }else if(ke.getCode() == KeyCode.ENTER){
                //create and display the loading scree
                SceneLoading loadingScreen = new SceneLoading();
                StageManager.setScene(loadingScreen);

                // Start a separate thread to load the models
                Thread loadingThread = new Thread(() -> {
                    // Delays the loading, so it can display the new stage content
                    try {
                        Thread.sleep(3000); // Adjust the duration as needed
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    // Terminate loading screen and open the chat window
                    Platform.runLater(() -> {
                        SceneChat sceneChat = new SceneChat();
                        StageManager.setScene(sceneChat);
                    });
                });
                loadingThread.start();

            }
        });
    }


}

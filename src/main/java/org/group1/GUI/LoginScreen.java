package org.group1.GUI;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.group1.GUI.utils.ButtonFactory;
import org.group1.back_end.Camera.CameraEndPoint;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.group1.database.DatabaseCredentials.setPassword;
import static org.group1.database.DatabaseCredentials.setUsername;

public class LoginScreen extends StageManager implements ICustomStage {
    //VIEW SETTINGS
    public Button loginButton;

    private final TextField loginTextField, passwordTextField;
    public LoginScreen(){
        loginTextField = new TextField();
        passwordTextField = new PasswordField();

        initStage();
        design();
        keyboardHandler();
    }
    private void setText(Text element, int posX, int posY){
        element.setFont(Font.font("Impact",20));
        element.setStyle("-fx-font-weight: bold");
        element.setFill(Color.WHITE);
        element.setTranslateX(posX);
        element.setTranslateY(posY);
        UIpane.getChildren().add(element);
    }
    private void setTextField(TextField textField, int posX, int posY){
        textField.setPrefWidth(250);
        textField.setPrefHeight(30);
        textField.setTranslateX(posX);
        textField.setTranslateY(posY);
        textField.setStyle("-fx-background-color: transparent;" +"-fx-border-width: 2px;" + "-fx-border-color:rgba(159,182,189,1);"+ "-fx-text-fill: white;");
        UIpane.getChildren().add(textField);
    }
    Thread loadingThread = new Thread();
    private void setTextElements(){
        Text username = new Text("Username: ");
        setText(username,550, 270);

        setTextField(loginTextField, 550, 280);


        Text password = new Text("Password: ");
        setText(password, 550, 340);

        setTextField(passwordTextField, 550, 350);

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
        UIpane.getChildren().add(line);

        Polygon triangle = new Polygon(150,340 ,150,310 ,120,340);
        triangle.setFill(Color.rgb(159,182,189));
        UIpane.getChildren().add(triangle);
        Polygon triangle1 = new Polygon(295,340 ,295,310 ,325,310);
        triangle1.setFill(Color.rgb(159,182,189));
        UIpane.getChildren().add(triangle1);

        setTextElements();

        loginButton = ButtonFactory.createButton("Login", 550, 400);
        loginButton.setStyle("-fx-background-color: rgba(159,182,189,1);");
        UIpane.getChildren().add(loginButton);
        loginButton.setOnMouseEntered(e -> {
            loginButton.setStyle("-fx-background-color: rgba(42,97,117,1)");
        });
        loginButton.setOnMouseExited(e -> {
            loginButton.setStyle("-fx-background-color: rgba(159,182,189,1)");
        });
        loginButton.setOnAction(e -> {
            if(CameraEndPoint.authenticate()) {
                //close the existing stage
                UIstage.close();
                //create and display the loading scree
                LoadingScreen loadingScreen = new LoadingScreen();
                loadingScreen.show();

                // Start a separate thread to load the models
                Thread loadingThread = new Thread(() -> {
                    // Delays the loading so it can display the new stage content
                    try {
                        Thread.sleep(6000); // Adjust the duration as needed
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    // Terminate loading screen and open the chatwindow
                    Platform.runLater(() -> {
                        loadingScreen.close();
                        openChatWindow();
                    });
                });
                loadingThread.start();

            }else{
                loginButton.setText("Wrong Credentials");
                loginButton.setStyle("-fx-background-color: rgba(255,0,0,1);");
            }});
    }

    //@Override
    public void keyboardHandler() {
        UIscene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
                    UIstage.close();
                    ke.consume();
                }else if(ke.getCode() == KeyCode.ENTER){
                    ChatWindow chatWindow= null;
                    setPassword(passwordTextField.getText());
                    setUsername(loginTextField.getText());
                    try {
                        chatWindow = new ChatWindow();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    chatWindow.setStage(UIstage);

                }
            }
        });
    }


}

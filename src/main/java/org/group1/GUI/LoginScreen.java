package org.group1.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.group1.response.database.DatabaseCredentials;
import org.group1.response.database.SQLGUIConnection;

public class LoginScreen implements CustomStage {
    //VIEW SETTINGS
    private AnchorPane UIpane;
    private Stage UIstage;
    private Scene UIscene;
    public Stage exitStage;
    static int screenWidth = 900;
    static int screenHeight = 700;
    public Button loginButton;
    private Stage chatWindow;
    DatabaseCredentials databaseCredentials = new DatabaseCredentials();
    public LoginScreen(){
        UIpane = new AnchorPane();
        UIstage = new Stage();
        UIscene = new Scene(UIpane,screenWidth,screenHeight);
        UIscene.setFill(Color.rgb(18,64,76));
        UIpane.setStyle("-fx-background-color: transparent");
        UIstage.setScene(UIscene);
        design();
        keyboardHandler();
    }
    public Stage getUIstage(){
        return UIstage;
    }
    public void setStage(Stage mainStage){
        this.chatWindow=mainStage;
        mainStage.close();
        UIstage.show();
    }
    @Override
    public void design(){

        Line line = new Line(screenWidth/2.0,screenHeight/5.0,screenWidth/2.0,(screenHeight/5.0)*4);
        line.setStyle("-fx-stroke-width: 2px");
        line.setStroke(Color.rgb(159,182,189));

        UIpane.getChildren().add(line);



        Text username = new Text("Username: ");
        username.setFont(Font.font("Impact",20));
        username.setStyle("-fx-font-weight: bold");
        username.setFill(Color.WHITE);
        username.setTranslateX(550);
        username.setTranslateY(270);
        UIpane.getChildren().add(username);

        Text password = new Text("Password: ");
        password.setFont(Font.font("Impact",20));
        password.setStyle("-fx-font-weight: bold");
        password.setFill(Color.WHITE);
        password.setTranslateX(550);
        password.setTranslateY(340);
        UIpane.getChildren().add(password);

        Text logo1 = new Text("UM ");
        logo1.setFont(Font.font("Impact",40));
        logo1.setStyle("-fx-font-weight: bold");
        logo1.setFill(Color.rgb(75,105,116));
        logo1.setTranslateX(160);
        logo1.setTranslateY(340);
        UIpane.getChildren().add(logo1);

        Text logo = new Text("Chat ");
        logo.setFont(Font.font("Impact",40));
        logo.setStyle("-fx-font-weight: bold");
        logo.setFill(Color.WHITE);
        logo.setTranslateX(210);
        logo.setTranslateY(340);
        UIpane.getChildren().add(logo);

        Polygon triangle = new Polygon(150,340 ,150,310 ,120,340);
        triangle.setFill(Color.rgb(159,182,189));
        UIpane.getChildren().add(triangle);
        Polygon triangle1 = new Polygon(295,340 ,295,310 ,325,310);
        triangle1.setFill(Color.rgb(159,182,189));
        UIpane.getChildren().add(triangle1);

        TextField loginTextField = new TextField();
        loginTextField.setPrefWidth(250);
        loginTextField.setPrefHeight(30);
        loginTextField.setTranslateX(550);
        loginTextField.setTranslateY(280);
        loginTextField.setStyle("-fx-background-color: transparent;" +"-fx-border-width: 2px;" + "-fx-border-color:rgba(159,182,189,1);" + "-fx-text-fill: white;");
        PasswordField passwordTextField = new PasswordField();
        passwordTextField.setPrefWidth(250);
        passwordTextField.setPrefHeight(30);
        passwordTextField.setTranslateX(550);
        passwordTextField.setTranslateY(350);
        passwordTextField.setStyle("-fx-background-color: transparent;" +"-fx-border-width: 2px;" + "-fx-border-color:rgba(159,182,189,1);"+ "-fx-text-fill: white;"+"-fx-font-size: 8");
        UIpane.getChildren().add(loginTextField);
        UIpane.getChildren().add(passwordTextField);

        loginButton = new Button();
        loginButton.setText("LOGIN");
        loginButton.setFont(Font.font("Impact", FontWeight.BOLD,20));
        loginButton.setStyle("-fx-background-color: rgba(159,182,189,1);");
        loginButton.setPrefWidth(250);
        loginButton.setTextFill(Color.WHITE);
        loginButton.setLayoutX(550);
        loginButton.setLayoutY(400);
        loginButton.setCursor(Cursor.CLOSED_HAND);
        loginButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loginButton.setStyle("-fx-background-color: rgba(42,97,117,1)");
            }
        });
        loginButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loginButton.setStyle("-fx-background-color: rgba(159,182,189,1)");
            }
        });
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("hello");
                ChatWindow chatWindow= null;
                try {
                    chatWindow = new ChatWindow();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                databaseCredentials.setPassword(passwordTextField.getText());
                databaseCredentials.setUsername(loginTextField.getText());
                chatWindow.setStage(UIstage);
            }
        });
        UIpane.getChildren().add(loginButton);

    }

    //@Override
    public void keyboardHandler() {
        UIscene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
                    UIstage.close();
                    ke.consume();
                }
            }
        });
    }

}

package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;



public class ChatWindow implements CustomStage {

    String chat = "";
    private AnchorPane UIpane;
    private Stage UIstage;
    private Stage menuStage;
    private Scene UIscene;
    private TextArea userInput,userOutput;
    private Button sendUserInput, helpButton, skillsButton, logoutButton, exitButton;
    private Image sendUserImage= new Image("GUI/send.png",70,30,false,true);
    private ImageView sendUserView = new ImageView(sendUserImage);
    public ChatWindow(){
        UIpane = new AnchorPane();
        UIscene = new Scene(UIpane,LoginScreen.screenWidth,LoginScreen.screenHeight);
        UIstage = new Stage();
        UIscene.setFill(Color.LIGHTGRAY);
        UIpane.setStyle("-fx-background-color: transparent");
        UIstage.setScene(UIscene);
        design();
        keyboardHandler();
    }
    public void setStage(Stage mainStage){
        this.menuStage=mainStage;
        mainStage.close();
        UIstage.show();
    }
    @Override
    public void design(){
        //User input textField
        userInput = new TextArea();
        userInput.setPrefSize(250,50);
        userInput.setTranslateX(50);
        userInput.setTranslateY(430);
        userInput.setWrapText(true);
        UIpane.getChildren().add(userInput);

        //Chat textField
        userOutput = new TextArea();
        userOutput.setPrefSize(250,330);
        userOutput.setTranslateX(50);
        userOutput.setTranslateY(50);
        userOutput.setCursor(Cursor.CLOSED_HAND);
        UIpane.getChildren().add(userOutput);
        userOutput.setEditable(false);
        userOutput.setWrapText(true);

        ColorAdjust effect = new ColorAdjust();
        effect.setBrightness(-0.5);

        // send button
        sendUserInput = new Button();
        sendUserInput.setText("SEND");
        sendUserInput.setFont(Font.font("Impact", FontWeight.BOLD,20));
        sendUserInput.setStyle("-fx-background-color: transparent");
        sendUserInput.setTextFill(Color.WHITE);
        sendUserInput.setLayoutX(310);
        sendUserInput.setLayoutY(437);
        sendUserInput.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chat = chat + "you: "+ userInput.getText() + "\n";
                userOutput.setText(chat);
                System.out.println(chat);
            }
        });
        sendUserInput.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sendUserInput.setEffect(effect);
            }
        });
        sendUserInput.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sendUserInput.setEffect(null);
            }
        });
        UIpane.getChildren().add(sendUserInput);

        //side menu
        Rectangle sideMenu = new Rectangle(400,50,250,330);
        sideMenu.setFill(Color.DARKGRAY);
        UIpane.getChildren().add(sideMenu);

        //help button
        helpButton = new Button();
        helpButton.setText("HELP");
        helpButton.setFont(Font.font("Impact", FontWeight.BOLD,30));
        helpButton.setStyle("-fx-background-color: transparent");
        helpButton.setTextFill(Color.WHITE);
        helpButton.setLayoutX(480);
        helpButton.setLayoutY(170);
        helpButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                helpButton.setEffect(effect);
            }
        });
        helpButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                helpButton.setEffect(null);
            }
        });
        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        UIpane.getChildren().add(helpButton);

        //skills editor button
        skillsButton = new Button();
        skillsButton.setText("SKILLS EDITOR");
        skillsButton.setFont(Font.font("Impact", FontWeight.BOLD,30));
        skillsButton.setStyle("-fx-background-color: transparent");
        skillsButton.setTextFill(Color.WHITE);
        skillsButton.setLayoutX(420);
        skillsButton.setLayoutY(130);
        skillsButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                skillsButton.setEffect(effect);
            }
        });
        skillsButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                skillsButton.setEffect(null);
            }
        });
        skillsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SkillEditor skillEditor = new SkillEditor();
                skillEditor.setStage(UIstage);
            }
        });
        UIpane.getChildren().add(skillsButton);

        //logout button
        logoutButton = new Button();
        logoutButton.setText("SIGN OUT");
        logoutButton.setFont(Font.font("Impact", FontWeight.BOLD,30));
        logoutButton.setStyle("-fx-background-color: transparent");
        logoutButton.setTextFill(Color.WHITE);
        logoutButton.setLayoutX(450);
        logoutButton.setLayoutY(210);
        logoutButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logoutButton.setEffect(effect);
            }
        });
        logoutButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logoutButton.setEffect(null);
            }
        });
        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setStage(UIstage);
            }
        });
        UIpane.getChildren().add(logoutButton);

        //exit Button
        exitButton = new Button();
        exitButton.setText("EXIT");
        exitButton.setFont(Font.font("Impact", FontWeight.BOLD,30));
        exitButton.setStyle("-fx-background-color: transparent");
        exitButton.setTextFill(Color.WHITE);
        exitButton.setLayoutX(480);
        exitButton.setLayoutY(250);
        exitButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exitButton.setEffect(effect);
            }
        });
        exitButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exitButton.setEffect(null);
            }
        });
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UIstage.close();
            }
        });
        UIpane.getChildren().add(exitButton);
    }
    @Override
        public void keyboardHandler(){
            UIscene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                public void handle(KeyEvent ke) {
                    if (ke.getCode() == KeyCode.ESCAPE) {
                        LoginScreen loginScreen = new LoginScreen();
                        loginScreen.setStage(UIstage);
                        ke.consume();
                    }
                }
            });
        }

}

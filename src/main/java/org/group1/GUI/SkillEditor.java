package org.group1.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.group1.response.FileService;

import java.io.IOException;


public class SkillEditor implements CustomStage{


    FileService fs;


    private AnchorPane UIpane;
    private Stage UIstage;
    private Scene UIscene;
    private Stage chatStage;
    private Button displaySkills,back,help,sendUserInput,defineSkills;
    ErrorHandling errorHandling = new ErrorHandling();
    public  SkillEditor(){
        UIpane = new AnchorPane();
        UIscene = new Scene(UIpane,LoginScreen.screenWidth,LoginScreen.screenHeight);
        UIstage = new Stage();
        UIscene.setFill(Color.rgb(18,64,76));
        UIpane.setStyle("-fx-background-color: transparent");
        UIstage.setScene(UIscene);
        design();
        keyboardHandler();
    }
    public void setStage(Stage mainStage){
        this.chatStage=mainStage;
        mainStage.close();
        UIstage.show();
    }
    public void setStage(Stage mainStage,Stage chatStage){
        this.chatStage=chatStage;
        mainStage.close();
        UIstage.show();
    }
   // @Override
    public void keyboardHandler(){
        UIscene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
                    ChatWindow chatWindow = new ChatWindow();
                    chatWindow.setStage(UIstage);
                    ke.consume();
                }
            }
        });
    }

    @Override
    public void design() {
        //side menu
        Rectangle sideMenu = new Rectangle(0,0,250,LoginScreen.screenHeight);
        sideMenu.setFill(Color.rgb(159,182,189));
        UIpane.getChildren().add(sideMenu);

        displaySkills = new Button();
        displaySkills.setText("DISPLAY SKILLS");
        displaySkills.setFont(Font.font("Impact", FontWeight.BOLD,30));
        displaySkills.setStyle("-fx-background-color: transparent");
        displaySkills.setTextFill(Color.WHITE);
        displaySkills.setLayoutX(20);
        displaySkills.setLayoutY(130);
        displaySkills.setCursor(Cursor.CLOSED_HAND);
        displaySkills.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                displaySkills.setTextFill(Color.rgb(42,97,117));
            }
        });
        displaySkills.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                displaySkills.setTextFill(Color.WHITE);
            }
        });
        displaySkills.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              DisplaySkills displaySkills = new DisplaySkills();
              displaySkills.setStage(UIstage,chatStage);
            }
        });
        UIpane.getChildren().add(displaySkills);

        defineSkills = new Button();
        defineSkills.setText("DEFINE SKILLS");
        defineSkills.setFont(Font.font("Impact", FontWeight.BOLD,30));
        defineSkills.setStyle("-fx-background-color: transparent");
        defineSkills.setTextFill(Color.rgb(42,97,117));
        defineSkills.setLayoutX(20);
        defineSkills.setLayoutY(170);

        defineSkills.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO: change the window to the one where you can choose skills
            }
        });
        UIpane.getChildren().add(defineSkills);

        help = new Button();
        help.setText("HELP");
        help.setFont(Font.font("Impact", FontWeight.BOLD,30));
        help.setStyle("-fx-background-color: transparent");
        help.setTextFill(Color.WHITE);
        help.setLayoutX(20);
        help.setLayoutY(210);
        help.setCursor(Cursor.CLOSED_HAND);
        help.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                help.setTextFill(Color.rgb(42,97,117));
            }
        });
        help.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                help.setTextFill(Color.WHITE);
            }
        });
        help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               //TODO : SET HELP WINDOW
            }
        });
        UIpane.getChildren().add(help);

        TextArea questionInput = new TextArea();
        questionInput.setFont(Font.font("Arial Narrow",25));
        questionInput.setStyle("-fx-control-inner-background: rgb(159,182,189);"+ "-fx-text-fill: white ");
        questionInput.setLayoutX(385);
        questionInput.setWrapText(true);
        questionInput.setLayoutY(280);
        questionInput.setPrefSize(400,70);
        UIpane.getChildren().add(questionInput);

        back = new Button();
        back.setText("BACK");
        back.setFont(Font.font("Impact", FontWeight.BOLD,30));
        back.setStyle("-fx-background-color: transparent");
        back.setTextFill(Color.WHITE);
        back.setLayoutX(20);
        back.setLayoutY(250);
        back.setCursor(Cursor.CLOSED_HAND);
        back.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                back.setTextFill(Color.rgb(42,97,117));
            }
        });
        back.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                back.setTextFill(Color.WHITE);
            }
        });
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChatWindow chatWindow = new ChatWindow();
                chatWindow.setStage(UIstage,chatStage);
            }
        });
        UIpane.getChildren().add(back);

        Text username = new Text("Write your question");
        username.setFont(Font.font("Impact",40));
        username.setStyle("-fx-font-weight: bold");
        username.setFill(Color.WHITE);
        username.setTranslateX(420);
        username.setTranslateY(250);
        UIpane.getChildren().add(username);

        sendUserInput = new Button();
        sendUserInput.setText("SUBMIT");
        sendUserInput.setFont(Font.font("Impact", FontWeight.BOLD,20));
        sendUserInput.setStyle("-fx-background-color: rgba(159,182,189,1)");
        sendUserInput.setTextFill(Color.WHITE);
        sendUserInput.setCursor(Cursor.CLOSED_HAND);
        sendUserInput.setPrefSize(400,50);
        sendUserInput.setLayoutX(386);
        sendUserInput.setLayoutY(380);
        sendUserInput.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(errorHandling.stringLengthError(questionInput.getText())) {
                    String question = "Question " + questionInput.getText();

                    // this makes the next available rule .txt
                    // in which we will add the actions & slots
                    try {
                        fs = new FileService();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        fs.write(question);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    // TODO: write to txt file in recourses/skills/rule_

                }  else System.out.println("invalid message");
                questionInput.setText("");
            }
        });
        sendUserInput.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sendUserInput.setStyle("-fx-background-color: rgba(42,97,117,1)");
            }
        });
        sendUserInput.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sendUserInput.setStyle("-fx-background-color: rgba(159,182,189,1)");
            }
        });
        UIpane.getChildren().add(sendUserInput);



    }

}

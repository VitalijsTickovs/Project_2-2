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
import java.util.Arrays;


public class SkillEditor implements CustomStage{


    FileService fs;

    private AnchorPane UIpane;
    private Stage UIstage;
    private Scene UIscene;
    private Stage chatStage;
    private Button displaySkills,back,help,sendUserInput,defineSkills,addActions,actionButton, slotButton;
    ErrorHandling errorHandling = new ErrorHandling();
    private TextArea questionInput;
    private Text username;

    private String skillInput="";
    public  SkillEditor(){
        try {
            fs = new FileService();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    public void createButtons(){
        //add actions button
        addActions = new Button();
        addActions.setText("ADD ACTIONS");
        addActions.setFont(Font.font("Impact", FontWeight.BOLD,30));
        addActions.setStyle("-fx-background-color: transparent");
        addActions.setTextFill(Color.WHITE);
        addActions.setLayoutX(20);
        addActions.setLayoutY(400);
        addActions.setTextFill(Color.rgb(42,97,117));
        addActions.setCursor(Cursor.CLOSED_HAND);
        UIpane.getChildren().add(addActions);

        //displaySkillsButton
        displaySkills = new Button();
        displaySkills.setText("DISPLAY SKILLS");
        displaySkills.setFont(Font.font("Impact", FontWeight.BOLD,30));
        displaySkills.setStyle("-fx-background-color: transparent");
        displaySkills.setTextFill(Color.WHITE);
        displaySkills.setLayoutX(20);
        displaySkills.setLayoutY(130);
        displaySkills.setCursor(Cursor.CLOSED_HAND);
        UIpane.getChildren().add(displaySkills);

        //define skills button
        defineSkills = new Button();
        defineSkills.setText("DEFINE SKILLS");
        defineSkills.setFont(Font.font("Impact", FontWeight.BOLD,30));
        defineSkills.setStyle("-fx-background-color: transparent");
        defineSkills.setTextFill(Color.rgb(42,97,117));
        defineSkills.setLayoutX(20);
        defineSkills.setLayoutY(170);
        UIpane.getChildren().add(defineSkills);

        //help button
        help = new Button();
        help.setText("HELP");
        help.setFont(Font.font("Impact", FontWeight.BOLD,30));
        help.setStyle("-fx-background-color: transparent");
        help.setTextFill(Color.WHITE);
        help.setLayoutX(20);
        help.setLayoutY(210);
        help.setCursor(Cursor.CLOSED_HAND);
        UIpane.getChildren().add(help);

        //back button
        back = new Button();
        back.setText("BACK");
        back.setFont(Font.font("Impact", FontWeight.BOLD,30));
        back.setStyle("-fx-background-color: transparent");
        back.setTextFill(Color.WHITE);
        back.setLayoutX(20);
        back.setLayoutY(250);
        back.setCursor(Cursor.CLOSED_HAND);
        UIpane.getChildren().add(back);


        //send user input button
        sendUserInput = new Button();
        sendUserInput.setText("SUBMIT QUESTION");
        sendUserInput.setFont(Font.font("Impact", FontWeight.BOLD,20));
        sendUserInput.setStyle("-fx-background-color: rgba(159,182,189,1)");
        sendUserInput.setTextFill(Color.WHITE);
        sendUserInput.setCursor(Cursor.CLOSED_HAND);
        sendUserInput.setPrefSize(200,50);
        sendUserInput.setLayoutX(500);
        sendUserInput.setLayoutY(380);
        UIpane.getChildren().add(sendUserInput);

        //slot button
        slotButton = new Button();
        slotButton.setFont(Font.font("Impact", FontWeight.BOLD,20));
        slotButton.setStyle("-fx-background-color: rgba(159,182,189,1)");
        slotButton.setText("SUBMIT SLOT");
        slotButton.setTextFill(Color.WHITE);
        slotButton.setCursor(Cursor.CLOSED_HAND);
        slotButton.setPrefSize(200,50);
        slotButton.setLayoutX(700);
        slotButton.setLayoutY(380);

        //action button
        actionButton = new Button();
        actionButton.setFont(Font.font("Impact", FontWeight.BOLD,20));
        actionButton.setStyle("-fx-background-color: rgba(159,182,189,1)");
        actionButton.setText("SUBMIT ACTION");
        actionButton.setTextFill(Color.WHITE);
        actionButton.setCursor(Cursor.CLOSED_HAND);
        actionButton.setPrefSize(200,50);
        actionButton.setLayoutX(500);
        actionButton.setLayoutY(380);
    }
    public void defineButtonActions() {
        //displaySkillsAction
        displaySkills.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                displaySkills.setTextFill(Color.rgb(42, 97, 117));
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
                System.out.println(fs.getFiles().length);
                DisplaySkills displaySkills = new DisplaySkills(fs.getFiles().length);
                displaySkills.setStage(UIstage, chatStage);
            }
        });
        //define skills action
        defineSkills.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO: change the window to the one where you can choose skills
            }
        });

        //help button action
        help.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                help.setTextFill(Color.rgb(42, 97, 117));
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

        //back button action
        back.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                back.setTextFill(Color.rgb(42, 97, 117));
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
                chatWindow.setStage(UIstage, chatStage);
            }
        });

        //send user input action
        sendUserInput.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (errorHandling.stringLengthError(questionInput.getText())) {
                    String question = questionInput.getText();
                    skillInput += "Question " + toUpper(question);

                    UIpane.getChildren().remove(sendUserInput);
                    username.setText("Add the slots");
                    UIpane.getChildren().add(slotButton);
                    // this makes the next available rule .txt
                    // in which we will add the actions & slots
                } else System.out.println("invalid message");
                questionInput.setText("");
                // TODO: go into this file to define slots and actions...

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

        //slot button action
        slotButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (errorHandling.stringLengthError(questionInput.getText())) {
                    String slot = questionInput.getText();
                    skillInput += addNamingSlot(toUpper(slot));

                    UIpane.getChildren().remove(slotButton);
                    username.setText("Add the actions");
                    //  UIpane.getChildren().add(actionButton);
                } else System.out.println("invalid message");
                questionInput.setText("");
            }
        });

        //action button actions
        actionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (errorHandling.stringLengthError(questionInput.getText())) {
                    String action = questionInput.getText();
                    skillInput += addNamingAction(toUpper(action));

                    // this makes the next available rule .txt
                    // in which we will add the actions & slots
                    try {
                        fs.write(skillInput);
                        skillInput = "";
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    //transition into slot input, then into action input
                    //each time open the same file id, used in fs

                } else System.out.println("invalid message");
                questionInput.setText("");
            }
        });
        actionButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                actionButton.setStyle("-fx-background-color: rgba(42,97,117,1)");
            }
        });
        actionButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                actionButton.setStyle("-fx-background-color: rgba(159,182,189,1)");
            }
        });
    }

    private String addNamingAction(String string){
        String[] stringArr = string.split("\n");
        for(int i=0; i<stringArr.length; i++){
            stringArr[i] = "Action " + stringArr[i];
        }
        string = myArrayToString(stringArr);
        return string + "/nAction I have no idea";
    }

    private String addNamingSlot(String string){
        String[] stringArr = string.split("\n| ");
        for(int i=0; i<stringArr.length; i++){
            if(stringArr[i].matches(".*?<.+>.*?")){
                stringArr[i] = "Slot " + stringArr[i];
            }
        }
        string = myArrayToString(stringArr);
        return string;
    }

    private String toUpper(String string){
        //Make placeholder upper case
        String[] slotArray = string.split("\n| ");

        for(int i=0; i<slotArray.length; i++){
            if(slotArray[i].matches(".*?<.+>.*?")){
                slotArray[i] = slotArray[i].toUpperCase();
            }
        }
        string = myArrayToString(slotArray);
        return string;
    }

    @Override
    public void design() {
        //side menu
        Rectangle sideMenu = new Rectangle(0,0,250,LoginScreen.screenHeight);
        sideMenu.setFill(Color.rgb(159,182,189));
        UIpane.getChildren().add(sideMenu);

        questionInput = new TextArea();
        questionInput.setFont(Font.font("Arial Narrow",25));
        questionInput.setStyle("-fx-control-inner-background: rgb(159,182,189);"+ "-fx-text-fill: white ");
        questionInput.setLayoutX(385);
        questionInput.setWrapText(true);
        questionInput.setLayoutY(280);
        questionInput.setPrefSize(400,70);
        UIpane.getChildren().add(questionInput);

        username = new Text("Define your question");
        username.setFont(Font.font("Impact",40));
        username.setStyle("-fx-font-weight: bold");
        username.setFill(Color.WHITE);
        username.setTranslateX(420);
        username.setTranslateY(250);
        UIpane.getChildren().add(username);


        createButtons();
        defineButtonActions();
    }

    private String myArrayToString(String[] string){
        String finalString = "";
        for(String str: string){
            if(str.contains("Slot") || str.contains("Action")) finalString += "\n";
            finalString += str + " ";
        }

        return finalString;

    }


    /********* TODO: Remove and Modify skills from GUI *******/



}

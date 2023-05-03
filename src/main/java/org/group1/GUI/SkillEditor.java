package org.group1.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.group1.GUI.utils.ButtonFactory;
import org.group1.GUI.utils.ErrorHandling;
import org.group1.back_end.response.Response;

import static org.group1.back_end.utilities.GeneralFileService.*;


public class SkillEditor extends StageManager implements ICustomStage {
    private Stage chatStage;
    private Button displaySkills,back,help,sendUserInput,defineSkills,actionButton, slotButton;
    private final ErrorHandling errorHandling = new ErrorHandling();
    private TextArea questionInput, previousQuestion;
    private Text username;
    private final Response response;

    private String skillInput="";
    public  SkillEditor(Response response){
        this.response = response;
        initStage();
        design();
        keyboardHandler();
    }

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
        //displaySkillsButton
        displaySkills = ButtonFactory.createButton("DISPLAY SKILLS", 20, 130);
        UIpane.getChildren().add(displaySkills);

        //help button
        help = ButtonFactory.createButton("HELP", 20, 210);
        UIpane.getChildren().add(help);

        //back button
        back = ButtonFactory.createButton("BACK", 20, 250);
        UIpane.getChildren().add(back);

        //define skills button
        defineSkills = ButtonFactory.createButton("DEFINE SKILLS", 20, 170);
        defineSkills.setTextFill(Color.rgb(42,97,117));
        UIpane.getChildren().add(defineSkills);

        //send user input button
        sendUserInput = ButtonFactory.createButton("SUBMIT QUESTION", 500, 380);
        sendUserInput.setStyle("-fx-background-color: rgba(159,182,189,1)");
        sendUserInput.setPrefSize(200,50);
        UIpane.getChildren().add(sendUserInput);

        //slot button
        slotButton = ButtonFactory.createButton("SUBMIT SLOT", 500, 380);
        slotButton.setStyle("-fx-background-color: rgba(159,182,189,1)");
        slotButton.setPrefSize(200,50);

        //action button
        actionButton = ButtonFactory.createButton("SUBMIT ACTION", 500, 380);
        actionButton.setStyle("-fx-background-color: rgba(159,182,189,1)");
    }

    public void defineButtonActions() {
        //displaySkillsAction
        ButtonFactory.setDefaultActions(displaySkills);
        displaySkills.setOnAction(e -> {
            DisplaySkills displaySkills = new DisplaySkills(response);
            displaySkills.setStage(UIstage, chatStage);
        });

        //define skills action
        defineSkills.setOnAction(e -> {
            //TODO: change the window to the one where you can choose skills
        });

        //help button action
        ButtonFactory.setDefaultActions(help);
        help.setOnAction(e -> {
            //TODO : SET HELP WINDOW
        });

        //back button action
        ButtonFactory.setDefaultActions(back);
        back.setOnAction(e -> {
            ChatWindow chatWindow = new ChatWindow();
            chatWindow.setStage(UIstage, chatStage);
        });

        //send user input action
        sendUserInput.setOnAction(e -> {
            if (errorHandling.stringLengthError(questionInput.getText())) {
                String question = questionInput.getText();
                skillInput += "Question " + toUpper(question);

                UIpane.getChildren().remove(sendUserInput);
                username.setTranslateX(475);
                username.setText("Add the slots");
                UIpane.getChildren().add(slotButton);

                previousQuestion = new TextArea(skillInput);
                previousQuestion.setFont(Font.font("Impact", FontWeight.BOLD,20));
                previousQuestion.setStyle("-fx-control-inner-background: rgba(18,64,76);"+ "-fx-text-fill: white ;"
                        +"-fx-text-box-border: transparent;"+"-fx-focus-color: transparent;");
                previousQuestion.setLayoutX(385);
                previousQuestion.setLayoutY(450);
                previousQuestion.setWrapText(true);
                previousQuestion.setPrefSize(400,300);
                UIpane.getChildren().add(previousQuestion);
            } else System.out.println("invalid message");
            questionInput.setText("");
            // TODO: go into this file to define slots and actions...

        });
        sendUserInput.setOnMouseEntered(e-> {
            sendUserInput.setStyle("-fx-background-color: rgba(42,97,117,1)");
        });
        sendUserInput.setOnMouseExited(e -> {
            sendUserInput.setStyle("-fx-background-color: rgba(159,182,189,1)");
        });

        //slot button action
        slotButton.setOnAction(e -> {
            if (errorHandling.stringLengthError(questionInput.getText())) {
                String slot = questionInput.getText();
                skillInput += addNamingSlot(toUpper(slot));

                UIpane.getChildren().remove(slotButton);
                username.setText("Add the actions");
                username.setTranslateX(460);
                UIpane.getChildren().add(actionButton);
            } else System.out.println("invalid message");
            questionInput.setText("");
        });

        //action button actions
        actionButton.setOnMouseEntered(e-> {
            actionButton.setStyle("-fx-background-color: rgba(42,97,117,1)");
        });
        actionButton.setOnMouseExited(e -> {
            actionButton.setStyle("-fx-background-color: rgba(159,182,189,1)");
        });
        actionButton.setOnAction(new EventHandler<ActionEvent>() {
            private void createAlert(){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Skill edited sucessfuly");
                alert.setOnCloseRequest( e ->{
                            ChatWindow chatWindow = new ChatWindow();
                            chatWindow.setStage(UIstage, chatStage);
                        }
                );

                alert.showAndWait();
            }
            @Override
            public void handle(ActionEvent event) {
                if (errorHandling.stringLengthError(questionInput.getText())) {
                    String action = questionInput.getText();
                    skillInput += addNamingAction(toUpper(action));

                    // this makes the next available rule .txt
                    // in which we will add the actions & slots
                    write(skillInput);
                    skillInput = "";

                    createAlert();

                } else System.out.println("invalid message");
                questionInput.setText("");
            }
        });
    }

    private String addNamingAction(String string){
        String[] stringArr = string.split("\n");
        for(int i=0; i<stringArr.length; i++){
            stringArr[i] = "Action " + stringArr[i];
        }
        string = myArrayToString(stringArr);
        return string + "\nAction I have no idea";
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
        Rectangle sideMenu = new Rectangle(0,0,250,screenHeight);
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
}

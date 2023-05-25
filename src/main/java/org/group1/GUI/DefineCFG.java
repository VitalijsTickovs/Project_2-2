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
import org.group1.GUI.ChatWindow;
import org.group1.GUI.DisplaySkills;
import org.group1.GUI.ICustomStage;
import org.group1.GUI.StageManager;
import org.group1.GUI.utils.ButtonFactory;
import org.group1.GUI.utils.ErrorHandling;
import org.group1.back_end.response.Response;

import java.util.ArrayList;

import static org.group1.back_end.utilities.GeneralFileService.*;


public class DefineCFG extends StageManager implements ICustomStage {
    private Stage chatStage;
    private Button displaySkills,back,help,sendUserInput,defineSkills,actionButton, slotButton,defineCFG,next;
    private final ErrorHandling errorHandling = new ErrorHandling();
    private TextArea questionInput, previousQuestion;
    private Text taskText;
    private final Response response;
    private ArrayList<String> ruleCollection = new ArrayList<>();
    private String skillInput="";
    public  DefineCFG(Response response){
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
        help = ButtonFactory.createButton("HELP", 20, 250);
        UIpane.getChildren().add(help);

        //back button
        back = ButtonFactory.createButton("BACK", 20, 290);
        UIpane.getChildren().add(back);

        //define skills button
        defineSkills = ButtonFactory.createButton("DEFINE SKILLS", 20, 170);
        UIpane.getChildren().add(defineSkills);

        //define CFG button
        defineCFG = ButtonFactory.createButton("DEFINE CFG", 20, 210);
        defineCFG.setTextFill(Color.rgb(42,97,117));
        UIpane.getChildren().add(defineCFG);

        //send user input button
            sendUserInput = ButtonFactory.createButton("FINISH RULE", 380, 380);
        sendUserInput.setStyle("-fx-background-color: rgba(159,182,189,1)");
        sendUserInput.setPrefSize(200,50);
        UIpane.getChildren().add(sendUserInput);

        //next button
        next = ButtonFactory.createButton("NEXT RULE", 560, 380);
        next.setStyle("-fx-background-color: rgba(159,182,189,1)");
        next.setPrefSize(200,50);
        UIpane.getChildren().add(next);

        //slot button
        slotButton = ButtonFactory.createButton("FINISH ACTION", 380, 380);
        slotButton.setStyle("-fx-background-color: rgba(159,182,189,1)");
        slotButton.setPrefSize(200,50);
    }

    public void defineButtonActions() {
        //displaySkillsAction
        ButtonFactory.setDefaultActions(displaySkills);
        displaySkills.setOnAction(e -> {
            DisplaySkills displaySkills = new DisplaySkills(response);
            displaySkills.setStage(UIstage, chatStage);
        });

        //define skills action
        ButtonFactory.setDefaultActions(defineSkills);
        defineSkills.setOnAction(e ->{
            SkillEditor skillEditor = new SkillEditor(response);
            skillEditor.setStage(UIstage,chatStage);
        });

        //help button action
        ButtonFactory.setDefaultActions(help);
        help.setOnAction(e -> {
            HelpScreen helpScreen = new HelpScreen();
            helpScreen.setStage(UIstage);
        });

        //back button action
        ButtonFactory.setDefaultActions(back);
        back.setOnAction(e -> {
            ChatWindow chatWindow = new ChatWindow();
            chatWindow.setStage(UIstage, chatStage);
        });

        //next action
        //TODO: GET THE RULE AND ACTION HERE
        next.setOnAction(e -> {
            if (errorHandling.stringLengthError(questionInput.getText())) {
                String question = questionInput.getText();
                skillInput += "RULE " + toUpper(question);
                ruleCollection.add(skillInput);
                questionInput.setText("");

            } else System.out.println("invalid message");
            questionInput.setText("");

        });
        next.setOnMouseEntered(e-> {
            next.setStyle("-fx-background-color: rgba(42,97,117,1)");
        });
        next.setOnMouseExited(e -> {
            next.setStyle("-fx-background-color: rgba(159,182,189,1)");
        });

        //send user input action

        sendUserInput.setOnAction(e -> {
            if (errorHandling.stringLengthError(questionInput.getText())) {
                UIpane.getChildren().remove(sendUserInput);

                taskText.setTranslateX(475);
                taskText.setText("Define Actions");
                UIpane.getChildren().add(slotButton);

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

        slotButton.setOnMouseEntered(e-> {
            slotButton.setStyle("-fx-background-color: rgba(42,97,117,1)");
        });
        slotButton.setOnMouseExited(e -> {
            slotButton.setStyle("-fx-background-color: rgba(159,182,189,1)");
        });


        slotButton.setOnAction(new EventHandler<ActionEvent>() {
            private void createAlert(){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("CFG edited sucessfuly");
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
        createSideMenu();

        questionInput = new TextArea();
        questionInput.setFont(Font.font("Arial Narrow",25));
        questionInput.setStyle("-fx-control-inner-background: rgb(159,182,189);"+ "-fx-text-fill: white ");
        questionInput.setLayoutX(385);
        questionInput.setWrapText(true);
        questionInput.setLayoutY(280);
        questionInput.setPrefSize(400,70);
        UIpane.getChildren().add(questionInput);

        taskText = createText("Define Your Question", 420,250);

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
package org.group1.GUI.stage.scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.group1.GUI.stage.StageManager;
import org.group1.GUI.stage.scenes.utils.ButtonFactory;
import org.group1.GUI.stage.scenes.utils.ErrorHandling;

import static org.group1.back_end.utilities.GeneralFileService.*;


public class SceneDefineTemplate extends SceneManager implements ICustomScene {
    private Button displaySkills,back,help,sendUserInput,defineSkills,actionButton, slotButton, defineCFG;
    private final ErrorHandling errorHandling = new ErrorHandling();
    private TextArea questionInput, previousQuestion;
    private Text taskText;

    private String skillInput="";
    public SceneDefineTemplate(){
        makeNewPane();
        design();
        keyboardHandler();
    }

    public void keyboardHandler(){
        addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
                    SceneChat chatWindow = new SceneChat();
                    StageManager.setScene(chatWindow);
                    ke.consume();
                }
            }
        });
    }
    public void createButtons(){
        //displaySkillsButton
        displaySkills = ButtonFactory.createButton("DISPLAY SKILLS", 20, 130);
        UIPane.getChildren().add(displaySkills);

        //help button
        help = ButtonFactory.createButton("HELP", 20, 250);
        UIPane.getChildren().add(help);

        //back button
        back = ButtonFactory.createButton("BACK", 20, 290);
        UIPane.getChildren().add(back);

        //define skills button
        defineSkills = ButtonFactory.createButton("DEFINE TEMPLATE", 20, 170);
        defineSkills.setTextFill(Color.rgb(42,97,117));
        UIPane.getChildren().add(defineSkills);

        //define CFG button
        defineCFG = ButtonFactory.createButton("DEFINE CFG", 20, 210);
        UIPane.getChildren().add(defineCFG);

        //send user input button
        sendUserInput = ButtonFactory.createButton("SUBMIT QUESTION", 500, 380);
        sendUserInput.setStyle("-fx-background-color: rgba(159,182,189,1)");
        sendUserInput.setPrefSize(200,50);
        UIPane.getChildren().add(sendUserInput);

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
            SceneSkillList sceneSkillList = new SceneSkillList();
            StageManager.setScene(sceneSkillList);
        });

        //define skills action
        defineSkills.setOnAction(e -> {
            //TODO: change the window to the one where you can choose skills
        });

        //help button action
        ButtonFactory.setDefaultActions(help);
        help.setOnAction(e -> {
            SceneHelp sceneHelp = new SceneHelp();
            StageManager.setScene(sceneHelp);
        });

        //back button action
        ButtonFactory.setDefaultActions(back);
        back.setOnAction(e -> {
            SceneChat chatWindow = new SceneChat();
            StageManager.setScene(chatWindow);
        });

        //define CFG action
        ButtonFactory.setDefaultActions(defineCFG);
        defineCFG.setOnAction(e -> {
            SceneDefineCFG chatWindow = new SceneDefineCFG();
            StageManager.setScene(chatWindow);
        });

        //send user input action
        sendUserInput.setOnAction(e -> {
            if (errorHandling.stringLengthError(questionInput.getText())) {
                String question = questionInput.getText();
                skillInput += "Question " + toUpper(question);

                UIPane.getChildren().remove(sendUserInput);
                taskText.setTranslateX(475);
                taskText.setText("Add the slots");
                UIPane.getChildren().add(slotButton);

                previousQuestion = new TextArea(skillInput);
                previousQuestion.setFont(Font.font("Impact", FontWeight.BOLD,20));
                previousQuestion.setStyle("-fx-control-inner-background: rgba(18,64,76);"+ "-fx-text-fill: white ;"
                        +"-fx-text-box-border: transparent;"+"-fx-focus-color: transparent;");
                previousQuestion.setLayoutX(385);
                previousQuestion.setLayoutY(450);
                previousQuestion.setWrapText(true);
                previousQuestion.setPrefSize(400,300);
                UIPane.getChildren().add(previousQuestion);
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

                UIPane.getChildren().remove(slotButton);
                taskText.setText("Add the actions");
                taskText.setTranslateX(460);
                UIPane.getChildren().add(actionButton);
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
                            SceneChat chatWindow = new SceneChat();
                            StageManager.setScene(chatWindow);
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
        createSideMenu();

        questionInput = new TextArea();
        questionInput.setFont(Font.font("Arial Narrow",25));
        questionInput.setStyle("-fx-control-inner-background: rgb(159,182,189);"+ "-fx-text-fill: white ");
        questionInput.setLayoutX(385);
        questionInput.setWrapText(true);
        questionInput.setLayoutY(280);
        questionInput.setPrefSize(400,70);
        UIPane.getChildren().add(questionInput);

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

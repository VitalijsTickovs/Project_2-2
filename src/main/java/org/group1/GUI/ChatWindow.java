package org.group1.GUI;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.*;

import org.group1.back_end.utilities.strings.RegexUtilities;

import org.apache.commons.lang3.text.WordUtils;
import org.group1.back_end.response.Response;
import org.group1.database.DatabaseCredentials;
import org.group1.database.TxtToSQL;

import javax.xml.crypto.Data;


public class ChatWindow implements CustomStage {

    //TODO:
    // - clear the input window after user input is send
    // - ...
    ArrayList<TextArea> listText= new ArrayList();
    String chat = "";
    String currentUserInput="";
    String currentBotInput="";

    ErrorHandling errorHandling = new ErrorHandling();
    // javafx elements
    private TextArea userInput,userOutput;
    private AnchorPane UIpane,scrollChat;
    private Stage UIstage;
    private Stage menuStage;
    private Scene UIscene;
    private Button sendUserInput, helpButton, skillsButton, logoutButton, exitButton, addActions;
    private Response responseGenerator;


    public ChatWindow(){
        System.out.println(DatabaseCredentials.getUsername());
        try{
            responseGenerator = new Response();
            generateSQL();
        } catch (Exception e) {
            e.printStackTrace();
        }
        UIpane = new AnchorPane();
        scrollChat = new AnchorPane();
        UIscene = new Scene(UIpane,LoginScreen.screenWidth,LoginScreen.screenHeight);
        UIstage = new Stage();
        UIscene.setFill(Color.rgb(18,64,76));
        UIpane.setStyle("-fx-background-color: transparent");
        UIstage.setScene(UIscene);
        design();
        keyboardHandler();
    }
    private void generateSQL(){
        TxtToSQL sql = new TxtToSQL();
        List<List<List<String>>> rules = responseGenerator.getSQL();
        List<String> questions = responseGenerator.getQuestion();
        int maxColumns=0;
        System.out.println("Rules: " + rules);
        //Go through all rules
        for(int i=0; i<rules.size();i++){
            int numberOfPlaceholders = RegexUtilities.countRegexOccurrences(questions.get(i),"<.*?>");
            String id = Integer.toString(i+1);
            List<List<String>> actions = rules.get(i);
            Set<String> columnActions = new LinkedHashSet<>();
            String questionq = questions.get(i);
            for(int z=0;z<RegexUtilities.countRegexOccurrences(questions.get(i),"<.*?>");z++) {
                String temp = RegexUtilities.getOriginalFormatFromRegex(questionq, "<.*?>");
                columnActions.add(temp.replace("<","").replace(">",""));
                questionq = RegexUtilities.replaceRegex(questionq, temp, "");
            }
            List<String[]> slots = new ArrayList<>();
            //Going through actions in rule_i
            System.out.println("Actions: " + actions);
            System.out.println();
            for(int j=0; j < actions.size(); j++){
                //Get the action
                List<String> action = actions.get(j);
                String[] remapped = new String[numberOfPlaceholders+1];
                String text = action.toString();
//                //Count number of occurences of <>(placeholders)
//                int counter = RegexUtilities.countRegexOccurrences(text,"<.*?>");
                //Adds the <> if not in Set


                //Get used slots and action only
                //Filter out commas
                String[] bla = text.split(",");
                //Filtering of "[","]" and ""
                int counter =0;
                for(int z=0;z<bla.length-1;z++){
                    bla[z] = bla[z]
                            .replaceAll("\\[","")
                            .replaceAll("\\]","")
                            .trim();
                    System.out.println("bla: " + bla[z]);
                    //add the word only if it doesn't contain placeholder
                    if(RegexUtilities.countRegexOccurrences(bla[z],"<.*?>") == 0){
                        remapped[counter]=bla[z];
                        counter++;
                    }
                }
                remapped[remapped.length-1]=bla[bla.length-1];
                slots.add(remapped);
            }

            Set<String> updatedSet = new LinkedHashSet<>();
            for(String action: columnActions){
                updatedSet.add(action.replace("<","").replace(">",""));
            }

            columnActions = updatedSet;
            //remove ""
            columnActions.remove("");
            sql.removeTables(id);
            //Create a table slot_id with columnActions
            sql.createTable("slot_"+id,new ArrayList<>(columnActions));

            //Create a table action_id with columnActions
            sql.createActionTable(Integer.toString(i+1),columnActions.toArray());
            //Inserting data in to action_id
            sql.insertAction(columnActions,slots,id);

            //Inserting all slots in to slot_id
            slots.remove(slots.size()-1);
            sql.insertSlots("slot_"+id, slots);
        }
    }
    public void setStage(Stage mainStage){
        this.menuStage=mainStage;
        mainStage.close();
        UIstage.show();
    }
    public void setStage(Stage mainStage,Stage setPreviousChat){
        this.menuStage=mainStage;
        mainStage.close();
        this.UIstage=setPreviousChat;
        UIstage.show();
    }
    public void crateTextAreas(){
        //User input textField
        userInput = new TextArea();
        userInput.setPrefSize(470,70);
        userInput.setTranslateX(340);
        userInput.setTranslateY(570);
        userInput.setWrapText(true);
        userInput.setFont(Font.font("Arial Narrow",15));
        userInput.setStyle("-fx-control-inner-background:rgb(159,182,189);"+ "-fx-text-fill: white ");
        UIpane.getChildren().add(userInput);
    }
     public void createButtons(){
         //help button
         helpButton = new Button();
         helpButton.setText("HELP");
         helpButton.setFont(Font.font("Impact", FontWeight.BOLD,30));
         helpButton.setStyle("-fx-background-color: transparent");
         helpButton.setTextFill(Color.WHITE);
         helpButton.setLayoutX(20);
         helpButton.setLayoutY(170);
         helpButton.setCursor(Cursor.CLOSED_HAND);
         UIpane.getChildren().add(helpButton);

         //skills editor button
         skillsButton = new Button();
         skillsButton.setText("SKILLS EDITOR");
         skillsButton.setFont(Font.font("Impact", FontWeight.BOLD,30));
         skillsButton.setStyle("-fx-background-color: transparent");
         skillsButton.setTextFill(Color.WHITE);
         skillsButton.setLayoutX(20);
         skillsButton.setLayoutY(130);
         skillsButton.setCursor(Cursor.CLOSED_HAND);
         UIpane.getChildren().add(skillsButton);





         //logout button
         logoutButton = new Button();
         logoutButton.setText("SIGN OUT");
         logoutButton.setFont(Font.font("Impact", FontWeight.BOLD,30));
         logoutButton.setStyle("-fx-background-color: transparent");
         logoutButton.setTextFill(Color.WHITE);
         logoutButton.setLayoutX(20);
         logoutButton.setLayoutY(210);
         logoutButton.setCursor(Cursor.CLOSED_HAND);
         UIpane.getChildren().add(logoutButton);

         //exit Button
         exitButton = new Button();
         exitButton.setText("EXIT");
         exitButton.setFont(Font.font("Impact", FontWeight.BOLD,30));
         exitButton.setStyle("-fx-background-color: transparent");
         exitButton.setTextFill(Color.WHITE);
         exitButton.setLayoutX(20);
         exitButton.setCursor(Cursor.CLOSED_HAND);
         exitButton.setLayoutY(250);
         UIpane.getChildren().add(exitButton);

         // send button
         sendUserInput = new Button();
         sendUserInput.setText("SEND");
         sendUserInput.setFont(Font.font("Impact", FontWeight.BOLD,20));
         sendUserInput.setStyle("-fx-background-color: transparent");
         sendUserInput.setTextFill(Color.WHITE);
         sendUserInput.setCursor(Cursor.CLOSED_HAND);
         sendUserInput.setLayoutX(810);
         sendUserInput.setLayoutY(590);
         UIpane.getChildren().add(sendUserInput);


        setActionForButtons();
     }
     public void setActionForButtons(){
        //help button
         helpButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 helpButton.setTextFill(Color.rgb(42,97,117));
             }
         });
         helpButton.setOnMouseExited(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 helpButton.setTextFill(Color.WHITE);
             }
         });
         helpButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 HelpWindow helpWindow = new HelpWindow();
                 helpWindow.setStage(UIstage);
             }
         });
         //skills button
         skillsButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 skillsButton.setTextFill(Color.rgb(42,97,117));
             }
         });
         skillsButton.setOnMouseExited(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 skillsButton.setTextFill(Color.WHITE);
             }
         });
         skillsButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 SkillEditor skillEditor = new SkillEditor(responseGenerator);
                 skillEditor.setStage(UIstage);
             }
         });
         //logout button
         logoutButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 logoutButton.setTextFill(Color.rgb(42,97,117));
             }
         });
         logoutButton.setOnMouseExited(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 logoutButton.setTextFill(Color.WHITE);
             }
         });
         logoutButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 LoginScreen loginScreen = new LoginScreen();
                 loginScreen.setStage(UIstage);
             }
         });
        //exit button
         exitButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 exitButton.setTextFill(Color.rgb(42,97,117));
             }
         });
         exitButton.setOnMouseExited(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 exitButton.setTextFill(Color.WHITE);
             }
         });
         exitButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 UIstage.close();
             }
         });


         //user chat
         sendUserInput.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 if(errorHandling.stringLengthError(userInput.getText())) {
                     //user input
                     setUserInput(userInput.getText());
                     listText.add(new TextArea(userInput.getText()));
                     listText.get(listText.size()-1).setWrapText(true);
                     if ((countCharAtLongestLine( WordUtils.wrap(userInput.getText(), 30))*7+20)>150){
                         listText.get(listText.size() - 1).setPrefSize(countCharAtLongestLine( WordUtils.wrap(currentUserInput, 30))*7+20, countLines( WordUtils.wrap(currentUserInput, 30)));
                     }else {
                         listText.get(listText.size() - 1).setPrefSize(countCharAtLongestLine(currentUserInput)*7+20, countLines(currentUserInput) );
                     }

                     listText.get(listText.size()-1).setLayoutX(0);
                     listText.get(listText.size()-1).setLayoutY(y);
                     listText.get(listText.size()-1).setEditable(false);
                     listText.get(listText.size()-1).setFont(Font.font("Arial Narrow",15));
                     listText.get(listText.size()-1).setStyle("-fx-control-inner-background: rgb(159,182,189);"+ "-fx-text-fill: white; ");
                     y+=listText.get(listText.size()-1).getPrefHeight()+20;
                     scrollChat.getChildren().add(listText.get(listText.size()-1));

                     //Getting response from the bot
                     System.out.println("User input: " + userInput.getText() + " \nResponse: " + responseGenerator.getResponse(userInput.getText()));
                     setBotChatText(responseGenerator.getResponse(userInput.getText()));

                     listText.add(new TextArea(currentBotInput));
                     if ((countCharAtLongestLine(userInput.getText())*7+20)>220){
                         listText.get(listText.size()-1).setPrefSize(220,countLines(currentBotInput));
                     }else {
                         listText.get(listText.size() - 1).setPrefSize(countCharAtLongestLine(currentBotInput)*7+20, countLines(currentBotInput) );
                     }
                     listText.get(listText.size()-1).setLayoutX(240);
                     listText.get(listText.size()-1).setEditable(false);
                     listText.get(listText.size()-1).setWrapText(true);
                     listText.get(listText.size()-1).setLayoutY(y);
                     listText.get(listText.size()-1).setFont(Font.font("Arial Narrow",15));
                     listText.get(listText.size()-1).setStyle("-fx-control-inner-background: rgb(159,182,189);" + "-fx-text-fill: white; ");
                     y+=listText.get(listText.size()-1).getPrefHeight()+20;
                     scrollChat.getChildren().add(listText.get(listText.size()-1));

                     //scrollPane.setVvalue(1.0);

                     userInput.setText("");
                 }else System.out.println("invalid message");
             }
         });
         sendUserInput.setOnMouseEntered(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 sendUserInput.setTextFill(Color.rgb(42,97,117));
             }
         });
         sendUserInput.setOnMouseExited(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 sendUserInput.setTextFill(Color.WHITE);
             }
         });

     }
         // TODO: Here we set bot response
         public String getBotInputString(){
             return currentBotInput;
         }
         public void setBotChatText(String botResponse){
            this.currentBotInput = botResponse;
         }
         public String getUserInputString(){
            return currentUserInput;
         }
        public void setUserInput(String string){
            this.currentUserInput=string;
        }
        public void createScrollPane(){
            ScrollPane scrollPane = new ScrollPane();

            scrollChat.heightProperty().addListener(observable -> scrollPane.setVvalue(1D));
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            //adding components that will go into the scrollPane
            scrollChat.setStyle("-fx-background-color: transparent");

            scrollPane.setTranslateX(340);
            scrollPane.setTranslateY(50);
            scrollPane.setPrefSize(470,460);
            // TODO: IF YOU NEED THE RED BORDER add " -fx-border-color: red"
            scrollPane.setStyle("-fx-background-color: transparent;");
            scrollPane.setContent(scrollChat);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            // scrollPane.setContent(exitButton);
            UIpane.getChildren().add(scrollPane);
            UIstage.setOnShown(e ->
                    scrollPane.lookup(".viewport").setStyle("-fx-background-color: transparent;"));
        }

    /**
     * Main method to design the javaFX scene (magic go booom)
     */
    @Override
    public void design(){
        //side menu
        Rectangle sideMenu = new Rectangle(0,0,250,LoginScreen.screenHeight);
        sideMenu.setFill(Color.rgb(159,182,189));
        UIpane.getChildren().add(sideMenu);

        crateTextAreas();
        createButtons();
        createScrollPane();
    }
//    @Override
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

        public int countLines(String string){
            String[] lines = string.split("\r\n|\r|\n");
            System.out.println("counde lines: "+lines.length);
            int count=lines.length;
            for (int i = 0; i < lines.length; i++) {
                count=count+18;
            }
            System.out.println("height "+count);
            return count;
        }
        public int countCharAtLongestLine(String string){
        int count=0;
            String[] lines = string.split("\r\n|\r|\n");
            for (int i = 0; i < lines.length; i++) {
                if(lines[i].length()>count){
                    count = lines[i].length();
                }
            }

            System.out.println("counted chars: "+count);
            return count;
        }
    private int y=0;
}

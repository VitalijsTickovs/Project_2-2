package org.group1.GUI.stage.scenes;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.group1.GUI.stage.StageManager;
import org.group1.GUI.stage.scenes.utils.ButtonFactory;
import org.group1.GUI.stage.scenes.utils.ErrorHandling;
import org.group1.back_end.utilities.enums.DB;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.group1.monitor.SoundLevelDetector;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.*;



public class SceneChat extends SceneManager implements ICustomScene {

    // SpeechRecognition
    SoundLevelDetector sd = new SoundLevelDetector();
    String transcription = "";

    ArrayList<TextArea> listText= new ArrayList<>();
    String chat = "";
    String currentUserInput="";
    String currentBotInput="";

    ErrorHandling errorHandling = new ErrorHandling();
    // javafx elements
    private TextArea userInput;
    private Stage menuStage;
    private Button sendUserInput, helpButton, skillsButton, logoutButton, exitButton, mic, auto, mtd;
    private int chatYPos =0;
    private boolean isMTD= false;

    public SceneChat(){
        makeNewPane();
        design();
        keyboardHandler();
//        try {
//            display(10,450,240);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //startCameraAvailabilityCheck();
    }
    public void crateTextArea(){
        //User input textField
        userInput = new TextArea();
        userInput.setPrefSize(470,70);
        userInput.setTranslateX(340);
        userInput.setTranslateY(570);
        userInput.setWrapText(true);
        userInput.setFont(Font.font("Arial Narrow",15));
        userInput.setStyle("-fx-control-inner-background:rgb(159,182,189);"+ "-fx-text-fill: white ");
        userInput.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                event.consume();
            }
        });

        UIPane.getChildren().add(userInput);
    }
    static Boolean micOnOff=false;
    public void createButtons(){
        //help button
        helpButton = ButtonFactory.createButton("HELP", 20, 170);
        UIPane.getChildren().add(helpButton);

        //skills editor button
        skillsButton = ButtonFactory.createButton("SKILLS EDITOR", 20, 130);
        UIPane.getChildren().add(skillsButton);

        //logout button
        logoutButton = ButtonFactory.createButton("SIGN OUT", 20, 210);
        UIPane.getChildren().add(logoutButton);

        //exit Button
        exitButton = ButtonFactory.createButton("EXIT", 20, 580);
        UIPane.getChildren().add(exitButton);

        // send button
        sendUserInput = ButtonFactory.createButton("SEND", 810, 570);
        sendUserInput.setFont(Font.font("Impact", FontWeight.BOLD,20));
        UIPane.getChildren().add(sendUserInput);

        // auto button
        auto = ButtonFactory.createButton("AUTO", 270, 590);
        auto.setFont(Font.font("Impact", FontWeight.BOLD,20));
        UIPane.getChildren().add(auto);

        // multi-turn dialogue system
        mtd = ButtonFactory.createButton("Multi-turn", 20, 250);

        // mic button
        Image microphone = new Image("redMic.png");
        ImageView viewMic = new ImageView(microphone);
        viewMic.setFitWidth(30);
        viewMic.setFitHeight(30);


        mic = new Button();
        mic.setTranslateX(820);
        mic.setTranslateY(600);
        mic.setGraphic(viewMic);
        mic.setBackground(null);
        mic.setStyle("-fx-background-color: transparent");
        UIPane.getChildren().add(mic);


        setActionForButtons();
    }

    public void setActionForButtons(){
        //help button
        ButtonFactory.setDefaultActions(helpButton);
        helpButton.setOnAction( e -> {
            SceneHelp helpWindow = new SceneHelp();
            StageManager.setScene(helpWindow);
        });
        //skills button
        ButtonFactory.setDefaultActions(skillsButton);
        skillsButton.setOnAction( e -> {
            SceneDefineTemplate sceneDefineTemplate = new SceneDefineTemplate();
            StageManager.setScene(sceneDefineTemplate);
        });
        // logout button
        ButtonFactory.setDefaultActions(logoutButton);
        logoutButton.setOnAction( e -> {
            SceneLogin loginScreen = new SceneLogin();
            StageManager.setScene(loginScreen);
        });
        //exit button
        ButtonFactory.setDefaultActions(exitButton);
        exitButton.setOnAction( e -> {
            StageManager.close();
        });

        ButtonFactory.setDefaultActions(mtd);
        mtd.setOnAction(event -> {
            if(!isMTD){
                mtd.setText("Turn of multi-turn");
                isMTD=true;
            }else{
                mtd.setText("Multi-turn mode");
                isMTD = false;
            }
        });

        //auto button
        ButtonFactory.setDefaultActions(auto);
        auto.setOnAction( e -> {
            //todo: auto button action
        });

        //mic button
        mic.setOnMouseEntered(e ->{
            if(!micOnOff){
                Image microphone = new Image("redMic.png");
                ImageView viewMic = new ImageView(microphone);
                viewMic.setFitWidth(35);
                viewMic.setFitHeight(35);
                mic.setGraphic(viewMic);
            }else {
                Image microphone = new Image("greenMic.png");
                ImageView viewMic = new ImageView(microphone);
                viewMic.setFitWidth(35);
                viewMic.setFitHeight(35);
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setBrightness(1.0);
                viewMic.setEffect(colorAdjust);
                mic.setGraphic(viewMic);
            }
        });
        mic.setOnMouseExited(e ->{
            if(!micOnOff){
                Image microphone = new Image("redMic.png");
                ImageView viewMic = new ImageView(microphone);
                viewMic.setFitWidth(30);
                viewMic.setFitHeight(30);
                mic.setGraphic(viewMic);
            }else {
                Image microphone = new Image("greenMic.png");
                ImageView viewMic = new ImageView(microphone);
                viewMic.setFitWidth(30);
                viewMic.setFitHeight(30);
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setBrightness(1.0);
                viewMic.setEffect(colorAdjust);
                mic.setGraphic(viewMic);
            }
        });
        mic.setOnAction( e -> {
            // TODO: SET ACTION
            if (micOnOff) {
                // Turn mic off
                System.out.println("off");
                micOnOff = false;
                System.out.println("micStatus " + micOnOff);

                // Stop recording
                try {
                    transcription = sd.recordStop();
                    System.out.println("Transcription -> " + transcription);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


                // User input
                setUserInput(transcription); // get from mic detection string
                setChatText(currentUserInput, false); // set text on chat equal to user input
                if(isMTD) {
                    setBotChatText(StageManager.getResponse(currentUserInput, true));
                }else{
                    // Getting response from the bot
                    setBotChatText(StageManager.getResponse(currentUserInput)); // get response from bot
                }
                setChatText(currentBotInput, true); // set text on chat equal to bot response
                userInput.setText(""); // reset user input text
                // For TTS (s)
                String botAsnwer = sd.botGetSpeakerID() +"   "+ currentBotInput;
                sd.botTextToSpeech(botAsnwer,"tts_Loris.wav");
            } else {
                // Turn mic on
                System.out.println("on");
                micOnOff = true;
                System.out.println("micStatus " + micOnOff);

                // Start recording
                try {
                    sd.recordStart();
                } catch (LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //user chat
        ButtonFactory.setDefaultActions(sendUserInput);
        sendUserInput.setOnAction(e -> {
            if (true) {
                if(errorHandling.stringLengthError(userInput.getText())) {

                        //user input
                        setUserInput(userInput.getText());
                        setChatText(currentUserInput, false);

                        //Getting response from the bot
                        setBotChatText(StageManager.getResponse(currentUserInput));
                        setChatText(currentBotInput, true);
                        userInput.setText("");

                } else System.out.println("invalid message");
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert Dialog");
                alert.setHeaderText("No User Detected");
                alert.setContentText("Sorry, your presence was not detected on the camera. The program cannot proceed with the request.");
                alert.showAndWait();
            }
        });
    }

    public void setChatText(String input, boolean isBotOutput){
        // Adding the input into a list that will display chat messages
        TextArea textArea = new TextArea(input);
        listText.add(textArea);
        listText.get(listText.size()-1).setWrapText(true);
        // Computing the size of window around the text
        // The limit for the width is 300
        // Any other number will do
        int width = Math.min(input.length()*7 +20,300);
        // Setting the box around the text size
        listText.get(listText.size()-1).setPrefSize(width, countLines(input));
        // Initial position
        // Used for user's input
        int layoutX = 0;
        // Constant size of entire chatPane
        int leftBoundary = 510;
        // Change the sides if it bots response
        if (isBotOutput) {
            //Calculate the text border position for top-left corner
            layoutX = leftBoundary - width;
        }
        // Setting the position of the text
        listText.get(listText.size()-1).setLayoutX(layoutX);
        listText.get(listText.size()-1).setLayoutY(chatYPos);
        // Some minor sets
        listText.get(listText.size()-1).setEditable(false);
        listText.get(listText.size()-1).setFont(Font.font("Arial Narrow",15));
        listText.get(listText.size()-1).setStyle("-fx-control-inner-background: rgb(159,182,189);"+ "-fx-text-fill: white; ");
        // The next y position should be lower
        chatYPos +=listText.get(listText.size()-1).getPrefHeight()+40;
        // Displaying the text in the chat
        scrollChat.getChildren().add(listText.get(listText.size()-1));
    }
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
        scrollPane.setPrefSize(520,460);
        // TODO: IF YOU NEED THE RED BORDER add " -fx-border-color: red"
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; ");
        scrollPane.setContent(scrollChat);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        UIPane.getChildren().add(scrollPane);
    }

    public void createScroolList(){

        //This is the blank rectangle to the left of the arrow
        Rectangle rectangle = new Rectangle(40,260,155,25);
        rectangle.setFill(Color.WHITE);
        Text textMessage = new Text("Perfect Matching");
        textMessage.setX(50);
        textMessage.setY(280);
        textMessage.setFill(Color.LIGHTGRAY);
        textMessage.setFont(Font.font("Ariel",16));
        //textMessage.setStyle("-fx-font-weight: bold");
        UIPane.getChildren().add(rectangle);
        UIPane.getChildren().add(textMessage);

        //Here we add the content of the list
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll("Perfect Matching", "Keywords", "Vectors", "Vector Sequences");

        // Enable vertical scrolling for the ListView
        listView.setPrefSize(155, 125);

        // Initially hide the list
        listView.setVisible(false);
        listView.setTranslateX(40);
        listView.setTranslateY(260);

        // Listeners to show and hide list
        Button bar = new Button("\\/");
        bar.setOnMouseClicked(event -> {
            listView.setVisible(!listView.isVisible());
        });
        rectangle.setOnMouseClicked(event -> {
            listView.setVisible(!listView.isVisible());
        });
        bar.setTranslateX(190);
        bar.setTranslateY(260);
        UIPane.getChildren().add(bar);
        UIPane.getChildren().add(listView);

        // THIS IS WHERE WE DETECT IF AN ITEM IN THE LIST HAS BEEN SELECTED
        listView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> ov,
                                        String old_val, String new_val) {
                        switch (new_val){
                            case ("Perfect Matching"):
                                textMessage.setText("Perfect Matching");
                                StageManager.getConnection().setDatabase(DB.DB_PERFECT_MATCHING);
                                try {
                                    StageManager.getConnection().reload();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case ("Keywords"):
                                textMessage.setText("Keywords");
                                StageManager.getConnection().setDatabase(DB.DB_KEYWORDS);
                                try {
                                    StageManager.getConnection().reload();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case ("Vectors"):
                                textMessage.setText("Vectors");
                                StageManager.getConnection().setDatabase(DB.DB_VECTORS);
                                try {
                                    StageManager.getConnection().reload();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case ("Vector Sequences"):
                                textMessage.setText("Vector Sequences");
                                StageManager.getConnection().setDatabase(DB.DB_VECTORS_SEQ);
                                try {
                                    StageManager.getConnection().reload();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                        }
                        // You can use the if statements to match the enum names to item names
                    }
                });
    }

    /**
     * Main method to design the javaFX scene (magic go booom)
     */
    @Override
    public void design(){
        //side menu
        createSideMenu();
        crateTextArea();
        createButtons();
        createScrollPane();
        createScroolList();
    }
    public void keyboardHandler(){
        addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
                    SceneLogin sceneLogin = new SceneLogin();
                    StageManager.setScene(sceneLogin);
                    ke.consume();
                }else if(ke.getCode() == KeyCode.ENTER) {
                    // Send user message on enter
                    String input = userInput.getText().replaceAll("\n", "");
                    setUserInput(input);
                    setChatText(input, false);

                    setBotChatText(StageManager.getResponse(currentUserInput));
                    setChatText(currentBotInput, true);
                    userInput.setText("");
                }
            }
        });
    }

        public int countLines(String string){
            String[] lines = string.split("\r\n|\r|\n");
            int count=lines.length;
            for (int i = 0; i < lines.length; i++) {
                count=count+18;
            }
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
            return count;
        }

}

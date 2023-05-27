package org.group1.GUI.stage.scenes;


import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.group1.GUI.stage.StageManager;
import org.group1.GUI.stage.scenes.utils.ButtonFactory;
import org.group1.GUI.stage.scenes.utils.ErrorHandling;

import java.util.*;



public class SceneChat extends SceneManager implements ICustomScene {
    ArrayList<TextArea> listText= new ArrayList<>();
    String chat = "";
    String currentUserInput="";
    String currentBotInput="";

    ErrorHandling errorHandling = new ErrorHandling();
    // javafx elements
    private TextArea userInput;
    private Stage menuStage;
    private Button sendUserInput, helpButton, skillsButton, logoutButton, exitButton;
    private int chatYPos =0;

    public SceneChat(){
        makeNewPane();
        design();
        keyboardHandler();
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
        exitButton = ButtonFactory.createButton("EXIT", 20, 250);
        UIPane.getChildren().add(exitButton);

        // send button
        sendUserInput = ButtonFactory.createButton("SEND", 810, 590);
        sendUserInput.setFont(Font.font("Impact", FontWeight.BOLD,20));
        UIPane.getChildren().add(sendUserInput);

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

        //user chat
        ButtonFactory.setDefaultActions(sendUserInput);
        sendUserInput.setOnAction(e -> {
            if(errorHandling.stringLengthError(userInput.getText())) {
                //user input
                setUserInput(userInput.getText());
                setChatText(currentUserInput, false);
                //Getting response from the bot
                setBotChatText(StageManager.getResponse(currentUserInput));
                setChatText(currentBotInput, true);
                userInput.setText("");
            }else System.out.println("invalid message");
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

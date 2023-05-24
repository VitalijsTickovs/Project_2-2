package org.group1.GUI;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

import org.group1.GUI.utils.ButtonFactory;
import org.group1.GUI.utils.ErrorHandling;
import org.group1.back_end.response.Response;

public class ChatWindow extends StageManager implements ICustomStage {
    ArrayList<TextArea> listText= new ArrayList<>();
    String chat = "";
    String currentUserInput="";
    String currentBotInput="";

    ErrorHandling errorHandling = new ErrorHandling();
    // javafx elements
    private TextArea userInput;
    private Stage menuStage;
    private Button sendUserInput, helpButton, skillsButton, logoutButton, exitButton;
    private Response responseGenerator;
    private int chatYPos =0;

    public ChatWindow(){
        try{
            responseGenerator = new Response();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initStage();
        design();
        keyboardHandler();
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
        helpButton = ButtonFactory.createButton("HELP", 20, 170);
        UIpane.getChildren().add(helpButton);

        //skills editor button
        skillsButton = ButtonFactory.createButton("SKILLS EDITOR", 20, 130);
        UIpane.getChildren().add(skillsButton);

        //logout button
        logoutButton = ButtonFactory.createButton("SIGN OUT", 20, 210);
        UIpane.getChildren().add(logoutButton);

        //exit Button
        exitButton = ButtonFactory.createButton("EXIT", 20, 580);
        UIpane.getChildren().add(exitButton);

        // send button
        sendUserInput = ButtonFactory.createButton("SEND", 810, 590);
        sendUserInput.setFont(Font.font("Impact", FontWeight.BOLD,20));
        UIpane.getChildren().add(sendUserInput);

        setActionForButtons();
    }

    public void setActionForButtons(){
        //help button
        ButtonFactory.setDefaultActions(helpButton);
        helpButton.setOnAction( e -> {
            HelpWindow helpWindow = new HelpWindow();
            helpWindow.setStage(UIstage);
        });
        //skills button
        ButtonFactory.setDefaultActions(skillsButton);
        skillsButton.setOnAction( e -> {
            SkillEditor skillEditor = new SkillEditor(responseGenerator);
            skillEditor.setStage(UIstage);
        });//logout button
        ButtonFactory.setDefaultActions(logoutButton);
        logoutButton.setOnAction( e -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setStage(UIstage);
        });
        //exit button
        ButtonFactory.setDefaultActions(exitButton);
        exitButton.setOnAction( e -> {
            UIstage.close();
        });

        //user chat
        ButtonFactory.setDefaultActions(sendUserInput);
        sendUserInput.setOnAction(e -> {
            if(errorHandling.stringLengthError(userInput.getText())) {
                //user input
                setUserInput(userInput.getText());
                setChatText(currentUserInput, false);
                //Getting response from the bot
                setBotChatText(responseGenerator.getResponse(currentUserInput));
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
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setContent(scrollChat);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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
        createSideMenu();
        crateTextAreas();
        createButtons();
        createScrollPane();
        createScroolList();
    }

    public void createScroolList(){

        //This is the blank rectangle to the left of the arrow
        Rectangle rectangle = new Rectangle(40,260,100,25);
        rectangle.setFill(Color.WHITE);
        Text textMessage = new Text("NameOfList");
        textMessage.setX(50);
        textMessage.setY(280);
        textMessage.setFill(Color.LIGHTGRAY);
        textMessage.setFont(Font.font("Ariel",16));
        //textMessage.setStyle("-fx-font-weight: bold");
        UIpane.getChildren().add(rectangle);
        UIpane.getChildren().add(textMessage);

        //Here we add the content of the list
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll("Item 1", "Item 2", "Item 3", "Item 4", "Item 5","Item 6", "Item 7", "Item 8");

        // Enable vertical scrolling for the ListView
        listView.setPrefSize(100, 125);

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
        bar.setTranslateX(140);
        bar.setTranslateY(260);
        UIpane.getChildren().add(bar);
        UIpane.getChildren().add(listView);

        // THIS IS WHERE WE DETECT IF AN ITEM IN THE LIST HAS BEEN SELECTED
        listView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> ov,
                                        String old_val, String new_val) {
                        System.out.println("selected "+ new_val);
                        // You can use the if statements to match the enum names to item names
                    }
                });
    }

    public void keyboardHandler(){
        UIscene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
                    LoginScreen loginScreen = new LoginScreen();
                    loginScreen.setStage(UIstage);
                    ke.consume();
                }else if(ke.getCode() == KeyCode.ENTER){
                    // Send user message on enter
                    String input = userInput.getText().replaceAll("\n","");
                    setUserInput(input);
                    setChatText(input, false);

                    setBotChatText(responseGenerator.getResponse(currentUserInput));
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
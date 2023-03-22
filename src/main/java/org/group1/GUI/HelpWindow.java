package org.group1.GUI;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelpWindow implements CustomStage{

    private AnchorPane UIpane;
    private Stage UIstage;
    private Scene UIscene;
    private Stage chatStage;

    public HelpWindow(){
        UIpane = new AnchorPane();
        UIscene = new Scene(UIpane,LoginScreen.screenWidth,LoginScreen.screenHeight);
        UIstage = new Stage();
        UIscene.setFill(Color.LIGHTGRAY);
        UIpane.setStyle("-fx-background-color: transparent");
        UIstage.setScene(UIscene);
        keyboardHandler();
//



        // TODO: make it bonitinho
        Label lb1 = new Label("HOW TO DEFINE QUESTIONS?");
        Label exampleLabel = new Label("Which <FRUITS> are favourite in <COUNTRY>?");
        Label exampleLabel2 = new Label("What  <LOCATION> is <COMPANY> head quarters located?");

        Label lb2 = new Label("HOW TO DEFINE ACTIONS?");


        exampleLabel.setLayoutX(10);
        exampleLabel.setLayoutY(10);

        exampleLabel2.setLayoutX(10);
        exampleLabel2.setLayoutY(30);

        exampleLabel.setWrapText(true);
        exampleLabel2.setWrapText(true);

        UIpane.getChildren().add(exampleLabel);
        UIpane.getChildren().add(exampleLabel2);

    }

    public void setStage(Stage mainStage){
        this.chatStage=mainStage;
        mainStage.close();
        UIstage.show();
    }

    @Override
    public void design() {

    }

    //@Override
    public void keyboardHandler() {
        UIscene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
                    System.out.println("hiddfsdp");
                    ChatWindow chatWindow = null;
                    try {
                        chatWindow = new ChatWindow();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    chatWindow.setStage(UIstage);
                    ke.consume();
                }
            }
        });
    }

    //TODO: add example text here
}

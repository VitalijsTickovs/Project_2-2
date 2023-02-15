package GUI;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.text.WordUtils;

public class SkillEditor implements CustomStage{
    private AnchorPane UIpane;
    private Stage UIstage;
    private Scene UIscene;
    private Stage chatStage;
    private Button displaySkills,back,help,sendUserInput;
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
               //TODO: change the window to the one where you can choose skills
            }
        });
        UIpane.getChildren().add(displaySkills);

        help = new Button();
        help.setText("HELP");
        help.setFont(Font.font("Impact", FontWeight.BOLD,30));
        help.setStyle("-fx-background-color: transparent");
        help.setTextFill(Color.WHITE);
        help.setLayoutX(20);
        help.setLayoutY(170);
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

        TextArea questionInput = new TextArea("                  ... <  > ... <  > ... ");
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
        back.setLayoutY(210);
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
        sendUserInput.setStyle("-fx-background-color: transparent");
        sendUserInput.setTextFill(Color.WHITE);
        sendUserInput.setCursor(Cursor.CLOSED_HAND);
        sendUserInput.setLayoutX(810);
        sendUserInput.setLayoutY(390);
        sendUserInput.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(errorHandling.stringLengthError(questionInput.getText())) {

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
        UIpane.getChildren().add(sendUserInput);



    }

}

package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class ChatWindow {

    String chat = "";
    private AnchorPane UIpane;
    private Stage UIstage;
    private Stage menuStage;
    private Scene UIscene;
    private TextField userInput;
    private TextArea userOutput;
    private Button sendUserInput = new Button("send");
    private Image sendUserImage= new Image("GUI/send.png",70,30,false,true);
    private ImageView sendUserView = new ImageView(sendUserImage);
    public ChatWindow(){
        UIpane = new AnchorPane();
        UIscene = new Scene(UIpane,LoginScreen.screenWidth,LoginScreen.screenHeight);
        UIstage = new Stage();
        UIscene.setFill(Color.LIGHTPINK);
        UIpane.setStyle("-fx-background-color: transparent");
        UIstage.setScene(UIscene);
        design();

    }
    public void setStage(Stage mainStage){
        this.menuStage=mainStage;
        mainStage.hide();
        UIstage.show();
    }
    public void design(){
        userInput = new TextField();
        userInput.setPrefSize(250,50);
        userInput.setTranslateX(50);
        userInput.setTranslateY(430);
        UIpane.getChildren().add(userInput);

        userOutput = new TextArea();
        userOutput.setPrefSize(250,330);
        userOutput.setTranslateX(50);
        userOutput.setTranslateY(50);
        userOutput.setCursor(Cursor.CLOSED_HAND);

        UIpane.getChildren().add(userOutput);
        userOutput.setEditable(false);

        ColorAdjust effect = new ColorAdjust();
        effect.setBrightness(-0.5);

//        loginButtonView.setFitHeight(50);
//        loginButtonView.setFitWidth(80);
        sendUserInput = new Button();
        sendUserInput.setStyle("-fx-background-color: transparent");
        sendUserInput.setGraphic(sendUserView);
        sendUserInput.setLayoutX(310);
        sendUserInput.setLayoutY(445);
        sendUserInput.setCursor(Cursor.CLOSED_HAND);
        sendUserInput.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chat = chat + "you: "+ userInput.getText() + "\n";
                userOutput.setText(chat);
                System.out.println(chat);
            }
        });
        sendUserInput.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sendUserInput.setEffect(effect);
            }
        });
        sendUserInput.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sendUserInput.setEffect(null);
            }
        });
        UIpane.getChildren().add(sendUserInput);
    }

}

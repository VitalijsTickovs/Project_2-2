package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.lang.model.type.NullType;
import java.awt.event.ActionListener;


public class LoginScreen {
    //VIEW SETTINGS
    private AnchorPane UIpane;
    private Stage UIstage;
    private Scene UIscene;
    public Stage exitStage;
    static int screenWidth = 700;
    static int screenHeight = 500;
    public Button loginButton;
    private Image loginButtonImage= new Image("GUI/login.png",150,60,false,true);
    private ImageView loginButtonView = new ImageView(loginButtonImage);
    public LoginScreen(){
        UIpane = new AnchorPane();
        UIstage = new Stage();
        UIscene = new Scene(UIpane,screenWidth,screenHeight);
        UIscene.setFill(Color.LIGHTPINK);
        UIpane.setStyle("-fx-background-color: transparent");
        UIstage.setScene(UIscene);
        design();
    }
    public Stage getUIstage(){
        return UIstage;
    }
    //WHY DO U HAVE TO DO SO MUCH FOR ONE FUCKING BUTTON????
    //LIBGDX>>>>>>JAVAFX
    public void design(){

        ColorAdjust effect = new ColorAdjust();
        effect.setBrightness(-0.5);

        loginButtonView.setFitHeight(50);
        loginButtonView.setFitWidth(80);
        loginButton = new Button();
        loginButton.setStyle("-fx-background-color: transparent");
        loginButton.setGraphic(loginButtonView);
        loginButton.setLayoutX(50);
        loginButton.setLayoutY(200);
        loginButton.setCursor(Cursor.CLOSED_HAND);

        loginButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loginButton.setEffect(effect);
            }
        });
        loginButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loginButton.setEffect(null);
            }
        });
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("hello");
                ChatWindow chatWindow=new ChatWindow();
                chatWindow.setStage(UIstage);
            }
        });
        UIpane.getChildren().add(loginButton);
    }

}

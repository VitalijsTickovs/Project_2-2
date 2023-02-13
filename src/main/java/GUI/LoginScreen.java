package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

public class LoginScreen implements CustomStage {
    //VIEW SETTINGS
    private AnchorPane UIpane;
    private Stage UIstage;
    private Scene UIscene;
    public Stage exitStage;
    static int screenWidth = 700;
    static int screenHeight = 500;
    public Button loginButton;
    private Stage chatWindow;
    private Image robotImage= new Image("GUI/Joe.png",300,500,false,true);
    private ImageView robotImageView = new ImageView(robotImage);
    public LoginScreen(){
        UIpane = new AnchorPane();
        UIstage = new Stage();
        UIscene = new Scene(UIpane,screenWidth,screenHeight);
        UIscene.setFill(Color.LIGHTGRAY);
        UIpane.setStyle("-fx-background-color: transparent");
        UIstage.setScene(UIscene);
        design();
        keyboardHandler();
    }
    public Stage getUIstage(){
        return UIstage;
    }
    public void setStage(Stage mainStage){
        this.chatWindow=mainStage;
        mainStage.close();
        UIstage.show();
    }
    //WHY DO U HAVE TO DO SO MUCH FOR ONE FUCKING BUTTON????
    //LIBGDX>>>>>>JAVAFX
    @Override
    public void design(){

        ColorAdjust effect = new ColorAdjust();
        effect.setBrightness(-0.5);

        Rectangle rectangle = new Rectangle(40, 40, 300, 400);
        rectangle.setFill(Color.DARKGRAY);
        UIpane.getChildren().add(rectangle);

        loginButton = new Button();
        loginButton.setText("LOGIN");
        loginButton.setFont(Font.font("Impact", FontWeight.BOLD,25));
        loginButton.setStyle("-fx-background-color: transparent");
        loginButton.setTextFill(Color.WHITE);
        loginButton.setLayoutX(142);
        loginButton.setLayoutY(240);
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
        robotImageView.setLayoutX(340);
        robotImageView.setLayoutY(30);
//        robotImageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                robotImageView.setEffect(effect);
//            }
//        });
//        robotImageView.setOnMouseExited(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                robotImageView.setEffect(null);
//            }
//        });
        UIpane.getChildren().add(robotImageView);

        Text username = new Text("Username: ");
        username.setFont(Font.font("Impact",20));
        username.setStyle("-fx-font-weight: bold");
        username.setFill(Color.WHITE);
        username.setTranslateX(145);
        username.setTranslateY(120);
        UIpane.getChildren().add(username);

        Text password = new Text("Password: ");
        password.setFont(Font.font("Impact",20));
        password.setStyle("-fx-font-weight: bold");
        password.setFill(Color.WHITE);
        password.setTranslateX(145);
        password.setTranslateY(190);
        UIpane.getChildren().add(password);

        TextField loginTextField = new TextField();
        loginTextField.setTranslateX(110);
        loginTextField.setTranslateY(130);
        TextField passwordTextField = new TextField();
        passwordTextField.setTranslateX(110);
        passwordTextField.setTranslateY(200);
        UIpane.getChildren().add(loginTextField);
        UIpane.getChildren().add(passwordTextField);

    }

    @Override
    public void keyboardHandler() {
        UIscene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
                    UIstage.close();
                    ke.consume();
                }
            }
        });
    }

}

package org.group1.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DisplaySkills implements  CustomStage {
    ArrayList<String> skills =new ArrayList();
    ArrayList<Button> buttons = new ArrayList<>();
    ErrorHandling errorHandling = new ErrorHandling();
    private AnchorPane UIpane,scrollChat;
    private Stage UIstage;
    private Scene UIscene;
    private Stage chatStage;
    private Button submit,displaySkills,back,help,defineSkills;

    public DisplaySkills(){
        loadSkillsFromDatabase();
        UIpane = new AnchorPane();
        scrollChat = new AnchorPane();
        UIscene = new Scene(UIpane,LoginScreen.screenWidth,LoginScreen.screenHeight);
        UIstage = new Stage();
        UIscene.setFill(Color.rgb(18,64,76));
        UIpane.setStyle("-fx-background-color: transparent");
        UIstage.setScene(UIscene);
        design();
    }

    public void setStage(Stage mainStage,Stage chatStage){
        this.chatStage=chatStage;
        mainStage.close();
        UIstage.show();
    }
    //TODO: connect it to the database
    public void loadSkillsFromDatabase(){
        for (int i = 1; i < 15; i++) {
            skills.add("Skill "+ i);
        }
    }

    @Override
    public void design() {

        Text text = new Text("Choose which skill to access");
        text.setFont(Font.font("Impact",40));
        text.setStyle("-fx-font-weight: bold");
        text.setFill(Color.WHITE);
        text.setTranslateX(340);
        text.setTranslateY(40);
        UIpane.getChildren().add(text);

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
        displaySkills.setTextFill(Color.rgb(42,97,117));
        displaySkills.setCursor(Cursor.CLOSED_HAND);

        UIpane.getChildren().add(displaySkills);

        defineSkills = new Button();
        defineSkills.setText("DEFINE SKILLS");
        defineSkills.setFont(Font.font("Impact", FontWeight.BOLD,30));
        defineSkills.setStyle("-fx-background-color: transparent");
        defineSkills.setTextFill(Color.WHITE);
        defineSkills.setLayoutX(20);
        defineSkills.setLayoutY(170);
        defineSkills.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                defineSkills.setTextFill(Color.rgb(42,97,117));
            }
        });
        defineSkills.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                defineSkills.setTextFill(Color.WHITE);
            }
        });

        defineSkills.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SkillEditor skillEditor = new SkillEditor();
                skillEditor.setStage(UIstage,chatStage);
            }
        });
        UIpane.getChildren().add(defineSkills);

        help = new Button();
        help.setText("HELP");
        help.setFont(Font.font("Impact", FontWeight.BOLD,30));
        help.setStyle("-fx-background-color: transparent");
        help.setTextFill(Color.WHITE);
        help.setLayoutX(20);
        help.setLayoutY(210);
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

        back = new Button();
        back.setText("BACK");
        back.setFont(Font.font("Impact", FontWeight.BOLD,30));
        back.setStyle("-fx-background-color: transparent");
        back.setTextFill(Color.WHITE);
        back.setLayoutX(20);
        back.setLayoutY(250);
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
                ChatWindow chatWindow = null;
                try {
                    chatWindow = new ChatWindow();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                chatWindow.setStage(UIstage,chatStage);
            }
        });
        UIpane.getChildren().add(back);



        //scrollpane with skills
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollChat.setStyle("-fx-background-color: transparent");

        scrollPane.setTranslateX(340);
        scrollPane.setTranslateY(50);
        scrollPane.setPrefSize(470,460);
        // TODO: IF YOU NEED THE RED BORDER add " -fx-border-color: red"
        scrollPane.setStyle("-fx-background-color: transparent;"+"-fx-border-color: red" );
        scrollPane.setContent(scrollChat);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // scrollPane.setContent(exitButton);
        UIpane.getChildren().add(scrollPane);
        UIstage.setOnShown(e ->
                scrollPane.lookup(".viewport").setStyle("-fx-background-color: transparent;"));

        for (int i = 0,y=20; i < skills.size(); i++) {
            String temp = skills.get(i);
            buttons.add(new Button());
            Button button = buttons.get(i);
            button.setText(skills.get(i));
            button.setFont(Font.font("Impact", FontWeight.BOLD,30));
            button.setStyle("-fx-background-color: transparent");
            button.setTextFill(Color.WHITE);
            button.setLayoutX(180);
            button.setLayoutY(y);
            button.setCursor(Cursor.CLOSED_HAND);
            y+=50;
            button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    button.setTextFill(Color.rgb(42,97,117));
                }
            });
            button.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    button.setTextFill(Color.WHITE);
                }
            });
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("i am click");
                    SkillDetails skillDetails= new SkillDetails(temp);
                    skillDetails.setStage(UIstage,chatStage);
                }
            });
            scrollChat.getChildren().add(buttons.get(i));
        }
        //scrollChat.getChildren().add();
        scrollPane.setContent(scrollChat);

        submit = new Button();
        submit.setText("LOAD");
        submit.setFont(Font.font("Impact", FontWeight.BOLD,20));
        submit.setStyle("-fx-background-color: rgba(159,182,189,1)");
        submit.setTextFill(Color.WHITE);
        submit.setCursor(Cursor.CLOSED_HAND);
        submit.setPrefSize(400,50);
        submit.setLayoutX(386);
        submit.setLayoutY(520);
//        submit.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                if(errorHandling.stringLengthError(questionInput.getText())) {
//
//                }else System.out.println("invalid message");
//            }
//        });
        submit.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                submit.setStyle("-fx-background-color: rgba(42,97,117,1)");
            }
        });
        submit.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                submit.setStyle("-fx-background-color: rgba(159,182,189,1)");
            }
        });
        UIpane.getChildren().add(submit);

    }
}

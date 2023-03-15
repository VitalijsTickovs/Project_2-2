package org.group1.GUI;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.group1.response.FileService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SkillDetails implements CustomStage {
    // TODO: YOU WILL HAVE TO DEAL WITH CHAT HISTORY
    private AnchorPane UIpane,scrollChat;
    private Stage UIstage;
    private Scene UIscene;
    private Stage chatStage;
    private Button back,help;
    List<String> columnNames;
    String skillName;


    private TableView<TestSkill> table = new TableView<TestSkill>();
    private final ObservableList<TestSkill> data =
            FXCollections.observableArrayList(
                    new TestSkill("Jacob", "Smith", "jacob.smith@example.com"),
                    new TestSkill("Isabella", "Johnson", "isabella.johnson@example.com"),
                    new TestSkill("Ethan", "Williams", "ethan.williams@example.com"),
                    new TestSkill("Emma", "Jones", "emma.jones@example.com"),
                    new TestSkill("Michael", "Brown", "michael.brown@example.com")
            );

    public SkillDetails(String skillName){
        setColumnNames();
        this.skillName=skillName;

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
    public void setColumnNames(){
        columnNames = new ArrayList<>();
        columnNames.add("hello");
        columnNames.add("name");
        columnNames.add("ofjdaisf");
        columnNames.add("nafaisdjjiofdsme");
    }

    @Override
    public void design() {

        Text text = new Text(skillName);
        text.setFont(Font.font("Impact",40));
        text.setStyle("-fx-font-weight: bold");
        text.setFill(Color.WHITE);
        text.setTranslateX(540);
        text.setTranslateY(40);
        UIpane.getChildren().add(text);

        //side menu
        Rectangle sideMenu = new Rectangle(0,0,250,LoginScreen.screenHeight);
        sideMenu.setFill(Color.rgb(159,182,189));
        UIpane.getChildren().add(sideMenu);

        back = new Button();
        back.setText("BACK");
        back.setFont(Font.font("Impact", FontWeight.BOLD,30));
        back.setStyle("-fx-background-color: transparent");
        back.setTextFill(Color.WHITE);
        back.setLayoutX(20);
        back.setLayoutY(170);
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
                DisplaySkills displaySkills = new DisplaySkills(FileService.getFiles().length);
                displaySkills.setStage(UIstage,chatStage);
            }
        });
        UIpane.getChildren().add(back);

        help = new Button();
        help.setText("HELP");
        help.setFont(Font.font("Impact", FontWeight.BOLD,30));
        help.setStyle("-fx-background-color: transparent");
        help.setTextFill(Color.WHITE);
        help.setLayoutX(20);
        help.setLayoutY(130);
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

        //Create table

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //scrollChat.heightProperty().addListener(observable -> scrollPane.setVvalue(1D));

        scrollChat.setStyle("-fx-background-color: transparent");

        scrollPane.setTranslateX(340);
        scrollPane.setTranslateY(50);
        scrollPane.setPrefSize(470,460);
        // TODO: IF YOU NEED THE RED BORDER add " -fx-border-color: red"
        scrollPane.setStyle("-fx-background-color: transparent;"+"-fx-border-color: red" );
        scrollPane.setContent(scrollChat);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        UIpane.getChildren().add(scrollPane);
        UIstage.setOnShown(e ->
                scrollPane.lookup(".viewport").setStyle("-fx-background-color: transparent;"));
        //Display the skills
        //TODO : MAKE THIS AUTOMATED

        TableView<ObservableList<String>> table = new TableView<>();
        table.setPrefWidth(470);
        table.setEditable(true);

        for (int i = 0; i < columnNames.size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    columnNames.get(i)
            );
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            table.getColumns().add(column);
        }
        //add data
        int N_ROWS=2;
        String Data="arr";
        String Mail="more arr";
        for (int i = 0; i < N_ROWS; i++) {
            table.getItems().add(
                    FXCollections.observableArrayList(
                            Data,Mail,"dsfsd","fdhus"
                    )
            );
            Data="chaneg";
            Mail="works";
        }

//        TableColumn col1 = new TableColumn(" Name");
//        TableColumn col2 = new TableColumn(" Name");
//        TableColumn col3 = new TableColumn(" Name");
//        col1.setCellValueFactory(
//                new PropertyValueFactory<TestSkill, String>("firstName"));
//        col2.setCellValueFactory(
//                new PropertyValueFactory<TestSkill, String>("lastName"));
//        col3.setCellValueFactory(
//                new PropertyValueFactory<TestSkill, String>("email"));

//        col1.setPrefWidth(150);
//        col2.setPrefWidth(150);
//        col3.setPrefWidth(150);
//
//        table.setItems(data);
//        table.getColumns().addAll(col1, col2, col3);
        scrollChat.getChildren().add(table);
        scrollPane.setContent(scrollChat);
    }
}

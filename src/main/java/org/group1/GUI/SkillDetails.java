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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import org.group1.response.FileService;
import org.group1.response.database.SQLGUIConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillDetails implements CustomStage {
    // TODO: YOU WILL HAVE TO DEAL WITH CHAT HISTORY
    private AnchorPane UIpane,scrollChat;
    private Stage UIstage;
    private Scene UIscene;
    private Stage chatStage;
    private Button back,help,addAction;
    private ScrollPane scrollPane;
    private TableView<ObservableList<String>> table;
    private List<String> tempObservable;
    private int N_ROWS, ColNum;
    private SQLGUIConnection sql = new SQLGUIConnection();
    private ArrayList<ObservableList<String>> comboData = new ArrayList<>();
    FileService fs;

    List<String> columnNames;
    String tableName;
    ArrayList<ArrayList<String>> dataPerColumn = new ArrayList<>();
    public SkillDetails(String tableName, int ColNum, int RowNum, String slotTable) throws SQLException {
        columnNames = sql.getColumnNames(tableName);
        slotData(slotTable);
        this.tableName=tableName;
        this.ColNum=ColNum;
        this.N_ROWS=RowNum;
        collectDataFromDatabase();
        UIpane = new AnchorPane();
        scrollChat = new AnchorPane();
        UIscene = new Scene(UIpane,LoginScreen.screenWidth,LoginScreen.screenHeight);
        UIstage = new Stage();
        UIscene.setFill(Color.rgb(18,64,76));
        UIpane.setStyle("-fx-background-color: transparent");
        UIstage.setScene(UIscene);
        design();
    }
    public void collectDataFromDatabase() throws SQLException {
        List<String> tempColName = new ArrayList<>();
        tempColName = sql.getColumnNames(tableName);
        for (int i = 0; i < sql.getColumnNumber(tableName); i++) {
           dataPerColumn.add(sql.getAllColumnData(tempColName.get(i),tableName));
        }
    }
    public void setStage(Stage mainStage,Stage chatStage){
        this.chatStage=chatStage;
        mainStage.close();
        UIstage.show();
    }
    public void createButtons(){
        //back button
        back = new Button();
        back.setText("BACK");
        back.setFont(Font.font("Impact", FontWeight.BOLD,30));
        back.setStyle("-fx-background-color: transparent");
        back.setTextFill(Color.WHITE);
        back.setLayoutX(20);
        back.setLayoutY(170);
        back.setCursor(Cursor.CLOSED_HAND);
        UIpane.getChildren().add(back);

        //help button
        help = new Button();
        help.setText("HELP");
        help.setFont(Font.font("Impact", FontWeight.BOLD,30));
        help.setStyle("-fx-background-color: transparent");
        help.setTextFill(Color.WHITE);
        help.setLayoutX(20);
        help.setLayoutY(130);
        help.setCursor(Cursor.CLOSED_HAND);
        UIpane.getChildren().add(help);

        //addAction button
        addAction = new Button();
        addAction.setText("ADD ROW");
        addAction.setFont(Font.font("Impact", FontWeight.BOLD,30));
        addAction.setStyle("-fx-background-color: transparent");
        addAction.setTextFill(Color.WHITE);
        addAction.setLayoutX(380);
        addAction.setLayoutY(450);
        addAction.setCursor(Cursor.CLOSED_HAND);
        UIpane.getChildren().add(addAction);

    }
    public void setButtonActions(){
        //back button
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
                DisplaySkills displaySkills = new DisplaySkills(fs.getFiles().length);
                displaySkills.setStage(UIstage,chatStage);
            }
        });

        //help button
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

        //addColumn
        addAction.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addAction.setTextFill(Color.rgb(42,97,117));
            }
        });
        addAction.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addAction.setTextFill(Color.WHITE);
            }
        });
        addAction.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("click");
                addRow();
            }
        });
    }
    public void addRow(){
        //TODO: QUICK FIX FOR ROW : CUT THE WORDS
        tempObservable = new ArrayList<>();
            for (int j = 0; j < ColNum; j++) {
                tempObservable.add("-");
            }
            table.getItems().add(
                    FXCollections.observableArrayList(
                            tempObservable
                    )
            );
            tempObservable.clear();
    }

    public void createScrollPane(){

        scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollChat.setStyle("-fx-background-color: transparent");

        scrollPane.setTranslateX(340);
        scrollPane.setTranslateY(50);
        scrollPane.setPrefSize(470,400);
        // TODO: IF YOU NEED THE RED BORDER add " -fx-border-color: red"
        scrollPane.setStyle("-fx-background-color: transparent;"+"-fx-border-color: red" );
        scrollPane.setContent(scrollChat);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        UIpane.getChildren().add(scrollPane);
        UIstage.setOnShown(e ->
                scrollPane.lookup(".viewport").setStyle("-fx-background-color: transparent;"));
    }
    public int countCharAtLongestLine(String string){
        int count=0;
        String[] lines = string.split("\r\n|\r|\n");
        for (int i = 0; i < lines.length; i++) {
            if(lines[i].length()>count){
                count = lines[i].length();
            }
        }

        System.out.println("counted chars: "+count);
        return count;
    }
    public void createTable(){
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.setPrefWidth(480);
        table.setEditable(true);
        table.setStyle("-fx-cell-size: 50px;");

        ObservableList<String> cbValues = FXCollections.observableArrayList("1", "2", "3");
        //columns
        for (int i = 0; i < columnNames.size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    columnNames.get(i)
            );
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            // THIS ADDS THE OPTION OF COMBOBOXES IN A TABLE
            if(!columnNames.get(i).toUpperCase().equals("ACTION")) {
                column.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), comboData.get(i)));
            }else {
                column.setCellFactory(TextFieldTableCell.forTableColumn());
            }
            table.getColumns().add(column);
        }
        //row data


        //TODO: QUICK FIX FOR ROW : CUT THE WORDS
        tempObservable = new ArrayList<>();
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < ColNum; j++) {
                tempObservable.add(dataPerColumn.get(j).get(i));
//                System.out.println(dataPerColumn.get(j).get(i));
//                System.out.println(tempObservable.get(j));
            }
            table.getItems().add(
                    FXCollections.observableArrayList(
                            tempObservable
                    )
            );
            tempObservable.clear();
        }
        table.setFixedCellSize(60.0);
        scrollChat.getChildren().add(table);
        scrollPane.setContent(scrollChat);
    }
    public void slotData(String slotTable) throws SQLException {

        List<String> temp = new ArrayList<>();
        System.out.println("before loop");
        for (int i = 0; i < columnNames.size(); i++) {
            System.out.println("in loop");
            temp = sql.avaiableDataFromSlot(slotTable,columnNames.get(i));
            ObservableList<String> values = FXCollections.observableArrayList(temp);
            comboData.add(values);
        }

//        System.out.println("combo size: "+temp.size());
    }


    @Override
    public void design() {

        Text text = new Text(tableName);
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

        createButtons();
        setButtonActions();
        createScrollPane();
        createTable();

    }

}

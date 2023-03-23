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
import org.group1.response.database.SQLtoTxt;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkillDetails implements CustomStage {
    // TODO: YOU WILL HAVE TO DEAL WITH CHAT HISTORY
    private AnchorPane UIpane,scrollChat;
    private Stage UIstage,chatStage;
    private Scene UIscene;
    private Button back,help,addAction, slots;
    private ScrollPane scrollPane;
    private TableView<ObservableList<String>> table,table2;
    private List<String> tempObservable;
    private int N_ROWS, ColNum;
    private SQLGUIConnection sql = new SQLGUIConnection();
    private ArrayList<ObservableList<String>> comboData = new ArrayList<>();
    private ArrayList<ObservableList<String>> slotComboData = new ArrayList<>();

    private ArrayList<ObservableList<String>> comboDataSlots = new ArrayList<>();
    private Text text;
    FileService fs;

    List<String> columnNames,slotColumnNames;
    String tableName,tableName2;
    private String id;
    private ArrayList<ArrayList<String>> dataPerColumn = new ArrayList<>();
    private ArrayList<ArrayList<String>> dataPerColumnSlot = new ArrayList<>();
    private boolean isSlot= false;

    public SkillDetails(String tableName, int ColNum, int RowNum, String slotTable) throws SQLException {
        columnNames = sql.getColumnNames(tableName);
        id = tableName.replace("action_","");
        slotColumnNames = sql.getColumnNames("slot_"+id);
        //sql.setEmptyNull(tableName,columnNames);



        slotData(slotTable);
        typeData("slot_"+id);
        this.tableName=tableName;
        this.ColNum=ColNum;
        this.N_ROWS=RowNum;
        collectDataFromDatabase();
        collectDataFromDatabaseSlot();
        UIpane = new AnchorPane();
        scrollChat = new AnchorPane();
        UIscene = new Scene(UIpane,LoginScreen.screenWidth,LoginScreen.screenHeight);
        UIstage = new Stage();
        UIscene.setFill(Color.rgb(18,64,76));
        UIpane.setStyle("-fx-background-color: transparent");
        UIstage.setScene(UIscene);
        design();
    }


    /**
     * Constructor to make the slots table
     * @param tableName
     * @param ColNum
     * @param RowNum
     * @param slotTable
     * @param slots
     * @throws SQLException
     */
    public SkillDetails(String tableName, int ColNum, int RowNum, String slotTable, int slots)throws SQLException{

        columnNames = sql.getColumnNames(tableName);
        slotColumnNames = sql.getColumnNames("slot_"+id);

        //sql.setEmptyNull(tableName,columnNames);
        id = tableName.replace("slots_","");

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

    public void collectDataFromDatabaseSlot() throws SQLException {
        for (int i = 0; i < slotColumnNames.size(); i++) {
            ArrayList<String> query = sql.getAllColumnData(slotColumnNames.get(i),"slot_"+id);
            dataPerColumnSlot.add(query);
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

        //slots button
        slots = new Button();
        slots.setText("SLOTS");
        slots.setFont(Font.font("Impact", FontWeight.BOLD,20));
        slots.setStyle("-fx-background-color: rgba(159,182,189,1)");
        slots.setTextFill(Color.WHITE);
        slots.setCursor(Cursor.CLOSED_HAND);
        slots.setPrefSize(400,50);
        slots.setLayoutX(380);
        slots.setLayoutY(500);
        UIpane.getChildren().add(slots);

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
                try {
                    sql.setEmptyNull(tableName,columnNames);
                    SQLtoTxt.overWrite(tableName.replace("action_",""));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
                try {
                    addRow();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        //slotbutton
        slots.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isSlot = !isSlot;
                if(isSlot){
                    slots.setText("Action");
                    text.setText("slot_"+id);
                }else{
                    slots.setText("Slots");
                    text.setText("action_"+id);
                }
                table.setVisible(!isSlot);
                table2.setVisible(isSlot);
            }

            //TODO:
            //      0. we need to see on which action ID we are...
            //      1. generate the table from sql
            //      2. display in left pane

        });
    }
    public void addRow() throws SQLException {
        //TODO: QUICK FIX FOR ROW : CUT THE WORDS
        tempObservable = new ArrayList<>();
            for (int j = 0; j < ColNum; j++) {
                tempObservable.add("-");
            }
        System.out.println(Arrays.toString(tempObservable.toArray()));
            table.getItems().add(
                    FXCollections.observableArrayList(
                            tempObservable
                    )
            );

            tempObservable.clear();
            sql.addRow(tableName,columnNames);
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

        for (int i = 0; i <columnNames.size()  ;i++) {
            System.out.println("column: "+columnNames.get(i));
        }

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
            //handles editiing cells
            //Todo might be changed
            //updates the database on edit
            table.getColumns().forEach(col -> {
                col.setOnEditCommit(event -> {
                    int row = event.getTablePosition().getRow();
                    int colIndex = event.getTablePosition().getColumn();
                    Object newValue = event.getNewValue();
                    System.out.println(" i am edit row: "+ row + "col :" + colIndex);
                    System.out.println(newValue.toString());
                    // handle the edit
                    try {
                        sql.updateDatabase(row,newValue.toString(),columnNames.get(colIndex),tableName);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            });
        }
        //row data


        //TODO: QUICK FIX FOR ROW : CUT THE WORDS
        tempObservable = new ArrayList<>();
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < ColNum; j++) {
                tempObservable.add(dataPerColumn.get(j).get(i));
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

    public void createSlotTable(){

        table2 = new TableView<>();
        table2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table2.setPrefWidth(480);
        table2.setEditable(true);
        table2.setStyle("-fx-cell-size: 50px;");

        for (int i = 0; i <slotColumnNames.size()  ;i++) {
            System.out.println("column: "+slotColumnNames.get(i));
        }

        //columns
        for (int i = 0; i < slotColumnNames.size(); i++) {

            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    slotColumnNames.get(i)
            );
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            // THIS ADDS THE OPTION OF COMBOBOXES IN A TABLE
            if(slotColumnNames.get(i).toUpperCase().equals("SLOTTYPE")) {
                column.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), slotComboData.get(i)));
            }else {
                column.setCellFactory(TextFieldTableCell.forTableColumn());
            }
            table2.getColumns().add(column);

            //handles editing cells
            //TODO: change it to support same keys
            System.out.println("cols size: " + table2.getColumns().size());
            table2.getColumns().forEach(col -> {
                col.setOnEditCommit(event -> {
                    int row = event.getTablePosition().getRow();
                    int colIndex = event.getTablePosition().getColumn();
                    Object newValue = event.getNewValue();
                    System.out.println(" i am edit row: "+ row + "col :" + colIndex);
                    System.out.println(newValue.toString());
                    // handle the edit
                    try {
                        sql.updateDatabase(row,newValue.toString(),slotColumnNames.get(colIndex),"slot_"+id);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            });
            table2.setVisible(isSlot);
        }
        //row data


        //TODO: QUICK FIX FOR ROW : CUT THE WORDS
        tempObservable = new ArrayList<>();

        System.out.println("size: " + dataPerColumnSlot.size());
        for (int j = 0; j < dataPerColumnSlot.get(0).size(); j++) {
            for (int i = 0; i < 2; i++) {
                tempObservable.add(dataPerColumnSlot.get(i).get(j));
            }
            table2.getItems().add(
                    FXCollections.observableArrayList(
                            tempObservable
                    )
            );
            tempObservable.clear();
        }
        table2.setFixedCellSize(60.0);
        scrollChat.getChildren().add(table2);
        scrollPane.setContent(scrollChat);
    }

    public void slotData(String slotTable) throws SQLException {

        List<String> temp = new ArrayList<>();
        System.out.println("before loop");
        for (int i = 0; i < columnNames.size(); i++) {
            System.out.println("in loop");
            temp = sql.avaiableDataFromSlot(slotTable,columnNames.get(i));
            System.out.println(Arrays.toString(temp.toArray()));
            ObservableList<String> values = FXCollections.observableArrayList(temp);
            comboData.add(values);
        }
    }

    public void typeData(String slotTable) throws SQLException {
        List<String> temp = new ArrayList<>();
        System.out.println("in loop");
        temp = sql.getSlotType(slotTable);
        System.out.println(Arrays.toString(temp.toArray()));
        ObservableList<String> values = FXCollections.observableArrayList(temp);
        slotComboData.add(values);
    }

    @Override
    public void design() {

        text = new Text(tableName);
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
        createSlotTable();
    }

}

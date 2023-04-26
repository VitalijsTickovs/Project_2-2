package org.group1.GUI;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import org.group1.back_end.response.Response;
import org.group1.back_end.response.skills.SkillData;
import org.group1.back_end.response.skills.SkillFileService;
import org.group1.back_end.response.skills.dataframe.DataFrame;
import org.group1.back_end.response.skills.dataframe.DataFrameEditor;
import org.group1.back_end.response.skills.dataframe.Rows;
import org.group1.back_end.utilities.GeneralFileService;
import org.group1.database.SQLGUIConnection;
import org.group1.database.SQLtoTxt;

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
    List<List<String>> TwoColData = new ArrayList<>();
    private Button back,help,addAction, slots,delete,saveButton;
    private ScrollPane scrollPane;
    private TableView<ObservableList<String>> table,table2;
    private List<String> tempObservable;
    private int N_ROWS, ColNum,currentRow;
//    private SQLGUIConnection sql = new SQLGUIConnection();
    private ArrayList<ObservableList<String>> comboData = new ArrayList<>();
    private ArrayList<ObservableList<String>> slotComboData = new ArrayList<>();

    private ArrayList<ObservableList<String>> comboDataSlots = new ArrayList<>();
    private Text text;
    SkillFileService fs;

    List<String> columnNames,slotColumnNames;
    String tableName;
    private int id;
    private List<List<String>> dataPerColumn = new ArrayList<>();
    private List<List<String>> dataPerColumnSlot = new ArrayList<>();
    private boolean isSlot= false;
    private final Response response;
    private List<SkillData> dataFrames;

    public SkillDetails(int indexOfRule, Response responseGenerator) throws SQLException {
        id = indexOfRule;
        response = responseGenerator;
        this.dataFrames = responseGenerator.getSkillData();
        columnNames = dataFrames.get(indexOfRule).getColumnNames();
        slotColumnNames = dataFrames.get(indexOfRule).getSlotNames();
        this.ColNum=dataFrames.get(indexOfRule).getActions().getColumnNames().size();
        this.N_ROWS=dataFrames.get(indexOfRule).getActions().getData().size();

        slotData();
        typeData();

        collectDataFromDatabase();
        collectDataFromDatabaseSlot();

        UIpane = new AnchorPane();
        scrollChat = new AnchorPane();
        UIscene = new Scene(UIpane,LoginScreen.screenWidth,LoginScreen.screenHeight);
        UIstage = new Stage();
        UIscene.setFill(Color.rgb(18,64,76));
        UIpane.setStyle("-fx-background-color: transparent");
        UIstage.setScene(UIscene);

        //TODO: SWING UI
        dataFrames.get(indexOfRule).display();
        design();
        createTable();
    }

    public void collectDataFromDatabase() throws SQLException {
//        List<String> tempColName = new ArrayList<>();
//        tempColName = columnNames;
        for (int i = 0; i < dataFrames.get(id).getActions().getColumnNames().size(); i++) {
            dataPerColumn.add(dataFrames.get(id).getActions().getColumnData(i));
        }
//        System.out.println("Size of dataPerCol: " + dataPerColumn.size());
//        for (int i = 0; i < dataPerColumn.size(); i++) {
//            System.out.println("size of row data "+ dataPerColumn.get(i).get());
//        }
    }

    public void collectDataFromDatabaseSlot() throws SQLException {
//        List<String> tempColName = new ArrayList<>();
//        tempColName = columnNames;
        for (int i = 0; i < dataFrames.get(id).getSlots().getColumnNames().size(); i++) {
            dataPerColumnSlot.add(dataFrames.get(id).getSlots().getColumnData(i));
        }

    }

    public void setStage(Stage mainStage,Stage chatStage){
        this.chatStage=chatStage;
        mainStage.close();
        UIstage.show();
    }

    public void createButtons(){
        //save button
        saveButton = new Button();
        saveButton.setText("SAVE");
        saveButton.setFont(Font.font("Impact", FontWeight.BOLD,30));
        saveButton.setStyle("-fx-background-color: transparent");
        saveButton.setTextFill(Color.WHITE);
        saveButton.setLayoutX(20);
        saveButton.setLayoutY(210);
        saveButton.setCursor(Cursor.CLOSED_HAND);
        UIpane.getChildren().add(saveButton);
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

        //help button
        delete = new Button();
        delete.setText("DELETE");
        delete.setFont(Font.font("Impact", FontWeight.BOLD,30));
        delete.setStyle("-fx-background-color: transparent");
        delete.setTextFill(Color.WHITE);
        delete.setLayoutX(650);
        delete.setLayoutY(450);
        delete.setCursor(Cursor.CLOSED_HAND);
        UIpane.getChildren().add(delete);

    }

    public void setButtonActions(){
        //save button
        saveButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                saveButton.setTextFill(Color.rgb(42,97,117));
            }
        });
        saveButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                saveButton.setTextFill(Color.WHITE);
            }
        });
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               //TODO: SAVE LOGIC FOR THE TABLE
                    DataFrame slots = new DataFrame(slotColumnNames);
                    ObservableList<ObservableList<String>> slotRows =  table2.getItems();
                    //For each row
                    for(int row = 0; row < slotRows.size(); row++){
                        int columnIndex=0;
                        String columnType = slotRows.get(row).get(0);
                        String columnValue = slotRows.get(row).get(1);
                        //Finding the order/position of the slotType in columnNames array
                        for(int type=0; type<slotColumnNames.size(); type++){
                            String typeName = slotColumnNames.get(type);
                            if(columnType.equals(typeName)){
                                columnIndex = type;
                            }
                        }
                        //Inserting the values to first free slot
                        slots.insertToFirstFreeSlot(columnIndex, columnValue);
                    }
                    dataFrames.get(id).setSlots(slots);

                    DataFrame action = new DataFrame(columnNames);
                    ObservableList<ObservableList<String>> actionRows =  table.getItems();
                    //For each row
                    for(int row = 0; row < actionRows.size(); row++){
                        List<String> rowData = actionRows.get(row);
                        List<org.group1.back_end.response.skills.dataframe.Cell> rowDataCells = new ArrayList<>();
                        for(String data: rowData){
                            org.group1.back_end.response.skills.dataframe.Cell cell = new
                                    org.group1.back_end.response.skills.dataframe.Cell(data);
                            rowDataCells.add(cell);
                        }
                        Rows rowDataProcessed = new Rows(rowDataCells);
                        action.insert(rowDataProcessed);
                        //Finding the order/position of the slotType in columnNames array
                        //Inserting the values to first free slot
                    }
                    dataFrames.get(id).setActions(action);
            }

        });
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
                    GeneralFileService.overWrite(dataFrames.get(id));
                    response.reload();
                } catch (SQLException e) {
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }
                DisplaySkills displaySkills = new DisplaySkills(response);
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
                try {
                    addRow();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        delete.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                delete.setTextFill(Color.rgb(42,97,117));
            }
        });
        delete.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                delete.setTextFill(Color.WHITE);
            }
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isSlot) {
                    table2.getItems().remove(currentRow);
                }else table.getItems().remove(currentRow);

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
        });
    }

    /**
     * Adds blank ("-") rows to the javaFX table
     * @throws SQLException
     */
    public void addRow() throws SQLException {
        //TODO: QUICK FIX FOR ROW : CUT THE WORDS
        if(isSlot){     // slot

            tempObservable = new ArrayList<>();

            for (int j = 0; j < 2; j++) {
                tempObservable.add("-");
            }

            table2.getItems().add(
                    FXCollections.observableArrayList(
                            tempObservable
                    )
            );

            tempObservable.clear();
        } else {        // we are in action
            tempObservable = new ArrayList<>();
            for (int j = 0; j < columnNames.size(); j++) {
                tempObservable.add("-");
            }
            table.getItems().add(
                    FXCollections.observableArrayList(
                            tempObservable
                    )
            );

            tempObservable.clear();
        }




    }

    /**
     * Creates the scrollable pane for the table
     */
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


    public void createTable(){
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.setPrefWidth(480);
        table.setEditable(true);
        table.setStyle("-fx-cell-size: 50px;");

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
            //Todo UPDATE THE DATAFRAME HERE
            //updates the database on edit
            table.getColumns().forEach(col -> {
                col.setOnEditCommit(event -> {
                    int row = event.getTablePosition().getRow();
                    int colIndex = event.getTablePosition().getColumn();
                    String newValue =(String) event.getNewValue();
                    // handle the edit
                    table.getItems().get(row).set(colIndex, newValue);
                });
            });
        }
        //row data


        //TODO: QUICK FIX FOR ROW : CUT THE WORDS
        tempObservable = new ArrayList<>();
        for (int i = 0; i < N_ROWS; i++) {
            //System.out.println("in 1");
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

        //selection listener
        table.getFocusModel().focusedCellProperty().addListener(
                new ChangeListener<TablePosition>() {
                    @Override
                    public void changed(ObservableValue<? extends TablePosition> observable,
                                        TablePosition oldPos, TablePosition pos) {
                        currentRow = pos.getRow();

                    }
                });

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

        ArrayList<String> names = new ArrayList<>();
        names.add("SlotType");
        names.add("SlotValue");
        //columns
        for (int i = 0; i < names.size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    names.get(i)
            );
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            // THIS ADDS THE OPTION OF COMBOBOXES IN A TABLE for SlotType( i ==0)
            if(i==0) {
                column.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), slotComboData.get(i)));
            }else {
                column.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
            }
            table2.getColumns().add(column);
            table2.getColumns().forEach(col -> {
                col.setOnEditCommit(event -> {
                    int row = event.getTablePosition().getRow();
                    int colIndex = event.getTablePosition().getColumn();
                    String newValue = (String) event.getNewValue();
                    table2.getItems().get(row).set(colIndex,newValue);
                });
            });
            //handles editing cells
            table2.setVisible(isSlot);
        }
        //row data

        tempObservable = new ArrayList<>();
        arrangeSlotTypeSlotValue();
        System.out.println("size two col data inner array: " + TwoColData.get(0).size());
        for (int j = 0; j < TwoColData.get(0).size(); j++) {
            ObservableList<String> rowObservable = FXCollections.observableArrayList();
            for (int i = 0; i < 2; i++) {
                rowObservable.add(TwoColData.get(i).get(j));
            }
            table2.getItems().add(rowObservable);
        }

        //selection listener
        table2.getFocusModel().focusedCellProperty().addListener(
                new ChangeListener<TablePosition>() {
                    @Override
                    public void changed(ObservableValue<? extends TablePosition> observable,
                                        TablePosition oldPos, TablePosition pos) {
                        currentRow = pos.getRow();
                        System.out.println(table2.getItems().get(currentRow));
                    }
                });

        table2.setFixedCellSize(60.0);
        scrollChat.getChildren().add(table2);
        scrollPane.setContent(scrollChat);
    }

    public void arrangeSlotTypeSlotValue(){
        List<String> tempArray = new ArrayList<>();
        List<String> tempArray2 = new ArrayList<>();

        // We are adding to SlotValue (Monday,Friday,... etc.)
        for (int j=0; j< dataFrames.get(id).getSlots().getColumnNames().size(); j++) {
            for (int i = 0; i < dataFrames.get(id).getSlots().getData().size(); i++) {
                if(!dataFrames.get(id).getSlots().getData().get(i).get(j).toString().equals(" ")) {
                    tempArray2.add(dataFrames.get(id).getSlots().getData().get(i).get(j).toString());
                }
            }
        }

        // i = row size, j = col size
        // We are adding to SlotType (DAY,DAY,DAY.. etc.)
        for (int j=0; j< dataFrames.get(id).getSlots().getColumnNames().size(); j++) {
            for (int i = 0; i < dataFrames.get(id).getSlots().getData().size(); i++) {
                if(!dataFrames.get(id).getSlots().getData().get(i).get(j).toString().equals(" ")) {
                    tempArray.add(dataFrames.get(id).getSlots().getColumnNames().get(j));
                }
            }
        }

        TwoColData.add(tempArray);
        TwoColData.add(tempArray2);
        System.out.println("TwoCol Size " +TwoColData.size());

    }
    public void slotData() throws SQLException {

        List<String> temp = new ArrayList<>();

        for (int i = 0; i < dataFrames.get(id).getSlots().getColumnNames().size(); i++) {
            temp = dataFrames.get(id).getSlots().getDistinctValues(i);
            ObservableList<String> values = FXCollections.observableArrayList(temp);
            comboData.add(values);
        }
//        for (int i = 0; i < comboData.size() ; i++) {
//            for (int j = 0; j < comboData.get(i).size(); j++) {
//                System.out.println("distinct: "+ comboData.get(i).get(j));
//            }
//            System.out.println(" ");
//        }
    }

    public void typeData() throws SQLException {
        List<String> temp = new ArrayList<>();
//        temp = sql.getSlotType(slotTable);
        temp=dataFrames.get(id).getSlots().getColumnNames();
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
        //createTable();
        createSlotTable();
    }

}

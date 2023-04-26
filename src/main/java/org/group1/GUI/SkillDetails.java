package org.group1.GUI;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
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
import org.group1.back_end.response.skills.dataframe.Cell;
import org.group1.back_end.response.skills.dataframe.DataFrame;
import org.group1.back_end.response.skills.dataframe.DataFrameEditor;
import org.group1.back_end.response.skills.dataframe.Rows;
import org.group1.back_end.utilities.GeneralFileService;
import org.group1.database.SQLGUIConnection;
import org.group1.database.SQLtoTxt;
import org.group1.database.Slots;

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
    private Button back,help,addAction, slots,delete,saveButton;
    private ScrollPane scrollPane;
    private TableView<ObservableList<String>> table;
    private TableView<Slot> table2;
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
            dataPerColumnSlot.add(dataFrames.get(id).getSlots().getDistinctValues(i));
        }

        System.out.println("Size of dataPerCol: " + dataPerColumnSlot.size());

    }

    public void setStage(Stage mainStage,Stage chatStage){
        this.chatStage=chatStage;
        mainStage.close();
        UIstage.show();
    }

    public void createButtons(){
        //save button
        saveButton = new Button();
        saveButton.setText("Save");
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
                try {
                    System.out.println("Id:"+ id);
                    System.out.println("DataFrame" + dataFrames.get(id));

                    GeneralFileService.overWrite(dataFrames.get(id));
                    response.reload();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
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

            for (int j = 0; j < ColNum; j++) {
                tempObservable.add("-");
            }

            table2.getItems().add(
                    new Slot()
            );

            tempObservable.clear();
            //sql.addRow("slot_"+id,slotColumnNames);
        } else {        // we are in action
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
            //sql.addRow(tableName,columnNames);
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
            if(!columnNames.get(i).equalsIgnoreCase("action")) {
                column.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), comboData.get(0)));
            }else {
                column.setCellFactory(TextFieldTableCell.forTableColumn());
            }
            table.getColumns().add(column);

            //updates the database on edit
            table.getColumns().forEach(col -> {
                col.setOnEditCommit(event -> {
                    int row = event.getTablePosition().getRow();
                    int colIndex = event.getTablePosition().getColumn();
                    Object newValue = event.getNewValue();
                    DataFrame dataFrame = dataFrames.get(id).getActions();

                    //Update the row edited
                    Rows rowData = dataFrame.get(row);
                    rowData.get(colIndex).setValue(newValue);
                });
            });
        }
        //row data


        //TODO: QUICK FIX FOR ROW : CUT THE WORDS
        tempObservable = new ArrayList<>();
        for (int i = 0; i < N_ROWS; i++) {
            //System.out.println("in 1");
            for (int j = 0; j < ColNum; j++) {
//                System.out.println("colNum: "+ColNum);
//                System.out.println("rowNum: "+N_ROWS);
//                System.out.println("i "+i+" j "+j);
                tempObservable.add(dataPerColumn.get(j).get(i));
            }
            table.getItems().add(
                    FXCollections.observableArrayList(
                            tempObservable
                    )
            );
            for (int j = 0; j < tempObservable.size(); j++) {
//                System.out.println("tempObs: "+tempObservable.get(j));
            }
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

        String[] columnNames = {"type", "value"};
        //columns
        for (int i = 0; i < 2; i++) {

            TableColumn<Slot, String> column = new TableColumn<>(
                    columnNames[i]
            );
            column.setCellValueFactory(new PropertyValueFactory<>(columnNames[i]));
            // THIS ADDS THE OPTION OF COMBOBOXES IN A TABLE
            if(i==0) column.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), slotComboData.get(0)));
            else column.setCellFactory(TextFieldTableCell.forTableColumn());
            table2.getColumns().add(column);

            //handles editing cells
            //TODO: change it to support same keys
            table2.setVisible(isSlot);
        }
        table2.getColumns().forEach(col -> {
            col.setOnEditCommit(event -> {
                int row = event.getTablePosition().getRow();
                int colIndex = event.getTablePosition().getColumn();
                Object newValue = event.getNewValue();
                DataFrame dataFrame = dataFrames.get(id).getActions();

                //Update the row edited
                Rows rowData = dataFrame.get(row);
                rowData.get(colIndex).setValue(newValue);
            });
        });
        //row data

        //TODO: QUICK FIX FOR ROW : CUT THE WORDS
        tempObservable = new ArrayList<>();
        ObservableList<Slot> data = FXCollections.observableArrayList();
        for(int type = 0; type< dataPerColumnSlot.size(); type++) {
            List<String> columnData = dataPerColumnSlot.get(type);
            for (int j = 0; j < columnData.size(); j++) {
                // Inserting the types based in which array of the data we are
                // E.g, [Maastricht, Amsterdam] would result in filling 2 rows of <CITY>
                // for first column, since its the type
                data.add(new Slot(slotColumnNames.get(type), columnData.get(j)));
            }
        }
        table2.setItems(data);

        //selection listener
        table2.getFocusModel().focusedCellProperty().addListener(
                new ChangeListener<TablePosition>() {
                    @Override
                    public void changed(ObservableValue<? extends TablePosition> observable,
                                        TablePosition oldPos, TablePosition pos) {
                        currentRow = pos.getRow();

                    }
                });

        table2.setFixedCellSize(60.0);
        scrollChat.getChildren().add(table2);
        scrollPane.setContent(scrollChat);
    }

    public static class Slot {
        private String type;
        private String value;

        public Slot(){}

        public Slot(String type, String value) {
            this.type = type;
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    //TODO:
    public void slotData() throws SQLException {

        List<String> temp = new ArrayList<>();

        for (int i = 0; i < dataFrames.get(id).getSlots().getColumnNames().size(); i++) {
            temp = dataFrames.get(id).getSlots().getDistinctValues(i);
            ObservableList<String> values = FXCollections.observableArrayList(temp);
            comboData.add(values);
        }
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
//        createTable();
        createSlotTable();
    }

}

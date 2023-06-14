package org.group1.GUI.stage.scenes;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.converter.DefaultStringConverter;
import org.group1.GUI.stage.StageManager;
import org.group1.GUI.stage.scenes.utils.ButtonFactory;
import org.group1.back_end.response.skills.ContextFreeGrammar;
import org.group1.back_end.response.skills.SkillData;
import org.group1.back_end.response.skills.dataframe.Cell;
import org.group1.back_end.response.skills.dataframe.DataFrame;
import org.group1.back_end.response.skills.dataframe.Rows;
import org.group1.back_end.utilities.GeneralFileService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SceneCFGEditor extends SceneManager implements ICustomScene {

    private String tableName;
    private final int id;
    private Text text = new Text();
    private Button back,help,addAction, slots,delete,saveButton;
    private ScrollPane scrollPane;
    private TableView<ObservableList<String>> table,table2;
//    private final ArrayList<ObservableList<String>> comboData, slotComboData;
    private final List<String> tableNames;
        //,slotColumnNames,column names;
//    private final List<List<String>> dataPerColumn;
//    private final int ColNum, N_ROWS;
    private int currentRow;
    private boolean isSlot= false;
    private ContextFreeGrammar currentCFG;

    public SceneCFGEditor(int indexOfCFG){
        System.out.println("SCENE CFG "+ indexOfCFG);
        makeNewPane();
        id = indexOfCFG;

        //dataFrames = StageManager.getConnection().getSkillData();
        currentCFG = StageManager.getConnection().getCurrentCFG(id);
        // Generating table names
        tableNames = new ArrayList<>();
        tableNames();
        //columnNames = dataFrames.get(indexOfCFG).getColumnNames();
        List<String> actionToSlots = StageManager.getConnection().getActionToSlots(currentCFG);
        List<List<String[]>> getSlotsUsed = StageManager.getConnection().getSlotsUsed(currentCFG);
        for (int i = 0; i < actionToSlots.size(); i++) {
            System.out.println("action to slot: "+actionToSlots.get(i));
        }
        for (int i = 0; i < getSlotsUsed.size(); i++) {
            for (int j = 0; j < getSlotsUsed.get(i).size(); j++) {
                System.out.println("get slot used: "+ Arrays.toString(getSlotsUsed.get(i).get(j)));
            }
            System.out.println(" ");
        }
        //slotColumnNames = dataFrames.get(indexOfCFG).getSlotNames();
        //ColNum=dataFrames.get(indexOfCFG).getActions().getColumnNames().size();
        //N_ROWS=dataFrames.get(indexOfCFG).getActions().getData().size();
        //dataPerColumn = new ArrayList<>();
        //comboData = new ArrayList<>();
        //slotComboData = new ArrayList<>();

        //setPlaceholderComboData();
        //setSlotsTypeComboData();
        //collectDataFromDatabase();
        //design();
    }

    public void tableNames(){
        boolean flag=false;
        for (int i = 0; i < currentCFG.beforeKleeneStar.size(); i++) {
            String temp = currentCFG.beforeKleeneStar.get(i);
            if (tableNames.size()==0){
                tableNames.add(temp);
            }
            for (int j = 0; j < tableNames.size(); j++) {
                System.out.println("ColumnName: " + tableNames.get(j));
                System.out.println("Temp: "+ temp);
                if (tableNames.get(j).equals(temp)){
                    flag=true;
                }else flag=false;
                System.out.println(flag);
                System.out.println("");
            }
            if (flag==false){
                tableNames.add(temp);
            }
        }
    }

//    public void createButtons(){
//        //save button
//        saveButton = ButtonFactory.createButton("SAVE", 20,210);
//        UIPane.getChildren().add(saveButton);
//        //back button
//        back = ButtonFactory.createButton("BACK", 20, 170);
//        UIPane.getChildren().add(back);
//        //help button
//        help = ButtonFactory.createButton("HELP", 20, 130);
//        UIPane.getChildren().add(help);
//        //addAction button
//        addAction = ButtonFactory.createButton("ADD ROW", 380, 450);
//        UIPane.getChildren().add(addAction);
//        //slots button
//        slots = ButtonFactory.createButton("SLOTS", 380, 500);
//        slots.setPrefSize(400,50);
//        UIPane.getChildren().add(slots);
//        //help button
//        delete = ButtonFactory.createButton("DELETE", 650, 450);
//        UIPane.getChildren().add(delete);
//
//    }
//
//    public void setButtonActions(){
//        //save button
//        ButtonFactory.setDefaultActions(saveButton);
//        saveButton.setOnAction(e -> {
//            DataFrame slots = new DataFrame(slotColumnNames);
//            ObservableList<ObservableList<String>> slotRows =  table2.getItems();
//            //For each row
//            for (ObservableList<String> slotRow : slotRows) {
//                int columnIndex = 0;
//                String columnType = slotRow.get(0);
//                String columnValue = slotRow.get(1);
//                // Check for user to correctly fill the cells
//                if(!columnType.equals("-") && !columnValue.equals("-")) {
//                    //Finding the order/position of the slotType in columnNames array
//                    for (int type = 0; type < slotColumnNames.size(); type++) {
//                        String typeName = slotColumnNames.get(type);
//                        if (columnType.equals(typeName)) {
//                            columnIndex = type;
//                        }
//                    }
//                    //Inserting the values to first free slot
//                    slots.insertToFirstFreeSlot(columnIndex, columnValue);
//                }
//            }
//            dataFrames.get(id).setSlots(slots);
//
//            DataFrame action = new DataFrame(columnNames);
//            ObservableList<ObservableList<String>> actionRows =  table.getItems();
//            //For each row
//            for (List<String> rowData : actionRows) {
//                List<Cell> rowDataCells = new ArrayList<>();
//                for (String data : rowData) {
//                    Cell cell;
//                    if(data.equals("-")) cell = new Cell("");
//                    else cell = new Cell(data);
//                    rowDataCells.add(cell);
//                }
//                Rows rowDataProcessed = new Rows(rowDataCells);
//                action.insert(rowDataProcessed);
//            }
//            dataFrames.get(id).setActions(action);
//            GeneralFileService.overWrite(dataFrames.get(id));
//        });
//        //back button
//        ButtonFactory.setDefaultActions(back);
//        back.setOnAction(e -> {
//            SceneSkillList sceneSkillList = new SceneSkillList();
//            StageManager.setScene(sceneSkillList);
//        });
//
//        //help button
//        ButtonFactory.setDefaultActions(help);
//        help.setOnAction(e -> {
//            //TODO : SET HELP WINDOW
//        });
//
//        //addColumn
//        ButtonFactory.setDefaultActions(addAction);
//        addAction.setOnAction(e -> {
//            addRow();
//        });
//
//        ButtonFactory.setDefaultActions(delete);
//        delete.setOnAction(e -> {
//            if (isSlot) {
//                table2.getItems().remove(currentRow);
//            }else table.getItems().remove(currentRow);
//        });
//
//        //slotbutton
//        slots.setOnAction(e -> {
//            isSlot = !isSlot;
//            if(isSlot){
//                slots.setText("Action");
//                text.setText("slot_"+id);
//            }else{
//                slots.setText("Slots");
//                text.setText("action_"+id);
//            }
//            table.setVisible(!isSlot);
//            table2.setVisible(isSlot);
//        });
//    }
//
//    /**
//     * Adds blank ("-") rows to the javaFX table
//     */
//    public void addRow() {
//        TableView<ObservableList<String>> tableView;
//        if (isSlot) {
//            tableView = table2;
//
//        }else {
//            tableView = table;
//        }
//        List<String> tempObservable = new ArrayList<>();
//        System.out.println("tableView size:" + tableView.getItems().get(0).size());
//        for (int j = 0; j < tableView.getItems().get(0).size(); j++) {
//            tempObservable.add("-");
//        }
//        tableView.getItems().add(FXCollections.observableArrayList(tempObservable));
//        tempObservable.clear();
//    }
//
//    /**
//     * Creates the scrollable pane for the table
//     */
//    public void createScrollPane(){
//        scrollPane = new ScrollPane();
//        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//
//        scrollChat.setStyle("-fx-background-color: transparent");
//
//        scrollPane.setTranslateX(340);
//        scrollPane.setTranslateY(50);
//        scrollPane.setPrefSize(470,400);
//        // TODO: IF YOU NEED THE RED BORDER add " -fx-border-color: red"
//        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; " +"-fx-border-color: red;");
//        scrollPane.setContent(scrollChat);
//        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        UIPane.getChildren().add(scrollPane);
//    }
//
//
//    public void createTable(){
//        table = new TableView<>();
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
//        table.setPrefWidth(480);
//        table.setEditable(true);
//        table.setStyle("-fx-cell-size: 50px;");
//
//        // Constructing all the columns in Action
//        for (int i = 0; i < columnNames.size(); i++) {
//
//            final int finalIdx = i;
//            // Making the columns with the placeholder name
//            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
//                    columnNames.get(i)
//            );
//            column.setCellValueFactory(param ->
//                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
//            );
//            // THIS ADDS THE OPTION OF COMBOBOXES IN A TABLE
//            if(!columnNames.get(i).toUpperCase().equals("ACTION")) {
//                column.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), comboData.get(i)));
//            }else {
//                column.setCellFactory(TextFieldTableCell.forTableColumn());
//            }
//            table.getColumns().add(column);
//            //handles editing cells
//            table.getColumns().forEach(col -> {
//                col.setOnEditCommit(event -> {
//                    int row = event.getTablePosition().getRow();
//                    int colIndex = event.getTablePosition().getColumn();
//                    String newValue =(String) event.getNewValue();
//                    // handle the edit
//                    table.getItems().get(row).set(colIndex, newValue);
//                });
//            });
//        }
//
//        //selection listener
//        table.getFocusModel().focusedCellProperty().addListener(
//                new ChangeListener<>() {
//                    @Override
//                    public void changed(ObservableValue<? extends TablePosition> observable,
//                                        TablePosition oldPos, TablePosition pos) {
//                        currentRow = pos.getRow();
//
//                    }
//                });
//
//        table.setFixedCellSize(60.0);
//        scrollChat.getChildren().add(table);
//        scrollPane.setContent(scrollChat);
//    }
//
//    private void populateTable() {
//        List<String> tempObservable = new ArrayList<>();
//        for (int i = 0; i < N_ROWS; i++) {
//            for (int j = 0; j < ColNum; j++) {
//                tempObservable.add(dataPerColumn.get(j).get(i));
//            }
//            table.getItems().add(FXCollections.observableArrayList(tempObservable));
//            tempObservable.clear();
//        }
//    }
//
//    public void createSlotTable(){
//        table2 = new TableView<>();
//        table2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        table2.setPrefWidth(480);
//        table2.setEditable(true);
//        table2.setStyle("-fx-cell-size: 50px;");
//
//        String[] names = new String[]{"SlotType","SlotValue"};
//        //columns
//        for (int i = 0; i < names.length; i++) {
//            final int finalIdx = i;
//            TableColumn<ObservableList<String>, String> column = new TableColumn<>(names[i]);
//            column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx)));
//            // THIS ADDS THE OPTION OF COMBOBOXES IN A TABLE for SlotType, i.e, i == 0
//            if(i==0) {
//                column.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), slotComboData.get(i)));
//            }else {
//                column.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
//            }
//            table2.getColumns().add(column);
//            table2.getColumns().forEach(col -> {
//                col.setOnEditCommit(event -> {
//                    int row = event.getTablePosition().getRow();
//                    int colIndex = event.getTablePosition().getColumn();
//                    String newValue = (String) event.getNewValue();
//                    table2.getItems().get(row).set(colIndex,newValue);
//                });
//            });
//            //handles editing cells
//            table2.setVisible(isSlot);
//        }
//        //row data
//
//        List<List<String>> twoColData = arrangeSlotTypeSlotValue();
//        for (int j = 0; j < twoColData.get(0).size(); j++) {
//            ObservableList<String> rowObservable = FXCollections.observableArrayList();
//            for (int i = 0; i < 2; i++) {
//                rowObservable.add(twoColData.get(i).get(j));
//            }
//            table2.getItems().add(rowObservable);
//        }
//
//        //selection listener
//        table2.getFocusModel().focusedCellProperty().addListener(
//                new ChangeListener<TablePosition>() {
//                    @Override
//                    public void changed(ObservableValue<? extends TablePosition> observable,
//                                        TablePosition oldPos, TablePosition pos) {
//                        currentRow = pos.getRow();
//                    }
//                });
//
//        table2.setFixedCellSize(60.0);
//        scrollChat.getChildren().add(table2);
//        scrollPane.setContent(scrollChat);
//    }
//
//    public List<List<String>> arrangeSlotTypeSlotValue(){
//        List<List<String>> colData = new ArrayList<>();
//        List<String> slotTypes = new ArrayList<>();
//        List<String> slotValues = new ArrayList<>();
//
//        for (int j=0; j< slotColumnNames.size(); j++) {
//            List<Rows> dataFrame = dataFrames.get(id).getSlots().getData();
//            for (int i = 0; i < dataFrame.size(); i++) {
//                if(!dataFrame.get(i).get(j).toString().equals(" ")) {
//                    // Adding to SlotType (DAY,DAY,DAY.. etc.)
//                    slotTypes.add(dataFrames.get(id).getSlots().getColumnNames().get(j));
//                    // Adding to SlotValue (Monday,Friday,... etc.)
//                    slotValues.add(dataFrames.get(id).getSlots().getData().get(i).get(j).toString());
//                }
//            }
//        }
//
//        colData.add(slotTypes);
//        colData.add(slotValues);
//        return colData;
//    }
//    public void setPlaceholderComboData() {
//
//        List<String> temp;
//
//        for (int i = 0; i < slotColumnNames.size(); i++) {
//            temp = dataFrames.get(id).getSlots().getDistinctValues(i);
//            temp.add(""); // add option to add empty space for a slot
//            ObservableList<String> values = FXCollections.observableArrayList(temp);
//            comboData.add(values);
//        }
//    }
//
//    public void setSlotsTypeComboData() {
//        ObservableList<String> values = FXCollections.observableArrayList(slotColumnNames);
//        slotComboData.add(values);
//    }
//
//    @Override
    public void design() {
//        createText(tableName, 540, 40);
//
//        createSideMenu();
//
//        createButtons();
//        setButtonActions();
//        createScrollPane();
//        createTable();
//        populateTable();
//        createSlotTable();
    }

}

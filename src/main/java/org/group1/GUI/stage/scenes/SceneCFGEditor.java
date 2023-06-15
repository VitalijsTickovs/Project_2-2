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

import java.util.*;

public class SceneCFGEditor extends SceneManager implements ICustomScene {

    //private String tableName;
    private final int id;
    private Text text = new Text();
    private Button back, help, addAction, slots, delete, saveButton;
    private ScrollPane scrollPane;
    //    private final ArrayList<ObservableList<String>> comboData, slotComboData;
    private final List<String> tableNames;
    private Map<String, List<String>> columnNamesUndevided;
    //names;
//    private final List<List<String>> dataPerColumn;
//    private final int ColNum, N_ROWS;
    private int currentRow;
    private boolean isSlot = false;
    private ContextFreeGrammar currentCFG;
    private Map<String, String[]> slotValues;
    private Map<String, List<TableView<ObservableList<String>>>> tableCollection;
    private List<List<String[]>> getSlotsUsed;
    private List<String> actionToSlots;

    public SceneCFGEditor(int indexOfCFG) {
        makeNewPane();
        id = indexOfCFG;
        currentCFG = StageManager.getConnection().getCurrentCFG(id);
        // Generating table names
        tableNames = new ArrayList<>();
        tableNames();

        //Actions
        actionToSlots = StageManager.getConnection().getActionToSlots(currentCFG);

        //Data Row
        getSlotsUsed = StageManager.getConnection().getSlotsUsed(currentCFG);

        //Column Names for each table - table name [column names]
        columnNamesUndevided = currentCFG.getColumnNamesTable();
        for (int i = 0; i < actionToSlots.size(); i++) {
            System.out.println("action to slot: " + actionToSlots.get(i));
        }
        for (int i = 0; i < getSlotsUsed.size(); i++) {
            for (int j = 0; j < getSlotsUsed.get(i).size(); j++) {
                System.out.println("get slot used: " + Arrays.toString(getSlotsUsed.get(i).get(j)));
            }
            System.out.println(" ");
        }
        slotValues = currentCFG.getSlotValues();
        for (String keys : slotValues.keySet()) {
            System.out.println(keys + " Corresponding values: " + Arrays.toString(slotValues.get(keys)));
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
        design();
    }

    public void tableNames() {
        boolean flag = false;
        for (int i = 0; i < currentCFG.beforeKleeneStar.size(); i++) {
            String temp = currentCFG.beforeKleeneStar.get(i);
            if (tableNames.size() == 0) {
                tableNames.add(temp);
            }
            for (int j = 0; j < tableNames.size(); j++) {
                if (tableNames.get(j).equals(temp)) {
                    flag = true;
                } else flag = false;
            }
            if (flag == false) {
                tableNames.add(temp);
            }
        }
    }

        public void createButtons(){
        //save button
        saveButton = ButtonFactory.createButton("SAVE", 20,210);
        UIPane.getChildren().add(saveButton);
        //back button
        back = ButtonFactory.createButton("BACK", 20, 170);
        UIPane.getChildren().add(back);
        //help button
        help = ButtonFactory.createButton("HELP", 20, 130);
        UIPane.getChildren().add(help);
        //addAction button
        addAction = ButtonFactory.createButton("ADD ROW", 380, 450);
        UIPane.getChildren().add(addAction);
        //slots button
        slots = ButtonFactory.createButton("SLOTS", 380, 500);
        slots.setPrefSize(400,50);
        UIPane.getChildren().add(slots);
        //help button
        delete = ButtonFactory.createButton("DELETE", 650, 450);
        UIPane.getChildren().add(delete);

    }

    public void setButtonActions(){
        //save button TODO: ADD SAVING LOGIC
        //If you need the current table (Schedule or Location for example) you can use the tablecollection hashmap
        //its keys are the table names (you can get current table name using global currenttable name string)
        //position 0 corresponds to its action table and 1 to its slot table
        //tableCollection.get(currentTable).get(1); - slot table
        //tableCollection.get(currentTable).get(0); - action table
        ButtonFactory.setDefaultActions(saveButton);
        saveButton.setOnAction(e -> {

        });
        //back button
        ButtonFactory.setDefaultActions(back);
        back.setOnAction(e -> {
            SceneSkillList sceneSkillList = new SceneSkillList();
            StageManager.setScene(sceneSkillList);
        });

        //help button
        ButtonFactory.setDefaultActions(help);
        help.setOnAction(e -> {
            //TODO : SET HELP WINDOW
        });

        //addColumn
        ButtonFactory.setDefaultActions(addAction);
        addAction.setOnAction(e -> {
            addRow();
        });

        ButtonFactory.setDefaultActions(delete);
        delete.setOnAction(e -> {
            if (isSlot) {
                tableCollection.get(currentTable).get(1).getItems().remove(currentRow);
            }else tableCollection.get(currentTable).get(0).getItems().remove(currentRow);
        });

        //slotbutton
        slots.setOnAction(e -> {
            System.out.println("Click");
            System.out.println("current table "+currentTable);
            isSlot = !isSlot;
            if(isSlot){
                slots.setText("Action");
                text.setText("slot_"+id);

                scrollChat.getChildren().clear();
                tableCollection.get(currentTable).get(1).setVisible(isSlot);
                scrollChat.getChildren().add(tableCollection.get(currentTable).get(1));
                System.out.println(isSlot);
            }else{
                slots.setText("Slots");
                text.setText("action_"+id);

                scrollChat.getChildren().clear();
                tableCollection.get(currentTable).get(1).setVisible(!isSlot);
                scrollChat.getChildren().add(tableCollection.get(currentTable).get(0));
                System.out.println(isSlot);
            }



        });
    }
//
//    /**
//     * Adds blank ("-") rows to the javaFX table
//     */
    public void addRow() {
        TableView<ObservableList<String>> tableView;
        if (isSlot) {
            tableView = tableCollection.get(currentTable).get(1);
        }else {
            tableView = tableCollection.get(currentTable).get(0);
        }
        List<String> tempObservable = new ArrayList<>();
        System.out.println("tableView size:" + tableView.getItems().get(0).size());
        for (int j = 0; j < tableView.getItems().get(0).size(); j++) {
            tempObservable.add("-");
        }
        tableView.getItems().add(FXCollections.observableArrayList(tempObservable));
        tempObservable.clear();
    }
//
//    /**
//     * Creates the scrollable pane for the table
//     */
    public void createScrollPane(){
        scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollChat.setStyle("-fx-background-color: transparent");

        scrollPane.setTranslateX(340);
        scrollPane.setTranslateY(50);
        scrollPane.setPrefSize(470,400);
        // TODO: IF YOU NEED THE RED BORDER add " -fx-border-color: red"
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; " +"-fx-border-color: red;");
        scrollPane.setContent(scrollChat);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        UIPane.getChildren().add(scrollPane);
    }
//
//
    public TableView<ObservableList<String>> createTable(String currentTableName) {
        TableView<ObservableList<String>> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.setPrefWidth(480);
        table.setEditable(true);
        table.setStyle("-fx-cell-size: 50px;");

        //Store column Names
        ArrayList<String> tempColumnNames = new ArrayList<>();
        for (String key : columnNamesUndevided.keySet()) {
            if (currentTableName.equals(key)) {
                //Loop through content of retrieved list
                for (int i = 0; i < columnNamesUndevided.get(key).size(); i++) {
                    tempColumnNames.add(columnNamesUndevided.get(key).get(i));
                }
            }
        }
        ArrayList<ObservableList<String>> comboData=setPlaceholderComboData(tempColumnNames);
        tempColumnNames.add("ACTION");
        // Constructing all the columns in Action
        for (int i = 0; i < tempColumnNames.size(); i++) {

            final int finalIdx = i;
            // Making the columns with the placeholder name
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    tempColumnNames.get(i)
            );

            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            // THIS ADDS THE OPTION OF COMBOBOXES IN A TABLE
            if (!tempColumnNames.get(i).toUpperCase().equals("ACTION")) {
                column.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), comboData.get(i)));
            } else {
                column.setCellFactory(TextFieldTableCell.forTableColumn());
            }
            table.getColumns().add(column);
            //handles editing cells
            table.getColumns().forEach(col -> {
                col.setOnEditCommit(event -> {
                    int row = event.getTablePosition().getRow();
                    int colIndex = event.getTablePosition().getColumn();
                    String newValue = (String) event.getNewValue();
                    // handle the edit
                    table.getItems().get(row).set(colIndex, newValue);
                });
            });
        }

        //selection listener
        table.getFocusModel().focusedCellProperty().addListener(
                new ChangeListener<>() {
                    @Override
                    public void changed(ObservableValue<? extends TablePosition> observable,
                                        TablePosition oldPos, TablePosition pos) {
                        currentRow = pos.getRow();

                    }
                });

        table.setFixedCellSize(60.0);
        System.out.println("before Populate");
        populateTable(currentTableName,tempColumnNames,table);
        System.out.println("after Populate");
        return table;
    }

    private void populateTable(String thisTableName,ArrayList<String> slotNames,TableView<ObservableList<String>> table) {
        List<String> tempObservable = new ArrayList<>();
        List<List<String>>tempalldata = getDataPerColumn(slotNames, 1,thisTableName);
//
        for (int i = 0; i < getRowNum(thisTableName); i++) {
            for (int j = 0; j < slotNames.size(); j++) {
                List<String> columnData = tempalldata.get(i);
                tempObservable.addAll(columnData);
                //System.out.println("temp obs content: "+tempObservable.get(i).toString());
                //tempObservable = (getDataPerColumn(slotNames,j));
            }
            //System.out.println("temp observable size: "+tempObservable.size());
            table.getItems().add(FXCollections.observableArrayList(tempObservable));
            tempObservable.clear();
        }

    }

    public int getRowNum(String tableName){
        int count=0;
        for (int i = 0; i < currentCFG.beforeKleeneStar.size(); i++) {
            if (currentCFG.beforeKleeneStar.get(i).equals(tableName)){
                count++;
            }
        }
        return count;
    }


    public TableView<ObservableList<String>> createSlotTable(String currentTableName) {
        TableView<ObservableList<String>> table2 = new TableView<>();
        table2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table2.setPrefWidth(480);
        table2.setEditable(true);
        table2.setStyle("-fx-cell-size: 50px;");

        String[] names = new String[]{"SlotType", "SlotValue"};
        ArrayList<ObservableList<String>>slotComboData=setSlotsTypeComboData(names,currentTableName);
        //columns
        for (int i = 0; i < names.length; i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(names[i]);
            column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx)));
            // THIS ADDS THE OPTION OF COMBOBOXES IN A TABLE for SlotType, i.e, i == 0
            if (i == 0) {
                column.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), slotComboData.get(i)));
            } else {
                column.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
            }
            table2.getColumns().add(column);
            table2.getColumns().forEach(col -> {
                col.setOnEditCommit(event -> {
                    int row = event.getTablePosition().getRow();
                    int colIndex = event.getTablePosition().getColumn();
                    String newValue = (String) event.getNewValue();
                    table2.getItems().get(row).set(colIndex, newValue);
                });
            });
            //handles editing cells
            table2.setVisible(isSlot);
        }
        //row data
        List<List<String>> twoColData = arrangeSlotTypeSlotValue(currentTableName);
        for (int j = 0; j < twoColData.get(0).size(); j++) {
            ObservableList<String> rowObservable = FXCollections.observableArrayList();
            for (int i = 0; i < 2; i++) {
                rowObservable.add(twoColData.get(i).get(j));
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
                    }
                });

        table2.setFixedCellSize(60.0);
        // TODO: ADD WHEN DISPLAYING FROM BUTTON TABLE AND THEN USE IS VISIBLE
        return table2;
    }

        public List<List<String>> arrangeSlotTypeSlotValue(String currentTableName){
        //One list stores all slotTypes other stores all slotValues
        List<List<String>> colData = new ArrayList<>();
        List<String> slotTypes = new ArrayList<>();
        List<String> slotValuesCurrent = new ArrayList<>();

        List<String> tempavaiableslotTypes = new ArrayList<>();
        // deciding on this table avaiable slotTypes
            for (String key : columnNamesUndevided.keySet()) { //column names
                if (currentTableName.equals(key)) { //we check which key matches our table
                    //Loop through content of retrieved list
                    for (int i = 0; i < columnNamesUndevided.get(key).size(); i++) {
                        tempavaiableslotTypes.add(columnNamesUndevided.get(key).get(i));
                    }
                }
            }
            //adding slot types (how many times <Day> appears and etc.)
            for (int j = 0; j < tempavaiableslotTypes.size() ; j++) { //check for each <key>
                for (String keys : slotValues.keySet()) { // looping through <Key1>,<key2>,etc..
                    for (int i = 0; i < slotValues.get(keys).length; i++) { //looping through each key content
                        if(keys.equals(tempavaiableslotTypes.get(j))) {
                            slotTypes.add(keys);
                        }
                    }
                }
            }
            //adding slot values (Monday,Sunday, etc..)
            for (int j = 0; j < tempavaiableslotTypes.size() ; j++) {
                for (String keys : slotValues.keySet()) {
                    for (int i = 0; i < slotValues.get(keys).length; i++) {
                        if (keys.equals(tempavaiableslotTypes.get(j))) {
                            slotValuesCurrent.add(slotValues.get(keys)[i]);
                        }
                    }
                }
            }
        colData.add(slotTypes);
        colData.add(slotValuesCurrent);
        return colData;
    }
    ArrayList<Button>tableButtons;
    String currentTable;
    public void createMultipleTables() {
        tableButtons = new ArrayList<>();
        tableCollection = new HashMap<>();
        for (int i = 0,y=250; i < tableNames.size(); i++,y+=40) {
            currentTable=tableNames.get(i);
            //creating tables
            TableView<ObservableList<String>> table1 = createTable(tableNames.get(i));
            TableView<ObservableList<String>> table2 = createSlotTable(tableNames.get(i));
            List<TableView<ObservableList<String>>> temp = new ArrayList<>();
            temp.add(table1);
            temp.add(table2);
            tableCollection.put(tableNames.get(i), temp);

            //create Buttons to change new table
            Button button = ButtonFactory.createButton(currentTable, 20, y);
            ButtonFactory.setDefaultActions(button);
            button.setOnAction(e -> {
                isSlot=false;
                tableCollection.get(button.getText()).get(1).setVisible(isSlot);
                //delete current window table
                scrollChat.getChildren().clear();
                //add new table corresponding to this button title
                scrollChat.getChildren().add(tableCollection.get(button.getText()).get(0));
                currentTable=button.getText();
            });
            tableButtons.add(button);
            UIPane.getChildren().add(button);
        }
        for (String  keys : tableCollection.keySet()) {
            System.out.println("each table col stores: "+tableCollection.get(keys).size());
        }
        //add initial table
        currentTable=tableNames.get(0);
        scrollChat.getChildren().add(tableCollection.get(tableNames.get(0)).get(0));
        scrollPane.setContent(scrollChat);
    }

    public ArrayList<ObservableList<String>> setPlaceholderComboData(ArrayList<String> slotNames) {
        List<String> temp = new ArrayList<>();
        ArrayList<ObservableList<String>> comboData = new ArrayList<ObservableList<String>>();

        for (int i = 0; i < slotNames.size(); i++) {
            for (String keys : slotValues.keySet()) {
                if(keys.equals(slotNames.get(i))) {
                    temp = List.of(slotValues.get(keys));
                }
            }
//            temp.add(""); // add option to add empty space for a slot
            for (int j = 0; j < temp.size(); j++) {
                System.out.println("temp: "+temp.get(j));
            }
            System.out.println(" ");
            ObservableList<String> values = FXCollections.observableArrayList(temp);
            comboData.add(values);
        }
        return comboData;
    }
    public List<List<String>> getDataPerColumn (ArrayList<String> slotNames,int index,String thisTableName) {
        List<List<String>> allData = new ArrayList<>();// all rows stored in a list
        ArrayList<String> storeTempColData = new ArrayList<>();
        boolean flag=false;
        String action="";
        for (int j = 0; j < getSlotsUsed.size(); j++) { //check if corresponds to this table 5 rows
            if(currentCFG.beforeKleeneStar.get(j).equals(thisTableName)){ // 2 rows will go through UP THIS POINT CORRECT
                action=actionToSlots.get(j);
                for (int i = 0; i < slotNames.size(); i++) { // we will do it n column times (3 times)
                    // if column corresponds to current col add it
                    for (int k = 0; k < getSlotsUsed.get(j).size(); k++) { // number of data avaiable for this col
                        if (getSlotsUsed.get(j).get(k)[0].equals(slotNames.get(i))){ // if corresponds to this col add
                            storeTempColData.add(getSlotsUsed.get(j).get(k)[1]);
                            flag=true; //we found
                        }
                    }// check if empty after operation
                    if(!flag){
                        storeTempColData.add("");
                    }
                    flag=false;
                    if(storeTempColData.size()==slotNames.size()){
                        //change last element to cerrect action
                        storeTempColData.remove(slotNames.size()-1);
                        storeTempColData.add(action);

                        ArrayList<String> clonedList = new ArrayList<>(storeTempColData);
                        allData.add(clonedList);
                        storeTempColData.clear();
                    }
                }
                // after signle operation add list to the list of lists

            }
            //returning 2 row datas
        }
        return allData;
    }


    public ArrayList<ObservableList<String>> setSlotsTypeComboData(String[] slotnames,String currentTableName) {
        ArrayList<ObservableList<String>> slotComboData = new ArrayList<>();
        ArrayList<String> tempComboData = new ArrayList<>();

        // Adding Slot types into array (<Day>,<Month>,etc...)
        for (String key : columnNamesUndevided.keySet()) { //column names
            if (currentTableName.equals(key)) { //we check which key matches our table
                //Loop through content of retrieved list
                for (int i = 0; i < columnNamesUndevided.get(key).size(); i++) {
                    tempComboData.add(columnNamesUndevided.get(key).get(i));
                }
            }
        }
        tempComboData.add("");
        ObservableList<String> values = FXCollections.observableArrayList(tempComboData);
        slotComboData.add(values);
        return slotComboData;
    }

    @Override
    public void design() {
//        createText(tableName, 540, 40);
        createSideMenu();
        createButtons();
        setButtonActions();
        //ADDING TABLE TO SCROLLPANE MAKE BUTON TO DELETE FROM SCROLLPANE
        createScrollPane();
        createMultipleTables();
//        createTable("<LOCATION>");
//        createSlotTable();


    }

}

package org.group1.back_end.response.skills;

import org.group1.back_end.response.skills.dataframe.Cell;
import org.group1.back_end.response.skills.dataframe.DataFrame;
import org.group1.back_end.response.skills.dataframe.Rows;
import org.group1.back_end.utilities.GeneralFileService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.table.*;

public class
SkillData {

    private String question;
    private DataFrame slots;
    private DataFrame actions;

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setSlots(DataFrame slots) {
        this.slots = slots;
        constructActionFrame();
    }

    public void setActions(DataFrame actions) {
        this.actions = actions;
    }

    public List<String> getSlotNames(){
        return slots.getColumnNames();
    }
    public List<String> getColumnNames(){return actions.getColumnNames();}

    public DataFrame getActions() {
        return actions;
    }

    public DataFrame getSlots(){
        return slots;
    }

    private void constructActionFrame(){
        List<String> actionNames = new ArrayList<>(slots.getColumnNames());
        actionNames.add("Action");
        actions = new DataFrame(actionNames);
    }


    private Rows emptyActionRow(){
        List<Cell> cells = new ArrayList<>();
        for(int i = 0; i < actions.getColumnNames().size(); i++){
            cells.add(new Cell(""));
        }
        return new Rows(cells);
    }



    @Override
    public String toString(){
        String toret = "Question: " + question + "\n";
        toret += slots + "\n";
        toret += actions + "\n";
        return toret;
    }

    public void display() {
//        System.out.println(this);
        JFrame frame = new JFrame("DataFrame Editor");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        JTable table = new JTable(toTableModel());
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Row");
        JButton deleteButton = new JButton("Delete Row");
        JButton saveButton = new JButton("Save");

        addButton.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(new Object[]{});
        });

        deleteButton.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                model.removeRow(selectedRow);
            }
        });

        saveButton.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int rowCount = model.getRowCount();
            int columnCount = model.getColumnCount();
            DataFrame updated = new DataFrame(actions.getColumnNames());
            for (int i = 0; i < rowCount; i++) {
                List<Cell> rowData = new ArrayList<>();
                for (int j = 0; j < columnCount; j++) {
                    Object modelOutput = model.getValueAt(i,j);
                    if(modelOutput==null){
                        rowData.add(new Cell<>(null));
                    }else{
                        rowData.add(new Cell<>(modelOutput));
                    }


                }
                boolean add = false;
                for(Cell cell : rowData){
                    if(cell.getValue() != null){
                        add = true;
                    }
                }
                if(add){
                    updated.insert(new Rows(rowData));
                    this.insert(new Rows(rowData));
                }
            }
            this.actions = updated;
            GeneralFileService.overWrite(this);
            frame.dispose();
        });

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void insert(Rows dogwater) {
        insertSlots(dogwater);
        slots.removeNullRows();
    }

    private void insertSlots(Rows action){
        List<Cell> cells = action.getCells();
        for(int i = 0; i < cells.size()-1; i++){
            if(!slots.columnContainsValue(i, cells.get(i).getValue())){ //check if contains
                slots.insertToFirstFreeSlot(i, cells.get(i)); //add it
            }
        }
    }


    public String getQuestion() {
        return question;
    }

    public DefaultTableModel toTableModel() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        this.actions.getColumnNames().forEach(model::addColumn);
        for (Rows row : this.actions.getData()) {
            String[] rowData = new String[row.size()];
            for (int i = 0; i < row.size(); i++) {
                if(row.get(i).getValue() == null){
                    rowData[i] = "";
                }else{
                    rowData[i] = (String)row.get(i).getValue();
                }
            }
            model.addRow(rowData);
        }

        return model;
    }


    public void insertAction(List<String> _slots, String action) {

        //slots; <type> slot, <type> slot, <type> slot
        Rows row = emptyActionRow();
        List<String> types = slots.getColumnNames();
        for (String slot : _slots) {
//            System.out.println("SLOTSLOT: ");
            Pattern pattern = Pattern.compile("^(<[^>]+>)\\s+(.+)$");
            Matcher matcher = pattern.matcher(slot);
            String type, value;
            type = value = "";
            if (matcher.find()) {
                type = matcher.group(1);
                value = matcher.group(2);
//                System.out.println("Type: " + type);
//                System.out.println("Value: " + value);
            } else {
                System.out.println("The input string does not match the pattern.");
            }
            int index = types.indexOf(type);
            if (!type.equals("")) row.getCells().get(index).setValue(value);
        }
        row.getCells().get(row.size() - 1).setValue(action);
        actions.insert(row);

    }


}

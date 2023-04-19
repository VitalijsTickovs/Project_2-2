package org.group1.back_end.response.skills.dataframe;


import org.group1.back_end.response.skills.dataframe.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DataFrameEditor extends JFrame {

    private final DataFrame dataFrame;
    private final DefaultTableModel tableModel;

    public DataFrameEditor(DataFrame dataFrame) {

        this.dataFrame = dataFrame;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DataFrame Editor");

        // Create a table model with the column names and data from the DataFrame
        String[] columnNames = dataFrame.getColumnNames().toArray(new String[0]);
        List<Rows> rowsList = dataFrame.getData();
        Object[][] data = new Object[rowsList.size()][columnNames.length];
        for (int i = 0; i < rowsList.size(); i++) {
            List<Cell> rowData = rowsList.get(i).getCells();
            for (int j = 0; j < columnNames.length; j++) {
                data[i][j] = rowData.get(j).getValue();
            }
        }
        tableModel = new DefaultTableModel(data, columnNames);

        // Create a table with the table model
        JTable table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(table);

        // Create buttons for adding and deleting rows
        JButton addButton = new JButton("Add Row");
        addButton.addActionListener(e -> {
            Object[] rowData = new Object[columnNames.length];
            for (int i = 0; i < columnNames.length; i++) {
                rowData[i] = null;
            }
            tableModel.addRow(rowData);
        });
        JButton deleteButton = new JButton("Delete Row");
        deleteButton.addActionListener(e -> {
            int[] selectedRows = table.getSelectedRows();
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                tableModel.removeRow(selectedRows[i]);
            }
        });

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        buttonPanel.add(deleteButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Add the components to the frame
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }




//    public DataFrame getDataFrame() {
//        // Update the DataFrame with the data from the table model
//        List<Rows> rowsList = new ArrayList<>();
//        for (int i = 0; i < tableModel.getRowCount(); i++) {
//            List<Cell> rowData = new ArrayList<>();
//            for (int j = 0; j < tableModel.getColumnCount(); j++) {
//                Object value = tableModel.getValueAt(i, j);
//                rowData.add(new Cell<>(value));
//            }
//            rowsList.add(new Rows(rowData));
//        }
//        return new DataFrame(dataFrame.getColumnNames(), rowsList);
//    }




    public static void main(String[] args) {
        // Create a DataFrame object with some initial data
        DataFrame data = new DataFrame(Arrays.asList("Name", "Age", "City"))
                .insert(new Rows(Arrays.asList(new Cell("Alice"), new Cell(25), new Cell("New York"))))
                .insert(new Rows(Arrays.asList(new Cell("Bob"), new Cell(30), new Cell("Los Angeles"))))
                .insert(new Rows(Arrays.asList(new Cell("Charlie"), new Cell(35), new Cell("Chicago"))));

        // Display the initial data
        System.out.println("Initial data:");
        System.out.println(data);

        // Display the initial data in GUI format
        SwingUtilities.invokeLater(() -> {
            new DataFrameEditor(data);
        });




        // Allow the user to edit the data
//        Scanner scanner = new Scanner(System.in);
//        boolean done = false;
//        while (!done) {
//            System.out.println("Enter a command (insert/delete/exit):");
//            String command = scanner.nextLine();
//            switch (command) {
//                case "insert":
//                    System.out.println("Enter a comma-separated row of data:");
//                    String rowString = scanner.nextLine();
//                    String[] values = rowString.split(",");
//                    Rows row = new Rows(Arrays.asList(values));
//                    data.insert(row);
//                    System.out.println("Data after insert:");
//                    System.out.println(data);
//                    break;
//                case "delete":
//                    System.out.println("Enter a comma-separated row of data to delete:");
//                    String rowString2 = scanner.nextLine();
//                    String[] values2 = rowString2.split(",");
//                    Rows row2 = new Rows(Arrays.asList(values2));
//                    data.delete(row2);
//                    System.out.println("Data after delete:");
//                    System.out.println(data);
//                    break;
//                case "exit":
//                    done = true;
//                    break;
//                default:
//                    System.out.println("Invalid command");
//                    break;
//            }
//        }

        System.out.println("Done");
    }

}

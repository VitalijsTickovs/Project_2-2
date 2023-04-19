package org.group1.back_end.response.skills.dataframe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.*;


/**
 * Dataframe
 */
public class DataFrame {

    List<Rows> rowsList;
    List<String> columnNames;

    boolean isSet = false;

    public DataFrame(List<String> columnNames) {
        this.columnNames = columnNames;
        rowsList = new ArrayList<>();
    }

    private DataFrame(List<String> columnNames, List<Rows> rows) {
        this.columnNames = columnNames;
        this.rowsList = rows;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public DataFrame insert(Rows rows) {
        if (isSet && this.contains(rows)) return this;
        rowsList.add(rows);
        return this;
    }


    public DataFrame insert(List<String[]> rows){
        for(String[] entry: rows){
            List<Cell> cells = new ArrayList<Cell>();
            for(int i=0; i<entry.length; i++){
                cells.add(new Cell(entry[i]));
            }
            Rows row = new Rows(cells);
            insert(row);
        }
        return this;
    }

    public List<Rows> getData() {
        return this.rowsList;
    }

    public DataFrame delete(Rows row) {
        for (int i = 0; i < this.rowsList.size(); i++) {
            if (this.rowsList.get(i).equals(row)) {
                this.rowsList.remove(i);
                i--;
            }
        }
        return this;
    }

    public DataFrame isSet(boolean isSet) {
        this.isSet = isSet;
        return this;
    }

    public boolean contains(Rows row) {
        for (Rows r : rowsList) {
            if (row.equals(r)) return true;
        }
        return false;
    }

    public DataFrame getColumn(int index) {
        List<Rows> rows = new ArrayList<>();
        for (Rows r : this.rowsList) {
            rows.add(new Rows(Arrays.asList(r.get(index))));
        }
        return new DataFrame(Arrays.asList(this.columnNames.get(index)), rows);
    }

    public DataFrame getColumn(String columnName) {
        int index = this.columnNames.indexOf(columnName);
        return this.getColumn(index);
    }

    public DataFrame delete(Cell c) {
        return null;
    }

    public DataFrame delete(int index) {
        this.rowsList.remove(index);
        return this;
    }

    public Rows get(int index) {
        return rowsList.get(index);
    }

    public Rows get(Rows row) {
        for (Rows r : rowsList) {
            if (r.equals(row)) return r;
        }
        return null;
    }

    public int size() {
        return this.rowsList.size();
    }

    public DataFrame subset(int start, int end) {
        return new DataFrame(this.columnNames, rowsList.subList(start, end));
    }

    private int getWidth() {
        return this.columnNames.size();
    }

    private int getHeight() {
        return this.rowsList.size();
    }

    public DataFrame insertCell(String columnName, Object value) {
        return insertCell(this.columnNames.indexOf(columnName), value);
    }

    public DataFrame insertCell(int columnIndex, Object value) {
        if (columnIndex < 0 || columnIndex >= this.columnNames.size()) {
            throw new IllegalArgumentException("Invalid column index");
        }

        int lastRowIndex = this.rowsList.size() - 1;
        if (lastRowIndex < 0 || this.rowsList.get(lastRowIndex).get(columnIndex) != null) {
            // Create a new row with empty cells
            List<Cell> rowData = new ArrayList<>();
            for (int i = 0; i < this.columnNames.size(); i++) {
                if(i == columnIndex) rowData.add(new Cell<>(value));
                else rowData.add(new Cell<>(null));
            }
            this.rowsList.add(new Rows(rowData));
        }

        return this;
    }

    public DataFrame insertToFirstFreeSlot(int columnIndex, Object value){
        for(Rows rows : this.rowsList){
            System.out.println("dirty printy man: "+  rows.getCells().get(columnIndex).getValue() + "?=" + value);
            if(rows.getCells().get(columnIndex).getValue() == null ||
                    rows.getCells().get(columnIndex).getValue().toString().equals(" ")||
                    rows.getCells().get(columnIndex).getValue().toString().equals("")){
                rows.getCells().get(columnIndex).setValue(value);
                return this;
            }
        }
        return insertCell(columnIndex, value);
    }

    public boolean columnContainsValue(int columnIndex, Object value) {
        if (columnIndex < 0 || columnIndex >= this.columnNames.size()) {
            throw new IllegalArgumentException("Invalid column index");
        }

        for (Rows row : this.rowsList) {
            Cell cell = row.get(columnIndex);
            if(cell.getValue() != null){
                if(cell.getValue().toString().equals(value)){
                    return true;
                }
            }
        }

        return false;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        List<Integer> columnWidths = new ArrayList<>();
        System.out.println("This must be 2 when being printed: " + columnNames);
        for (int i = 0; i < columnNames.size(); i++) {
            int maxNameWidth = columnNames.get(i).length();
            int finalI = i;
            int maxValueWidth = rowsList.stream()
                    .map(row -> row.get(finalI).toString().length())
                    .max(Integer::compare)
                    .orElse(0);
            columnWidths.add(Math.max(maxNameWidth, maxValueWidth) + 2);
        }

        // Top border
        sb.append("+");
        for (int width : columnWidths) {
            for (int j = 0; j < width; j++) {
                sb.append("-");
            }
            sb.append("+");
        }
        sb.append("\n");

        // Column names
        sb.append("|");
        for (int i = 0; i < columnNames.size(); i++) {
            sb.append(String.format(" %-" + (columnWidths.get(i) - 1) + "s|", columnNames.get(i)));
        }
        sb.append("\n");

        // Separator
        sb.append("+");
        for (int width : columnWidths) {
            for (int j = 0; j < width; j++) {
                sb.append("-");
            }
            sb.append("+");
        }
        sb.append("\n");

        // Rows
        for (Rows row : rowsList) {
            sb.append("|");
            for (int i = 0; i < columnNames.size(); i++) {
                String cellValue = row.get(i).toString();
                sb.append(String.format(" %-" + (columnWidths.get(i) - 1) + "s|", cellValue));
            }
            sb.append("\n");
        }

        // Bottom border
        sb.append("+");
        for (int width : columnWidths) {
            for (int j = 0; j < width; j++) {
                sb.append("-");
            }
            sb.append("+");
        }
        sb.append("\n");

        return sb.toString();
    }

    public DataFrame removeNullRows() {
        rowsList.removeIf(Rows::isNull);
        return this;
    }

    public static DataFrame merge(DataFrame one, DataFrame two) {
        List<String> columnNames = new ArrayList<>();
        columnNames.addAll(one.columnNames);
        columnNames.addAll(two.columnNames);

        int maxRows = Math.max(one.getHeight(), two.getHeight());
        List<Rows> rows = new ArrayList<>();

        for (int i = 0; i < maxRows; i++) {
            List<Cell> rowData = new ArrayList<>();

            if (i < one.getHeight()) {
                rowData.addAll(one.get(i).getCells());
            } else {
                for (int j = 0; j < one.getWidth(); j++) {
                    rowData.add(new Cell<>(null));
                }
            }

            if (i < two.getHeight()) {
                rowData.addAll(two.get(i).getCells());
            } else {
                for (int j = 0; j < two.getWidth(); j++) {
                    rowData.add(new Cell<>(null));
                }
            }

            rows.add(new Rows(rowData));
        }

        return new DataFrame(columnNames, rows);
    }

    public static DataFrame mergeDataFrames(List<DataFrame> dataFrames) {
        if (dataFrames == null || dataFrames.isEmpty()) {
            return null;
        }

        DataFrame merged = dataFrames.get(0);

        for (int i = 1; i < dataFrames.size(); i++) {
            merged = merge(merged, dataFrames.get(i));
        }

        return merged;
    }


    // TODO: please build a method, that displays the dataframe in a Jframe and allows to edit it
    public DefaultTableModel toTableModel() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        columnNames.forEach(model::addColumn);

        for (Rows row : rowsList) {
            Object[] rowData = new Object[row.size()];
            for (int i = 0; i < row.size(); i++) {
                rowData[i] = row.get(i).getValue();
            }
            model.addRow(rowData);
        }

        return model;
    }

}

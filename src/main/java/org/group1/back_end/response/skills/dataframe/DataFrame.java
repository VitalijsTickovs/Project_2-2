package org.group1.back_end.response.skills.dataframe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Dataframe
 */
public class DataFrame {


    List<Rows> rowsList;
    List<String> columnNames;

    public DataFrame(List<String> columnNames){
        this.columnNames = columnNames;
        rowsList = new ArrayList<>();
    }

    private DataFrame(List<String> columnNames, List<Rows> rows){
        this.columnNames = columnNames;
        this.rowsList = rows;
    }

    public DataFrame insert(Rows rows){
        rowsList.add(rows);
        return this;
    }

    public DataFrame insert(List<Rows> rows){
        rowsList.addAll(rows);
        return this;
    }

    // TODO: - index delete - matching delete
    public DataFrame delete(Rows row) {
        for(int i = 0; i < this.rowsList.size(); i++){
            if(this.rowsList.get(i).equals(row)){
                this.rowsList.remove(i);
                i--;
            }
        }
        return this;
    }


    public DataFrame delete(Cell c) {
        return null;
    }

    public DataFrame delete(int index){
        this.rowsList.remove(index);
        return this;
    }

    public Rows get(int index){
        return rowsList.get(index);
    }

    public Rows get(Rows row){
        for(Rows r : rowsList){
            if(r.equals(row)) return r;
        }
        return null;
    }

    public int size(){
        return this.rowsList.size();
    }

    public DataFrame subset(int start, int end){
        return new DataFrame(this.columnNames, rowsList.subList(start, end));
    }

    private int getWidth(){
        return this.columnNames.size();
    }

    private int getHeight(){
        return this.rowsList.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Calculate the max width for each column
        List<Integer> columnWidths = new ArrayList<>();
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
        for (int width : columnWidths) {
            for (int j = 0; j < width; j++) {
                sb.append("-");
            }
            sb.append("-");
        }
        sb.append("\n");

        // Column names
        sb.append("|");
        for (int i = 0; i < columnNames.size(); i++) {
            sb.append(String.format(" %-" + (columnWidths.get(i) - 1) + "s|", columnNames.get(i)));
        }
        sb.append("\n");

        // Separator
        for (int width : columnWidths) {
            for (int j = 0; j < width; j++) {
                sb.append("-");
            }
            sb.append("-");
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
        for (int width : columnWidths) {
            for (int j = 0; j < width; j++) {
                sb.append("-");
            }
            sb.append("-");
        }
        sb.append("\n");

        return sb.toString();
    }

    public static void main(String[] args) {
        // Create column names
        List<String> columnNames = Arrays.asList("Name", "Age", "Country");

        // Create a new DataFrame
        DataFrame df = new DataFrame(columnNames);
        System.out.println("Empty DataFrame:");
        System.out.println(df);

        // Insert rows
        Rows row1 = new Rows(Arrays.asList(new Cell<>("Alice"), new Cell<>(30), new Cell<>("USA")));
        Rows row2 = new Rows(Arrays.asList(new Cell<>("Bob"), new Cell<>(25), new Cell<>("Canada")));
        Rows row3 = new Rows(Arrays.asList(new Cell<>("Charlie"), new Cell<>(22), new Cell<>("UK")));
        df.insert(row1);
        df.insert(row2);
        df.insert(row3);
        System.out.println("DataFrame with rows inserted:");
        System.out.println(df);

        // Delete a row
        df.delete(row2);
        System.out.println("DataFrame with a row deleted:");
        System.out.println(df);


        // Delete a row by index
        df.delete(0);
        System.out.println("DataFrame with a row deleted by index:");
        System.out.println(df);

        // Get a row
        Rows retrievedRow = df.get(0);
        System.out.println("Retrieved row:");
        System.out.println(retrievedRow);

        Rows retrievedRow2 = df.get(row3);
        System.out.println("Retrieved row:");
        System.out.println(retrievedRow2);

        // Insert a list of rows
        Rows row4 = new Rows(Arrays.asList(new Cell<>("Eve"), new Cell<>(29), new Cell<>("Germany")));
        Rows row5 = new Rows(Arrays.asList(new Cell<>("Frank"), new Cell<>(33), new Cell<>("Australia")));
        df.insert(Arrays.asList(row4, row5));
        System.out.println("DataFrame with a list of rows inserted:");
        System.out.println(df);

        // Get the size of the DataFrame
        int size = df.size();
        System.out.println("Size of the DataFrame: " + size);

        // Create a subset of the DataFrame
        DataFrame subset = df.subset(1, 3);
        System.out.println("Subset of the DataFrame:");
        System.out.println(subset);
    }
}

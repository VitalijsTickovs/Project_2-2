package org.group1.response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLtoTxt {


    // Connect to the database
    String url = "jdbc:mysql://localhost:3306/skillbase";
    String user = "cakeboy";
    String password = "cake043";





    public List<String> getColumnNames(String tableName) {
        List<String> columnNames = new ArrayList<>();
        try {

            Connection conn = DriverManager.getConnection(url, user, password);

            // Query the column names for the specified table
            String sql = "SELECT COLUMN_NAME FROM information_schema.columns WHERE table_name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tableName);
            ResultSet rs = stmt.executeQuery();

            // Extract the column names from the result set
            while (rs.next()) {
                columnNames.add(rs.getString("COLUMN_NAME"));
            }

            // Close the database connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // flip the column

        return columnNames;
    }


    public String slotText(List<String> colNames, String tableName) {

        ArrayList<String> answers = new ArrayList<>();

        try {

            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            // Iterate through the column names and fetch their distinct values from the table


            for (int i=0;i<colNames.size();i++) {
                System.out.println("colname: " + colNames.get(i));


                String sql = "SELECT DISTINCT " + colNames.get(i) + " FROM " + tableName;
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);


                // Add each distinct value to the answers list
                while (rs.next()) {
                    System.out.println(rs.getString(1));
                    answers.add(rs.getString(1));
                }

                System.out.println(answers.get(i));
                // Close the statement and result set
                rs.close();
                stmt.close();

            }

            // Close the connection
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the list of distinct values as a comma-separated string
        return String.join(", ", answers);
    }

    public static void main(String[] args) {

        SQLtoTxt s = new SQLtoTxt();

        String tableName = "rule";
        List<String> colNames = s.getColumnNames(tableName);

        System.out.println(colNames);

        s.slotText(colNames,tableName);

    }


    //String temp = "Slot " +

    // get all based on some column name
//    for(int i=0;i<columNames.)


    // TODO: build the slots part
   // String sql = "SELECT FROM " +


}

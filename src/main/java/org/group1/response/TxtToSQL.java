package org.group1.response;


import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TxtToSQL {

    private ArrayList<String> slotType = new ArrayList<>();

    // TODO:
    // - ADD GENERIC ROW ACTION 'I DON'T HAVE AN IDEA'


    int tableID = 1;                                        // ...
    String tableName = "Rule " + tableID;                   // ...
    ArrayList<String> colNames = new ArrayList<>();         // this is the slotNames


    // Connect to the database
    String url = "jdbc:mysql://localhost:3306/skillbase";
    String user = "cakeboy";
    String password = "cake043";

    /**
     * Build SQL table
     * @param tableName
     * @param colNames
     */
    public void createTable(String tableName, ArrayList<String> colNames){

        try {

            // SQL query
            String sql = "CREATE TABLE " + tableName + " (";

            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            for(int i=0;i<colNames.size();i++){
                sql = sql + colNames.get(i) + " VARCHAR(255), ";
            }
            //
            System.out.println(sql);

            stmt.executeUpdate(sql);

            // Close the database connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    
    public void createTableSlot(HashMap<String, String> mapping){
        try{
            String sql = "CREATE TABLE Slots" +" (";

            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            for(int i=0; i<2; i++){

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    /**
     * Insert record into sql table
     * @param tableName
     * @param slotType
     * @param slotValue
     */
    public void insertRecord(String tableName, String[] slotType, String[] slotValue) {

        try {

            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO " + tableName + "(";

            for(int i=0; i<slotType.length; i++){
                sql += slotType[i];
                if(i+1<slotType.length) sql += ",";
            }

            sql += " VALUES (";

            for(int i=0; i<slotValue.length; i++){
                sql += "'" + slotValue[i] + "'";
                if(i+1<slotType.length) sql += ",";
            }
            sql += ")";
            // SQL query
                // slotType + ") VALUES ('" + slotValue + "')";

            // Create a statement and execute the query
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            // Close the database connection
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //TODO: extra testing
    public static void main(String[] args) {
        TxtToSQL t = new TxtToSQL();
       // t.insertRecord("rule", "Action","I have no idea");
    }


}

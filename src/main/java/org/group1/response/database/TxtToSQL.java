package org.group1.response.database;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class TxtToSQL {

    private ArrayList<String> slotType = new ArrayList<>();

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
                sql = sql + colNames.get(i) + " VARCHAR(255)";
                if(i+1<colNames.size()){
                    sql+= ", ";
                }
            }
            sql += ");";
            //
            System.out.println(sql);

            stmt.executeUpdate(sql);

            // Close the database connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    
//    public void createTableSlot(HashMap<String, String> mapping){
//        try{
//            String sql = "CREATE TABLE Slots" +" (";
//
//            Connection conn = DriverManager.getConnection(url, user, password);
//            Statement stmt = conn.createStatement();
//
//            for(int i=0; i<2; i++){
//
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }



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
            sql += ") VALUES (";

            for(int i=0; i<slotValue.length; i++){
                sql += "'" + slotValue[i] + "'";
                if(i+1<slotType.length) sql += ",";
            }
            
            sql += ");";
            

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

    public void insertAction(Set<Slots> slotSet, String action, String id){

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO action_"+ id +"(";
            for(Slots slot: slotSet){
                sql += slot.getSlotType() + ",";
            }
            sql += "Action) VALUES(";

            for(Slots slot: slotSet){
                sql += "'" + slot.getSlotValue() + "'" + ",";
            }
            sql += "'" +action + "');";

            // Create a statement and execute the query
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            // Close the database connection
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    // ======================================

    // TODO: here we make
//    public void makeTablesFromTXT(){
////        makeSlotIDtable();
////        makeActionIDtable();
//    }
//
////    public void makeSlotIDtable(String text){
////
////
////
////    }
////
////    public void makeActionIDtable(String text){
////
////
////
////    }
//


    //TODO: extra testing
    public static void main(String[] args) {
        TxtToSQL t = new TxtToSQL();
       // t.insertRecord("rule", "Action","I have no idea");
    }


}

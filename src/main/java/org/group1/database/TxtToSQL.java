package org.group1.database;


import javax.xml.crypto.Data;
import java.sql.*;
import java.util.*;

import static org.group1.database.GenerateDB.createDatabase;

public class TxtToSQL {

    private ArrayList<String> slotType = new ArrayList<>();

    int tableID = 1;                                        // ...
    String tableName = "Rule " + tableID;                   // ...
    ArrayList<String> colNames = new ArrayList<>();         // this is the slotNames


    // Connect to the database
    String url = "jdbc:mysql://localhost:3306/skilldb";
    String user;
    String password;

    public TxtToSQL(){
        user = DatabaseCredentials.getUsername();
        password = DatabaseCredentials.getPassword();
        createDatabase(user, password);
    }

    /**
     * Build SQL table
     * @param tableName
     * @param colNames
     */
    public void createTable(String tableName, List<String> colNames){
        try {
            // SQL query
            String sql = "CREATE TABLE "+ tableName + " (TableID int NOT NULL AUTO_INCREMENT, ";

            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            for(int i=0;i<colNames.size();i++){
                sql = sql + colNames.get(i) + " VARCHAR(255)";
                if(i+1<colNames.size()){
                    sql+= ", ";
                }
            }
            sql+=", PRIMARY KEY(TableID));";
            //
            System.out.println(sql);

            stmt.executeUpdate(sql);

            // Close the database connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    public void createActionTable(String id, Object[] colNames){
        try {
            // SQL query
            String sql = "CREATE TABLE action_" + id + " (TableID int NOT NULL AUTO_INCREMENT, ";

            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            for(int i=0;i<colNames.length;i++){
                sql = sql + colNames[i] + " VARCHAR(255)";
                if(i+1<colNames.length){
                    sql+= ", ";
                }
            }
            sql+=",Action VARCHAR(255), PRIMARY KEY(TableID));";
//            sql += ", PRIMARY KEY (TABLEID));";
//            sql += ", PRIMARY KEY (";
//            //
//
//            // BUILD COMBINED PRIMARY KEY BASED ON COMBINED
//            for(int i=0;i<colNames.size()-1;i++){
//                sql += colNames.get(i)+",";
//
//            }
//            sql = sql.substring(0,sql.length()-1)+") ,INDEX idx_table_id (TableID));";
//            sql +=

            // TODO: default value

            System.out.println(sql);

            stmt.executeUpdate(sql);

            // Close the database connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }






    /**
     * Insert record into sql table
     * @param tableName
     */
    public void insertSlots(String tableName, List<String[]> slots) {

        try {

            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO " + tableName + "(SlotType, SlotValue) VALUES(";

            for(int i=0; i<slots.size()-1; i++){
                for(int j=0; j<slots.get(i).length;j++){
                    sql += "('" + slots.get(i)+ "','";
                    if(slots.get(i+1).equals("")){
                        sql+=" '";
                    }else{
                        sql+=slots.get(i+1)+"')";
                    }
                }
                sql+=",(";
            }
            sql = sql.substring(0,sql.length()-2);
            sql += ";";
            

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

    /**
     * Insert record into sql table
     * @param columns, row
     * @param row, columns
     * @param id, table id
     */
    public void insertAction(Set<String> columns, List<String[]> row, String id){

        try {

            String sql = "INSERT INTO action_"+ id +"(";
            for(String column: columns){
                sql += column + ",";
            }
            sql += "Action) VALUES(";
            for(int i=0; i<row.size();i++){
                for(int j=0; j<row.get(i).length-1;j++){
                    sql += "'" + row.get(i)[j] + "',";
                }
                sql+="'"+row.get(i)[row.get(i).length-1]+"'),(";
            }
            sql = sql.substring(0,sql.length()-2);
            sql += ";";

            // Create a statement and execute the query
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            // Close the database connection
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Remove table
     * @param id
     */
    public void removeTables(String id){
        try{
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            //Statement to create the database
            String sql = "DROP TABLE IF EXISTS action_"+id+";";
            stmt.execute(sql);

            sql = "DROP TABLE IF EXISTS slot_"+id+";";
            stmt.execute(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //TODO: extra testing
    public static void main(String[] args) {
        TxtToSQL t = new TxtToSQL();
       // t.insertRecord("rule", "Action","I have no idea");
    }


}

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
        createDatabase();
    }

    /**
     * Build SQL table
     * @param tableName
     */
    public void createTable(String tableName){
        try {
            // SQL query
            String sql = "CREATE TABLE "+ tableName +
                    " (TableID int NOT NULL AUTO_INCREMENT, " +
                    "SlotType VARCHAR(255), SlotValue VARCHAR(255)";
            sql+=", PRIMARY KEY(TableID));";

            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            // Close the database connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    public void createActionTable(String id, ArrayList<String> colNames){
        try {
            // SQL query
            String sql = "CREATE TABLE action_" + id + " (TableID int NOT NULL AUTO_INCREMENT, ";

            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            for(int i=0;i<colNames.size();i++){
                sql = sql +colNames.get(i).toLowerCase() + " VARCHAR(255)";
                if(i+1<colNames.size()){
                    sql+= ", ";
                }
            }
            sql+=", Action VARCHAR(255), PRIMARY KEY(TableID));";


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
    public void insertSlots(String tableName, List<String[]> slotValues, List<String> slotTypes) {

        try {

            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO " + tableName + "(SlotType, SlotValue) VALUES";
            for(int i=0; i<slotValues.size(); i++){
                String[] values = slotValues.get(i);
                for(int j=0; j<values.length-1;j++) {
                    if(values[j]!=null && !values[j].equals("")){
                        sql +="('"+ slotTypes.get(j) + "','"+values[j]+"'),";
                    }
                }
            }
            sql = sql.substring(0,sql.length()-1);
            sql += ";";
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
    public void insertAction(List<String> columns, List<String[]> row, String id){

        try {

            String sql = "INSERT INTO action_"+ id +"(";
            for(String column: columns){
                sql += column.toLowerCase() + ",";
            }
            sql += "Action) VALUES(";
            for(int i=0; i<row.size();i++){
                String[] action = row.get(i);
                for(int j=0; j<action.length;j++){
                    if(action[j]==null || action[j].equals("")){
                        sql+= "'',";
                    }else{
                        sql+= "'"+action[j].replace("[","").replace("]","")+"',";
                    }
                }
                sql = sql.substring(0,sql.length()-1)+"),(";
            }
            sql= sql.substring(0,sql.length()-2);

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
    }


}

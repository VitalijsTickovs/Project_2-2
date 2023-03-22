package org.group1.response.database;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.group1.response.FileService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLtoTxt {

    // Connect to the database
    String url = "jdbc:mysql://localhost:3306/skilldb"; String user = "root";  String password = "helloSQL";

    String question = "";
    String defaultAction = "Action I have no idea";
    int numberOfCols = 0;

    /**
     * Slot ID for the format to put into the rule .txt file
     * @param id
     * @return
     * @throws SQLException
     */
    public String slotIDtoString(String id) throws SQLException {
        String tableName = "slot_" + id;

        List<String> colNames = getColumnNames(tableName);
        System.out.println("number of colnames: " + colNames.size());

        Connection conn = DriverManager.getConnection(url, user, password);
        try {
            //Initialize the script runner
            ScriptRunner sr = new ScriptRunner(conn);
            //Creating a reader object
            Reader reader = new BufferedReader(new FileReader("src/main/resources/database/skilldb.sql"));
            //Running the script
            sr.runScript(reader);
        }catch(Exception e){
            e.printStackTrace();
        }

        String sql="";
        Statement stmt = conn.createStatement();

        sql = "SELECT DISTINCT " + "*" + " FROM skillbase.slot_" + id + " ORDER BY SlotType;";

        System.out.println(sql);
        ResultSet rs = stmt.executeQuery(sql);

        String RULE_SLOT = "";

        while (rs.next()) {

            System.out.println(rs.getString(1));

            RULE_SLOT += "Slot " + "<" + rs.getString(1) + "> " + rs.getString(2) + "\n";

        }

        System.out.println(RULE_SLOT);

        // Close the database connection
        conn.close();

        return RULE_SLOT;
    }


    // TODO: if null, don't build the string
    public String actionIDtoString(String id) throws SQLException{

        // sql magic
        Connection conn = DriverManager.getConnection(url, user, password);
        String sql = "SELECT DISTINCT " + "*" + " FROM skillbase.action_" + id + " ;";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        // column names, reverse

        List<String> colNames = getColumnNames("action_" + id);
        System.out.println("ColNames: " + colNames);
       // Collections.reverse(colNames);

        // number of columns
        ResultSetMetaData rsmd = rs.getMetaData();
        int numColumns = rsmd.getColumnCount();
        int columnCount = numColumns;


        String ACTION_STRING = "";

        while (rs.next()) {


            ACTION_STRING += "Action ";

            for(int j=1;j<columnCount;j++){

                if(rs.getString(j)!=null){

                    ACTION_STRING += "<" + colNames.get(j-1) + "> " + rs.getString(j) + " ";

                }

            }

            if(rs.getString(columnCount)!=null ){

                // add the action at end
                ACTION_STRING += rs.getString(columnCount);
            }


            ACTION_STRING += "\n";

        }

        System.out.println(ACTION_STRING);

        return ACTION_STRING+"Action "+defaultAction;
    }

    public void overWrite(String id){
        try {
            FileService fs = new FileService();
            fs.overWrite(id,actionIDtoString(id));
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**  AUXILIARY */
    public List<String> getColumnNames(String tableName) {
        List<String> columnNames = new ArrayList<>();
        try {

            Connection conn = DriverManager.getConnection(url, user, password);

            // Query the column names for the specified table
            String sql = "SELECT COLUMN_NAME FROM information_schema.columns WHERE table_name = ? ORDER BY ORDINAL_POSITION";
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




    // testing...
    public static void main(String args[]) throws Exception {
        String url = "jdbc:mysql://localhost:3306/"; String user = "root";  String password = "helloSQL";
        //Registering the Driver
        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        String sql = "IF DB_ID('skilldb') IS NULL CREATE DATABASE skilldb";
        stmt.execute(sql);
        stmt.execute("USE skilldb");

        //Getting the connection
        ScriptRunner sr = new ScriptRunner(conn);
        //Creating a reader object
        Reader reader = new BufferedReader(new FileReader("src/main/resources/database/skilldb.sql"));
        //Running the script
        sr.runScript(reader);
    }



}

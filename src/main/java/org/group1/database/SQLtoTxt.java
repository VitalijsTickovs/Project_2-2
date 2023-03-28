package org.group1.database;

import org.group1.back_end.response.skills.SkillFileService;
import org.group1.back_end.utilities.GeneralFileService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLtoTxt {


    /**
     * Slot ID for the format to put into the rule .txt file
     * @param id
     * @return
     * @throws SQLException
     */
    public static String slotIDtoString(String id) throws SQLException {
        String tableName = "slot_" + id;

        Connection conn = DriverManager.getConnection(DatabaseCredentials.getURL(), DatabaseCredentials.getUsername(), DatabaseCredentials.getPassword());

        String sql="";
        Statement stmt = conn.createStatement();

        sql = "SELECT DISTINCT " + "SlotType, SlotValue" + " FROM " + tableName + " ORDER BY SlotType;";

        ResultSet rs = stmt.executeQuery(sql);

        String RULE_SLOT = "";

        while (rs.next()) {

            if(!rs.getString(2).equals(""))
                RULE_SLOT += "Slot <" + rs.getString(1).toUpperCase() + "> " + rs.getString(2) + "\n";

        }


        // Close the database connection
        conn.close();

        return RULE_SLOT;
    }


    // TODO: if null, don't build the string
    public static String actionIDtoString(String id) throws SQLException{
        String tablename = "action_" + id;
        // sql magic
        Connection conn = DriverManager.getConnection(DatabaseCredentials.getURL(),
                DatabaseCredentials.getUsername(), DatabaseCredentials.getPassword());

        // column names, reverse

        List<String> colNames = getColumnNames("action_" + id);
        colNames.remove(0);

        String sql = "SELECT DISTINCT "+convertToString(colNames)+" FROM "+tablename+";";
        Statement stmt_1 = conn.createStatement();
        ResultSet rs1 = stmt_1.executeQuery(sql);
        // number of columns
        ResultSetMetaData rsmd = rs1.getMetaData();
        int numColumns = rsmd.getColumnCount();
        int columnCount = numColumns;


        String ACTION_STRING = "";

        int rowCounter=1;

        while (rs1.next()) {

            ACTION_STRING += "Action ";

            for(int j=1;j<colNames.size();j++){

                if(rs1.getString(j) !=null && !rs1.getString(j).equals("")){
                    String actionEntry = "<" + colNames.get(j-1).toUpperCase() + "> " + rs1.getString(j) + " ";
                    ACTION_STRING += actionEntry;
                }

            }

            if(rs1.getString(columnCount)!=null && !rs1.getString(columnCount).equals("")){
                // add the action at end
                ACTION_STRING += rs1.getString(columnCount);
            }


            ACTION_STRING += "\n";
            rowCounter++;
        }

        return ACTION_STRING;
    }

    public static void overWrite(String id){
        try {
            GeneralFileService.overWrite(id,slotIDtoString(id)+actionIDtoString(id));
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**  AUXILIARY */
    public static List<String> getColumnNames(String tableName) {
        List<String> columnNames = new ArrayList<>();
        try {

            Connection conn = DriverManager.getConnection(DatabaseCredentials.getURL(), DatabaseCredentials.getUsername(), DatabaseCredentials.getPassword());

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


    public static String convertToString(List<String> columns){
        String converted = "";
        for(String column: columns){
            converted += column +=",";
        }
        return converted.substring(0,converted.length()-1);
    }


}

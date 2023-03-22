package org.group1.database;


import org.group1.back_end.response.skills.SkillFileService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLtoTxt {

    // Connect to the database
    String url = "jdbc:mysql://localhost:3306/skillbase"; String user = "cakeboy";  String password = "cake043";

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
    public static void main(String[] args) throws SQLException {

        SQLtoTxt s = new SQLtoTxt();
        String id = "3";

        s.slotIDtoString(id);
        s.actionIDtoString(id);

    }



}

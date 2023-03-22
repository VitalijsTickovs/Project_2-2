package org.group1.response.database;

import org.group1.response.FileService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLGUIConnection {


    // Connect to the database
    String url = "jdbc:mysql://localhost:3306/Skills"; public static String user;  public static String password;
    String database = "Skills";

    /**
     * Slot ID for the format to put into the rule .txt file
     * @param id
     * @return
     * @throws SQLException
     */




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
                if (!rs.getString("COLUMN_NAME").equals("TableID")) {
                    columnNames.add(rs.getString("COLUMN_NAME"));
                }
            }

            // Close the database connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // flip the column

        return columnNames;
    }

    //GETTING NAMES TABLES NAMES FROM
    public List<String> getActionTableNames() {
        List<String> tableNames = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            DatabaseMetaData databaseMetaData = conn.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = databaseMetaData.getTables(database, null, "%", types);
            while (rs.next()) {
                if(rs.getString("TABLE_NAME").charAt(0)=='a') {
                    tableNames.add(rs.getString("TABLE_NAME"));
                    System.out.println(rs.getString("TABLE_NAME"));
                }
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // flip the column

        return tableNames;
    }
    public List<String> getSlotTableNames() {
        List<String> tableNames = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            DatabaseMetaData databaseMetaData = conn.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = databaseMetaData.getTables(database, null, "%", types);
            while (rs.next()) {
                if(rs.getString("TABLE_NAME").charAt(0)=='s') {
                    tableNames.add(rs.getString("TABLE_NAME"));
                    System.out.println(rs.getString("TABLE_NAME"));
                }
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // flip the column

        return tableNames;
    }

    /*
    SELECT COUNT(*)
  FROM INFORMATION_SCHEMA.COLUMNS
 WHERE table_catalog = 'database_name' -- the database
   AND table_name = 'table_name'
     */
    //GETTING NAMES TABLES NAMES FROM
    public int getColumnNumber(String tableName) {
        int count=0;
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            // Query the column names for the specified table
            String sql = "SELECT COLUMN_NAME FROM information_schema.columns WHERE table_name = ? ORDER BY ORDINAL_POSITION";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tableName);
            ResultSet rs = stmt.executeQuery();

            // Extract the column names from the result set
            while (rs.next()) {
                count++;
            }

            // Close the database connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // flip the column
        System.out.println("COUNT "+count);
        return count-1;
    }

    public int getRowNumber(String tableName) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        int count=0;
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS countNumber from " + tableName);
        while (resultSet.next()) {
            count= resultSet.getInt("countNumber");
        }
        return count;
    }
    public ArrayList<String> getAllColumnData (String columnName, String tableName) throws SQLException {
        ArrayList<String> temp= new ArrayList<>();
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT "+ columnName+ " from "+ tableName);
        while (resultSet.next()) {
            temp.add(resultSet.getString(columnName));
        }
        return temp;
    }
    //SELECT DISTINCT Country FROM Customers
    public List<String> avaiableDataFromSlot(String tableName, String slotType) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ArrayList<String> temp= new ArrayList<>();
        System.out.println("SELECT DISTINCT SlotValue from " + tableName +" WHERE SlotType = " + "'" + slotType + "'");
        ResultSet resultSet = statement.executeQuery("SELECT DISTINCT SlotValue from " + tableName +" WHERE SlotType = " + "'" + slotType + "'");
        while (resultSet.next()) {
            temp.add(resultSet.getString("SlotValue"));
            System.out.println("IN METHOD " + resultSet.getString("SlotValue"));
        }
        return temp;
    }

    public void updateDatabase(int rowID,String newValue, String columnName, String tableName) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        rowID=rowID+1;
        System.out.println( "ID ROW " + rowID);
        System.out.println(newValue);
        System.out.println(columnName);
        System.out.println(tableName);
        String sql ="UPDATE "+ tableName +" SET " + columnName +"='" + newValue+ "' WHERE TableID="+rowID;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();

        //ResultSet resultSet = statement.executeQuery("UPDATE "+ tableName +" SET " + columnName +"='" + newValue+ "' WHERE TableID="+rowID );

    }
    public void addRow(String tableName, List<String> columnNames) throws SQLException {

        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();

        //restart row cound
        String sql = "SET  @num := 0";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();

         sql = "UPDATE "+ tableName +" SET TableID = @num := (@num+1)";
         preparedStatement = connection.prepareStatement(sql);
         preparedStatement.executeUpdate();

        sql = "ALTER TABLE "+ tableName +" AUTO_INCREMENT = 1";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();

         sql ="INSERT INTO `"+ tableName +"`(" + columnNames.get(0) +") VALUES (NULL)";
         preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        int rowIndex = getRowNumber(tableName)-1;
        for (int i = 1; i < columnNames.size(); i++) {
            sql = "UPDATE "+ tableName +" SET " + columnNames.get(i) +"='-' WHERE TableID="+rowIndex;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        }

    }
    public void setEmptyNull(String tableName,List<String> columnNames) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);

        //restart row cound
//        for (int i = 0; i < columnNames.size(); i++) {
//            String sql = "UPDATE " + tableName + " SET " + columnNames.get(i) + "= NULL WHERE " + columnNames.get(i) + "='-'";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.executeUpdate();
//        }
    }
    // testing...
    public static void main(String[] args) throws SQLException {

        SQLGUIConnection s = new SQLGUIConnection();
        String id = "3";

//        s.slotIDtoString(id);
//        s.actionIDtoString(id);
        s.getRowNumber("action_1");

    }



}

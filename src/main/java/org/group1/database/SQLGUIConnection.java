package org.group1.database;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLGUIConnection extends    DatabaseCredentials {


    // Connect to the database


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

            Connection conn = DriverManager.getConnection(url, username, password);

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
            Connection conn = DriverManager.getConnection(url, username, password);

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
            Connection conn = DriverManager.getConnection(url, username, password);

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
            Connection conn = DriverManager.getConnection(url, username, password);
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
        Connection connection = DriverManager.getConnection(url, username, password);
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
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT "+ columnName+ " from "+ tableName);
        while (resultSet.next()) {
            temp.add(resultSet.getString(columnName));
        }
        return temp;
    }
    //SELECT DISTINCT Country FROM Customers
    public List<String> avaiableDataFromSlot(String tableName, String slotType) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
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

    public List<String> getSlotType(String tableName) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        ArrayList<String> temp= new ArrayList<>();
        System.out.println("SELECT DISTINCT SlotType from " + tableName);
        ResultSet resultSet = statement.executeQuery("SELECT DISTINCT SlotType from " + tableName);
        while (resultSet.next()) {
            temp.add(resultSet.getString("SlotType"));
            System.out.println("IN METHOD " + resultSet.getString("SlotType"));
        }
        return temp;
    }

    public void updateDatabase(int rowID,String newValue, String columnName, String tableName) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
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

        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();

//        //restart row cound
//        String sql = "SET  @num := 0";
//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        preparedStatement.executeUpdate();
//
//         sql = "UPDATE "+ tableName +" SET TableID = @num := (@num+1)";
//         preparedStatement = connection.prepareStatement(sql);
//         preparedStatement.executeUpdate();
//
//        sql = "ALTER TABLE "+ tableName +" AUTO_INCREMENT = 1";
//        preparedStatement = connection.prepareStatement(sql);
//        preparedStatement.executeUpdate();


         String sql ="INSERT INTO `"+ tableName +"`(" + columnNames.get(0) +") VALUES (NULL)";
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        int rowIndex = getRowNumber(tableName); // was -1 and i was 1
        for (int i = 0; i < columnNames.size(); i++) {
            sql = "UPDATE "+ tableName +" SET " + columnNames.get(i) +"='-' WHERE TableID="+rowIndex;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        }

    }

    public void deleteRecords(String tableName, List<Boolean> records) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        for(int i=0; i< records.size();i++){
            if(records.get(i)) {
                String sql = "DELETE FROM `" + tableName + "`WHERE TableID = "+ i;
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeUpdate();
            }
        }
    }

    public void setEmptyNull(String tableName,List<String> columnNames) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);

        //restart row cound
        for (int i = 0; i < columnNames.size()-1; i++) {
            String sql = "UPDATE " + tableName + " SET " + columnNames.get(i) + "= NULL WHERE " + columnNames.get(i) + "='-'";

            sql+= ";";
            System.out.println("god's word: " + sql);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        }
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

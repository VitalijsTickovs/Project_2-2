package database;
import java.sql.*;
import java.util.ArrayList;

public class DastabaseMenager {
    // TODO: MAKE SURE THE PORT IS CORRECT FOR YOU
    // TODO: ALSO MAKE SURE IN THE GRADLE BUILD THE MYSQL IMPORT MATCHES YOUR MYSQL VERSION THAT U HAVE DOWNLOADED
    final String DB_URL = "jdbc:mysql://localhost:3306/skills";
    //TODO: Change the username and password to your sql one
    final String username = "root";
    final String password = "Jirka123";
    private Connection connection;
    private Statement statement;
    public DastabaseMenager() {
        try {
            connection = DriverManager.getConnection(DB_URL, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            System.out.println("MySQL JDBC driver found");
//        } catch (ClassNotFoundException e) {
//            System.err.println("MySQL JDBC driver not found");
//        }
    }
    public ArrayList<String> getAllUsernames() throws SQLException {
        ArrayList<String> arrayList = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT username from users");
        while (resultSet.next()) {
            arrayList.add(resultSet.getString("username"));
        }
        return arrayList;
    }
    public ArrayList<String> getAllPasswords() throws SQLException {
        ArrayList<String> arrayList = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT password from users");
        while (resultSet.next()) {
            arrayList.add(resultSet.getString("password"));
        }
        return arrayList;
    }
}

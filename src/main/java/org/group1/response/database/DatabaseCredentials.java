package org.group1.response.database;

public class DatabaseCredentials {

    // TODO: link this to login of start-page, and possible .gitignore

    public static String username;
    public static String password;
    public String tableName;
    public String dbName = "skillbase";
    String url = "jdbc:mysql://localhost:3306/"+dbName;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        DatabaseCredentials.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DatabaseCredentials.password = password;
    }
}

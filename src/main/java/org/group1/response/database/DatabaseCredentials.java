package org.group1.response.database;

public class DatabaseCredentials {


    public static String username;
    public static String password;

    public final static String url = "jdbc:mysql://localhost:3306/skilldb";

    public static String getUsername() {
        return username;
    }

    public static String getURL(){
        return url;
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

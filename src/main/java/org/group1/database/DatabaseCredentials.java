package org.group1.database;

public class DatabaseCredentials {


    public static String username,password;


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

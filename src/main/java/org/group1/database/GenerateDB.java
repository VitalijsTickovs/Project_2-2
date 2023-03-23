package org.group1.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class GenerateDB {

    // generate db on the fly
    // from .txt

    public static void createDatabase(String user, String password){

        String url = "jdbc:mysql://localhost:3306/";
        try {
            // Making connection to the sql server
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            //Statement to create the database
            String sql = "CREATE DATABASE IF NOT EXISTS skilldb;";
            stmt.execute(sql);
            stmt.execute("USE skilldb");
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}

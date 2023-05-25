package org.app.connection;

import org.app.connection.backEnd.BackEndManager;
import org.app.connection.frontEnd.FrontEndManager;

import java.util.Scanner;

public class Connection {
    private BackEndManager backEnd;
    private FrontEndManager frontEnd;

    public Connection(){
        backEnd = new BackEndManager();
        frontEnd = new FrontEndManager(this);
    }

    public String getResponse(String question){
        return backEnd.getResponse(question);
    }

    public void run(){
        frontEnd.run();
    }

    public static void main(String[] args) {
        Connection a = new Connection();
        a.run();
    }
}

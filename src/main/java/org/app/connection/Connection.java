package org.app.connection;

import org.app.connection.backEnd.BackEndManager;
import org.app.connection.frontEnd.FrontEndManager;

public class Connection {
    private BackEndManager backEnd;
    private FrontEndManager frontEnd;

    public Connection(){
        backEnd = new BackEndManager();
        frontEnd = new FrontEndManager();
    }

    public void run(){
        frontEnd.run();
    }
}

package org.app;

import org.app.connection.Connection;

public class App {
    public static void main(String[] args) {
        Connection connection = new Connection();
        connection.run();
    }
}

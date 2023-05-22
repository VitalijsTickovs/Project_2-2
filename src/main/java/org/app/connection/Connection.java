package org.app.connection;

import org.app.connection.backEnd.BackEndManager;
import org.app.connection.frontEnd.FrontEndManager;

import java.util.Scanner;

public class Connection {
    private BackEndManager backEnd;
    private FrontEndManager frontEnd;

    public Connection(){
//        backEnd = new BackEndManager();
        frontEnd = new FrontEndManager(this);
    }

    public String getResponse(String question){
        return backEnd.getResponse(question);
    }

    public void run(){
        frontEnd.run();
    }

    public static void main(String[] args) throws Exception {
        Connection c = new Connection();
        c.run();

//        Scanner sc = new Scanner(System.in);
//        System.out.println("Ask a question");
//        String a = sc.nextLine();
//        System.out.println("Of course do you wanna play?");
//        //yes
//        String query = sc.nextLine();
////        System.out.println(JavaStringCompiler.getResponse("src/main/resources/Scripts/TicTacToe.txt"));
//        String program = c.backEnd.getResponse(query);
//        System.out.println(program);
//        System.out.println(JavaStringCompiler.getResponse(query));
        //OpenWebPage.open("src/main/resources/Web/web_1.txt");
    }
}

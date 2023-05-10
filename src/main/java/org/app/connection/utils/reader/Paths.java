package org.app.connection.utils.reader;

import java.io.File;

public class Paths {

    public static String getPath(String fileName) throws Exception {
        File file = new File(fileName);
        if(!file.exists()) {
            System.out.println("File " + file + " does not exist.");
            throw new Exception(file + " does not exist");
        }else {
            return file.getPath();
        }
    }
}

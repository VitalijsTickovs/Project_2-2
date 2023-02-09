package org.group1.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CSV_Reader {

    private CSV_Reader(){}

    /**
     * Method for write csv files.
     * @param data data
     * @param file name of the file
     */
    public static void writeCSV(String[] data, String file) {

        try (PrintWriter writer = new PrintWriter(new File(file))) {
            StringBuilder sb = new StringBuilder();
            for (String datum : data) {
                sb.append(datum);
                sb.append('\n');
            }
            writer.write(sb.toString());

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}

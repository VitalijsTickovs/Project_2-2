package org.group1.back_end.experiments;

import org.group1.back_end.response.Response;
import org.group1.back_end.utilities.enums.DB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ROC_curves {

    String deafault = "i have no idea";
    static Response s;

    static {
        try {
            s = new Response();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ROC_curves(){

    }


    public double[] computeROC(List<String[]> data) throws Exception {

        double TP = 0;
        double TN = 0;
        double FP = 0;
        double FN = 0;



        for (String[] query : data) {

            String answer = s.getResponse(query[0]).trim().toLowerCase();
            String realAns = query[1].trim().toLowerCase();
            System.out.println("QUERY: " + query[0]);
            System.out.println("BOT: " + answer +  "    REAL:" + realAns);

            if (realAns.equals(deafault) && answer.equals(deafault)){
                TN++;
            } else if (answer.equals(realAns)) {
                TP++;
            } else if (realAns.equals(deafault) && !answer.equals(deafault)) {
                FP++;
            }else if (answer.equals(deafault) && !realAns.equals(deafault)){
                FN++;
            }

        }

        return new double[]{TP, TN, FP, FN};

    }




    public static void main(String[] args) throws Exception {

        ROC_curves a = new ROC_curves();
        List<String[]> list = a.read("src/main/java/org/group1/back_end/experiments/Robustness.txt", "->");

        Response.setDatabase(DB.DB_VECTORS_SEQ);
        double[] vaues = a.computeROC(list);

        for (double s : vaues ) {
            System.out.println(s);
        }
    }

    public List<String[]> read(String path, String regex) throws FileNotFoundException {

        List<String[]> data = new ArrayList<>();

        Scanner sc = new Scanner(new File(path));

        while (sc.hasNextLine()){
            data.add(sc.nextLine().split(regex));
        }
        return data;
    }



}

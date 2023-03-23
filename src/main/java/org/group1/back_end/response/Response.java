package org.group1.back_end.response;

import org.group1.back_end.ML.model_markov_decision.Markov;
import org.group1.back_end.textprocessing.ComplexProcess;
import org.group1.back_end.textprocessing.SimpleProcess;
import java.util.*;
import org.group1.back_end.utilities.enums.DB;

public class Response {


    public ResponseLibrary responseLibrary;

    public static DB database = DB.DB_PERFECT_MATCHING;

    public Response() throws Exception{
        this.responseLibrary = new ResponseLibrary();
    }

    public String getResponse(String word){
       word = process(word);
        return responseLibrary.getResponseSkills(word);
    }

    public void reload() throws Exception{
        responseLibrary.reload();
    }

    public String process(String word){

        List<String> words = SimpleProcess.process(word);
//        for (String w : words) {
//            if(!ComplexProcess.isCorrect(w)){
//                //NGRAM
//
//            }
//        }
//
        String result = "";
        for (String w: words) {
            result += " " + w + " ";
        }

        return result.trim();
    }


    public static DB getDatabase() {
        return database;
    }

    public static void setDatabase(DB s) {
        database = s;
    }
}

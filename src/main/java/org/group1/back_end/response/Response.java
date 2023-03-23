package org.group1.back_end.response;

import org.group1.back_end.ML.model_markov_decision.Markov;
import org.group1.back_end.response.skills.Skill;
import org.group1.back_end.textprocessing.ComplexProcess;
import org.group1.back_end.textprocessing.SimpleProcess;
import java.util.*;

import org.group1.back_end.textprocessing.SpellingCorrectProcess;
import org.group1.back_end.utilities.algebra.Distances;
import org.group1.back_end.utilities.enums.DB;
import org.nd4j.common.primitives.Pair;

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
        for (int i = 0; i < words.size(); i++) {
            if(!ComplexProcess.isCorrect(words.get(i))){
                words.set(i, SpellingCorrectProcess.correct(words.get(i)));
            }
        }
//
        String result = "";
        for (String w: words) {
            result += " " + w + " ";
        }

        return result.trim();
    }

    public static void main(String[] args) throws Exception {
        Skill test = new Skill();
        Response response = new Response();
        System.out.println(response.getResponse("What is yuor approximately age?"));

    }

    public static DB getDatabase() {
        return database;
    }

    public static void setDatabase(DB s) {
        database = s;
    }
}

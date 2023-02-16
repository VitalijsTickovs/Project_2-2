package org.amulvizk.service.skills;

import org.group1.reponse.procesor.PreProcessor;

import java.util.ArrayList;
import java.util.List;

public class IDGenerator {

    public static int getIDFromQuestion(String question) throws Exception{
        List<String> processedQuestion = new ArrayList<>();
        for(String word : PreProcessor.preprocess(question)){
            processedQuestion.add(getEntityType(word));
        }
        System.out.println(processedQuestion);
        return hash(String.join(" ", processedQuestion));
    }


    private static int hash(String text){
        int hash = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            hash = hash * 31 + c;
        }
        return hash;
    }

    //TODO: THIS MUST BE SUBSTITUTED WITH A DATABASE or some form of entity recognition
    private static String getEntityType(String text){
        switch (text.toLowerCase()){
            case "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday" -> {
                return "day";
            }
            case "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"
                    -> {
                return "time";
            }
            case "kitchen" -> {
                return "room";
            }

            default -> {
                return text;
            }
        }
    }

    public static void main(String[] args) {
        String[] testCases = {
            "What lecture do we have on <DAY> at <TIME>?",
            "What lecture will we have on monday at 12?",
            "Which lecture do we have on Tuesday at 9?",
            "What lecture do I have on Friday at 18?",
            "What lecture do we have on <DAY> at <TIME> in <ROOM>?",
            "what lecture do we have on Monday at 12 in Kitchen?",
            "Will we be meeting at 9 in the lecture hall on Monday?"
        };
        for(String testCase : testCases){
            try {
                System.out.println(getIDFromQuestion(testCase));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}

package org.group1.back_end.response;

import org.group1.back_end.response.skills.ContextFreeGrammar;
import org.group1.back_end.response.skills.RasaAPI;
import org.group1.back_end.response.skills.Skill;
import org.group1.back_end.response.skills.SkillData;
import org.group1.back_end.textprocessing.ComplexProcess;
import org.group1.back_end.textprocessing.SimpleProcess;

import java.io.IOException;
import java.util.*;

import org.group1.back_end.textprocessing.SpellingCorrectProcess;
import org.group1.back_end.utilities.enums.DB;

public class Response {

    public ResponseLibrary responseLibrary;
    public RasaAPI rasaAPI;

    public static DB database = DB.DB_PERFECT_MATCHING;

    public Response() throws Exception{
        this.responseLibrary = new ResponseLibrary();
    }

    public String getResponse(String word){
       word = process(word);
        return responseLibrary.getResponseSkills(word);
    }

    public String getResponse(String word, boolean useMulti){
        this.rasaAPI = new RasaAPI("http://0.0.0.0:5005");
        String botOutput = "";
        try {
            botOutput = rasaAPI.sendMessageToRasa(word);
        }catch(IOException e){
            e.printStackTrace();
        }
        if(useMulti && !botOutput.contains("Final output")){
            return botOutput;
        }else{
            botOutput = botOutput.replace("Final output", "");
            word = process(botOutput);
            return responseLibrary.getResponseSkills(word);
        }
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

        String result = "";
        for (String w: words) {
            result += " " + w + " ";
        }

        return result.trim();
    }


    public DB getDatabase() {
        return database;
    }

    public void setDatabase(DB s) {
        database = s;
    }

    public List<SkillData> getSkillData(){
        return responseLibrary.getSkillData();
    }
    public List<String> getQuestion(){
        return responseLibrary.getQuestions();
    }
    public List<Set<String>> getSlots(){
        return responseLibrary.getSets();
    }
    //cfg related
    public ArrayList<ContextFreeGrammar> getAllCFGs() {return responseLibrary.getAllCFGs();}
    public ContextFreeGrammar getCurrentCFG(int index) {return responseLibrary.getCurrentCFG(index);}
    public List<String> getActionToSlots(ContextFreeGrammar contextFreeGrammar){return contextFreeGrammar.actionsToSlots;}
    public List<List<String[]>> getSlotsUsed(ContextFreeGrammar contextFreeGrammar){return contextFreeGrammar.slotsUsed;}
    public ArrayList<ContextFreeGrammar> getCFGData(){return responseLibrary.getAllCFGs();}

}

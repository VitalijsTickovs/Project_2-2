package org.group1.back_end.response;

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.group1.back_end.ML.model_vectorization.VectorSIF;
import org.group1.back_end.ML.model_vectorization.VectorWord2Vec;
import org.group1.back_end.response.skills.Skill;
import org.group1.back_end.response.skills.SkillData;

import java.io.IOException;
import java.util.*;

public class ResponseLibrary {

    public static Word2Vec WORD2VEC;
    public static VectorSIF VECTOR_SIF;


    static {
//        try {
//            WORD2VEC = new VectorWord2Vec().getWord2Vec();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        VECTOR_SIF = new VectorSIF();
        VECTOR_SIF.createFrequencyTable();
    }

    private Skill skills;

    public ResponseLibrary() throws Exception {
        this.skills = new Skill();
//        WORD2VEC = new VectorWord2Vec().getWord2Vec();
    }

    public void reload() throws Exception{
        this.skills = new Skill();
    }

    public String getResponseSkills(String words){
        String response = skills.getSkill(words, Response.database);
        return response;
    }


    public String getResponseChatBot(String words){
        return null;
    }


    public List<SkillData> getSkillData(){
        return skills.getSkillData();
    }
    public List<String> getQuestions(){
        return skills.getQuestions();
    }
    public List<Set<String>> getSets(){
        return skills.getSlots();
    }

    public static class Manager {
    }
}

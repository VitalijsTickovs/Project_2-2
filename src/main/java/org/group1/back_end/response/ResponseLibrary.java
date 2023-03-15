package org.group1.back_end.response;

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.group1.back_end.MAIN;
import org.group1.back_end.ML.model_vectorization.VectorSIF;
import org.group1.back_end.ML.model_vectorization.VectorWord2Vec;
import org.group1.back_end.response.skills.Skill;

import java.io.IOException;

public class ResponseLibrary {

    public static Word2Vec WORD2VEC;
    public static VectorSIF VECTOR_SIF;

    static {
        try {
            WORD2VEC = new VectorWord2Vec().getWord2Vec();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        VECTOR_SIF = new VectorSIF();
    }
//tupaksan fñclksdlknvckldsn´´k
    private final Skill skills;
    public ResponseLibrary() throws Exception {
        this.skills = new Skill();
        WORD2VEC = new VectorWord2Vec().getWord2Vec();
    }

    public String getResponseSkills(String words){
        return skills.getSkill(words, MAIN.DATABASE);
    }

    public String getResponseChatBot(String words){
        return null;
    }

}

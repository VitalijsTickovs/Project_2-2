package org.group1.response.skills;

import org.group1.processor.PreProcessor;

public class Question implements iDataStore{
    String question;

    public Question(String question) throws Exception{
        this.question = question;
        processQuestion();
    }


    public void setQuestion(String question) throws Exception{
        this.question = question;
        processQuestion();
    }

    public void processQuestion() throws Exception{
        this.question = PreProcessor.preprocess(this.question).toString();
    }

    public String getQuestion(){
        return question;
    }

    @Override
    public void add(String text) {

    }
}

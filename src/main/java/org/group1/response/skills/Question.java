package org.group1.response.skills;

import org.group1.processor.PreProcessor;

import java.util.List;

public class Question implements iDataStore{
    String question;
    List<String> placeHolders;

    public Question(String question, List<String> placeHolders) throws Exception{
        this.question = question;
        this.placeHolders = placeHolders;
        processQuestion();
        for(String placeHolder : PreProcessor.preprocess(placeHolders)) this.question = this.question.replaceAll(placeHolder, "");
    }


    public void setQuestion(String question) throws Exception{
        this.question = question;
        processQuestion();
    }

    public void processQuestion() throws Exception{
        this.question = String.join(" ", PreProcessor.preprocess(this.question));
    }

    public String getQuestion(){
        return question;
    }

    @Override
    public void add(String text) {

    }
}

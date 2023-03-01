package org.group1.response.skills;

public class Question implements iDataStore{
    String question;

    public Question(String question) {
        this.question = question;
    }


    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion(){
        return question;
    }

    @Override
    public void add(String text) {

    }
}

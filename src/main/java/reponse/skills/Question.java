package org.group1.reponse.skills;

public class Question implements iDataStore{

    String question;
    double questionID;

    public Question(String question) {
        this.question = question;
    }

    public Question(){

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

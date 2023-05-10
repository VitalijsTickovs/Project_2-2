package org.app.connection.backEnd.response;

import org.app.connection.backEnd.response.skill.Skill;
import org.app.connection.utils.enums.DataBase;

public class ResponseManager {

    private Skill skills;

    public ResponseManager(){
        skills = new Skill();
    }

    public String getResponse(String query){
        return skills.getResponse(query);
    }

    public void setDatabase(DataBase dataBase){
        skills.setDatabase(dataBase);
    }

    public Skill getSkills(){
        return skills;
    }

    public void setSkills(Skill skills){
        this.skills = skills;
    }
}

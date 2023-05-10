package org.app.connection.backEnd.response.skill;

import org.app.connection.backEnd.response.skill.skillData.SkillData;
import org.app.connection.utils.enums.DataBase;

public class Skill {

    public SkillData skills;

    public Skill(){
        this.skills = new SkillData();
    }

    public SkillData getSkillData() {
        return skills;
    }

    public void setSkillsData(SkillData skills) {
        this.skills = skills;
    }

    public String getResponse(String query){
        return getResponse(query);
    }

    public void setDatabase(DataBase dataBase){
        setDatabase(dataBase);
    }


}

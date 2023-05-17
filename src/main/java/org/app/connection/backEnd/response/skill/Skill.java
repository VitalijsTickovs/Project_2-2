package org.app.connection.backEnd.response.skill;

import org.app.connection.backEnd.response.skill.data_bases_manager.Data_Base_Manager;
import org.app.connection.backEnd.response.skill.skillData.SkillData;
import org.app.connection.backEnd.response.skill.skillProcessor.SkillProcessor;
import org.app.connection.utils.enums.DataBase;

import java.util.List;

public class Skill {

    public SkillData skills;
    private Data_Base_Manager DATABASE_MANGER;

    public Skill(){
        DATABASE_MANGER = new Data_Base_Manager();
        this.skills = new SkillData();
        List<String[]> data = process(skills);
        DATABASE_MANGER.put(data);

    }

    public void put(List<String[]> data){

        DATABASE_MANGER.put(data);
    }

    public List<String[]> process(SkillData skillData){
        SkillProcessor skillProcessor = new SkillProcessor(skillData);
        return skillProcessor.getUnifiedData();
    }

    public SkillData getSkillData() {
        return skills;
    }

    public void setSkillsData(SkillData skills) {
        this.skills = skills;
    }

    public String getResponse(String query){
        return DATABASE_MANGER.get(query);
    }

    public void setDatabase(DataBase dataBase){
        DATABASE_MANGER.setDataBase(dataBase);
    }


}

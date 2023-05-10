package org.app.connection.backEnd;

import org.app.connection.backEnd.response.ResponseManager;
import org.app.connection.backEnd.response.skill.skillData.SkillData;
import org.app.connection.utils.enums.DataBase;


public class BackEndManager {

    private DataBase currentDataBase;
    private ResponseManager responseManager;

    public BackEndManager(){
        responseManager = new ResponseManager();
        currentDataBase = DataBase.DB_PERFECT_MATCHING;
    }

    public String getResponse(String question){
        return question;
    }

    public void setDataBase(DataBase dataBase){
         responseManager.setDatabase(dataBase);
    }

    public DataBase getDataBase(){
        return currentDataBase;
    }
    
    public SkillData getSkillData(){
        return  responseManager.getSkills().getSkillData();
    }
    
    public void setSkillData(SkillData skillData){
        responseManager.getSkills();
    }
}

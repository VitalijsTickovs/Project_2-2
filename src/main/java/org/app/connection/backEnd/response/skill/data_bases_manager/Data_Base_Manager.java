package org.app.connection.backEnd.response.skill.data_bases_manager;

import org.app.connection.backEnd.response.skill.data_bases_manager.data_bases.Data_Base_Query;
import org.app.connection.utils.enums.DataBase;

import java.util.List;
import java.util.Map;

public class Data_Base_Manager {

    private DataBase currentDataBase;
    // Stores all possible questions
    private Data_Base_Query dataBase;

    public Data_Base_Manager(){
        dataBase = new Data_Base_Query();
        currentDataBase = DataBase.DB_QUERY;
    }

    public void put(List<String[]> data){dataBase.add(data);}

    public String get(String key){
        Map<String, String> database = dataBase.get(key);
        //return Retrievalblabla.getValue()
        return database.getOrDefault(key, "I have no idea");
    }

    public void setDataBase(DataBase dataBase){
        this.currentDataBase = dataBase;
    }

}

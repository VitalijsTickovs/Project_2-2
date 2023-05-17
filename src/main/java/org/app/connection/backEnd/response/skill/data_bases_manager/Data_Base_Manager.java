package org.app.connection.backEnd.response.skill.data_bases_manager;

import org.app.connection.backEnd.response.skill.data_bases_manager.data_bases.Data_Base_Query;
import org.app.connection.utils.enums.DataBase;

import java.util.List;

public class Data_Base_Manager {

    private DataBase currentDataBase;
    private Data_Base_Query dataBaseQuery;

    public Data_Base_Manager(){
        dataBaseQuery = new Data_Base_Query();
        currentDataBase = DataBase.DB_QUERY;
    }

    public void put(List<String[]> data){
        dataBaseQuery.add(data);
    }

    public String get(String key){
        return dataBaseQuery.get(key);
    }

    public void setDataBase(DataBase dataBase){
        this.currentDataBase = dataBase;
    }
}

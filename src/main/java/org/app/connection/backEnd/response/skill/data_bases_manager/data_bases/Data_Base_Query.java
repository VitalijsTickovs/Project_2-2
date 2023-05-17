package org.app.connection.backEnd.response.skill.data_bases_manager.data_bases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data_Base_Query {

    Map<String, String> DATA_BASE;

    public Data_Base_Query() {
        DATA_BASE = new HashMap<>();
    }

    public void add(List<String[]> data){
        data.forEach(keyValue -> DATA_BASE.put(keyValue[0], keyValue[1]));
    }

    public String get(String key){
        return DATA_BASE.get(key);
    }
}

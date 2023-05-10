package org.app.connection.backEnd.response.skill.skillProcessor.format;

import java.util.*;

public class FormatQuery {

    private List<String[]> data;

    public FormatQuery(List<String> data){
        data = new ArrayList<>();
        process(data);
    }

    public void process(List<String> data){

    }



    public List<String[]> getData(){
        return data;
    }
}

package org.app.connection.backEnd.response.skill.skillProcessor.process;

import java.util.ArrayList;
import java.util.List;

public class FormatTemplate {
    private List<String[]> data;

    public FormatTemplate(List<String> data){
        data = new ArrayList<>();
        process(data);
    }

    public void process(List<String> data){

    }



    public List<String[]> getData(){
        return data;
    }
}

package org.app.connection.backEnd.response.skill.skillProcessor.process;

import java.util.*;

public class FormatQuery {
    private List<String[]> data;
    public FormatQuery(List<String> data){
        this.data = new ArrayList<>();
        process(data);
    }
    public void process(List<String> lines){
        for (String line : lines) {
            data.add(line.split("--->"));
        }
    }
    public List<String[]> getData(){
        return data;
    }
}

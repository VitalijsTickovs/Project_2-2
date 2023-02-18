package org.group1.reponse.skills;

import java.util.ArrayList;
import java.util.List;

public class Action implements iDataStore{

    List<String> action = new ArrayList<>();

    public Action() {}

    @Override
    public void add(String text) {
        action.add(text);
    }

    public String toString(){
        String toret = "";
        for(String text : action){
            toret += " "+text;
        }
        return toret;
    }
}

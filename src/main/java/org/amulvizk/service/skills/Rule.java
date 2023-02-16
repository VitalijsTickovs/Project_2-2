package org.amulvizk.service.skills;

import java.util.List;

public class Rule {

    public List<String> pairs;
    public Action action;

    public Rule(List<String> pairs, Action action) {
        this.pairs = pairs;
        this.action = action;
    }

    public Rule(){}

//    public boolean matches(String[] pairs){
//        //TODO: add typo support
//        for(int i=0; i< pairs.length;i++){
//            if(!pairs[i].equals(this.pairs.get(i))){
//              return false;
//            }
//        }
//        return true;
//    }
    }

package org.amulvizk.service.skills;

import java.util.ArrayList;
import java.util.List;


public class Slot implements iDataStore{

    List<String[]> slots;

    public Slot() {
        slots = new ArrayList<>();
    }

    @Override
    public void add(String text) {

    }

    public void add(String[] lines){
        this.slots.add(lines);
    }

    public void setCols(int cols){

    }

    public void setRows(int rows){

    }

}

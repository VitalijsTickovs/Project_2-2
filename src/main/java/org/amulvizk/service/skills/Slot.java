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

    public List<String> getSlot(){
        return slots;
    }

    public String getSlotString(){
        return String.join(" ", this.slots.toArray(new String[0]));
    }

    public void setCols(int cols){

    }

    public void setRows(int rows){

    }

}

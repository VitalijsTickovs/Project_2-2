package org.group1.response.database;

import java.util.Arrays;

public class Slots{
    private String slotType;
    private String slotValue;

    public Slots(String[] slots){
        this.slotType = slots[1].replace("<","").replace(">","");
        this.slotValue = slots[0];
    }

    public String getSlotValue() {
        return slotValue;
    }

    public String getSlotType() {
        return slotType;
    }
    @Override
    public int hashCode(){
        return slotType.hashCode()+slotValue.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Slots)) return false;
        Slots other = (Slots) obj;
        return slotValue.equals(other.getSlotValue());
    }

    @Override
    public String toString() {
        return "Slot Type: "+ slotType + "\nSlot Value: " + slotValue;
    }
}

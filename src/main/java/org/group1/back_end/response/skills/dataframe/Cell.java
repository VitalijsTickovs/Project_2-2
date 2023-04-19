package org.group1.back_end.response.skills.dataframe;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * One box/cell in the dataframe
 * @param <E> java generic
 */
public class Cell<E> {

    E value;

    public Cell(E value) {
        this.setValue(value);
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        if(value == "" || value == " "){
            this.value = null;
        }else{
            this.value = value;
        }
    }

    public String toString() {
        if(value == null) return " ";
        return value.toString();
    }

    public String getType(){
        return value.getClass().getSimpleName();
    }

    public boolean equals(Cell<?> c) {
        if (this.value instanceof Set && c.getValue() instanceof Set) {
            Set<?> thisSet = (Set<?>) this.value;
            Set<?> otherSet = (Set<?>) c.getValue();
            return thisSet.equals(otherSet);
        } else if (this.value instanceof List && c.getValue() instanceof List) {
            List<?> thisList = (List<?>) this.value;
            List<?> otherList = (List<?>) c.getValue();
            return thisList.equals(otherList);
        } else {
            return this.value.equals(c.getValue());
        }
    }
}

package org.group1.back_end.response.skills.dataframe;

/**
 * One box/cell in the dataframe
 * @param <E> java generic
 */
public class Cell<E> {

    E value;

    public Cell(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public String toString() {
        return value.toString();
    }

    public String getType(){
        return value.getClass().getSimpleName();
    }
}

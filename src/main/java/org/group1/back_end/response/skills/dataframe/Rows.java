package org.group1.back_end.response.skills.dataframe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Any legal number of cells, therefore, represent a record
 */
public class Rows {


    private List<Cell> cells;

    public Rows(List<Cell> cells) {
        this.cells = new ArrayList<>(cells);
    }

    public Rows() {
        this(new ArrayList<>());
    }

    public List<Cell> getCells() {
        return cells;
    }


    public int size(){
        return this.cells.size();
    }

    public boolean equals(Rows r){
        for(Cell l : r.getCells()) {
            if (!this.contains(l)) return false;
        }
        return true;
    }

    public Cell get(int index){
        return this.cells.get(index);
    }

    public boolean contains(Cell c){
        for(Cell cell : cells){
            if(cell.equals(c)) return true;
        }
        return false;
    }

    public boolean isNull() {
        for (Cell cell : cells) {
            if (cell.getValue() != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return cells.stream()
                .map(cell -> {
                    Object value = cell.getValue();
                    return value != null ? value.toString() : "";
                })
                .collect(Collectors.joining(", "));
    }

}

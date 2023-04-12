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
        List<Cell> k = r.getCells();
        for(Cell l : r.getCells()) if(!this.contains(l)) return false;
        return true;
    }

    public Cell get(int index){
        return this.cells.get(index);
    }

    public boolean contains(Cell c){
        for(Cell cell : cells){
            if(cell.getValue() == c.getValue()) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return cells.stream()
                .map(cell -> cell.getValue().toString())
                .collect(Collectors.joining(", "));
    }

}

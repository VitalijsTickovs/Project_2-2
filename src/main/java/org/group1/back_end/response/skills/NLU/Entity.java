package org.group1.back_end.response.skills.NLU;

public class Entity {
    private int start;
    private int end;
    private String value;
    private String entity;

    public Entity(int start, int end, String value, String entity) {
        this.start = start;
        this.end = end;
        this.value = value;
        this.entity = entity;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
}

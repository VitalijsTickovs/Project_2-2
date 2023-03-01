package org.group1.collections;

public enum CV {
    C(false),
    V(true);

    private final boolean value;

    CV(boolean a){
        this.value = a;
    }

    public boolean getValue() {
        return value;
    }
}

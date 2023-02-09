package org.group1.collections;

public enum Delim {
    SPACE(" "),
    COMA(","),
    DOT("."),
    QUESTION("?"),
    EXCLAMATION("!"),
    DOUBLE_DOT(":");

    public final String delim;
    Delim(String delim){
        this.delim = delim;
    }
}
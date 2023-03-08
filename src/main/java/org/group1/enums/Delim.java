package org.group1.enums;

public enum Delim {
    SPACE(" "),
    COMA(","),
    DOT("."),
    QUESTION("?"),
    EXCLAMATION("!"),
    TAB("\t"),
    DOUBLE_DOT(":");

    public final String delim;

    Delim(String delim){
        this.delim = delim;
    }
}
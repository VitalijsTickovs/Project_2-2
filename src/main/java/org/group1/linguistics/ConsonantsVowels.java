package org.group1.linguistics;

/**
 * This class describes the consonants and vocals,
 * these are phonological.
 * It is utilized for Porter Algorithm.
 */
public enum ConsonantsVowels {
    C(false),
    V(true);

    private final boolean value;

    ConsonantsVowels(boolean a){
        this.value = a;
    }

    public boolean getValue() {
        return value;
    }
}

package org.group1.reponse.procesor.helpers;

public class StemConstants {

    public static final String[] STEP2_I = {
            "ational", "tional", "enci", "anci", "izer", "abli",
            "alli", "entli", "eli", "ousli",
            "ization", "ation", "ator", "alism", "iveness",
            "fulness", "ousness", "ality", "ivity", "bility"
    };

    public static final String[] STEP2_O = {
            "ate", "tion", "ence", "ance", "ize", "able",
            "al", "ent", "e", "ous", "ize", "ate", "ate",
            "al", "ive", "ful", "ous", "al", "ive", "ble"
    };

    public static final String[] STEP3_I = {
            "icative", "ative", "alize", "iciti", "ical", "ful", "ness"
    };

    public static final String[] STEP3_O = {
            "ic", "", "al", "ic", "ic", "ic", ""
    };

    public static final String[] STEP4_I = {
            "al", "ance", "ence", "er", "ic", "able", "ible",
            "ant", "ement", "ment", "ent", "tion", "sion",
            "ou", "ism", "ate", "iti", "ous", "ive", "ize"
    };

    public static final String STEP4_O = "";

    public static final String STEP5_I = "e";

    public static final String STEP5_O = "";
}

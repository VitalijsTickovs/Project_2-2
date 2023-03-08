package org.group1.enums;

public enum Paths {

    DATA_TXT_SKILLS("/src/main/resources/skills/"),
    DATA_TXT_STOP_WORDS("/src/main/resources/words_datasets/StopWords.txt"),
    DATA_TXT_DICCTIONARY("/src/main/resources/words_datasets/Words.txt"),
    DATA_TXT_SYNONYMS("/src/main/resources/words_datasets/Synonyms.txt"),
    DATA_TXT_LEMMAS("/src/main/resources/words_datasets/Lemmas.txt"),
    DATA_BIN_STOP_WORDS("/src/main/resources/words_datasets/StopWords.txt"),
    DATA_BIN_DICCTIONARY("/src/main/resources/words_datasets/Words.txt"),
    DATA_BIN_SYNONYMS("/src/main/resources/words_datasets/Synonyms.txt"),
    DATA_BIN_LEMMAS("/src/main/resources/words_datasets/Lemmas.txt");

    public final String path;

    Paths(String path){this.path = path;}

}

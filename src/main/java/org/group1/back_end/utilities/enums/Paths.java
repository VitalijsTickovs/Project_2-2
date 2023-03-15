package org.group1.back_end.utilities.enums;

public enum Paths {

    DATA_TXT_SKILLS("/src/main/resources/skills/"),
    DATA_DATABASE("/src/main/java/org/group1/response/data/dataBase.txt"),
    DATA_TXT_STOP_WORDS("/src/main/resources/words_datasets/StopWords.txt"),
    DATA_TXT_DICCTIONARY("/src/main/resources/words_datasets/Words.txt"),
    DATA_TXT_SYNONYMS("/src/main/resources/words_datasets/Synonyms.txt"),
    DATA_TXT_LEMMAS("/src/main/resources/words_datasets/Lemmas.txt"),
    DATA_BIN_STOP_WORDS("/src/main/resources/words_datasets/StopWords.txt"),
    DATA_BIN_DICCTIONARY("/src/main/resources/words_datasets/Words.txt"),
    DATA_BIN_SYNONYMS("/src/main/resources/words_datasets/Synonyms.txt"),
    DATA_BIN_LEMMAS("/src/main/resources/words_datasets/Lemmas.txt"),
    MODEL_BIN("/src/main/resources/models/bin/"),
    MODEL_BIN_WORD2VEC("/src/main/resources/models/bin/Word2Vec.bin");

    public final String path;

    Paths(String path){this.path = path;}

}

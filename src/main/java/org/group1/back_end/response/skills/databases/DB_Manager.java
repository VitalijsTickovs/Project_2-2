package org.group1.back_end.response.skills.databases;

import org.group1.back_end.utilities.enums.DB;

public class DB_Manager{

    DB_Keywords keywords;
    DB_PerfectMatching perfectMatch;
    DB_Vectors vectors;
    DB_VectorsSequence vectorsSeq;
    public DB_Manager() {
        this.keywords = new DB_Keywords();
        this.perfectMatch = new DB_PerfectMatching();
        this.vectors = new DB_Vectors();
        this.vectorsSeq = new DB_VectorsSequence();
    }

    public void add(String key, String value){
        keywords.add(key, value);
        perfectMatch.add(key, value);
        vectors.add(key, value);
        vectorsSeq.add(key, value);
    }

    public void addCFG(String key, String value){
        keywords.addCFG(key, value);
        perfectMatch.addCFG(key, value);
        vectors.addCFG(key, value);
        vectorsSeq.addCFG(key, value);
    }
    public String add(String key, String value, DB dataBase) {

        switch (dataBase){
            case DB_PERFECT_MATCHING -> perfectMatch.add(key, value);
            case DB_KEYWORDS -> keywords.add(key, value);
            case DB_VECTORS -> vectors.add(key, value);
            case DB_VECTORS_SEQ -> vectorsSeq.add(key, value);
        }
        return value;
    }

    public String getFirst(String key, DB dataBase) {
        String value = "";

        switch (dataBase){
            case DB_PERFECT_MATCHING -> value = perfectMatch.getFirst(key);
            case DB_KEYWORDS -> value = keywords.getFirst(key);
            case DB_VECTORS -> value = vectors.getFirst(key);
            case DB_VECTORS_SEQ -> value = vectorsSeq.getFirst(key);
        }
        return value;
    }

    public void printKeys(DB ... dataBases) {
        for (int i = 0; i < dataBases.length; i++) {
            switch (dataBases[i]){
                case DB_PERFECT_MATCHING -> perfectMatch.printKeys();
                case DB_KEYWORDS ->  keywords.printKeys();
                case DB_VECTORS ->  vectors.printKeys();
                case DB_VECTORS_SEQ -> vectorsSeq.printKeys();
            }
        }
    }
}

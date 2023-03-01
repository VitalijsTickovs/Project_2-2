//package org.group1.textprocessing;
//
//import java.io.*;
//import java.util.*;
//
//
//public class Lemmatization {
//
//    private static HashMap<String, String> DICTIONARY = new HashMap<>();
//
//    private final static String DATA = "/src/main/java/org/group1/reponse/procesor/helpers/dictionaries/lemmatization-en.txt";
//    private Lemmatization(){}
//
//
//    public static String getLemma(String word) throws IOException {
//
//        createDictionary();
//        String lemma = DICTIONARY.get(word);
//        //if(lemma == null){return Stemming.Steam(word);}
//        return lemma;
//    }
//
//    private static void createDictionary() throws IOException {
//        if (DICTIONARY.size() == 0) {
//            DICTIONARY = Readers.lemma_txt(new File(".").getCanonicalPath() + DATA);
//            System.out.println("Loading english lemmas dictionary...");
//        }
//    }
//
//
//    public static void main(String[] args) throws IOException {
//        getLemma("evaluation");
//    }
//}
//
//

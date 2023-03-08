package org.group1.textprocessing;

import org.group1.response.*;
import org.group1.utilities.*;
import java.io.*;
import java.util.*;

public class ComplexProcess {

    private static Set<String> STOP_WORDS;
    private static Set<String> DICCTIONARY;
    private static Map<String, String> SYNONYMS;
    private static Map<String, String> LEMMAS;
    private static Map<String, String> POS;


    private static final String PATH_STOP_WORDS;
    private static final String PATH_DICCTIONARY;
    private static final String PATH_SYNONYMS;
    private static final String PATH_LEMMAS;
    private static final String PATH_POS;


    private static String CANONICAL_PATH;

    public static Set<String> process(List<String> tokens){
        List<String> temp = new ArrayList<>(tokens);
        temp = removeStopWords(temp);
        //temp = checkSynonyms(temp);
        temp = checkVocabulary(temp);
        temp = lemmas(temp);
        //temp = checkOrthography(temp);


        return  new HashSet<>(temp);
    }

    public static List<String> checkVocabulary(List<String> tokens){

        List<String> temp = new LinkedList<>();

        for (String t : tokens) {

            if(!Skill.VOCABULARY.contains(t)) {
                int threshold = Distances.getSimilarDistanceFromSet(t, Skill.VOCABULARY);

                if (threshold < 2) {
                    t = Distances.getSimilarFromSet(t, Skill.VOCABULARY);
                }
            }

            temp.add(t);
        }

        return temp;
    }

    public static List<String> removeStopWords(List<String> tokens){
        List<String> temp = new LinkedList<>();
        for (String t : tokens) {
            if(!STOP_WORDS.contains(t)) temp.add(t);
        }
        return temp;
    }

    public static List<String> checkOrthography(List<String> tokens){
        List<String> temp = new LinkedList<>();

        for (String t : tokens) {

            if(!(DICCTIONARY.contains(t) || DICCTIONARY.contains(LEMMAS.get(t)))){

                String tempWord = "";
                int minDistance = Integer.MAX_VALUE;

                for (String s : DICCTIONARY) {
                    int distance = Distances.levenshtein(t, s);

                    if (distance < minDistance) {
                        tempWord = s;
                        minDistance = distance;
                    }
                }
                t = tempWord;
            }
            temp.add(t);
        }
        return temp;
    }

    public static List<String> checkSynonyms(List<String> tokens){
        List<String> temp = new LinkedList<>();

        for (String t : tokens) {

            if(!(SYNONYMS.get(t) == null)){
                temp.add(SYNONYMS.get(t));
            }else{
                temp.add(t);
            }
        }
        return temp;
    }

    public static List<String> lemmas(List<String> tokens){
        List<String> temp = new LinkedList<>();

        for (String t : tokens) {

            if(!(LEMMAS.get(t) == null)){
                temp.add(LEMMAS.get(t));
            }else{
                temp.add(t);
            }
        }
        return temp;
    }

    public static List<String> POS(List<String> tokens){
        List<String> temp = new LinkedList<>();

        for (String t : tokens) {

            if(!(POS.get(t) == null)){
                temp.add(POS.get(t));
            }else{
                temp.add(t);
            }
        }
        return temp;
    }

    static {

        // HERE I LOAD THE PATH SO THAT ALL COMPUTERS CAN WORK WITH OUT ERRORS
        try {
            CANONICAL_PATH = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PATH_STOP_WORDS = CANONICAL_PATH + "/src/main/resources/binary/StopWords.bin";
        PATH_DICCTIONARY = CANONICAL_PATH + "/src/main/resources/binary/Words.bin";
        PATH_SYNONYMS = CANONICAL_PATH + "/src/main/resources/binary/Synonyms.bin";
        PATH_LEMMAS = CANONICAL_PATH + "/src/main/resources/binary/Lemmas.bin";
        PATH_POS = CANONICAL_PATH + "/src/main/resources/binary/POS.bin";


        // HERE I LOAD THE BINARY FILE STOP WORDS
        STOP_WORDS = null;
        try (FileInputStream fis = new FileInputStream(PATH_STOP_WORDS);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            STOP_WORDS = (Set<String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        // HERE I LOAD THE BINARY FILE DICCTIONARY
        DICCTIONARY = null;
        try (FileInputStream fis = new FileInputStream(PATH_DICCTIONARY);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            DICCTIONARY = (Set<String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        // HERE I LOAD THE BINARY FILE LEMMAS
        LEMMAS = null;
        try (FileInputStream fis = new FileInputStream(PATH_LEMMAS);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            LEMMAS = (HashMap<String, String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // HERE I LOAD THE BINARY FILE POS
        POS = null;
        try (FileInputStream fis = new FileInputStream(PATH_POS);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            POS = (HashMap<String, String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        // HERE I LOAD THE BINARY FILE SYNONYMS
        SYNONYMS = null;
        try (FileInputStream fis = new FileInputStream(PATH_SYNONYMS);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            SYNONYMS = (HashMap<String, String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

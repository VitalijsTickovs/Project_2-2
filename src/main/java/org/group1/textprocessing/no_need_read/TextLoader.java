package org.group1.textprocessing.no_need_read;

import java.io.*;
import java.util.*;

public class TextLoader {

    private static Set<String> STOP_WORDS;
    private static Set<String> DICCTIONARY;
    private static Map<String, String> SYNONYMS;
    private static Map<String, String> LEMMAS;
    private static Map<String, String> POS;

    private static String CANONICAL_PATH;

    static {
        try {
            CANONICAL_PATH = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static final String PATH_STOP_WORDS = CANONICAL_PATH + "/src/main/resources/words_datasets/StopWords.txt";
    private static final String PATH_DICCTIONARY = CANONICAL_PATH + "/src/main/resources/words_datasets/Words.txt";
    private static final String PATH_SYNONYMS = CANONICAL_PATH + "/src/main/resources/words_datasets/Synonyms.txt";
    private static final String PATH_LEMMAS = CANONICAL_PATH + "/src/main/resources/words_datasets/Lemmas.txt";
    private static final String PATH_POS = CANONICAL_PATH + "/src/main/resources/words_datasets/POS.txt";


    private static final String oPATH_STOP_WORDS = CANONICAL_PATH + "/src/main/resources/binary/StopWords.bin";
    private static final String oPATH_DICCTIONARY = CANONICAL_PATH + "/src/main/resources/binary/Words.bin";
    private static final String oPATH_SYNONYMS = CANONICAL_PATH + "/src/main/resources/binary/Synonyms.bin";
    private static final String oPATH_LEMMAS = CANONICAL_PATH + "/src/main/resources/binary/Lemmas.bin";
    private static final String oPATH_POS = CANONICAL_PATH + "/src/main/resources/binary/POS.bin";


    public static void main(String[] args) throws FileNotFoundException {
        importStopWords();
        importDicctionary();
        importLemmas();
        importPOS();
        System.out.println(LEMMAS.get("gone"));
        System.out.println(LEMMAS.get("went"));
        System.out.println(LEMMAS.get("interested"));
        System.out.println(LEMMAS.get("interesting"));
        importSynonyms();
        System.out.println(POS.get("gone"));
        System.out.println(POS.get("went"));
        System.out.println(POS.get("interested"));
        System.out.println(POS.get("interesting"));



    }


    public static void importStopWords() throws FileNotFoundException {

        StringBuilder content = new StringBuilder();

        Scanner sc = new Scanner(new File(PATH_STOP_WORDS));
        while (sc.hasNextLine()) content.append(sc.nextLine()).append("\n");
        List<String> lines = Arrays
                .stream(content.toString().toLowerCase().split("\n"))
                .toList();
        STOP_WORDS = new HashSet<>(lines);

        try (FileOutputStream fos = new FileOutputStream(oPATH_STOP_WORDS);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(STOP_WORDS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void importDicctionary() throws FileNotFoundException {

        StringBuilder content = new StringBuilder();
        Scanner sc = new Scanner(new File(PATH_DICCTIONARY));
        while (sc.hasNextLine()) content.append(sc.nextLine()).append("\n");
        List<String> lines = Arrays
                .stream(content.toString().toLowerCase().split("\n"))
                .toList();
        DICCTIONARY = new HashSet<>(lines);

        try (FileOutputStream fos = new FileOutputStream(oPATH_DICCTIONARY);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(DICCTIONARY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void importPOS() throws FileNotFoundException {

        StringBuilder content = new StringBuilder();
        Scanner sc = new Scanner(new File(PATH_POS));
        while (sc.hasNextLine()) content.append(sc.nextLine()).append("\n");
        List<String> lines = Arrays
                .stream(content.toString().toLowerCase().split("\n"))
                .toList();
        POS = new HashMap<>();

        for (String s : lines) {
            String[] split = s.split("\t");
            POS.put(split[0].toLowerCase(), split[1].toUpperCase());
        }

        try (FileOutputStream fos = new FileOutputStream(oPATH_POS);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(POS);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void importLemmas() throws FileNotFoundException {

        StringBuilder content = new StringBuilder();
        Scanner sc = new Scanner(new File(PATH_LEMMAS));
        while (sc.hasNextLine()) content.append(sc.nextLine()).append("\n");
        List<String> lines = Arrays
                .stream(content.toString().toLowerCase().split("\n"))
                .toList();
        LEMMAS = new HashMap<>();

        for (String s : lines) {
            String[] split = s.split("\t");
            LEMMAS.put(split[1].toLowerCase(), split[0].toLowerCase());
        }

        try (FileOutputStream fos = new FileOutputStream(oPATH_LEMMAS);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(LEMMAS);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void importSynonyms() throws FileNotFoundException {
        StringBuilder content = new StringBuilder();
        Scanner sc = new Scanner(new File(PATH_SYNONYMS));
        while (sc.hasNextLine()) content.append(sc.nextLine()).append("\n");
        List<String> lines = Arrays
                .stream(content.toString().toLowerCase().split("\n"))
                .toList();
        SYNONYMS = new HashMap<>();

        for (String s : lines) {
            String[] split = s.split("\t");
            SYNONYMS.put(split[1].toLowerCase(), split[0].toLowerCase());
        }

        try (FileOutputStream fos = new FileOutputStream(oPATH_SYNONYMS);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(SYNONYMS);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<String> tokenize(String text) {


        StringTokenizer tokens = new StringTokenizer(text, " ");
        List<String> list = new ArrayList<>();

        while (tokens.hasMoreTokens()){
            list.add(tokens.nextToken());
        }
        return list;
    }

}

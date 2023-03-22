package org.group1.response;



import org.group1.response.database.SQLtoTxt;
import org.group1.textprocessing.*;
import org.group1.utilities.Distances;

import java.io.*;
import java.util.*;

import static java.lang.Math.abs;


public class Skill {

    private static final double THRESHOLD = 0.6;
    private static final double SIMILAR_QUERY = 0.1;
    private FileService service;
    protected static  Map<Set<String>, String> DATAFRAME = new HashMap<>();
    protected static  Map<String, String> INVERT = new HashMap<>();
    public static Set<String> VOCABULARY = new HashSet<>();


    /**
     * Build a file service and list for skills.
     * This also generates the skills.
     * @throws IOException
     */
    public Skill() throws Exception {
        service = new FileService();
        generateSkills();
    }

    /**
     * Method that actually generates from skills.
     * Reads all files from directory and creates skills.
     * @throws IOException
     */
    public void generateSkills() throws Exception {
        //
        List<String[]> texts = service.readAll();

        for (String[] text : texts){
            SkillProcessor sp = new SkillProcessor(text[0], text[1]);
            List<String> questions = sp.getQuestions();
            List<String> actions = sp.getActions();
            String deafault = sp.getDeafault();

            Set<String> deafaultKey = ComplexProcess
                    .process(
                            SimpleProcess
                                    .process(sp
                                            .getDeafaultKey()));

           DATAFRAME.put(deafaultKey, deafault);

           for (int i = 0; i < questions.size(); i++) {

               INVERT.put(actions.get(i), questions.get(i)); //TODO  not consistent
               Set<String> nonProcessKey = new HashSet<>(ComplexProcess
                       .lemmas(
                               SimpleProcess
                                       .process(questions
                                               .get(i))));

               DATAFRAME.put(nonProcessKey, actions.get(i));
               //INVERT.put(actions.get(i), );

               Set<String> key = ComplexProcess
                       .process(
                               SimpleProcess
                                       .process(questions
                                               .get(i)));
               DATAFRAME.put(key, actions.get(i));
               addVocabulary(questions.get(i));
           }
        }
    }

    public void addVocabulary(String words){
        List<String> vocabulary = SimpleProcess.processForVocabulary(words);
        vocabulary = ComplexProcess.lemmas(vocabulary);
        VOCABULARY.addAll(vocabulary);
    }


    public boolean shouldI(Set<String> key){
        Map<Set<String>, String> copy = new HashMap<>(DATAFRAME);
        Set<String> a = findClosestKey(copy, key);
        double da = Distances.jaccard(a, key);
        copy.remove(a);
        Set<String> b = findClosestKey(copy, key);

        double db = Distances.jaccard(b, key);

        for (String st : key){

            b = findClosestKey(copy, key);
            copy.remove(b);
            db = Distances.jaccard(b, key);

            System.out.println(abs((da - db)));
        }
        System.out.println(abs((da - db)));
        return abs((da - db)) < SIMILAR_QUERY;
    }

    public String notFailingBot(Set<String> key){
        String mean = "If you mean ";
        String then = " then ";
        String els = " else if you mean ";
        Map<Set<String>, String> copy = new HashMap<>(DATAFRAME);
        Set<String> a = findClosestKey(copy, key);
        String mean1 = INVERT.get(DATAFRAME.get(a));
        String ans1 = DATAFRAME.get(a);
        copy.remove(a);
        Set<String> b = findClosestKey(copy, key);
        String mean2 = INVERT.get(DATAFRAME.get(b));
        String ans2 = DATAFRAME.get(b);

        return mean + mean1 + then + ans1 + els + mean2 + then + ans2;
    }

    public String getSkill(String text){
        Set<String> key = ComplexProcess
                .process(
                        SimpleProcess
                                .process(text));

        if(!(DATAFRAME.get(key) == null)){
            return DATAFRAME.getOrDefault(key, "I have no idea");
        }else{

            shouldI(key);

            // two keys are very close
            /*
            if(shouldI(key)){
                return notFailingBot(key);
            }

             */

            // should i use the nonfail bot?

            Set<String> newKey  = findClosestKey(DATAFRAME, key);
            double distance = Distances.jaccard(newKey, key);
            if(distance < THRESHOLD){
                return DATAFRAME.get(newKey);
            }
            return DATAFRAME.getOrDefault(key, "I have no idea");
        }
    }


    private static Set<String> findClosestKey(Map<Set<String>, String> map, Set<String> targetKey) {
        double closestSimilarity = 0;

        Set<String> closestKey = null;

        for (Map.Entry<Set<String>, String> entry : map.entrySet()) {
            Set<String> key = entry.getKey();

            double similarity = Distances.jaccard(key, targetKey);

            if (similarity > closestSimilarity) {
                closestSimilarity = similarity;
                closestKey = key;
            }
        }
        return closestKey;
    }

    private static Set<String> findClosestKeyDemerau(Map<Set<String>, String> map, Set<String> targetKey) {
        double closestSimilarity = 0;

        Set<String> closestKey = null;

        for (Map.Entry<Set<String>, String> entry : map.entrySet()) {
            Set<String> key = entry.getKey();

            double similarity = Distances.jaccard(key, targetKey);

            if (similarity > closestSimilarity) {
                closestSimilarity = similarity;
                closestKey = key;
            }
        }
        return closestKey;
    }

    public static void printKeys(){
        Set<Set<String>> keys = DATAFRAME.keySet();
        System.out.println("\n----------- KEYS -----------");
        for (Set<String> key : keys) {
            System.out.println(key + ": " + DATAFRAME.get(key));
        }
        System.out.println("-----------------------------------\n");
    }

    public static void printVocabulary(){
        System.out.println("\n----------- VOCABULARY -----------");
        for (String w : VOCABULARY){
            System.out.println(w);
        }
        System.out.println("-----------------------------------\n");
    }




    public static void main(String[] args) throws Exception {
        String sql = "Hello";
        sql = sql.substring(0,sql.length()-1);
        System.out.println(sql);
        Skill s = new Skill();




        System.out.println(s.getSkill("Wich lcturs are here on Monday t 9"));
        System.out.println(s.getSkill("Which lectures MonFay at xiv"));
        System.out.println(s.getSkill("have klass monay 9?"));
        System.out.println(s.getSkill("i have lektures on monday 9"));
        System.out.println(s.getSkill("WhiCh lecture Monay at nine"));
        System.out.println(s.getSkill("do i have lectures on saturday"));

        printKeys();
        //printVocabulary();
    }

}

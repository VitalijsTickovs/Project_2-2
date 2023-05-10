package org.group1.back_end.response.skills;

import org.apache.ibatis.jdbc.SQL;
import org.group1.back_end.response.Response;
import org.group1.back_end.response.ResponseLibrary;
import org.group1.back_end.response.skills.CFG.ContextFreeGrammar;
import org.group1.back_end.response.skills.databases.DB_Manager;
import org.group1.back_end.response.skills.dataframe.DataFrame;
import org.group1.back_end.textprocessing.SimpleProcess;
import org.group1.back_end.utilities.enums.DB;

import java.io.*;
import java.util.*;

import static org.group1.back_end.textprocessing.ComplexProcess.lemmas;


public class Skill {

    private final SkillFileService SERVICE;
    public static Set<String> VOCABULARY = new HashSet<>();
    public static List<List<String>> COMPLETE_QUERY = new LinkedList<>();
    public static DB_Manager DATABASE_MANAGER;
    public List<SkillData> skillDatas;
    public List<String> questions;
    public List<Set<String>> slots;

    private String cfg_rule = "Rule <S> <EXPR>" +
            "Rule <EXPR> <TERM> | <EXPR> <ADD_OP> <TERM>\n" +
            "Rule <TERM> <FACTOR> | <TERM> <MUL_OP> <FACTOR>\n" +
            "Rule <FACTOR> <NUMBER> | <LPAREN> <EXPR> <RPAREN>\n" +
            "Rule <ADD_OP> + | -\n" +
            "Rule <MUL_OP> * | /\n" +
            "Rule <LPAREN> (\n" +
            "Rule <RPAREN> )\n" +
            "Rule <NUMBER> <DIGIT> | <NUMBER> <DIGIT>\n" +
            "Rule <DIGIT> 0 | 1 | 2 \n" +
            "Action <EXPR> * <TERM> <ADD_OP> <TERM> eval_add_sub\n" +
            "Action <TERM> * <FACTOR> <MUL_OP> <FACTOR> eval_mul_div\n" +
            "Action <FACTOR> * <LPAREN> <EXPR> <RPAREN> eval_parentheses\n" +
            "Action <NUMBER> * <DIGIT> append_digit\n" +
            "Action <NUMBER> * <NUMBER> <DIGIT> append_digit" +
            "Action I have no idea";
    private ContextFreeGrammar cfg = new ContextFreeGrammar(cfg_rule);

    public DataFrame skill;

    /**
     * Build a file SERVICE and list for skills.
     * This also generates the skills.
     * @throws IOException
     */
    public Skill() throws Exception {
        DATABASE_MANAGER = new DB_Manager();
        SERVICE = new SkillFileService();
        skillDatas = new ArrayList<>();
        questions= new ArrayList<>();
        slots= new ArrayList<>();
        generateSkills();
    }

    /**
     * Method that actually generates from skills.
     * Reads all files from directory and creates skills.
     * @throws IOException
     */
    public void generateSkills() throws Exception {

        List<String> texts = SERVICE.readAll();
        List<String[]> pairSet = new ArrayList<>();
        for (String text : texts){
            System.out.println("=====================================");
            System.out.println("Skill: \n" + text + "\n");
            SkillGenerator sp = new SkillGenerator(text);

            List<String> questions = sp.getQuestions();
            List<String> actions = sp.getActions();


            addVocabulary(sp.getDeafaultKey());
            String[] pairs = new String[2];
            pairs[0] = sp.getDeafaultKey();
            pairs[1] = sp.getDeafault();
            pairSet.add(pairs);
            List<String> combinations = sp.combinations;
            for (String a : combinations) {
                addVocabulary(a);
            }

            ResponseLibrary.VECTOR_SIF.createFrequencyTable();

            for (String comb : combinations) {

                DATABASE_MANAGER.add(comb, sp.getDeafault(), DB.DB_PERFECT_MATCHING);
                DATABASE_MANAGER.add(comb, sp.getDeafault(), DB.DB_VECTORS_SEQ);

            }

           for (int i = 0; i < questions.size(); i++) {
               addVocabulary(questions.get(i));
               String[] pairs1 = new String[2];
               pairs1[0] = questions.get(i);
               pairs1[1] = actions.get(i);
               pairSet.add(pairs1);
           }
           this.skillDatas.add(sp.getSkillData());
           this.questions.add(sp.getOriginalQuestion());


            System.out.println("===================================== \n\n");
        }

        for (String[] pair : pairSet) {
            DATABASE_MANAGER.add(pair[0], pair[1]);
        }


        List<String[]> result = cfg.getResult();
        for(String[] w: result){
            DATABASE_MANAGER.add(w[0], w[1]);
        }
    }

    public void addVocabulary(String words){
        List<String> vocabulary = SimpleProcess.processForVocabulary(words);
        COMPLETE_QUERY.add(vocabulary);
        VOCABULARY.addAll(vocabulary);
    }


    public String getSkill(String text, DB dataBase){
       return DATABASE_MANAGER.getFirst(text, dataBase);
    }


    public List<SkillData> getSkillData(){
        return this.skillDatas;
    }

    public List<Set<String>> getSlots(){
        return this.slots;
    }
    public List<String> getQuestions(){
        return questions;
    }

    public static void main(String[] args) throws Exception {
        Response res = new Response();

    }


}

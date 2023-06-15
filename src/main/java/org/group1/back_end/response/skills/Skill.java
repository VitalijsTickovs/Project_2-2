package org.group1.back_end.response.skills;

import org.apache.ibatis.jdbc.SQL;
import org.group1.back_end.response.Response;
import org.group1.back_end.response.ResponseLibrary;
import org.group1.back_end.response.skills.NLU.JSONConverter;
import org.group1.back_end.response.skills.databases.DB_Manager;
import org.group1.back_end.response.skills.dataframe.DataFrame;
import org.group1.back_end.textprocessing.SimpleProcess;
import org.group1.back_end.utilities.enums.DB;
import org.group1.back_end.utilities.strings.RegexUtilities;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.group1.back_end.textprocessing.ComplexProcess.lemmas;


public class Skill {

    private final SkillFileService SERVICE;
    public static Set<String> VOCABULARY = new HashSet<>();
    public static List<List<String>> COMPLETE_QUERY = new LinkedList<>();
    public static DB_Manager DATABASE_MANAGER;
    public List<SkillData> skillDatas;
    public List<String> questions;
    public List<Set<String>> slots;
    public ArrayList<ContextFreeGrammar> storeIndividualCFGs;


    public DataFrame skill;

    /**
     * Build a file SERVICE and list for skills.
     * This also generates the skills.
     * @throws IOException
     */
    public Skill() throws Exception {
        storeIndividualCFGs = new ArrayList<>();
        DATABASE_MANAGER = new DB_Manager();
        SERVICE = new SkillFileService();
        skillDatas = new ArrayList<>();
        questions= new ArrayList<>();
        slots= new ArrayList<>();
        generateSkills();
        generateCFGSkill();
    }

    /**
     * Method that actually generates from skills.
     * Reads all files from directory and creates skills.
     * @throws IOException
     */
    public void generateSkills() throws Exception {
        List<String> texts = SERVICE.readAll();
        List<String[]> pairSet = new ArrayList<>();
        JSONConverter jsonConverter = new JSONConverter();
        for (String text : texts){
            System.out.println("=====================================");
            System.out.println("Skill: \n" + text + "\n");
            SkillGenerator sp = new SkillGenerator(text);

            jsonConverter.addInput(sp.input);

            List<String> questions = sp.getQuestions();
            List<String> actions = sp.getActions();


//            System.out.println("Default: " + sp.getDeafaultKey() + " " + sp.getDeafault());
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



//            for(Set<String> slot: slots){
//                System.out.println(slot);
//            }

            System.out.println("===================================== \n\n");
        }

        jsonConverter.makeJSON();

        for (String[] pair : pairSet) {
            DATABASE_MANAGER.add(pair[0], pair[1]);
        }


    }

    public void generateCFGSkill() throws IOException {
        List<String> cfgSkills = SERVICE.readCFG();
        for(String skill: cfgSkills) {
            String[] text2 = skill.split("\n");
            List<String> input = new ArrayList<>();
            for (String row: text2) {
                input.add(row);
            }
            ContextFreeGrammar cfg = new ContextFreeGrammar(input);
            //TODO: FOR NATALIA
            Map<String, String[]> productions = cfg.formatTree.PRODUCTIONS;
            Map<String, Set<String>> slotsInRules = new HashMap<>();
            Pattern pattern = Pattern.compile("<(.*?)>");

            for(String key: productions.keySet()){
                String[] values = productions.get(key);
                Set<String> slotset = new HashSet<>();
                for(String value: values){
                    int slotsUsed = RegexUtilities.countRegexOccurrences(value, "<.*?>" );
                    if(slotsUsed>0){
                        Matcher matcher = pattern.matcher(value);
                        while (matcher.find()) {
                            slotset.add(matcher.group());
                        }
                    }
                }
                slotsInRules.put(key, slotset);
            }

            boolean flag=true;

            //JUST TO TEST
//            System.out.println("slot representative Location: " + slotsInRules.get("<LOCATION>"));
//            System.out.println("slot representative SCHEDULE: " + slotsInRules.get("<SCHEDULE>"));
//            System.out.println("slot representative TIMEEXPRESSION: " + slotsInRules.get("<TIMEEXPRESSION>"));
            System.out.println("");
            Map<String, List<String>> expandedRules = new HashMap<>();

            Set<String> uniqueActions = new HashSet<>(cfg.beforeKleeneStar);

            for(String action : uniqueActions){
                //expands everything
                List<String> expandedSlot = getEnd(action, slotsInRules, new ArrayList<>());
                expandedRules.put(action, expandedSlot);
            }
            System.out.println("slot expanded SCHEDULE: " + expandedRules.get("<SCHEDULE>"));
            System.out.println("slot expanded LOCATION: " + expandedRules.get("<LOCATION>"));
            ///// TODO: slotInRules is a hash map which has key as <LOCATION> and in the for loop
            //// i get the largest set of slots used
            List<String[]> pairSet = cfg.getRealData();

            for (String[] pair : pairSet) {
                addVocabulary(pair[0]);
                DATABASE_MANAGER.addCFG(pair[0], pair[1]);
            }
            storeIndividualCFGs.add(cfg);
            cfg.setColumnNamesTable(expandedRules);
        }
    }


    public boolean isKey(String slot, Map<String, Set<String>> slotsInRules){
        boolean isKey = false;
        for(String value: slotsInRules.get(slot)){
            if(slotsInRules.get(value).size()>0){
                isKey=true;
            }
        }
        return isKey;
    }

    public List<String> getEnd(String slot, Map<String, Set<String>> slotsInRules, List<String> expansions){
        if(!isKey(slot,slotsInRules)) {
            expansions.addAll(slotsInRules.get(slot));
            return expansions;
        }
        for(String str: slotsInRules.get(slot)) {
            expansions.addAll(getEnd(str, slotsInRules, new ArrayList<>()));
        }
        return expansions;
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

    //cfg related
    public ArrayList<ContextFreeGrammar> getAllCFGs() {return storeIndividualCFGs;}
    public ContextFreeGrammar getCurrentCFG(int index) {return storeIndividualCFGs.get(index);}
}

package org.group1.back_end.ML.model_markov_decision;

import org.group1.back_end.response.skills.SkillFileService;
import org.group1.back_end.response.skills.databases.iProcess;
import org.group1.back_end.textprocessing.SimpleProcess;

import java.util.*;

public class N_Grams implements iDecision, iProcess<List<String>> {


    Map<List<String>, Integer> nGrams;
    List<String> words;
    int n;

    public N_Grams(int n) {
        this.n = n;
        this.nGrams = new HashMap<>();
    }

    @Override
    public String getNext(String sequence) {
        Comparator<String> cmp = (o1, o2) -> {
            try{
                double o1prob = getProbability(o1, sequence);
                double o2prob = getProbability(o2, sequence);
                return Double.valueOf(o2prob).compareTo(Double.valueOf(o1prob));
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return 0;
            }
        };
        Collections.sort(words, cmp);
        return words.get(0);//todo: maybe return a list of the most probable answers
    }

    @Override
    public void train() { // we construct the hashmap
        if(words == null) throw new NullPointerException("No data has been loaded");
        for(int i = 0; i < words.size() - n; i++) {
            List<String> subList = new LinkedList<>(words.subList(i, i + n));
            if(nGrams.containsKey(subList)) {
                nGrams.put(subList, nGrams.get(subList) + 1);
            } else {
                nGrams.put(subList, 1);
            }
        }
    }

    @Override
    public void retrain() {

    }

    @Override
    public void load() { // loading the data from the file
        words = new LinkedList<>(); //todo: here the data needs to loaded
    }

    @Override
    public void save() {

    }

    @Override
    public List<String> process(String sentence) {
        return SimpleProcess.process(sentence);
    }

    /**
     * Returns the probability of a word given a sentence
     * @param word, the word to be predicted
     * @param sentence, the sentence to be used to predict the word with at least n-words
     * @return the probability of the word given the sentence
     * @throws Exception, if the sentence is too short
     */
    public double getProbability(String word, String sentence) throws Exception{
        List<String> words = process(sentence);
        if(words.size() < n) throw new Exception("Sentence is too short for this " + n + "-gram model");
        List<String> words_sentence = process(String.join(" ", sentence, word));
        List<String> subListWords = new LinkedList<>(words.subList(words.size() - n, words.size()));
        List<String> subListSentenceWord = new LinkedList<>(words_sentence.subList(words_sentence.size() - n, words_sentence.size()));
        double h = (double) getCount(subListWords);
        double hw = (double) getCount(subListSentenceWord);
        return hw / h;
    }

    /**
     * Returns the count of a subList in the nGrams
     * @param subList, the subList to be counted
     * @return the count of the subList
     */
    public Integer getCount(List<String> subList){
        return this.nGrams.get(subList) == null ? 0 : this.nGrams.get(subList);
    }
}

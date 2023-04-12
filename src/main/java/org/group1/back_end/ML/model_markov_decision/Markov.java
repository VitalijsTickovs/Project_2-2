package org.group1.back_end.ML.model_markov_decision;
import org.group1.back_end.response.skills.Skill;
import org.group1.back_end.response.skills.SkillFileService;
import org.group1.back_end.response.skills.databases.iProcess;
import org.group1.back_end.textprocessing.SimpleProcess;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.regex.Pattern;

public class Markov {

    private Map<String, List<String>> model;
    private Map<List<String>, Double> probabilities;
    private Random random;

    public Markov() throws Exception{
        model = new HashMap<>();
        random = new Random();
        probabilities = new HashMap<>();
    }


    public void train(List<String> sentences) {

        double counter = 0;
        for (String sentence : sentences) {
            sentence = String.join(" ", process(sentence));
            List<String> subSequences = getSubsets(sentence);

            for(String subsequence : subSequences){
                counter += createProbabilities(Arrays.stream(subsequence.split(" ")).toList());
            }

            //subSequences.forEach(System.out::println);

        }

        for (Map.Entry<List<String>, Double> entry : probabilities.entrySet()){

            entry.setValue(entry.getValue() / counter);

            System.out.println(entry.getKey() + " -----> " + entry.getValue());
        }



    }

    public double createProbabilities(List<String> key){

        double counter = 0;
        if(probabilities.get(key) == null){
            probabilities.put(key, 1d);
            counter += 1.0;
        }else {
            Double count = probabilities.get(key);
            probabilities.put(key, ++count);
        }

        return counter;
    }

    public static List<String> getSubsets(String sentence) {
        List<String> subsets = new ArrayList<>();
        String[] words = sentence.split(" ");

        for (int i = 0; i < words.length; i++) {

            StringBuilder forwardSubsetBuilder = new StringBuilder(words[i]);
            subsets.add(words[i]);
            for (int j = i + 1; j < words.length; j++) {
                forwardSubsetBuilder.append(" ");
                forwardSubsetBuilder.append(words[j]);
                subsets.add(forwardSubsetBuilder.toString());
            }

            StringBuilder backwardSubsetBuilder = new StringBuilder(words[i]);
            for (int j = i - 1; j >= 0; j--) {
                backwardSubsetBuilder.insert(0, words[j] + " ");
                subsets.add(backwardSubsetBuilder.toString());
            }
        }

        return subsets;
    }


    public void train(String sentence) {
        String[] words = sentence.split(" ");
        for (int i = 0; i < words.length - 1; i++) {
            String current = words[i];
            String next = words[i + 1];

            if (!model.containsKey(current)) {
                model.put(current, new ArrayList<>());
            }

            model.get(current).add(next);
        }

    }

    public String predict(String sentence, String word){
        String result = "NULL";
        double prob = 0;
        double closestProb = 0;


        for (String w : Skill.VOCABULARY) {
            String s = sentence.replace(word, w);
            prob = calculateProbability(s);

            if(prob > closestProb){
                closestProb = prob;
                result = w;
            }
        }
        return result;
    }

    public double calculateProbability(String sentence){
        List<String> subsequences = getSubsets(sentence);
        double cumulativeProbability = 0;
        for (String sequence : subsequences) {
            List<String> seq = process(sequence);
            cumulativeProbability += probabilities.getOrDefault(seq, 0d);
        }
        return cumulativeProbability;
    }


    public List<String> read(String path, String regex) throws Exception {

        List<String> data = new ArrayList<>();

        Scanner sc = new Scanner(new File(path));

        while (sc.hasNextLine()){
            data.add(sc.nextLine().split(regex)[0]);
        }
        return data;
    }


    public static void main(String[] args) throws Exception {

        Skill test = new Skill();
        test.generateSkills();
        List<String> a = new ArrayList<>();
        a.add("How is the weather on Wednesday?");
        a.add("How is the weather on Monday?");
        a.add("How is the weather on Thursday?");
        a.add("How is the lecture on Friday?");
        a.add("How is the lecture on Monday?");


        Markov model = new Markov();
        model.train(a);

        System.out.println(model.predict("How is the lectudqwdqdre on Monday?", "lectudqwdqdre" ));
        //System.out.println(model.process("How is the weather on Wednesday?.,.,<><>//??|||[]]/0987654321"));

    }

    @Override
    public List<String> process(String sentence) {
        return SimpleProcess.process(sentence.replaceAll("[<>;:/|,.?\\[\\]]", "").trim());
    }

}


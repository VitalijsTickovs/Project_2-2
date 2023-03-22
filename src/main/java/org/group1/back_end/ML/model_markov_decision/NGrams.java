package org.group1.back_end.ML.model_markov_decision;

import org.group1.back_end.response.skills.databases.iProcess;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class NGrams implements iDecision, iProcess<List<String>> {

    private static final String PATH_OANC = "src/main/resources/skills";
    private static final String PATH_MAPS = "src/main/resources/maps_Skills";
    private static final File FILE_OANC = new File(PATH_OANC);
    private static final int pageLimit = 8808; //--> is the total number of pages in the corpus
    Map<String, Integer> occurenceMap;
    int n;
    public NGrams(int n) {
        this.occurenceMap = new HashMap<>();
        this.n = n;
    }
    @Override
    public String getNext(String sequence) { // return a list of the most probable answers/correction
        return null;
    }

    @Override
    public void train() {
        System.out.println("Training the model...");
        System.out.println("Loading the corpus...");
        int page = 1;
        for(File file : FILE_OANC.listFiles()) {
            if (file.getName().endsWith(".txt")) {
                if(page++ > pageLimit) break;
                insertFile(file);
            }
        }
        System.out.println("Corpus loaded " + (page-2) + " pages");
        System.out.println("Model trained");
        System.out.println("Saving the model...");
        this.save();
        System.out.println("Model saved");
    }

    @Override
    public void retrain() {
        this.train();
    }

    @Override
    public void load() { //loading the hashmap from file
        System.out.println("Loading the model...");
        try{
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(PATH_MAPS + "/"+n+"gram_map.ser"));
            occurenceMap = (HashMap<String, Integer>) inputStream.readObject();
            inputStream.close();
        }catch (IOException | ClassNotFoundException e){
            System.out.println("Error while loading the hashmap from file");
            this.train();
            this.save();
        }
        System.out.println("Model loaded");
        // Load the HashMap from the file


    }

    @Override
    public void save() { // writing hashmap to file
        // Save the HashMap to a file
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(PATH_MAPS + "/"+n+"gram_map.ser"));
            outputStream.writeObject(occurenceMap);
            outputStream.close();
        }catch (IOException e){
            System.out.println("Error while saving the hashmap to file");
        }

    }

    @Override
    public List<String> process(String sentence) {
        List<String> words = new ArrayList<>();
        String str = sentence.toLowerCase().replaceAll("^\\s+", "").replaceAll("[\n]", " ").replaceAll("[^a-zA-Z\\s]", "");
        for (int i = 0; i < str.length() - this.n + 1; i++)
            words.add(str.substring(i, i + this.n));
        return words;
    }

    private void updateMaps(String sequence) {
        if(!sequence.matches(".*[a-zA-Z].*") && sequence.isEmpty()) return;
        List<String> sequences = process(sequence);
        for(int i = 0; i < sequences.size(); i++) {
            insertCharSequence(sequences.get(i));
        }
    }


    private void insertCharSequence(String charSeqence){
        if(occurenceMap.containsKey(charSeqence)) {
            occurenceMap.put(charSeqence, occurenceMap.get(charSeqence) + 1);
        } else {
            occurenceMap.put(charSeqence, 1);
        }
    }

    public void insertFile(File file){
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;

            // read the file line by line
            while ((line = bufferedReader.readLine()) != null) {
                // check if the line contains any characters a-z (lowercase or uppercase)
                if (line.matches(".*[a-zA-Z].*") && !line.isEmpty())
                    updateMaps(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double computeNGramScore(String word){
        List<String> toscore = process(word);
        double score = 0;
        for(String s : toscore){
            if(occurenceMap.containsKey(s)){
                score += occurenceMap.get(s);
            }
        }
        return score;
    }

}



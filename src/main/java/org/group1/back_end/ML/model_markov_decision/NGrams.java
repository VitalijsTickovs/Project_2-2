package org.group1.back_end.ML.model_markov_decision;

import org.group1.back_end.response.skills.databases.iProcess;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class NGrams implements iDecision, iProcess<List<String>> {

    private static final String PATH_OANC = "src/main/resources/words_datasets/OANCTxt";
    private static final String PATH_MAPS = "src/main/resources/maps/AONC";
    private static final File FILE_OANC = new File(PATH_OANC);
    private static final int pageLimit = 8808; //--> is the total number of pages in the corpus
    Map<String, Integer> occurenceMap;

    HashMap<double[], String> existingWords;
    int n;
    public NGrams(int n) {
        this.occurenceMap = new HashMap<>();
        this.n = n;
        this.existingWords = new HashMap<>();
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
            System.out.println("path: " + PATH_MAPS + "/"+n+"gram_map.ser");
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
        System.out.println("sentence: " + str);
        for (int i = 0; i < str.length() - this.n + 1; i++)
            words.add(str.substring(i, i + this.n));
        return words;
    }

    private void updateMaps(String sequence) {
        if(!sequence.matches(".*[a-zA-Z].*") && sequence.isEmpty()) return;
        List<String> sequences = process(sequence);
        for(int i = 0; i < sequences.size(); i++) {
            insertCharSequence(sequences.get(i));
            this.existingWords.put(createNgramVector(sequences.get(i)), sequences.get(i));
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
                System.out.println(s + " ---> " + occurenceMap.get(s));
                score += occurenceMap.get(s);
            }
        }
        return score;
    }


    public double[] createNgramVector(String word){
        List<String> toscore = process(word);
        double[] vector = new double[toscore.size()];
        for(String s : toscore){
            if(occurenceMap.containsKey(s)){
                System.out.println(s + " ---> " + occurenceMap.get(s));
                vector[toscore.indexOf(s)] = occurenceMap.get(s);
            }
        }
        return vector;
    }

    public static boolean containsSubset(double[] array, double[] subset) {
        for (int i = 0; i <= array.length - subset.length; i++) {
            if (Arrays.equals(Arrays.copyOfRange(array, i, i + subset.length), subset)) {
                return true;
            }
        }
        return false;
    }

    public static List<double[]> generateSubsets(double[] array, int minSize) {
        List<List<Double>> result = new ArrayList<>();
        generateSubsetsHelper(array, 0, new ArrayList<>(), result, minSize);
        List<double[]> result2 = new ArrayList<>();
        for(List<Double> l : result){
            result2.add(l.stream().mapToDouble(Double::doubleValue).toArray());
            System.out.println(Arrays.toString(l.stream().mapToDouble(Double::doubleValue).toArray()));
        }
        return result2;
    }

    private static void generateSubsetsHelper(double[] array, int index, List<Double> currentSubset,
                                              List<List<Double>> result, int minSize) {
        if (currentSubset.size() >= minSize) {
            result.add(new ArrayList<>(currentSubset));
        }

        for (int i = index; i < array.length; i++) {
            currentSubset.add(array[i]);
            generateSubsetsHelper(array, i + 1, currentSubset, result, minSize);
            currentSubset.remove(currentSubset.size() - 1);
        }
    }

    public static void main(String[] args) {
//        NGrams nGrams = new NGrams(3);
//        nGrams.load();
//        System.out.println(Arrays.toString(nGrams.createNgramVector("Monkey")));
//        System.out.println(Arrays.toString(nGrams.createNgramVector("Minkey")));
//        System.out.println(Arrays.toString(nGrams.createNgramVector("Monkei")));
        generateSubsets(new double[]{1,2,3,4,5,6,7,8,9,10}, 3);
    }

}



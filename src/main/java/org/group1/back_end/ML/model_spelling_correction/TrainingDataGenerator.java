package org.group1.back_end.ML.model_spelling_correction;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.group1.back_end.ML.model_vectorization.VectorSIF;
import org.group1.back_end.ML.model_vectorization.VectorWord2Vec;
import org.group1.back_end.response.ResponseLibrary;
import org.group1.back_end.response.skills.Skill;
import org.group1.back_end.response.skills.databases.iProcess;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TrainingDataGenerator implements iProcess<List<CorrectionPair>> {

    static final String TRAINING_DATA_PATH = "src/main/java/org/group1/back_end/ML/model_spelling_correction/wikipediadat.txt";

    List<CorrectionPair> pairs;
    DataSet trainingData;
    DataSet testData;

    public TrainingDataGenerator(){
        pairs = new ArrayList<>();
    }

    public void constructTrainingData(){

    }

    public void create(){
        List<String> lines = TXTReader.readTextFile(TRAINING_DATA_PATH);
        this.pairs = process(String.join(" ", lines));
    }

    public void load(){

    }

    public DataSet getTrainingData(){
        return trainingData;
    }

    public DataSet getTestData(){
        return testData;
    }

    public void writeData(){

    }

    public void split(){

    }

    public static Word2Vec WORD2VEC;
    public static VectorSIF VECTOR_SIF;
    static {
        WORD2VEC = WordVectorSerializer.readWord2VecModel(new File("src/main/resources/models/bin/Word2Vec.bin"));
        VECTOR_SIF = new VectorSIF();
        VECTOR_SIF.createFrequencyTable();
    }
    public static void main(String[] args) throws Exception{
        TrainingDataGenerator generator = new TrainingDataGenerator();
        generator.create();
        System.out.println(WORD2VEC == null);

        INDArray shitstick = WORD2VEC.getWordVectorMatrix("monkey");
        System.out.println(shitstick);
        shitstick = WORD2VEC.getWordVectorMatrix("mionkey");
        System.out.println(shitstick);
        System.out.println(WORD2VEC.getWordVectorMatrix("shit stick"));
    }

    @Override
    public List<CorrectionPair> process(String sentence) {
        return processWikiData(sentence);
    }

    public static List<CorrectionPair> processWikiData(String sentence){
        List<String> lines = new ArrayList<>(Arrays.asList(sentence.split("\\$")));
        lines.removeIf(String::isEmpty);
        List<CorrectionPair> pairs = new ArrayList<>();

        for(String line : lines){
            String[] pair = line.trim().split(" ");
            if(pair.length < 2) continue;
            String original = pair[0];
            List<String> corrections = new ArrayList<>(List.of(Arrays.copyOfRange(pair, 1, pair.length)));
            pairs.add(new CorrectionPair(original, corrections));
        }
        return pairs;
    }
}

package org.group1.back_end.ML.model_vectorization;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.group1.back_end.utilities.enums.Paths;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.group1.back_end.utilities.algebra.Distances.cosine;


public class VectorWord2Vec {

    private final Word2Vec word2Vec;

    public VectorWord2Vec() throws IOException {
        this(Paths.MODEL_BIN_WORD2VEC.path);
    }

    public VectorWord2Vec(String path) throws IOException {

        File binary_model = new File(
                new File(".")
                        .getCanonicalPath()
                        + path
        );

        word2Vec = WordVectorSerializer
                .readWord2VecModel(binary_model)
        ;
    }

    public void test(){

    }

    public void train(String data,
                      int layers,
                      int seed,
                      int wordFrec,
                      String save
    ) throws IOException {

        SentenceIterator iterable = new LineSentenceIterator(
                new File(
                        new File(".")
                                .getCanonicalPath()
                                + data)
        );

        TokenizerFactory tokenized = new DefaultTokenizerFactory();
        tokenized.setTokenPreProcessor(new CommonPreprocessor());

        Word2Vec newModel = new Word2Vec
                .Builder()
                .minWordFrequency(wordFrec)
                .layerSize(layers)
                .seed(seed)
                .windowSize(5)
                .iterate(iterable)
                .tokenizerFactory(tokenized)
                .build();

        newModel.fit();

        File file = new File(
                new File(".")
                        .getCanonicalPath()
                        + Paths.MODEL_BIN.path
                        + save

        );
        WordVectorSerializer.writeWord2VecModel(newModel, file);
    }

    public Word2Vec getWord2Vec() {
        return word2Vec;
    }

    public static void main(String[] args) throws IOException {

        Word2Vec model = WordVectorSerializer.readWord2VecModel(new File("src/main/resources/models/bin/Word2Vec.bin"));

        SentenceIterator iter = new BasicLineIterator("src/main/resources/models/data/14 Million sentences.txt");

        TokenizerFactory tokenizer = new DefaultTokenizerFactory();
        tokenizer.setTokenPreProcessor(new CommonPreprocessor());


        model.setTokenizerFactory(tokenizer);
        model.setSentenceIterator(iter);
        model.fit();

        WordVectorSerializer.writeWord2VecModel(model, "src/main/resources/models/bin/Word2Vec.bin");

        String frase1 = "My cat is beautiful";
        String frase2 = "My cat is good";

        Word2Vec vec = WordVectorSerializer.readWord2VecModel(new File(".").getCanonicalPath() + Paths.MODEL_BIN_WORD2VEC.path);

        double[] frase1Vector = vec.getWordVectorsMean(Arrays.stream(frase1.split(" ")).toList()).toDoubleVector();
        double[] frase2Vector = vec.getWordVectorsMean(Arrays.stream(frase2.split(" ")).toList()).toDoubleVector();

        System.out.println("Similarity = " + cosine(frase2Vector, frase1Vector));
    }

}

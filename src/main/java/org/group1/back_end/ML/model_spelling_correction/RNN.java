package org.group1.back_end.ML.model_spelling_correction;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.BaseRecurrentLayer;
import org.deeplearning4j.nn.conf.layers.LSTM;
import org.deeplearning4j.nn.conf.layers.Layer;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.IUpdater;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;

/**
 * This class is used to create a simple RNN model of 1 layer using LSTM
 */
public class RNN {
    static final double learningRate = 0.001;
    static final int seed = 69;
    static final WeightInit weightInit = WeightInit.XAVIER;
    static final IUpdater updater = new Adam(learningRate);
    static final int hiddenSize = 100;
    static final String savingPath = "src/main/resources/models/bin";
    static final String savingName = "RNNSpellingCorrection";
    Layer LSTM;
    Layer RNNOutput;
    MultiLayerNetwork model;
    MultiLayerConfiguration conf;
    DataSet dataSet;
    int inputSize, outputSize;

    public RNN(DataSet dataset) {
        this.dataSet = dataset;
        this.inputSize = (int) dataset.getFeatures().shape()[1];
        this.outputSize = (int) dataset.getLabels().shape()[1];
        configureLSTM();
        configureRNNOutput();
        conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .updater(updater)
                .weightInit(weightInit)
                .list()
                .layer(LSTM)
                .layer(RNNOutput)
                .build();
        model = new MultiLayerNetwork(conf);
        model.init();
    }

    public void train(DataSet dataSet) throws IOException{
        model.fit(dataSet);
        this.save();
    }
    public void save() throws IOException {
        File file = new File(savingPath+"/"+savingName+".bin");
        ModelSerializer.writeModel(model, file, true);
    }
    public void load() throws IOException{
        File file = new File(savingPath+"/"+savingName+".bin");
        model = ModelSerializer.restoreMultiLayerNetwork(file);
    }

    public Layer configureLSTM(){ //GRU and truncated GRU can also be possible
        LSTM = new LSTM.Builder()
                .nIn(inputSize)
                .nOut(hiddenSize)
                .activation(Activation.TANH)
                .build();
        return LSTM;
    }

    public Layer configureRNNOutput(){
        RNNOutput = new RnnOutputLayer.Builder()
                .nIn(hiddenSize)
                .nOut(outputSize)
                .activation(Activation.SOFTMAX)
                .lossFunction(LossFunctions.LossFunction.MCXENT)
                .build();
        return RNNOutput;
    }
}

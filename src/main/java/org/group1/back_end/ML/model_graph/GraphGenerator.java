package org.group1.back_end.ML.model_graph;

import org.bytedeco.opencv.presets.opencv_core;
import org.group1.back_end.textprocessing.SimpleProcess;

import java.util.*;

public class GraphGenerator {

    protected Set<String> vocabulary;
    protected Graph graph;

    public GraphGenerator(List<String> sentences) {
        this.vocabulary = new HashSet<>();
        generateVocabulary(sentences);
        graph = new Graph(vocabulary);
        connectNodes(sentences);
    }

    public void generateVocabulary(List<String> sentences){
        for (String sentence : sentences) {
            List<String> words = SimpleProcess.process(sentence);
            vocabulary.addAll(words);
        }
    }

    public void connectNodes(List<String> sentences){

        for (String sentence : sentences) {
            List<String> words = SimpleProcess.process(sentence);
            for (String word : words) {
                for (String vocab : vocabulary) {
                    if(word.equals(vocab)){
                        continue;
                    }
                    graph.connectNodes(new GraphNode(word), new GraphNode(vocab));
                   // System.out.println(word + "------" +vocab);
                }
            }
        }
    }

    public double difference(double[] A, double[] B){
        double error = 0;
        for (int i = 0; i < A.length; i++) {
            error += 1/ Math.abs(A[i] - B[i]);
        }
        return error / A.length;
    }

    public void PageRank(){

        for (int i = 0; i < 10; i++) {
            System.out.println("ITERATION: " + (i + 1));
            double[] A = graph.vector;

            graph.iteration2();

            double[] B = graph.vector;
            for (GraphNode node : graph.nodes) {
                System.out.println(node.getWord() + " ---> " + node.getPR_value());
            }

            double error = difference(A, B);
            System.out.println(error);

        }

    }

    public void PageRank2(){

        double error = Double.MAX_VALUE;
        int i = 0;

        do{
            i++;
            System.out.println("ITERATION: " + i);

            double[] A = graph.vector;

            graph.iteration2();

            double[] B = graph.vector;
            for (GraphNode node : graph.nodes) {
                System.out.println(node.getWord() + " ---> " + node.getPR_value());
            }

            error = difference(A, B);
            System.out.println(error);
        }while (error > 0.001);

    }


    public static void main(String[] args) {
        String[] z = {
                "Which lectures are there on Monday at 9?",
                "What are the lectures on Monday at 9?",
                "What are the lectures on Monday at 9?",
                "Which lectures are lectures for 9 on Monday?",
                "Can you tell me which lectures are happening at 9 on Monday?",
                "What are the classes on Monday at 9am?",
                "I'm trying to find out which lectures are taking place on Monday at 9am, do you know?",
                "Can you give me a list of lectures happening on Monday at 9am?",
                "Which lectures should I attend on Monday at 9am?",
                "What's on the lecture for monday at ix?",
                "What are the lectures today at 9?",
                "How is the lectures on in 3 days?",
                "lecture monday nine"
        };;

        GraphGenerator a = new GraphGenerator(Arrays.stream(z).toList());

        a.PageRank2();
    }
}

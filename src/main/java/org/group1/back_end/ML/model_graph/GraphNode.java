package org.group1.back_end.ML.model_graph;

import java.util.ArrayList;
import java.util.List;

public class GraphNode {
    private final String word;
    List<GraphNode> adjacentNodes;
    private double PR_value;

    public GraphNode(String word) {
        adjacentNodes = new ArrayList<>();
        this.word = word;
        PR_value = 1;
    }

    public void add(GraphNode adjacent){
        adjacentNodes.add(adjacent);
    }


    public boolean equals(GraphNode obj) {
        return obj.getWord().equals(this.word);
    }

    public void linearEq() {
        double value = 0;
        for (GraphNode neigbour : adjacentNodes) {
            value +=  neigbour.PR_value * (1 / neigbour.getDegree());
        }
        this.PR_value = value;
    }

    public double getDegree(){

        return adjacentNodes.size() == 0 ? 1 : adjacentNodes.size();
    }

    public String getWord() {
        return word;
    }

    public double getPR_value(){
        return PR_value;
    }

    public void setPR_value(double PR_value) {
        this.PR_value = PR_value;
    }

    public List<GraphNode> getAdjacentNodes() {
        return adjacentNodes;
    }
}

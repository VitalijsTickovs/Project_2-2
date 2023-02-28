package TF_IDF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TFIDF {

    private List<String> listOfDocuments;
    private String corpus;
    private Map<String, Map<String, Double>> matrix;

    // This one is the test class , I implemented the simple methods of the TF-IDF algorithm in the Utils class, it is not very useful for now, it may be later

    public TFIDF(List<String> listOfDocuments) {
        this.listOfDocuments = listOfDocuments;
        this.corpus = TFIDFUtils.getCorpus(listOfDocuments);
        this.matrix = TFIDFUtils.getMatrixScore2(corpus, listOfDocuments);
    }

    public void printMatrixScore() {
        for (Map.Entry<String, Map<String, Double>> entry : matrix.entrySet()) {
            String term = entry.getKey();
            Map<String, Double> scores = entry.getValue();
            System.out.print("Word '"+ term + "' : ");
            for (Map.Entry<String, Double> scoreEntry : scores.entrySet()) {
                String document = scoreEntry.getKey();
                double score = scoreEntry.getValue();
                System.out.print(document + "=" + score + ", ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> listOfDocuments = new ArrayList<>();
        listOfDocuments.add(TFIDFUtils.fileToString("dataset/doc1.txt"));
        listOfDocuments.add(TFIDFUtils.fileToString("dataset/doc2.txt"));
        listOfDocuments.add(TFIDFUtils.fileToString("dataset/doc3.txt"));

        TFIDF tfidf = new TFIDF(listOfDocuments);
        System.out.println("\n CORPUS : \n" + tfidf.corpus);
        tfidf.printMatrixScore();
    }
}

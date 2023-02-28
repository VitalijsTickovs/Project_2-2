package TF_IDF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TFIDFUtils {

    public static String fileToString(String fileName) throws IOException {
        File input = new File(fileName);
        try (FileReader reader = new FileReader(input); BufferedReader br = new BufferedReader(reader)) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        }
    }

    public static double TF(String term, String document) {
        double count = 0;
        String[] words = document.split(" ");
        for (String w : words) {
            if (w.equals(term)) {
                count++;
            }
        }
        return count / words.length;
    }

    public static double IDF(String term, List<String> listOfDocuments) {
        double count = 0;
        for (String document : listOfDocuments) {
            if (document.contains(term)) {
                count++;
            }
        }
        double DF = listOfDocuments.size() / count;
        return Math.log(DF);
    }

    public static double getScore(String term, String document, List<String> listOfDocuments) {
        return TF(term, document) * IDF(term, listOfDocuments);
    }

    public static String getCorpus(List<String> listOfDocs) {
        StringBuilder builder = new StringBuilder();
        int i = 1;
        for (String doc : listOfDocs) {
            builder.append(" DOC ").append(i++).append(" : ").append(doc);
        }
        return builder.toString();
    }


    public static Map<String, Map<String, Double>> getMatrixScore2(String corpus, List<String> listOfDocs) {
        String[] corpusArray = corpus.split(" ");
        Map<String, Map<String, Double>> matrix = new HashMap<>();
        for (int i = 0; i < corpusArray.length; i++) {
            String term = corpusArray[i];
            Map<String, Double> scores = new HashMap<>();
            for (int j = 0; j < listOfDocs.size(); j++) {
                String document = "doc" + (j + 1);
                double score = getScore(term, listOfDocs.get(j), listOfDocs);
                scores.put(document, score);
            }
            matrix.put(term, scores);
        }
        return matrix;
    }
}

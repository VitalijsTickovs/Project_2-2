package org.group1.back_end.ML;

import java.util.*;

public class LanguajeModel {

    private Map<String, Map<String, Integer>> ngramCounts;
    private Random random;

    public LanguajeModel(List<String> corpus, int n) {
        ngramCounts = new HashMap<>();
        random = new Random();

        // Calcular las frecuencias de los n-gramas en el corpus
        for (String sentence : corpus) {
            String[] words = sentence.split("\\s+");
            for (int i = 0; i < words.length - n + 1; i++) {
                String ngram = String.join(" ", Arrays.copyOfRange(words, i, i + n));
                String nextWord = words[i + n - 1];
                if (!ngramCounts.containsKey(ngram)) {
                    ngramCounts.put(ngram, new HashMap<>());
                }
                Map<String, Integer> nextWordCounts = ngramCounts.get(ngram);
                nextWordCounts.put(nextWord, nextWordCounts.getOrDefault(nextWord, 0) + 1);
            }
        }
    }

    public String generateText(int length) {
        List<String> words = new ArrayList<>();
        String ngram = getRandomNgram();
        words.addAll(Arrays.asList(ngram.split("\\s+")));
        while (words.size() < length) {
            Map<String, Integer> nextWordCounts = ngramCounts.getOrDefault(ngram, new HashMap<>());
            if (nextWordCounts.isEmpty()) {
                ngram = getRandomNgram();
                words.addAll(Arrays.asList(ngram.split("\\s+")));
            } else {
                String nextWord = getRandomNextWord(nextWordCounts);
                words.add(nextWord);
                ngram = String.join(" ", words.subList(words.size() - (words.size() >= ngram.split("\\s+").length ? ngram.split("\\s+").length : words.size()), words.size()));
            }
        }
        return String.join(" ", words);
    }

    private String getRandomNgram() {
        List<String> ngrams = new ArrayList<>(ngramCounts.keySet());
        return ngrams.get(random.nextInt(ngrams.size()));
    }

    private String getRandomNextWord(Map<String, Integer> nextWordCounts) {
        List<String> words = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : nextWordCounts.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                words.add(entry.getKey());
            }
        }
        return words.get(random.nextInt(words.size()));
    }

}

package com.testing.laba3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UniqueWordsFinder {
    public static List<String> findUniqueWords(List<String> lines, WordAnalyzer analyzer) {
        ArrayList<String> words = new ArrayList<>();

        for (String nextLine : lines) {
            List<String> wordsInLine = Arrays.asList(nextLine.split("[\\s(),.!?:;@|-]+"));

            for (String word : wordsInLine) {
                if (analyzer.analyzeWord(word)) {
                    words.add(word);
                }
            }
        }

        return words;
    }
}

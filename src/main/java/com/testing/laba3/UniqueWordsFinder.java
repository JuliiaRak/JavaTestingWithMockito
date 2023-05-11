package com.testing.laba3;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UniqueWordsFinder {

    private DataReader dataReader;

    public UniqueWordsFinder(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    public List<String> findUniqueWords(WordAnalyzer analyzer) {
        List<String> lines = dataReader.readData();
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

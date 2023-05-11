package com.testing.laba3;

import java.util.List;

public class SysProg3 {
    public static void main(String[] args) {
        DataReader dataReader = new MockDataReader();
        List<String> inputLines = dataReader.readData();

        WordAnalyzer analyzer = new DefaultWordAnalyzer();
        UniqueWordsFinder uniqueWordsFinder = new UniqueWordsFinder(dataReader);
        List<String> uniqueWords = uniqueWordsFinder.findUniqueWords(analyzer);

        for (String word : uniqueWords) {
            System.out.println(word);
        }
    }
}
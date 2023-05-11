package com.testing.laba3;

public class DefaultWordAnalyzer implements WordAnalyzer {
    @Override
    public boolean analyzeWord(String word) {
        word = word.toLowerCase();
        if (word.length() > 30) {
            word = word.substring(0, 30);
        }
        if (word.isEmpty()) {
            return false;
        }
        for (int i = 0; i < word.length(); i++) {
            for (int j = i + 1; j < word.length(); j++) {
                if (word.charAt(i) == word.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }
}

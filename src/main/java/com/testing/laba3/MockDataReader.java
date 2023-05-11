package com.testing.laba3;

import java.util.Arrays;
import java.util.List;

public class MockDataReader implements DataReader {
    @Override
    public List<String> readData() {
        return Arrays.asList(
                "Привіт, це тестовий текст!",
                "Тут є кілька унікальних слів, але деякі повторюються."
        );
    }
}

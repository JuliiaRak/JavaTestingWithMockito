package com.testing.laba3;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;

public class UniqueWordsFinderTest {

    @Test
    public void testWithMockDataReader() {
        // Створюємо мок-об'єкт для DataReader
        DataReader mockDataReader = mock(DataReader.class);

        // При виклику методу readData() мок-об'єкт поверне задане значення
        when(mockDataReader.readData()).thenReturn(Arrays.asList("Привіт, це тестовий текст!", "машина, автомобіль, клас, мок"));

        // Створюємо звичайний об'єкт WordAnalyzer
        WordAnalyzer analyzer = new DefaultWordAnalyzer();
        UniqueWordsFinder uniqueWordsFinderMock = new UniqueWordsFinder(mockDataReader);

        List<String> expected = Arrays.asList("Привіт", "це", "клас", "мок");
        List<String> actual = uniqueWordsFinderMock.findUniqueWords(analyzer);
        // Перевіряємо, чи результат відповідає очікуваному значенню
        assertEquals(expected, actual);
    }


// сценарій який стосується обробки виключень;
    @Test
    public void testWithNullInput() {
        DataReader mockDataReader = mock(DataReader.class);
        when(mockDataReader.readData()).thenReturn(null);
        WordAnalyzer analyzer = new DefaultWordAnalyzer();
        UniqueWordsFinder uniqueWordsFinderMock = new UniqueWordsFinder(mockDataReader);
        assertThrows(NullPointerException.class, () -> {
            uniqueWordsFinderMock.findUniqueWords(analyzer);
        });
    }

//має бути реалізована перевірка того, що метод було викликано певну кількість разів;
    @Test
    public void testWithMockAnalyzer() {
        DataReader dataReader = new MockDataReader();

        // Створення мок об'єкта mockAnalyzer на основі інтерфейсу WordAnalyzer
        WordAnalyzer mockAnalyzer = mock(WordAnalyzer.class);

        // Встановлення поведінки для методу analyzeWord мок об'єкта mockAnalyzer,
        // коли будь-який рядок буде переданий як параметр, повертати true
        when(mockAnalyzer.analyzeWord(anyString())).thenReturn(true);

        // Виклик методу findUniqueWords з параметрами dataReader та mockAnalyzer
        UniqueWordsFinder uniqueWordsFinderMock = new UniqueWordsFinder(dataReader);
        uniqueWordsFinderMock.findUniqueWords(mockAnalyzer);

        // Перевірка, що метод analyzeWord був викликаний 12 разів
        // (бо 12 слів в нашому фіксованому списоку)
        verify(mockAnalyzer, times(12)).analyzeWord(anyString());
    }


// сценарії за яких мок об’єкти згенерують виключення
    @Test
    public void testWithMockDataReaderAndException() {
        DataReader mockDataReader = mock(DataReader.class);
        when(mockDataReader.readData()).thenThrow(new RuntimeException("Exception reading data"));
        UniqueWordsFinder uniqueWordsFinderMock = new UniqueWordsFinder(mockDataReader);
        WordAnalyzer analyzer = new DefaultWordAnalyzer();

        try {
            List<String> actual = uniqueWordsFinderMock.findUniqueWords(analyzer);
        } catch (RuntimeException ex) {
            assertEquals("Exception reading data", ex.getMessage());
        }
    }


// продемонстрована іціалізація за допомогою анотацій (@Mock, @InjectMocks);
    @Mock
    DataReader dataReader;
    @Mock
    WordAnalyzer wordAnalyzer;
    @InjectMocks
    UniqueWordsFinder uniqueWordsFinder;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testWithMock2() {
        when(dataReader.readData()).thenReturn(Arrays.asList( "океан, машина, подарунок"));

        when(wordAnalyzer.analyzeWord("океан")).thenReturn(false);
        when(wordAnalyzer.analyzeWord("подарунок")).thenReturn(true);
        assertEquals(Arrays.asList("подарунок"), uniqueWordsFinder.findUniqueWords(wordAnalyzer));
    }


//має бути використаний частковий мок об’єкт @Spy.
    @Spy
    private WordAnalyzer spyAnalyzer = new DefaultWordAnalyzer();
    @Test
    void testWithSpy() {
        List<String> inputLines = Arrays.asList(
                "океан, подарунок",
                "кілька унікальних слів"
        );

        UniqueWordsFinder uniqueWordsFinderMock = new UniqueWordsFinder(dataReader);
        //перевизначаємо метод analyzeWord() за допомогою мок-об'єкта, створеного з допомогою @Spy
        Mockito.when(spyAnalyzer.analyzeWord("океан")).thenReturn(false);

        List<String> uniqueWords = uniqueWordsFinderMock.findUniqueWords(spyAnalyzer);

        //унікальне лише "слів", бо ми перевизначили, що "океан" не унікальне
        assertEquals(0, uniqueWords.size());
    }

}

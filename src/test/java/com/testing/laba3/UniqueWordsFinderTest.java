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

        List<String> expected = Arrays.asList("Привіт", "це", "клас", "мок");
        List<String> actual = UniqueWordsFinder.findUniqueWords(mockDataReader.readData(), analyzer);
        // Перевіряємо, чи результат відповідає очікуваному значенню
        assertEquals(expected, actual);
    }


// сценарій який стосується обробки виключень;
    @Test
    public void testWithNullInput() {
        DataReader mockDataReader = mock(DataReader.class);
        when(mockDataReader.readData()).thenReturn(null);
        WordAnalyzer analyzer = new DefaultWordAnalyzer();

        assertThrows(NullPointerException.class, () -> {
            UniqueWordsFinder.findUniqueWords(mockDataReader.readData(), analyzer);
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
        UniqueWordsFinder.findUniqueWords(dataReader.readData(), mockAnalyzer);

        // Перевірка, що метод analyzeWord був викликаний 12 разів
        // (бо 12 слів в нашому фіксованому списоку)
        verify(mockAnalyzer, times(12)).analyzeWord(anyString());
    }

    @Test
    public void testAnalyzerCalledCorrectly() {
        WordAnalyzer mockAnalyzer = mock(WordAnalyzer.class);
        when(mockAnalyzer.analyzeWord("word1")).thenReturn(true);
        when(mockAnalyzer.analyzeWord("word2")).thenReturn(false);
        when(mockAnalyzer.analyzeWord("word3")).thenReturn(true);

        List<String> inputLines = Arrays.asList("word1", "word2", "word3", "word3");
        UniqueWordsFinder.findUniqueWords(inputLines, mockAnalyzer);

        verify(mockAnalyzer, times(1)).analyzeWord("word1");
        verify(mockAnalyzer, times(1)).analyzeWord("word2");
        verify(mockAnalyzer, times(2)).analyzeWord("word3");
    }


// сценарії за яких мок об’єкти згенерують виключення
    @Test
    public void testWithMockDataReaderAndException() {
        DataReader mockDataReader = mock(DataReader.class);
        when(mockDataReader.readData()).thenThrow(new RuntimeException("Exception reading data"));

        WordAnalyzer analyzer = new DefaultWordAnalyzer();
        try {
            List<String> actual = UniqueWordsFinder.findUniqueWords(mockDataReader.readData(), analyzer);
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

        assertEquals(Arrays.asList("подарунок"), uniqueWordsFinder.findUniqueWords(dataReader.readData(), wordAnalyzer));
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

        //перевизначаємо метод analyzeWord() за допомогою мок-об'єкта, створеного з допомогою @Spy
        Mockito.when(spyAnalyzer.analyzeWord("океан")).thenReturn(false);
        List<String> uniqueWords = UniqueWordsFinder.findUniqueWords(inputLines, spyAnalyzer);

        //унікальне лише "слів", бо ми перевизначили, що "океан" не унікальне
        assertEquals(1, uniqueWords.size());
    }

    //Створюємо частковий мок-об'єкт spyAnalyzer2
    @Spy
    private WordAnalyzer spyAnalyzer2 = new DefaultWordAnalyzer();
    @Test
    void testWithSpy2() {
        List<String> inputLines = Arrays.asList(
                "Hello",
                "World",
                "текст"
        );
        // Перевизначаємо поведінку методу analyzeWord для певного слова
        when(spyAnalyzer2.analyzeWord("текст")).thenReturn(true);
        //Mockito.when(spyAnalyzer2.analyzeWord(anyString())).thenReturn(true);

        // Викликаємо метод findUniqueWords з використанням часткового мок-об'єкта
        List<String> uniqueWords = UniqueWordsFinder.findUniqueWords(inputLines, spyAnalyzer2);

        // Перевіряємо, що метод analyzeWord був викликаний тричі (для кожного рядка вхідних даних)
        verify(spyAnalyzer2, times(3)).analyzeWord(anyString());

        // Перевіряємо, що список унікальних слів містить очікувані значення
        List<String> expectedUniqueWords = Arrays.asList("World", "текст");
        assertEquals(expectedUniqueWords, uniqueWords);
    }
}

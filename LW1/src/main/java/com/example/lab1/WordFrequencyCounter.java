package com.example.lab1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "WordFrequencyCounter", urlPatterns = {"/WordFrequencyCounter"})
public class WordFrequencyCounter extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Исходный текст для анализа
        String inputText = getInputText();

        // Разделение текста на слова
        String[] wordsArray = inputText.split("\s+");

        // Подсчет частоты слов
        Map<String, Integer> frequencyMap = countWordFrequency(wordsArray);

        // Получение слова для поиска из параметров запроса
        String searchWord = request.getParameter("word").toLowerCase();

        // Настройка ответа
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<html><body>");
        writer.println("<h2>Результаты поиска слова:</h2>");

        // Вывод частоты найденного слова
        if (frequencyMap.containsKey(searchWord)) {
            writer.println("Слово "" + searchWord + "" встречается " + frequencyMap.get(searchWord) + " раз(а).");
        } else {
            writer.println("Слово "" + searchWord + "" не найдено в тексте.");
        }

        writer.println("</body></html>");
    }

    // Метод для получения текста
    private String getInputText() {
        return "All the world's a stage,\n" +
                "And all the men and women merely players,\n" +
                "They have their exits and entrances,\n" +
                "And one man in his time plays many parts,\n" +
                "His acts being seven ages. At first the infant,\n" +
                "Mewling and puking in the nurse's arms.\n" +
                "Then, the whining schoolboy with his satchel\n" +
                "And shining morning face, creeping like snail\n" +
                "Unwillingly to school. And then the lover,\n" +
                "Sighing like furnace, with a woeful ballad\n" +
                "Made to his mistress' eyebrow. Then a soldier,\n" +
                "Full of strange oaths, and bearded like the pard,\n" +
                "Jealous in honour, sudden, and quick in quarrel,\n" +
                "Seeking the bubble reputation\n" +
                "Even in the cannon's mouth. And then the justice\n" +
                "In fair round belly, with good capon lin'd,\n" +
                "With eyes severe, and beard of formal cut,\n" +
                "Full of wise saws, and modern instances,\n" +
                "And so he plays his part. The sixth age shifts\n" +
                "Into the lean and slipper'd pantaloon,\n" +
                "With spectacles on nose, and pouch on side,\n" +
                "His youthful hose well sav'd, a world too wide,\n" +
                "For his shrunk shank, and his big manly voice,\n" +
                "Turning again towards childish treble, pipes\n" +
                "And whistles in his sound. Last scene of all,\n" +
                "That ends this strange eventful history,\n" +
                "Is second childishness and mere oblivion,\n" +
                "Sans teeth, sans eyes, sans taste, sans everything.";
    }

    // Метод для подсчета частоты слов
    private Map<String, Integer> countWordFrequency(String[] words) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        
        for (String word : words) {
            word = word.toLowerCase().replaceAll("[^a-zA-Z]", ""); // Удаляем пунктуацию
            if (!word.isEmpty()) {
                frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
            }
        }
        
        return frequencyMap;
    }
}

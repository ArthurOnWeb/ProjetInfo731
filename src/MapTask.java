import java.util.HashMap;
import java.util.Map;

public class MapTask {
    public Map<String, Integer> execute(String chunk) {
        Map<String, Integer> wordCounts = new HashMap<>();

        // Logique pour mapper les mots du chunk et compter les occurrences
        String[] words = chunk.split("\\s+"); // Supposons que les mots sont séparés par des espaces
        for (String word : words) {
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
        }

        return wordCounts;
    }
}

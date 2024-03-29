import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MapTask {
    public Map<String, Integer> execute(String chunk) {
        Map<String, Integer> wordCounts = new HashMap<>();
        String[] words = chunk.split("\\s+");

        // Expression régulière pour exclure les ponctuations
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");

        for (String word : words) {
            // Exclure les caractères de ponctuation
            String cleanedWord = pattern.matcher(word).replaceAll("");

            wordCounts.put(cleanedWord, wordCounts.getOrDefault(cleanedWord, 0) + 1);
        }

        return wordCounts;
    }
}


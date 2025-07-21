import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Performs the <em>map</em> phase of the word count example.
 * <p>
 * A {@code MapTask} receives a block of text and produces a map where each
 * word is associated with the number of occurrences found in that block.
 */
public class MapTask {

    /**
     * Counts the words in the provided text chunk.
     *
     * @param chunk the block of text to analyse
     * @return a map where keys are words and values are their counts in the chunk
     */
    public Map<String, Integer> execute(String chunk) {
        Map<String, Integer> wordCounts = new HashMap<>();
        String[] words = chunk.split("\\s+");

        // Regular expression used to remove punctuation characters
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");

        for (String word : words) {
            // Remove punctuation and normalize to lowercase
            String cleanedWord = pattern.matcher(word).replaceAll("").toLowerCase();

            if (!cleanedWord.isEmpty()) {
                wordCounts.put(cleanedWord, wordCounts.getOrDefault(cleanedWord, 0) + 1);
            }
        }

        return wordCounts;
    }
}


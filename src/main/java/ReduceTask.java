import java.util.HashMap;
import java.util.Map;

/**
 * Represents the <em>reduce</em> phase of the Map/Reduce workflow.
 * <p>
 * The task aggregates partial word counts produced by the map phase.
 */
public class ReduceTask {

    /**
     * Aggregates partial results for a single chunk.
     *
     * @param partialResults the map produced during the map phase
     * @return a new map containing the aggregated counts
     */
    public Map<String, Integer> execute(Map<String, Integer> partialResults) {
        Map<String, Integer> finalResult = new HashMap<>();
        for (Map.Entry<String, Integer> entry : partialResults.entrySet()) {
                String word = entry.getKey();
                int count = entry.getValue();
                finalResult.put(word, finalResult.getOrDefault(word, 0) + count);
        }

        return finalResult;
    }
}

import java.util.HashMap;
import java.util.Map;
public class ReduceTask {
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

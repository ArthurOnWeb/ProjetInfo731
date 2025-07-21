/**
 * Utility methods for manipulating {@code Map} instances used in the
 * Map/Reduce workflow.
 */
public class MapUtils {

    /**
     * Aggregates a list of maps by summing the counts of identical keys.
     *
     * @param maps the collection of maps produced by the map phase
     * @return a single map containing the aggregated counts
     */
    public static java.util.Map<String, Integer> aggregateMaps(java.util.ArrayList<java.util.Map<String, Integer>> maps) {
        java.util.Map<String, Integer> aggregatedMap = new java.util.HashMap<>();
        for (java.util.Map<String, Integer> map : maps) {
            for (java.util.Map.Entry<String, Integer> entry : map.entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();
                aggregatedMap.put(key, aggregatedMap.getOrDefault(key, 0) + value);
            }
        }
        return aggregatedMap;
    }

    /**
     * Splits a map into several smaller maps.
     *
     * @param inputMap the original map
     * @param numParts number of parts to create
     * @return a list containing the smaller maps
     */
    public static java.util.ArrayList<java.util.Map<String, Integer>> divideMap(java.util.Map<String, Integer> inputMap, int numParts) {
        java.util.ArrayList<java.util.Map<String, Integer>> dividedMaps = new java.util.ArrayList<>();
        int sizePerPart = (int) Math.ceil((double) inputMap.size() / numParts);
        java.util.ArrayList<java.util.Map.Entry<String, Integer>> entries = new java.util.ArrayList<>(inputMap.entrySet());
        int currentIndex = 0;
        for (int i = 0; i < numParts; i++) {
            java.util.Map<String, Integer> partMap = new java.util.HashMap<>();
            for (int j = 0; j < sizePerPart && currentIndex < entries.size(); j++) {
                java.util.Map.Entry<String, Integer> entry = entries.get(currentIndex);
                partMap.put(entry.getKey(), entry.getValue());
                currentIndex++;
            }
            dividedMaps.add(partMap);
        }
        return dividedMaps;
    }
}

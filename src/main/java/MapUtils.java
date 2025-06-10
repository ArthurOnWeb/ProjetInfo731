public class MapUtils {
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

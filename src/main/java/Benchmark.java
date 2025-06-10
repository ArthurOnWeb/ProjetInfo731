import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Benchmark {
    public static void main(String[] args) {
        // Effectue un benchmark avec différents nombres de MapTask et ReduceTask
        runBenchmark(1);
        runBenchmark(2);
        runBenchmark(3);
        runBenchmark(4);
        runBenchmark(5);
        runBenchmark(6);
        runBenchmark(7);
        runBenchmark(8);
        runBenchmark(9);
        runBenchmark(10);
        runBenchmark(11);
        runBenchmark(12);
        runBenchmark(13);
        runBenchmark(14);
        runBenchmark(15);
        runBenchmark(16);
    }

    private static void runBenchmark(int numMapTasks) {
        // Enregistre le temps de début
        long startTime = System.currentTimeMillis();

        try {
            String text = "lesmiserables.txt";
            CoordinateurNode coordinateur = new CoordinateurNode();

            coordinateur.setTextChunks(Splitter.splitText(text, numMapTasks));
            coordinateur.assignMapTasks(numMapTasks);
            ArrayList<Map<String, Integer>> resultsMapping = coordinateur.executeMapTasks();
            //Map<String, Integer> mapaggregated = aggregateMaps(resultsMapping);
            //ArrayList<Map<String, Integer>> dividedMaps = divideMap(mapaggregated,numReduceTasks);
            coordinateur.assignReduceTasks(numMapTasks);
            ArrayList<Map<String, Integer>> resultsReducing = coordinateur.executeReduceTasks(resultsMapping);

            ArrayList<Map<String, Integer>> results = coordinateur.aggregateResults(resultsReducing);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Enregistre le temps de fin
            long endTime = System.currentTimeMillis();

            // Calcule et affiche la différence
            System.out.println("Temps d'exécution: " + (endTime - startTime) + " millisecondes pour "+ (numMapTasks) +"Mapper et Reducer");
            System.out.println("--------------");
        }
    }
    public static Map<String, Integer> aggregateMaps(ArrayList<Map<String, Integer>> maps) {
        Map<String, Integer> aggregatedMap = new HashMap<>();

        for (Map<String, Integer> map : maps) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();

                // Ajoute la valeur existante à la valeur actuelle du résultat agrégé, ou initialise à 0 si la clé n'existe pas encore
                aggregatedMap.put(key, aggregatedMap.getOrDefault(key, 0) + value);
            }
        }

        return aggregatedMap;
    }

    public static ArrayList<Map<String, Integer>> divideMap(Map<String, Integer> inputMap, int numParts) {
        ArrayList<Map<String, Integer>> dividedMaps = new ArrayList<>();

        // Calculer la taille approximative de chaque partie
        int sizePerPart = (int) Math.ceil((double) inputMap.size() / numParts);

        ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<>(inputMap.entrySet());
        int currentIndex = 0;

        for (int i = 0; i < numParts; i++) {
            Map<String, Integer> partMap = new HashMap<>();

            for (int j = 0; j < sizePerPart && currentIndex < entries.size(); j++) {
                Map.Entry<String, Integer> entry = entries.get(currentIndex);
                partMap.put(entry.getKey(), entry.getValue());
                currentIndex++;
            }

            dividedMaps.add(partMap);
        }

        return dividedMaps;
    }
}

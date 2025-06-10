import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class Main {
    public static void main(String[] args) {
        // Enregistre le temps de début
        long startTime = System.currentTimeMillis();

        try {
            String text = "lesmiserables.txt";
            CoordinateurNode coordinateur = new CoordinateurNode();

            // Définir le nombre de MapTask et ReduceTask souhaité
            int numMapTasks = 4;
            int numReduceTasks = numMapTasks;

            coordinateur.setTextChunks(Splitter.splitText(text, numMapTasks));
            coordinateur.assignMapTasks(numMapTasks);
            ArrayList<Map<String, Integer>> resultsMapping = coordinateur.executeMapTasks();

            //Map<String, Integer> mapaggregated = aggregateMaps(resultsMapping);
            //ArrayList<Map<String, Integer>> dividedMaps = divideMap(mapaggregated,numReduceTasks);

            coordinateur.assignReduceTasks(numReduceTasks);
            ArrayList<Map<String, Integer>> resultsReducing = coordinateur.executeReduceTasks(resultsMapping);

            ArrayList<Map<String, Integer>> results = coordinateur.aggregateResults(resultsReducing);

            // Afficher le dictionnaire
            System.out.println(results);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Enregistre le temps de fin
            long endTime = System.currentTimeMillis();

            // Calcule et affiche la différence
            System.out.println("Temps d'exécution: " + (endTime - startTime) + " millisecondes");
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

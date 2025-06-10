import java.util.ArrayList;
import java.util.Map;

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

            //Map<String, Integer> mapaggregated = MapUtils.aggregateMaps(resultsMapping);
            //ArrayList<Map<String, Integer>> dividedMaps = MapUtils.divideMap(mapaggregated, numReduceTasks);

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

}

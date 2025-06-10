import java.util.ArrayList;
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
            //Map<String, Integer> mapaggregated = MapUtils.aggregateMaps(resultsMapping);
            //ArrayList<Map<String, Integer>> dividedMaps = MapUtils.divideMap(mapaggregated, numReduceTasks);
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
}

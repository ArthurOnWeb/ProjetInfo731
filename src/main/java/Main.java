import java.util.ArrayList;
import java.util.Map;

/**
 * Entry point of the Map/Reduce word count example.
 */
public class Main {

    /**
     * Launches the program with a fixed number of map and reduce tasks.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        // Record the start time
        long startTime = System.currentTimeMillis();

        try {
            String text = "lesmiserables.txt";
            CoordinateurNode coordinateur = new CoordinateurNode();

            // Define the number of MapTask and ReduceTask instances
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

            // Display the dictionary
            System.out.println(results);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Record the end time
            long endTime = System.currentTimeMillis();
            // Compute and print the duration
            System.out.println("Execution time: " + (endTime - startTime) + " milliseconds");
        }
    }

}

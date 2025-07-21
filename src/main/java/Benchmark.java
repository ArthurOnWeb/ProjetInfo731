import java.util.ArrayList;
import java.util.Map;

/**
 * Simple benchmarking tool used to measure the execution time of the program
 * for different numbers of tasks.
 */
public class Benchmark {

    /**
     * Launches a series of benchmarks with an increasing number of tasks.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        // Run benchmarks with different numbers of MapTask and ReduceTask
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

    /**
     * Executes a benchmark iteration with the provided number of map/reduce tasks.
     *
     * @param numMapTasks number of tasks to use for the benchmark
     */
    private static void runBenchmark(int numMapTasks) {
        // Record the start time
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
            // Record the end time
            long endTime = System.currentTimeMillis();
            // Compute and print the duration
            System.out.println("Execution time: " + (endTime - startTime) + " milliseconds for " + numMapTasks + " mapper/reducer pair(s)");
            System.out.println("--------------");
        }
    }
}

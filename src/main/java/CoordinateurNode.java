import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Central component coordinating the map and reduce phases.
 * <p>
 * It assigns tasks to threads, waits for their completion and finally aggregates
 * all intermediate results.
 */
public class CoordinateurNode {
    /** Blocks of text on which the map phase operates. */
    private ArrayList<String> textChunks;
    /** Collection of map tasks. */
    private ArrayList<MapTask> mapTasks;
    /** Collection of reduce tasks. */
    private ArrayList<ReduceTask> reduceTasks;

    /**
     * Sets the text chunks to be processed.
     *
     * @param textChunks list of blocks created by {@link Splitter}
     */
    public void setTextChunks(ArrayList<String> textChunks) {
        this.textChunks = textChunks;
    }

    /**
     * Creates and stores the requested number of map tasks.
     *
     * @param numMapTasks how many tasks to instantiate
     */
    public void assignMapTasks(int numMapTasks) {
        mapTasks = new ArrayList<>();
        for (int i = 0; i < numMapTasks; i++) {
            MapTask mapTask = new MapTask();
            mapTasks.add(mapTask);
        }
    }

    /**
     * Executes each map task on a separate thread.
     *
     * @return a list of maps containing partial word counts
     * @throws InterruptedException if a thread is interrupted while waiting
     */
    public ArrayList<Map<String, Integer>> executeMapTasks() throws InterruptedException {
        int numMapTasks = mapTasks.size();
        ArrayList<Map<String, Integer>> results = new ArrayList<>();
        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < textChunks.size(); i++) {
            final int index = i;
            Thread thread = new Thread(() -> {
                String chunk = textChunks.get(index);
                MapTask mapTask = mapTasks.get(index % numMapTasks);
                Map<String, Integer> result = mapTask.execute(chunk);

                synchronized (results) {
                    results.add(result);
                }
            });
            threads.add(thread);
            thread.start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        return results;
    }

    /**
     * Creates and stores the requested number of reduce tasks.
     *
     * @param numReduceTasks number of reduce tasks to instantiate
     */
    public void assignReduceTasks(int numReduceTasks) {
        reduceTasks = new ArrayList<>();
        for (int i = 0; i < numReduceTasks; i++) {
            ReduceTask reduceTask = new ReduceTask();
            reduceTasks.add(reduceTask);
        }
    }

    /**
     * Executes the reduce tasks in parallel.
     *
     * @param mapResults intermediate results produced by the map phase
     * @return a list containing partial reductions
     * @throws InterruptedException if a thread is interrupted while waiting
     */
    public ArrayList<Map<String, Integer>> executeReduceTasks(ArrayList<Map<String, Integer>> mapResults) throws InterruptedException {
        int numReduceTasks = reduceTasks.size();
        ArrayList<Map<String, Integer>> finalResults = new ArrayList<>();
        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < mapResults.size(); i++) {
            final int index = i;
            Thread thread = new Thread(() -> {
                Map<String, Integer> mapResult = mapResults.get(index);
                ReduceTask reduceTask = reduceTasks.get(index % numReduceTasks);
                Map<String, Integer> partialResult = reduceTask.execute(mapResult);

                synchronized (finalResults) {
                    finalResults.add(partialResult);
                }
            });
            threads.add(thread);
            thread.start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        return finalResults;
    }

    /**
     * Aggregates the results of all reduce tasks into a single map.
     *
     * @param reduceResults the partial results produced by each reduce task
     * @return a list containing one aggregated map
     */
    public ArrayList<Map<String, Integer>> aggregateResults(ArrayList<Map<String, Integer>> reduceResults) {
        ArrayList<Map<String, Integer>> finalResults = new ArrayList<>();
        Map<String, Integer> aggregatedResults = new HashMap<>();

        for (Map<String, Integer> reduceResult : reduceResults) {
            synchronized (aggregatedResults) {
                for (Map.Entry<String, Integer> entry : reduceResult.entrySet()) {
                    String word = entry.getKey();
                    int count = entry.getValue();
                    aggregatedResults.put(word, aggregatedResults.getOrDefault(word, 0) + count);
                }
            }
        }

        finalResults.add(aggregatedResults);

        return finalResults;
    }
}

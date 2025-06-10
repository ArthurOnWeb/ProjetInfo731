import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CoordinateurNode {
    private ArrayList<String> textChunks;
    private ArrayList<MapTask> mapTasks;
    private ArrayList<ReduceTask> reduceTasks;

    public void setTextChunks(ArrayList<String> textChunks) {
        this.textChunks = textChunks;
    }

    public void assignMapTasks(int numMapTasks) {
        mapTasks = new ArrayList<>();
        for (int i = 0; i < numMapTasks; i++) {
            MapTask mapTask = new MapTask();
            mapTasks.add(mapTask);
        }
    }

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
                    //System.out.println("MapTask " + index % numMapTasks + " a traité le chunk : " + chunk);
                    //System.out.println("Résultat partiel : " + result);
                }
            });
            threads.add(thread);
            thread.start();
        }

        // Attendre que tous les threads aient terminé
        for (Thread thread : threads) {
            thread.join();
        }

        return results;
    }

    public void assignReduceTasks(int numReduceTasks) {
        reduceTasks = new ArrayList<>();
        for (int i = 0; i < numReduceTasks; i++) {
            ReduceTask reduceTask = new ReduceTask();
            reduceTasks.add(reduceTask);
        }
    }

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
                    //System.out.println("ReduceTask " + index % numReduceTasks + " a traité les résultats partiels : " + mapResult);
                    //System.out.println("Résultat partiel après réduction : " + partialResult);
                }
            });
            threads.add(thread);
            thread.start();
        }

        // Attendre que tous les threads aient terminé
        for (Thread thread : threads) {
            thread.join();
        }

        return finalResults;
    }

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

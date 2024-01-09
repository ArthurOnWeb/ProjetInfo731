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
    public ArrayList<Map<String,Integer>> executeMapTasks() {
        int numMapTasks = mapTasks.size();
        ArrayList<Map<String,Integer>> results=new ArrayList<>();
        for (int i = 0; i < textChunks.size(); i++) {
            String chunk = textChunks.get(i);
            MapTask mapTask = mapTasks.get(i % numMapTasks); // Répartition des tâches entre les MapTasks
            Map<String, Integer> result = mapTask.execute(chunk);
            results.add(result);
            System.out.println("MapTask " + i % numMapTasks + " a traité le chunk : " + chunk);
            System.out.println("Résultat partiel : " + result);
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
    public ArrayList<Map<String, Integer>> executeReduceTasks(ArrayList<Map<String, Integer>> mapResults) {
        int numReduceTasks = reduceTasks.size();
        ArrayList<Map<String, Integer>> finalResults = new ArrayList<>();

        for (int i = 0; i < mapResults.size(); i++) {
            Map<String, Integer> mapResult = mapResults.get(i);
            ReduceTask reduceTask = reduceTasks.get(i % numReduceTasks); // Répartition des tâches entre les ReduceTasks
            Map<String, Integer> partialResult = reduceTask.execute(mapResult);

            // Traiter les résultats partiels, stocker ou envoyer à d'autres étapes selon tes besoins
            // ...

            System.out.println("ReduceTask " + i % numReduceTasks + " a traité les résultats partiels : " + mapResult);
            System.out.println("Résultat partiel après réduction : " + partialResult);

            finalResults.add(partialResult);
        }
        return finalResults;
    }

    public ArrayList<Map<String, Integer>> aggregateResults(ArrayList<Map<String, Integer>> reduceResults) {
        ArrayList<Map<String, Integer>> finalResults = new ArrayList<>();

        // Créer une Map pour stocker temporairement les résultats partiels pour chaque mot
        Map<String, Integer> aggregatedResults = new HashMap<>();

        for (Map<String, Integer> reduceResult : reduceResults) {
            // Additionner les valeurs pour chaque mot dans la Map agrégée
            for (Map.Entry<String, Integer> entry : reduceResult.entrySet()) {
                String word = entry.getKey();
                int count = entry.getValue();
                aggregatedResults.put(word, aggregatedResults.getOrDefault(word, 0) + count);
            }
        }

        // Ajouter la Map agrégée des résultats finaux à la liste
        finalResults.add(aggregatedResults);

        return finalResults;
    }
}

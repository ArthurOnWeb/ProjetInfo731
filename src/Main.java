import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String text = "discours.txt";
        CoordinateurNode coordinateur = new CoordinateurNode();
        coordinateur.setTextChunks(Splitter.splitText(text));
        // Définir le nombre de MapTask et ReduceTask souhaité
        int numMapTasks = 5;
        int numReduceTasks = 3;

        coordinateur.assignMapTasks(numMapTasks);
        ArrayList<Map<String,Integer>> resultsMapping = coordinateur.executeMapTasks();
        
        coordinateur.assignReduceTasks(numReduceTasks);
        ArrayList<Map<String,Integer>> resultsReducing=coordinateur.executeReduceTasks(resultsMapping);

        ArrayList<Map<String, Integer>> results = coordinateur.aggregateResults(resultsReducing);
        // Afficher ou utiliser les résultats selon tes besoins
        System.out.println(results);
    }


}

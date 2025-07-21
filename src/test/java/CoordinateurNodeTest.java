import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link CoordinateurNode} covering full map/reduce flow.
 */
public class CoordinateurNodeTest {

    @Test
    public void testEmptyFileProducesEmptyMap() throws Exception {
        CoordinateurNode coord = new CoordinateurNode();
        ArrayList<String> chunks = Splitter.splitText("empty.txt", 1);
        coord.setTextChunks(chunks);
        coord.assignMapTasks(1);
        ArrayList<Map<String, Integer>> mapResults = coord.executeMapTasks();
        coord.assignReduceTasks(1);
        ArrayList<Map<String, Integer>> reduceResults = coord.executeReduceTasks(mapResults);
        ArrayList<Map<String, Integer>> aggregated = coord.aggregateResults(reduceResults);

        assertEquals(1, aggregated.size());
        assertTrue(aggregated.get(0).isEmpty());
    }

    @Test
    public void testUnicodeCharactersAreIgnored() throws Exception {
        CoordinateurNode coord = new CoordinateurNode();
        ArrayList<String> chunks = Splitter.splitText("unicode.txt", 2);
        coord.setTextChunks(chunks);
        coord.assignMapTasks(2);
        ArrayList<Map<String, Integer>> mapResults = coord.executeMapTasks();
        coord.assignReduceTasks(2);
        ArrayList<Map<String, Integer>> reduceResults = coord.executeReduceTasks(mapResults);
        ArrayList<Map<String, Integer>> aggregated = coord.aggregateResults(reduceResults);

        Map<String, Integer> expected = new HashMap<>();
        expected.put("hello", 2);
        assertEquals(expected, aggregated.get(0));
    }

    @Test
    public void testConcurrentExecutionAggregatesCorrectly() throws Exception {
        CoordinateurNode coord = new CoordinateurNode();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("a ");
        }
        java.nio.file.Files.write(java.nio.file.Paths.get("src/main/resources/concurrency.txt"), sb.toString().getBytes());

        ArrayList<String> chunks = Splitter.splitText("concurrency.txt", 10);
        coord.setTextChunks(chunks);
        coord.assignMapTasks(10);
        ArrayList<Map<String, Integer>> mapResults = coord.executeMapTasks();
        coord.assignReduceTasks(5);
        ArrayList<Map<String, Integer>> reduceResults = coord.executeReduceTasks(mapResults);
        ArrayList<Map<String, Integer>> aggregated = coord.aggregateResults(reduceResults);

        assertEquals(1, aggregated.size());
        assertEquals(Collections.singletonMap("a", 100), aggregated.get(0));
        java.nio.file.Files.deleteIfExists(java.nio.file.Paths.get("src/main/resources/concurrency.txt"));
    }
}

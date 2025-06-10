import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class MapTaskTest {
    @Test
    public void testExecuteCountsWords() {
        MapTask mapTask = new MapTask();
        Map<String, Integer> result = mapTask.execute("Hello world Hello");

        Map<String, Integer> expected = new HashMap<>();
        expected.put("Hello", 2);
        expected.put("world", 1);
        assertEquals(expected, result);
    }

    @Test
    public void testExecuteRemovesPunctuation() {
        MapTask mapTask = new MapTask();
        Map<String, Integer> result = mapTask.execute("Bonjour, monde! Bonjour?");

        Map<String, Integer> expected = new HashMap<>();
        expected.put("Bonjour", 2);
        expected.put("monde", 1);
        assertEquals(expected, result);
    }
}

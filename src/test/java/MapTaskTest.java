import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * Unit tests for {@link MapTask}.
 */
public class MapTaskTest {
    @Test
    /**
     * Ensures that words are correctly counted.
     */
    public void testExecuteCountsWords() {
        MapTask mapTask = new MapTask();
        Map<String, Integer> result = mapTask.execute("Hello world Hello");

        Map<String, Integer> expected = new HashMap<>();
        expected.put("hello", 2);
        expected.put("world", 1);
        assertEquals(expected, result);
    }

    @Test
    /**
     * Ensures that punctuation characters are removed before counting.
     */
    public void testExecuteRemovesPunctuation() {
        MapTask mapTask = new MapTask();
        Map<String, Integer> result = mapTask.execute("Bonjour, monde! Bonjour?");

        Map<String, Integer> expected = new HashMap<>();
        expected.put("bonjour", 2);
        expected.put("monde", 1);
        assertEquals(expected, result);
    }

    @Test
    /**
     * Ensures that empty tokens are ignored after cleaning.
     */
    public void testExecuteSkipsEmptyTokens() {
        MapTask mapTask = new MapTask();
        Map<String, Integer> result = mapTask.execute("  !!!  ");

        assertTrue(result.isEmpty());
    }
}

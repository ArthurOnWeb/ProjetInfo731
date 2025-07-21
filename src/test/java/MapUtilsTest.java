import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for utility methods in {@link MapUtils}.
 */
public class MapUtilsTest {

    @Test
    public void testAggregateMapsEmptyListReturnsEmptyMap() {
        ArrayList<Map<String, Integer>> maps = new ArrayList<>();
        Map<String, Integer> result = MapUtils.aggregateMaps(maps);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testDivideMapSplitsEntriesEvenly() {
        Map<String, Integer> input = new HashMap<>();
        input.put("a", 1);
        input.put("b", 2);
        input.put("c", 3);
        input.put("d", 4);
        ArrayList<Map<String, Integer>> parts = MapUtils.divideMap(input, 2);
        assertEquals(2, parts.size());
        int totalEntries = parts.get(0).size() + parts.get(1).size();
        assertEquals(input.size(), totalEntries);
        Map<String, Integer> recombined = new HashMap<>();
        for (Map<String, Integer> part : parts) {
            recombined.putAll(part);
        }
        assertEquals(input, recombined);
    }
}

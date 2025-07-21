import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * Unit tests for {@link ReduceTask}.
 */
public class ReduceTaskTest {
    @Test
    /**
     * Ensures that the reduce phase returns identical counts for a single map.
     */
    public void testExecuteReturnsSameCounts() {
        Map<String, Integer> partial = new HashMap<>();
        partial.put("a", 1);
        partial.put("b", 2);

        ReduceTask reduceTask = new ReduceTask();
        Map<String, Integer> result = reduceTask.execute(partial);

        assertEquals(partial, result);
        assertNotSame(partial, result);
    }
}

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.io.*;

/**
 * Unit tests for {@link Splitter}.
 */
public class SplitterTest {
    @Test
    /**
     * Verifies that the text file is split into the expected number of blocks.
     */
    public void testSplitTextDividesFile() throws Exception {
        ArrayList<String> blocks = Splitter.splitText("discours.txt", 3);
        assertEquals(3, blocks.size());

        StringBuilder expected = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/discours.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                expected.append(line).append(" ");
            }
        }

        StringBuilder reconstructed = new StringBuilder();
        for (String block : blocks) {
            reconstructed.append(block);
        }

        assertEquals(expected.toString(), reconstructed.toString());
    }
}

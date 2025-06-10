import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.io.*;

public class SplitterTest {
    @Test
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

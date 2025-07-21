import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Utility class used to split an input file into several text chunks.
 */
public class Splitter {

    /**
     * Splits the given file into {@code nombreElements} blocks of text.
     *
     * @param nomFichier      the file name located in {@code src/main/resources}
     * @param nombreElements  number of chunks to create
     * @return a list containing each chunk in order
     */
    public static ArrayList<String> splitText(String nomFichier, int nombreElements) {
        ArrayList<String> blocs = new ArrayList<>();
        StringBuilder texteComplet = new StringBuilder();
        String cheminFichier = "src/main/resources/" + nomFichier;

        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                texteComplet.append(ligne).append(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Split the entire text into equally sized blocks
        int longueurBloc = texteComplet.length() / nombreElements;
        int debutIndex = 0;

        for (int i = 0; i < nombreElements; i++) {
            int finIndex = debutIndex + longueurBloc;
            if (i == nombreElements - 1) {
                // Last block takes the remainder of the text
                blocs.add(texteComplet.substring(debutIndex));
            } else {
                blocs.add(texteComplet.substring(debutIndex, finIndex));
            }
            debutIndex = finIndex;
        }

        return blocs;
    }
}

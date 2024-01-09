import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Splitter {
    // Méthode pour diviser le texte en blocs
    public static ArrayList<String> splitText(String nomFichier) {
        ArrayList<String> blocs = new ArrayList<>();
        String cheminFichier = "src/Ressources/" + nomFichier;

        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                blocs.add(ligne);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blocs;
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Splitter {
    // Méthode pour diviser le texte en blocs
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

        // Diviser le texte complet en blocs égaux
        int longueurBloc = texteComplet.length() / nombreElements;
        int debutIndex = 0;

        for (int i = 0; i < nombreElements; i++) {
            int finIndex = debutIndex + longueurBloc;
            if (i == nombreElements - 1) {
                // Dernier bloc, prenez le reste du texte
                blocs.add(texteComplet.substring(debutIndex));
            } else {
                blocs.add(texteComplet.substring(debutIndex, finIndex));
            }
            debutIndex = finIndex;
        }

        return blocs;
    }
}

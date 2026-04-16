package jeu;

import java.io.*;

public class GestionnaireSauvegarde implements Serializable {
    private static final long serialVersionUID = 1L;
    private String cheminSauvegarde;
    private String extensionFichier;

    public GestionnaireSauvegarde() {
        this("sauvegardes/partie", ".ser");
    }

    public GestionnaireSauvegarde(String cheminSauvegarde, String extensionFichier) {
        this.cheminSauvegarde = cheminSauvegarde;
        this.extensionFichier = extensionFichier;
    }

    public Partie sauvegarder(Partie partie) {
        String chemin = cheminSauvegarde + extensionFichier;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chemin))) {
            oos.writeObject(partie);
            System.out.println("Partie sauvegardée dans : " + chemin);
            return partie;
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
            return null;
        }
    }

    public Partie charger() {
        String chemin = cheminSauvegarde + extensionFichier;
        if (!existSauvegarde()) {
            System.out.println("Aucune sauvegarde trouvée.");
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chemin))) {
            Partie partie = (Partie) ois.readObject();
            System.out.println("Partie chargée depuis : " + chemin);
            return partie;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur lors du chargement : " + e.getMessage());
            return null;
        }
    }

    public boolean existSauvegarde() {
        File fichier = new File(cheminSauvegarde + extensionFichier);
        return fichier.exists() && fichier.isFile();
    }

    public String getCheminSauvegarde() {
        return cheminSauvegarde;
    }

    public String getExtensionFichier() {
        return extensionFichier;
    }
}

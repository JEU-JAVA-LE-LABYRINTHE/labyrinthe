package jeu;

import java.io.*;

public class GestionnaireSauvegarde implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String NOM_FICHIER = "partie.ser";
    private static final String NOM_DOSSIER = "sauvegarde";

    private String cheminSauvegarde;
    private String extentionFichier;

    public GestionnaireSauvegarde() {
        this.cheminSauvegarde = obtenirDossierSauvegarde().getAbsolutePath();
        this.extentionFichier = ".ser";
    }

    public Partie sauvegarder(Partie partie) {
        File dossier = obtenirDossierSauvegarde();
        if (!dossier.exists()) dossier.mkdirs();
        File fichier = new File(dossier, NOM_FICHIER);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier))) {
            oos.writeObject(partie);
            System.out.println("Sauvegarde → " + fichier.getAbsolutePath());
            return partie;
        } catch (IOException e) {
            System.err.println("Erreur sauvegarde : " + e.getMessage());
            return null;
        }
    }

    public Partie charger() {
        File fichier = new File(obtenirDossierSauvegarde(), NOM_FICHIER);
        System.out.println("Chargement depuis → " + fichier.getAbsolutePath());
        if (!fichier.exists()) {
            System.err.println("Fichier introuvable : " + fichier.getAbsolutePath());
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier))) {
            return (Partie) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur chargement : " + e.getMessage());
            return null;
        }
    }

    public boolean existSauvegarde() {
        return new File(obtenirDossierSauvegarde(), NOM_FICHIER).exists();
    }

    public String getCheminComplet() {
        return new File(obtenirDossierSauvegarde(), NOM_FICHIER).getAbsolutePath();
    }

    private static File obtenirDossierSauvegarde() {
        return new File(System.getProperty("user.dir"), NOM_DOSSIER);
    }
}

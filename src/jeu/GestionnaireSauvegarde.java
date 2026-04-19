package jeu;

import java.io.*;

public class GestionnaireSauvegarde implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String NOM_FICHIER = "partie.ser";
    private static final String NOM_DOSSIER = "sauvegarde";

    public Partie sauvegarder(Partie partie) {
        File dossier = obtenirDossierSauvegarde();
        if (!dossier.exists()) dossier.mkdirs();
        File fichier = new File(dossier, NOM_FICHIER);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier))) {
            oos.writeObject(partie);
            return partie;
        } catch (IOException e) {
            System.err.println("Erreur sauvegarde : " + e.getMessage());
            return null;
        }
    }

    public Partie charger() {
        File fichier = new File(obtenirDossierSauvegarde(), NOM_FICHIER);
        if (!fichier.exists()) return null;
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
        try {
            File location = new File(
                GestionnaireSauvegarde.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI());
            File base;
            if (location.isFile()) {
                // Exécution depuis un JAR → dossier du JAR
                base = location.getParentFile();
            } else {
                // Exécution depuis l'IDE → remonter depuis out/production/labyrinthe
                base = location;
                for (int i = 0; i < 3; i++) {
                    if (base.getParentFile() != null) base = base.getParentFile();
                }
            }
            return new File(base, NOM_DOSSIER);
        } catch (Exception e) {
            return new File(System.getProperty("user.dir"), NOM_DOSSIER);
        }
    }
}

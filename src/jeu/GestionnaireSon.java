package jeu;

import java.io.Serializable;

/**
 * Gestionnaire de son et musique - Version stub pour développement
 */
public class GestionnaireSon implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Zone {
        ZAMAN,
        PREHISTOIRE,
        EGYPTE_ANTIQUE,
        MOYEN_AGE,
        FUTUR
    }

    public enum EffetSonore {
        BORD_DE_MORT,
        VICTOIRE,
        DEFAITE
    }

    private boolean musicaAccelereeActive = false;

    public GestionnaireSon() {
    }

    public void activerMusiqueAcceleree() {
        musicaAccelereeActive = true;
    }

    public void desactiverMusiqueAcceleree() {
        musicaAccelereeActive = false;
    }

    public boolean isMusicaAccelereeActive() {
        return musicaAccelereeActive;
    }

    public void jouerMusiqueZone(Zone zone) {
    }

    public void jouerEffetSonore(EffetSonore effet) {
        System.out.println("Effet sonore: " + effet);
    }

    public String sauvegarder(Partie partie) {
        return "Sauvegarde en cours de développement";
    }

    public String charger() {
        return "Chargement en cours de développement";
    }
}


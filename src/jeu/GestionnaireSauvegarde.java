package jeu;

import java.io.Serializable;

public class GestionnaireSauvegarde implements Serializable {
    private static final long serialVersionUID = 1L;

    public GestionnaireSauvegarde() {
    }

    public String sauvegarder(Partie partie) {
        return "Sauvegarde en développement";
    }

    public Partie charger() {
        return null;
    }
}

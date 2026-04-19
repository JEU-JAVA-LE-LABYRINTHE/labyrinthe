package jeu;

import personnes.Joueur;

public abstract class Commandes {
    protected String motCommande;

    protected Commandes(String motCommande) {
        this.motCommande = motCommande;
    }

    public abstract void executer(Joueur joueur);

    public abstract String getDescription();

    public String getMotCommande() {
        return motCommande;
    }
}

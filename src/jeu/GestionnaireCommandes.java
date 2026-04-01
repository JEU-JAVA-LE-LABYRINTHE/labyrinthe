package jeu;

import personnes.Joueur;
import java.io.Serializable;

public class GestionnaireCommandes implements Serializable {
    private static final long serialVersionUID = 1L;

    public GestionnaireCommandes() {
    }

    public String traiterCommandes(String input, Partie partie, Joueur joueur) {
        if (input == null || input.isEmpty()) {
            return "Commande vide.";
        }

        String cmd = input.trim().toUpperCase();

        switch(cmd) {
            case "O", "OUEST" -> { return partie.moveOuest(); }
            case "E", "EST" -> { return partie.moveEst(); }
            case "Z" -> { return partie.retourZaman(); }
            case "L", "REGARDER" -> { return partie.regarder(); }
            case "SAC", "INVENTAIRE", "INV" -> { return partie.inventaireTexte(); }
            case "STATUS", "ETAT", "STAT" -> { return partie.statusTexte(); }
            case "TEMPS", "T" -> { return "Temps disponible (pas de limite à Zaman)"; }
            case "AIDE", "H", "?" -> { return "Aide disponible à Zaman"; }
            default -> {
                if (cmd.startsWith("P ") || cmd.startsWith("PRENDRE ")) {
                    String nomObjet = cmd.substring(cmd.indexOf(" ") + 1);
                    return partie.prendreObjet(nomObjet);
                }
                return "Commande inconnue.";
            }
        }
    }
}
package jeu;

import personnes.Joueur;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireCommandes implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Commandes> commandes;

    public GestionnaireCommandes() {
        this.commandes = new ArrayList<>();
    }

    public void enregistrer(Commandes commande) {
        commandes.add(commande);
    }

    public void executer(String input, Partie partie, Joueur joueur) {
        traiterCommandes(input, partie, joueur);
    }

    public Commandes obtenirCommande(String motCommande) {
        if (motCommande == null) return null;
        for (Commandes c : commandes) {
            if (c.getMotCommande().equalsIgnoreCase(motCommande)) return c;
        }
        return null;
    }

    public String afficherAide() {
        StringBuilder sb = new StringBuilder("Commandes disponibles :\n");
        for (Commandes c : commandes) {
            sb.append("  ").append(c.getMotCommande())
              .append(" — ").append(c.getDescription()).append("\n");
        }
        return sb.toString();
    }


    public String traiterCommandes(String input, Partie partie, Joueur joueur) {
        if (input == null || input.isEmpty()) {
            return "Commande vide.";
        }

        String cmd = input.trim().toUpperCase();

        return switch (cmd) {
            case "O", "OUEST"                  -> partie.moveOuest();
            case "E", "EST"                    -> partie.moveEst();
            case "Z"                           -> partie.retourZaman();
            case "L", "REGARDER"               -> partie.regarder();
            case "CH", "CHERCHER"              -> partie.chercher();
            case "OUVRIR"                      -> partie.ouvrir();
            case "I", "INDICE"                 -> partie.obtenirIndice();
            case "OB", "OBSERVER"              -> partie.observer();
            case "SAC", "INVENTAIRE", "INV"    -> partie.inventaireTexte();
            case "STATUS", "ETAT", "STAT"      -> partie.statusTexte();
            case "AIDE", "H", "?"              -> partie.afficherAide();
            case "SAVE", "SAUVEGARDER"         -> partie.sauvegarder();
            case "LOAD", "CHARGER"             -> partie.charger();
            case "NOUVELLE", "N"               -> partie.nouvellePartie();
            case "DEVERROUILLER"               -> partie.deverrouiller("");
            default -> {
                if (cmd.startsWith("P ") || cmd.startsWith("PRENDRE ")) {
                    yield partie.prendreObjet(cmd.substring(cmd.indexOf(" ") + 1));
                }
                if (cmd.startsWith("D ") || cmd.startsWith("DEPOSER ")) {
                    yield partie.deposer(cmd.substring(cmd.indexOf(" ") + 1));
                }
                if (cmd.startsWith("LAISSER ")) {
                    yield partie.laisserObjetDansZone(cmd.substring(cmd.indexOf(" ") + 1));
                }
                if (cmd.startsWith("R ") || cmd.startsWith("REPONDRE ")) {
                    yield partie.repondre(cmd.substring(cmd.indexOf(" ") + 1));
                }
                if (cmd.startsWith("DEVERROUILLER ")) {
                    yield partie.deverrouiller(cmd.substring(cmd.indexOf(" ") + 1));
                }
                yield "Commande inconnue : " + cmd + "  (tapez AIDE)";
            }
        };
    }
}

package controller;

import items.Item;
import items.Lettre;
import jeu.Partie;
import zones.Zones;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JeuController {
    private final Partie partie;
    
    private static final Map<String, String> DIRECTION_MAP = Map.ofEntries(
        Map.entry("ouest", "moveOuest"),
        Map.entry("o", "moveOuest"),
        Map.entry("est", "moveEst"),
        Map.entry("e", "moveEst"),
        Map.entry("z", "retourZaman")
    );

    public JeuController() {
        this.partie = new Partie();
    }

    public void demarrerJeu() {
        partie.demarrer();
    }

    public boolean deplacerJoueur(String direction) {
        String d = direction == null ? "" : direction.trim().toLowerCase();
        String msg = executerMouvement(d);
        
        if (msg == null) {
            return false;
        }
        
        System.out.println(msg);
        System.out.println(partie.statusTexte());
        return true;
    }
    
    private String executerMouvement(String direction) {
        return switch (direction) {
            case "ouest", "o" -> partie.moveOuest();
            case "est", "e" -> partie.moveEst();
            case "z" -> partie.retourZaman();
            default -> null;
        };
    }

    public String ramasserObjet(String nomObjet) {
        String msg = partie.prendreObjet(nomObjet);
        System.out.println(msg);
        return msg;
    }

    public void afficherEtatJeu() {
        System.out.println(partie.statusTexte());
    }

    public String traiterCommande(String commande) {
        return partie.traiterCommande(commande);
    }

    public boolean isJeuEnCours() {
        return partie.isJeuEnCours();
    }

    public String getNomJoueur() {
        return partie.getJoueur().getNom();
    }

    public int getScore() {
        return partie.getJoueur().getScore();
    }

    public int getNombreVies() {
        return partie.getJoueur().getNombreVies();
    }

    public Zones getZoneCourante() {
        return partie.getZoneCourante();
    }

    public String getNomZoneCourante() {
        Zones z = getZoneCourante();
        return z == null ? "-" : z.getNom();
    }

    public String sauvegarder() {
        return partie.sauvegarder();
    }

    public String charger() {
        return partie.charger();
    }

    public String nouvellePartie() {
        return partie.nouvellePartie();
    }

    public void demarrerNouvellePartie(String nom) {
        partie.demarrer(nom);
    }

    public List<String> getInventaireItems() {
        List<String> noms = new ArrayList<>();
        for (Item item : partie.getJoueur().getInventaire().getObjets()) {
            noms.add(item.getNom());
        }
        return noms;
    }

    public boolean isVictoire() {
        return partie.isVictoire();
    }

    public String getLettreNomZoneCourante() {
        return null;
    }
}

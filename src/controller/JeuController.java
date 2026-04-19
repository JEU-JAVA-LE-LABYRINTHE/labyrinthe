package controller;

import items.Item;
import items.Lettre;
import jeu.GestionnaireCommandes;
import jeu.GestionnaireSauvegarde;
import jeu.Partie;
import zones.Zones;
import java.util.ArrayList;
import java.util.List;

public class JeuController {
    private Partie partie;

    public JeuController() {
        this.partie = new Partie();
    }

    public void demarrerJeu() {
        partie.demarrer();
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
        GestionnaireSauvegarde gs = new GestionnaireSauvegarde();
        Partie loaded = gs.charger();
        if (loaded == null) return "Aucune sauvegarde trouvée.";
        this.partie = loaded;
        this.partie.gestionnaireCmd = new GestionnaireCommandes();
        this.partie.gestionnaireSauvegarde = gs;
        return "Partie chargée — " + loaded.getJoueur().getNom()
             + " | Vies : " + loaded.getJoueur().getNombreVies()
             + " | Lettres : " + loaded.getJoueur().getLettres().size() + "/4";
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

    public String getQuestionEnigme() {
        Zones z = partie.getZoneCourante();
        if (z == null || z.getEnigme() == null) return "Votre réponse :";
        return z.getEnigme().getQuestion();
    }

    public boolean toutesLettresCollectees() {
        return partie.getJoueur().getLettres().size() >= 4;
    }

    public boolean estAZaman() {
        Zones z = partie.getZoneCourante();
        return z != null && z.getNom().equals("Zaman");
    }

    public String getLettresJoueur() {
        return partie.getJoueur().lettresPourMot();
    }

    public boolean isEnigmeActive() {
        if (!partie.isJeuEnCours()) return false;
        Zones z = partie.getZoneCourante();
        if (z == null) return false;
        return z.obtenirCoffre() != null && z.obtenirCoffre().estOuvert() && !z.isTerminee();
    }
}

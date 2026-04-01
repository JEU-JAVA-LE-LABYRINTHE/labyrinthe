package jeu;

import inventaire.Inventaire;
import items.Item;
import items.Objet;
import personnes.Joueur;
import zones.*;
import java.io.Serializable;

public class Partie implements Serializable {
    private static final long serialVersionUID = 1L;

    private CarteZones carteZones;
    private Joueur joueur;
    private boolean jeuEnCours;
    private long zoneDebutMillis;

    public transient GestionnaireCommandes gestionnaireCommandes;
    public transient GestionnaireSauvegarde gestionnaireSauvegarde;

    public Partie() {
        this.carteZones = new CarteZones();
        this.gestionnaireCommandes = new GestionnaireCommandes();
        this.gestionnaireSauvegarde = new GestionnaireSauvegarde();
        resetPartie();
    }

    public void resetPartie() {
        this.joueur = new Joueur("Joueur", carteZones.getZoneDepart(), 3, 6);
        this.jeuEnCours = true;
        this.zoneDebutMillis = 0;
    }

    public String traiterCommande(String input) {
        if (gestionnaireCommandes == null) gestionnaireCommandes = new GestionnaireCommandes();
        if (!jeuEnCours) return "Le jeu est terminé.";
        return gestionnaireCommandes.traiterCommandes(input, this, joueur);
    }

    public void demarrer() {
        resetPartie();
        joueur.seDeplacer(carteZones.getZoneDepart());
    }

    public boolean isJeuEnCours() {
        if (!jeuEnCours) return false;
        if (joueur == null || !joueur.estEnVie()) return false;
        return true;
    }

    public String moveOuest() {
        return "Non disponible pour le moment";
    }

    public String moveEst() {
        return "Non disponible pour le moment";
    }

    public String retourZaman() {
        joueur.seDeplacer(carteZones.getZaman());
        return "Retour à Zaman";
    }

    public String regarder() {
        Zones z = (Zones) joueur.getZoneActuelle();
        return z == null ? "Aucune zone actuelle" : z.afficherDescription();
    }

    public String inventaireTexte() {
        Inventaire inv = joueur.getInventaire();
        if (inv.getTaille() == 0) return "Sac vide";
        StringBuilder sb = new StringBuilder("Sac:\n");
        for (Item item : inv.getObjets()) {
            sb.append("- ").append(item.getNom()).append("\n");
        }
        return sb.toString();
    }

    public String prendreObjet(String nomObjet) {
        Zones z = (Zones) joueur.getZoneActuelle();
        if (!(z instanceof Zaman)) return "Vous ne pouvez prendre des objets qu'à Zaman";
        
        Zaman zaman = (Zaman) z;
        Objet pris = zaman.prendreObjetDisponible(nomObjet);
        if (pris == null) return "Objet non trouvé";
        
        joueur.getInventaire().ajouter(pris);
        return "Objet pris : " + pris.getNom();
    }

    public String statusTexte() {
        Zones z = (Zones) joueur.getZoneActuelle();
        StringBuilder sb = new StringBuilder();
        sb.append("STATUS:\n");
        sb.append("- Joueur: ").append(joueur.getNom()).append("\n");
        sb.append("- Vies: ").append(joueur.getNombreVies()).append("\n");
        sb.append("- Zone: ").append(z == null ? "-" : z.getNom()).append("\n");
        sb.append("- Objets: ").append(joueur.getInventaire().getTaille()).append("\n");
        return sb.toString();
    }

    public String nouvellePartie() {
        demarrer();
        return "Nouvelle partie démarrée!";
    }

    public String sauvegarder() {
        if (gestionnaireSauvegarde == null) gestionnaireSauvegarde = new GestionnaireSauvegarde();
        return gestionnaireSauvegarde.sauvegarder(this);
    }

    public String charger() {
        if (gestionnaireSauvegarde == null) gestionnaireSauvegarde = new GestionnaireSauvegarde();
        Partie p = gestionnaireSauvegarde.charger();
        return p != null ? "Partie chargée" : "Erreur chargement";
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public Zones getZoneCourante() {
        return (Zones) joueur.getZoneActuelle();
    }

    public int getTempsRestantZoneSecPublic() {
        return 300;
    }
}

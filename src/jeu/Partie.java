package jeu;

import inventaire.Inventaire;
import items.Item;
import items.Lettre;
import items.Objet;
import personnes.Joueur;
import zones.*;
import java.io.Serializable;
import java.util.List;

public class Partie implements Serializable {
    private static final long serialVersionUID = 1L;

    private CarteZones carteZones;
    private Joueur joueur;
    private boolean jeuEnCours;
    private boolean victoire;
    private String nomJoueur;
    private long dateHeureSauvegarde;

    public transient GestionnaireCommandes gestionnaireCmd;
    public transient GestionnaireSauvegarde gestionnaireSauvegarde;

    public Partie() {
        this.carteZones = new CarteZones();
        this.gestionnaireCmd = new GestionnaireCommandes();
        this.gestionnaireSauvegarde = new GestionnaireSauvegarde();
        this.nomJoueur = "Explorateur";
        resetPartie();
    }

    public void resetPartie() {
        this.joueur = new Joueur(nomJoueur != null ? nomJoueur : "Explorateur", carteZones.getZoneDepart(), 3, 6);
        this.jeuEnCours = true;
        this.victoire = false;
    }

    public String traiterCommande(String input) {
        if (gestionnaireCmd == null) gestionnaireCmd = new GestionnaireCommandes();
        if (!jeuEnCours) return "Partie terminée. Tapez NOUVELLE pour recommencer.";
        return gestionnaireCmd.traiterCommandes(input, this, joueur);
    }

    public void demarrer() {
        resetPartie();
        joueur.seDeplacer(carteZones.getZoneDepart());
    }

    public void demarrer(String nom) {
        this.nomJoueur = (nom != null && !nom.trim().isEmpty()) ? nom.trim() : "Explorateur";
        this.carteZones = new CarteZones();
        resetPartie();
        joueur.seDeplacer(carteZones.getZoneDepart());
    }

    public void jouer() {
        demarrer();
    }

    public boolean verifierVictoire() {
        return !jeuEnCours;
    }

    public boolean isJeuEnCours() {
        return jeuEnCours && joueur != null && joueur.estEnVie();
    }

    // ── Déplacements ─────────────────────────────────────────────────────────

    public String moveOuest() {
        Zones current = joueur.getZoneActuelle();
        Zones next = carteZones.avancerTemps(current);
        if (next == null || next == current) {
            if (current instanceof Zaman) return "Toutes les zones ont été explorées.";
            return "Vous êtes déjà dans la zone la plus lointaine accessible.";
        }
        if (next.isTerminee()) return "Cette zone est déjà terminée. Vous ne pouvez plus y entrer.";
        if (next.isBloquee()) return "⚿ Zone verrouillée ! Résolvez d'abord l'énigme de la zone précédente pour progresser.";
        joueur.seDeplacer(next);
        String msg = "[ " + next.getNom().toUpperCase() + " ]\n" + next.afficherDescription();
        Coffre coffre = next.obtenirCoffre();
        if (coffre != null && coffre.estOuvert() && !next.isTerminee()) {
            Enigme e = next.getEnigme();
            if (e != null) msg += "\n\n[ ÉNIGME EN COURS ]\n" + e.getQuestion();
        }
        return msg;
    }

    public String moveEst() {
        Zones current = joueur.getZoneActuelle();
        Zones prev = carteZones.reculerTemps(current);
        if (prev == current) return "Vous êtes déjà à Zaman.";
        if (prev != carteZones.getZaman() && prev.isTerminee())
            return "Cette zone est déjà terminée. Vous ne pouvez plus y entrer.";
        joueur.seDeplacer(prev);
        return "[ " + prev.getNom().toUpperCase() + " ]\n" + prev.afficherDescription();
    }

    public String retourZaman() {
        Zaman z = carteZones.getZaman();
        joueur.seDeplacer(z);
        return "[ ZAMAN ]\n" + z.afficherDescription() + "\n\n" + z.afficherObjetsDisponible();
    }

    // ── Exploration ───────────────────────────────────────────────────────────

    public String regarder() {
        Zones z = joueur.getZoneActuelle();
        if (z == null) return "Aucune zone.";
        StringBuilder sb = new StringBuilder("[ " + z.getNom().toUpperCase() + " ]\n");
        sb.append(z.afficherDescription());
        if (z instanceof Zaman) {
            sb.append("\n\n").append(((Zaman) z).afficherObjetsDisponible());
            if (joueur.getLettres().size() == 4) {
                sb.append("\n\n★ Vous avez toutes les lettres : ").append(joueur.lettresPourMot());
                sb.append("\n  → Tapez DEVERROUILLER pour révéler l'énigme finale.");
            }
        }
        return sb.toString();
    }

    public String chercher() {
        Zones z = joueur.getZoneActuelle();
        if (z instanceof Zaman) return "Il n'y a pas de coffre caché à Zaman.";
        if (z.isTerminee()) return "Cette zone est terminée. Vous ne pouvez plus interagir ici.";
        if (z.isCoffreTrouve()) return "Vous avez déjà trouvé le coffre-fort ici.";
        boolean trouve = z.chercherCoffre();
        if (!trouve) return "Vous ne trouvez rien.";
        if (z instanceof Prehistoire) return "Quelque chose de massif se dissimule dans la roche.";
        if (z instanceof EgypteAntique) return "Une des fresques semble cacher quelque chose derrière elle.";
        if (z instanceof MoyenAge) return "Un objet imposant se trouve dans un coin, sous un drap poussiéreux.";
        if (z instanceof Futur) return "Une structure vitrée dans le fond de la pièce attire votre regard.";
        return "Vous trouvez quelque chose.";
    }

    public String ouvrir() {
        Zones z = joueur.getZoneActuelle();
        if (z instanceof Zaman) return "Il n'y a pas de coffre-fort à Zaman.";
        if (!z.isCoffreTrouve()) return "Rien à ouvrir ici.";
        Coffre coffre = z.obtenirCoffre();
        if (coffre == null || z.isTerminee()) return "Rien à ouvrir ici.";
        if (coffre.estOuvert()) {
            Enigme e = z.getEnigme();
            return e != null ? e.getQuestion() : "Le coffre est ouvert.";
        }
        String objetRequis = coffre.getObjetRequis();
        if (!joueur.getInventaire().contient(objetRequis)) {
            return "Ce coffre nécessite : " + objetRequis + ".";
        }
        coffre.ouvrir();
        Enigme e = z.getEnigme();
        return "Le coffre s'ouvre.\n\n" + (e != null ? e.getQuestion() : "");
    }

    public String repondre(String rep) {
        Zones z = joueur.getZoneActuelle();
        if (z instanceof Zaman) return "Il n'y a pas d'énigme à Zaman.";
        if (!z.isCoffreTrouve() || z.obtenirCoffre() == null || !z.obtenirCoffre().estOuvert())
            return "Ouvrez d'abord le coffre-fort (commande OUVRIR).";
        if (z.isTerminee()) return "Vous avez déjà résolu cette zone.";
        boolean ok = z.repondre(rep);
        if (ok) {
            Lettre lettre = z.getLettreRecuperee();
            joueur.ajouterLettre(lettre);
            joueur.getInventaire().ajouter(lettre);
            carteZones.debloquerZone(z);
            String msg = "✓ Bonne réponse ! Lettre '" + lettre.obtenirCaractere() + "' collectée.  (" + joueur.getLettres().size() + "/4)";
            if (joueur.getLettres().size() < 4) msg += "\n⚿ La zone suivante est maintenant déverrouillée !";
            return msg;
        }
        joueur.perdreUneVie();
        String msg = "✗ Mauvaise réponse ! Vies restantes : " + joueur.getNombreVies();
        if (!joueur.estEnVie()) {
            jeuEnCours = false;
            msg += "\n☠ Plus de vies ! GAME OVER.";
        }
        return msg;
    }

    public String obtenirIndice() {
        return joueur.getZoneActuelle().afficherIndice();
    }

    public String deverrouiller(String mot) {
        Zones z = joueur.getZoneActuelle();
        if (!(z instanceof Zaman)) return "Revenez à Zaman (Z) pour déverrouiller la sortie.";
        if (joueur.getLettres().size() < 4)
            return "Il vous manque des lettres (" + joueur.getLettres().size() + "/4). Explorez toutes les zones.";
        Zaman zaman = (Zaman) z;
        if (mot == null || mot.trim().isEmpty()) {
            return "══════════════════════════════════\n" +
                   "        ÉNIGME FINALE\n" +
                   "══════════════════════════════════\n" +
                   "Vos lettres : " + joueur.lettresPourMot() + "\n\n" +
                   zaman.getQuestionFinale() + "\n\n" +
                   "Répondez avec : DEVERROUILLER <votre réponse>\n" +
                   "══════════════════════════════════";
        }
        if (zaman.verifierMotSecret(mot)) {
            jeuEnCours = false;
            victoire = true;
            return "VICTOIRE";
        }
        joueur.perdreUneVie();
        if (!joueur.estEnVie()) {
            jeuEnCours = false;
            return "GAME_OVER";
        }
        return "MAUVAISE_REPONSE:" + joueur.getNombreVies();
    }

    public String observer() {
        Zones z = joueur.getZoneActuelle();
        if (z instanceof Zaman) {
            return ((Zaman) z).afficherObjetsDisponible();
        }
        z.setObserve(true);
        List<Item> objets = z.getObjetsPresents();
        if (objets.isEmpty()) return "Vous regardez attentivement... rien de particulier ne retient votre attention.";
        StringBuilder sb = new StringBuilder("Vous observez la zone :\n");
        for (Item item : objets) {
            sb.append("  - ").append(item.getNom()).append(" : ").append(item.getDescription()).append("\n");
        }
        return sb.toString().trim();
    }

    // ── Inventaire ────────────────────────────────────────────────────────────

    public String prendreObjet(String nomObjet) {
        Zones z = joueur.getZoneActuelle();

        if (z instanceof Zaman) {
            long nbObjets = joueur.getInventaire().getObjets().stream()
                .filter(i -> !(i instanceof Lettre)).count();
            if (nbObjets >= 2) return "Sac plein (2 objets max). Déposez un objet d'abord : D <objet>.";
            Objet pris = ((Zaman) z).prendreObjetDisponible(nomObjet);
            if (pris == null) return "Objet introuvable : '" + nomObjet + "'.";
            joueur.getInventaire().ajouter(pris);
            return "Vous prenez : " + pris.getNom() + "  (" + (nbObjets + 1) + "/2 objets)";
        }

        // Zones non-Zaman : on peut prendre les lettres présentes
        Item item = z.prendreItemPresent(nomObjet);
        if (item == null) return "Objet introuvable : '" + nomObjet + "'.";
        if (item instanceof Lettre) {
            boolean dejaCollectee = joueur.getLettres().stream()
                .anyMatch(l -> l.obtenirZoneOrigine().equals(((Lettre) item).obtenirZoneOrigine()));
            if (dejaCollectee) {
                z.ajouterObjet(item); // on remet l'item dans la zone
                return "Vous avez déjà la lettre de cette zone.";
            }
            joueur.ajouterLettre((Lettre) item);
            joueur.getInventaire().ajouter(item);
            z.setCoffreTrouve(true);
            int total = joueur.getLettres().size();
            String msg = "Lettre '" + ((Lettre) item).obtenirCaractere() + "' collectée !  (" + total + "/4)";
            if (total == 4) {
                msg += "\n★ Toutes les lettres collectées : " + joueur.lettresPourMot()
                    + "\n  → Retournez à Zaman (Z) et tapez DEVERROUILLER pour l'énigme finale !";
            } else {
                msg += "\n  Il vous reste " + (4 - total) + " lettre(s) à trouver dans les autres zones.";
            }
            return msg;
        }
        joueur.getInventaire().ajouter(item);
        return "Vous prenez : " + item.getNom();
    }

    public String deposer(String nom) {
        Zones z = joueur.getZoneActuelle();
        if (!(z instanceof Zaman)) return "Vous ne pouvez déposer des objets qu'à Zaman.";
        Item item = joueur.getInventaire().search(nom);
        if (item == null) return "Vous n'avez pas '" + nom + "' dans votre sac.";
        if (item instanceof Lettre) return "Vous ne pouvez pas déposer une lettre.";
        joueur.getInventaire().retirer(item);
        ((Zaman) z).ajouterObjetDisponible((Objet) item);
        return "Vous déposez " + item.getNom() + " sur l'établi de Zaman.";
    }

    public String laisserObjetDansZone(String nom) {
        Zones z = joueur.getZoneActuelle();
        if (z instanceof Zaman) return deposer(nom);
        Item item = joueur.getInventaire().search(nom);
        if (item == null) return "Vous n'avez pas '" + nom + "' dans votre sac.";
        if (item instanceof Lettre) return "Vous ne pouvez pas laisser une lettre.";
        joueur.getInventaire().retirer(item);
        z.ajouterObjet(item);
        return "Vous laissez " + item.getNom() + " dans la zone.";
    }

    public String inventaireTexte() {
        Inventaire inv = joueur.getInventaire();
        if (inv.getTaille() == 0) return "Sac vide.";
        long nbObjets = inv.getObjets().stream().filter(i -> !(i instanceof Lettre)).count();
        StringBuilder sb = new StringBuilder("SAC (" + nbObjets + "/2 objets");
        if (!joueur.getLettres().isEmpty()) sb.append(" + ").append(joueur.getLettres().size()).append(" lettre(s)");
        sb.append(") :\n");
        for (Item item : inv.getObjets()) {
            sb.append("  - ").append(item.getNom());
            if (item instanceof Lettre) sb.append(" [LETTRE]");
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    // ── Infos ────────────────────────────────────────────────────────────────

    public String statusTexte() {
        Zones z = joueur.getZoneActuelle();
        long nbObjets = joueur.getInventaire().getObjets().stream().filter(i -> !(i instanceof Lettre)).count();
        return "─── STATUS ───────────────────────\n" +
               "Explorateur : " + joueur.getNom() + "\n" +
               "Vies        : " + joueur.getNombreVies() + "\n" +
               "Zone        : " + (z == null ? "-" : z.getNom()) + "\n" +
               "Lettres     : " + (joueur.getLettres().isEmpty() ? "aucune" : joueur.lettresPourMot()) +
               "  (" + joueur.getLettres().size() + "/4)\n" +
               "Sac         : " + nbObjets + "/2 objets\n" +
               (joueur.getLettres().size() == 4
                   ? "──────────────────────────────────\n★ Allez à Zaman et tapez DEVERROUILLER !"
                   : "──────────────────────────────────");
    }

    public String afficherAide() {
        Zones z = joueur.getZoneActuelle();
        StringBuilder sb = new StringBuilder("Actions disponibles :\n");
        sb.append("  L  : Regarder   |  STATUS\n");
        if (z instanceof Zaman) {
            sb.append("  O  : Avancer dans le temps\n");
            sb.append("  P <objet>  : Prendre   |  D <objet>  : Déposer\n");
            sb.append("  SAC  |  SAVE  |  LOAD\n");
            if (joueur.getLettres().size() == 4) sb.append("  DEVERROUILLER <mot>\n");
        } else if (z.isTerminee()) {
            sb.append("  O  : Zone suivante   |  E  : Reculer   |  Z  : Zaman\n");
        } else if (!z.isCoffreTrouve()) {
            sb.append("  O  : Zone suivante   |  E  : Reculer   |  Z  : Zaman\n");
            sb.append("  CH : Chercher\n");
        } else if (z.obtenirCoffre() != null && !z.obtenirCoffre().estOuvert()) {
            sb.append("  OUVRIR\n");
            sb.append("  E  : Reculer   |  Z  : Zaman\n");
        } else {
            sb.append("  R <réponse>   |   INDICE\n");
            sb.append("  E  : Reculer   |  Z  : Zaman\n");
        }
        return sb.toString().trim();
    }

    public String nouvellePartie() {
        demarrer();
        return "Nouvelle partie démarrée !";
    }

    public String sauvegarder() {
        if (gestionnaireSauvegarde == null) gestionnaireSauvegarde = new GestionnaireSauvegarde();
        dateHeureSauvegarde = System.currentTimeMillis();
        Partie saved = gestionnaireSauvegarde.sauvegarder(this);
        if (saved == null) return "Erreur lors de la sauvegarde.";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return "✔ Partie sauvegardée le " + sdf.format(new java.util.Date(dateHeureSauvegarde));
    }

    public String resumeChargement() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        StringBuilder sb = new StringBuilder("═══════ PARTIE CHARGÉE ═══════\n");
        if (dateHeureSauvegarde > 0)
            sb.append("Sauvegardée le : ").append(sdf.format(new java.util.Date(dateHeureSauvegarde))).append("\n");
        sb.append("Joueur   : ").append(joueur.getNom()).append("\n");
        sb.append("Vies     : ").append(joueur.getNombreVies()).append("\n");
        sb.append("Score    : ").append(joueur.getScore()).append("\n");
        sb.append("Zone     : ").append(joueur.getZoneActuelle() != null ? joueur.getZoneActuelle().getNom() : "-").append("\n");
        sb.append("Lettres  : ");
        if (joueur.getLettres().isEmpty()) {
            sb.append("aucune");
        } else {
            sb.append(joueur.lettresPourMot()).append(" (").append(joueur.getLettres().size()).append("/4)");
        }
        sb.append("\n");
        long nbObjets = joueur.getInventaire().getObjets().stream()
            .filter(i -> !(i instanceof Lettre)).count();
        if (nbObjets == 0) {
            sb.append("Sac      : vide\n");
        } else {
            sb.append("Sac      : ");
            joueur.getInventaire().getObjets().stream()
                .filter(i -> !(i instanceof Lettre))
                .forEach(i -> sb.append(i.getNom()).append("  "));
            sb.append("\n");
        }
        sb.append("═══════════════════════════════");
        return sb.toString();
    }

    public String charger() {
        if (gestionnaireSauvegarde == null) gestionnaireSauvegarde = new GestionnaireSauvegarde();
        Partie p = gestionnaireSauvegarde.charger();
        return p != null ? "Partie chargée." : "Aucune sauvegarde trouvée.";
    }

    public Joueur getJoueur() { return joueur; }
    public Zones getZoneCourante() { return joueur.getZoneActuelle(); }
    public boolean isVictoire() { return victoire; }
}

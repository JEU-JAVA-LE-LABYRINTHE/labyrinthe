package personnes;

import inventaire.Inventaire;
import items.Lettre;
import zones.Zones;

import java.util.ArrayList;
import java.util.List;

public class Joueur extends Personne {
    private static final long serialVersionUID = 1L;

    private int nombreVies;
    private final Inventaire inventaire;
    private final List<Lettre> lettres;
    private int score;

    public Joueur(String nom, Zones zoneDepart, int nombreVies, int capaciteInventaire) {
        super(nom, zoneDepart);
        this.nombreVies = nombreVies;
        this.inventaire = new Inventaire(capaciteInventaire);
        this.lettres = new ArrayList<>();
        this.score = 0;
    }

    public int getNombreVies() {
        return nombreVies;
    }

    public void perdreUneVie() {
        nombreVies = Math.max(0, nombreVies - 1);
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public List<Lettre> getLettres() {
        return lettres;
    }

    public void ajouterLettre(Lettre lettre) {
        if (lettre == null) return;
        lettres.add(lettre);
    }

    public void gererObjets() {
        inventaire.getObjets().forEach(item -> System.out.println("- " + item.getNom()));
    }

    public void setZone(Zones z) {
        seDeplacer(z);
    }

    @Override
    public void afficherInfo() {
        System.out.println(toString());
    }

    public boolean contientObjet(String nomObjet) {
        return inventaire.contient(nomObjet);
    }

    public boolean estEnVie() {
        return nombreVies > 0;
    }

    public void ajouterScore(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }

    public String lettresPourMot() {
        StringBuilder sb = new StringBuilder();
        for (Lettre l : lettres) {
            sb.append(l.obtenirCaractere());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Joueur " + nom + " | Vies=" + nombreVies + " | Score=" + score;
    }
}


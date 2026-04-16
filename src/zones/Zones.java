package zones;

import items.Item;
import items.Lettre;
import items.Objet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Zones implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String nom;
    private final String description;

    private boolean terminee;
    private boolean coffreTrouve;
    private boolean observe;

    private Coffre coffre;
    private final Enigme enigme;

    private Lettre lettreRecuperee;
    protected List<Item> objetsPresents;

    protected Zones(String nom, String description, Coffre coffre, Enigme enigme) {
        this.nom = nom;
        this.description = description;
        this.coffre = coffre;
        this.enigme = enigme;
        this.terminee = false;
        this.coffreTrouve = false;
        this.observe = false;
        this.lettreRecuperee = null;
        this.objetsPresents = new ArrayList<>();
        creerObjet();
    }

    protected void creerObjet() {
    }

    protected void ajouterObjetsAleatoires(String[][] pool, int n) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < pool.length; i++) indices.add(i);
        Collections.shuffle(indices);
        int count = Math.min(n, pool.length);
        for (int i = 0; i < count; i++) {
            int idx = indices.get(i);
            ajouterObjet(new Objet(pool[idx][0], pool[idx][1], false, null));
        }
    }

    public void ajouterObjet(Item item) {
        if (item != null) objetsPresents.add(item);
    }

    public void retirerObjet(Item item) {
        objetsPresents.remove(item);
    }


    public String afficherDescription() {
        return description;
    }

    public boolean chercherCoffre() {
        if (terminee || coffre == null) return false;
        setCoffreTrouve(true);
        return true;
    }

    public boolean ouvrirCoffre() {
        return false;
    }

    public String afficherIndice() {
        if (terminee) return "Rien à chercher ici : zone déjà terminée.";
        if (enigme == null) return "Aucune énigme dans cette zone.";
        return enigme.afficherIndice();
    }

    public boolean repondre(String rep) {
        if (terminee) return false;
        if (coffre == null || enigme == null) return false;
        if (!coffre.estOuvert()) return false;
        boolean ok = enigme.tenter(rep);
        if (ok) {
            lettreRecuperee = coffre.recupererLettre();
            terminee = true;
        }
        return ok;
    }

    public Lettre getLettreRecuperee() {
        return lettreRecuperee;
    }

    public boolean isTerminee() {
        return terminee;
    }

    public boolean isCoffreTrouve() {
        return coffreTrouve;
    }

    public boolean isObserve() {
        return observe;
    }

    public void setObserve(boolean observe) {
        this.observe = observe;
    }

    public String getNom() {
        return nom;
    }

    public Coffre getCoffre() {
        return coffre;
    }

    protected void setCoffre(Coffre coffre) {
        this.coffre = coffre;
    }

    public void setCoffreTrouve(boolean b) {
        this.coffreTrouve = b;
    }

    public List<Item> getObjetsPresents() {
        return new ArrayList<>(objetsPresents);
    }

    public void definirLettreCollectable(String caractere) {
        objetsPresents.removeIf(item -> item instanceof Lettre);
        objetsPresents.add(0, new Lettre(caractere, nom));
    }

    public Item prendreItemPresent(String nom) {
        if (nom == null) return null;
        String cible = nom.trim().toLowerCase();
        for (Item item : new ArrayList<>(objetsPresents)) {
            if (item.getNom().toLowerCase().contains(cible) || cible.contains(item.getNom().toLowerCase())) {
                objetsPresents.remove(item);
                return item;
            }
        }
        return null;
    }

    public Enigme getEnigme() {
        return enigme;
    }
}


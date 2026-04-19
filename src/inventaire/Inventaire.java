package inventaire;

import items.Item;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventaire implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Item> objets;
    private int capaciteMax;
    private int capaciteActuelle;

    public Inventaire() {
        this(10);
    }

    public Inventaire(int capaciteMax) {
        this.objets = new ArrayList<>();
        this.capaciteMax = capaciteMax;
        this.capaciteActuelle = 0;
    }

    public boolean ajouter(Item item) {
        if (item == null) return false;
        if (objets.size() >= capaciteMax) return false;
        boolean added = objets.add(item);
        if (added) capaciteActuelle++;
        return added;
    }

    public boolean retirer(Item item) {
        boolean removed = objets.remove(item);
        if (removed) capaciteActuelle--;
        return removed;
    }

    public Item search(String nom) {
        for (Item item : objets) {
            if (item.getNom().equalsIgnoreCase(nom)) {
                return item;
            }
        }
        return null;
    }

    public List<Item> getObjets() {
        return new ArrayList<>(objets);
    }

    public int getTaille() {
        return objets.size();
    }

    public boolean contient(String nom) {
        return search(nom) != null;
    }

    public boolean estPleine() {
        return objets.size() >= capaciteMax;
    }

    public int getCapaciteActuelle() {
        return capaciteActuelle;
    }

    public void afficher() {
        if (objets.isEmpty()) {
            System.out.println("Inventaire vide.");
            return;
        }
        System.out.println("Inventaire (" + capaciteActuelle + "/" + capaciteMax + ") :");
        for (Item item : objets) {
            System.out.println("  - " + item.getNom());
        }
    }
}

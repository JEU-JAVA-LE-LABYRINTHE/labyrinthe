package inventaire;

import items.Item;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventaire implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Item> objets;
    private int capaciteMax;

    public Inventaire() {
        this(10);
    }

    public Inventaire(int capaciteMax) {
        this.objets = new ArrayList<>();
        this.capaciteMax = capaciteMax;
    }

    public boolean ajouter(Item item) {
        if (item == null) return false;
        if (objets.size() >= capaciteMax) return false;
        return objets.add(item);
    }

    public boolean retirer(Item item) {
        return objets.remove(item);
    }

    public Item chercher(String nom) {
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
        return chercher(nom) != null;
    }

    public boolean estPlein() {
        return objets.size() >= capaciteMax;
    }
}
package zones;

import items.Lettre;
import items.Item;
import java.io.Serializable;

public class Coffre implements Serializable {
    private static final long serialVersionUID = 1L;
    private Lettre lettre;
    private Item cleRequise;
    private boolean ouvert;
    private String objetRequis;

    public Coffre(Lettre lettre, Item cleRequise) {
        this.lettre = lettre;
        this.cleRequise = cleRequise;
        this.ouvert = false;
        this.objetRequis = cleRequise != null ? cleRequise.getNom() : "Aucun";
    }

    public boolean ouvrir() {
        ouvert = true;
        return true;
    }

    public boolean estOuvert() {
        return ouvert;
    }

    public Lettre recupererLettre() {
        return lettre;
    }

    public Item getCleRequise() {
        return cleRequise;
    }

    public String getObjetRequis() {
        return objetRequis;
    }
}

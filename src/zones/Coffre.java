package zones;

import items.Lettre;
import items.Item;
import java.io.Serializable;

public class Coffre implements Serializable {
    private static final long serialVersionUID = 1L;
    private Lettre lettre;
    private Item cleRequise;
    private boolean estOuvert;
    private String objetRequis;
    private String emplacement;

    public Coffre(Lettre lettre, Item cleRequise) {
        this(lettre, cleRequise, "inconnu");
    }

    public Coffre(Lettre lettre, Item cleRequise, String emplacement) {
        this.lettre = lettre;
        this.cleRequise = cleRequise;
        this.estOuvert = false;
        this.objetRequis = cleRequise != null ? cleRequise.getNom() : "Aucun";
        this.emplacement = emplacement;
    }

    public Coffre(Lettre lettre, String objetRequis, String emplacement) {
        this.lettre = lettre;
        this.cleRequise = null;
        this.estOuvert = false;
        this.objetRequis = objetRequis != null ? objetRequis : "Aucun";
        this.emplacement = emplacement;
    }

    public boolean ouvrir() {
        estOuvert = true;
        return true;
    }

    public boolean estOuvert() { return estOuvert; }

    public Lettre recupererLettre() {
        return lettre;
    }

    public Item getCleRequise() {
        return cleRequise;
    }

    public String getObjetRequis() {
        return objetRequis;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void afficherContenu() {
        System.out.println("Coffre [" + emplacement + "] - " + (estOuvert ? "estOuvert" : "fermé"));
        System.out.println("Objet requis : " + objetRequis);
        if (estOuvert && lettre != null) {
            System.out.println("Contient : " + lettre.getNom());
        }
    }
}

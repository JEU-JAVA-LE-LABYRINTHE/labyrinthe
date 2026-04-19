package items;

public class Objet extends Item {
    private static final long serialVersionUID = 1L;


    protected boolean peutEtrePris;
    private final String zoneUtilisation;

    public Objet(String nom, String description, boolean peutEtrePris, String zoneUtilisation) {
        super(nom, description);
        this.peutEtrePris = peutEtrePris;
        this.zoneUtilisation = zoneUtilisation;
    }

    public boolean peutEtrePris() {
        return peutEtrePris;
    }

    public boolean estUtilisable() {
        return peutEtrePris;
    }

    @Override
    public void utiliser() {
        if (!peutEtrePris) {
            System.out.println(nom + " ne peut pas être utilisé.");
            return;
        }
        System.out.println("Vous utilisez " + nom + ".");
    }

    @Override
    public void examiner() {
        System.out.println("Peut être pris : " + (peutEtrePris ? "oui" : "non"));
        if (zoneUtilisation != null && !zoneUtilisation.isEmpty()) {
            System.out.println("Zone d'utilisation : " + zoneUtilisation);
        }
    }

    @Override
    public String toString() {
        return "Objet: " + nom;
    }
}


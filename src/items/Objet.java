package items;

public class Objet extends Item {
    private static final long serialVersionUID = 1L;


    private final boolean peutEtrePris;

    public Objet(String nom, String description, boolean peutEtrePris, String zoneUtilisation) {
        super(nom, description);
        this.peutEtrePris = peutEtrePris;
    }

    public boolean peutEtrePris() {
        return peutEtrePris;
    }

    @Override
    public void utiliser() {
    }

    @Override
    public void examiner() {
    }

    @Override
    public String toString() {
        return "Objet: " + nom;
    }
}


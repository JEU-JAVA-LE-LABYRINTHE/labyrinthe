package items;

public class Lettre extends Item {
    private static final long serialVersionUID = 1L;

    private final String caractere;
    private final String zoneOrigine;

    public Lettre(String caractere, String zoneOrigine) {
        super("Lettre " + caractere, "Lettre pour former le mot secret.");
        this.caractere = caractere;
        this.zoneOrigine = zoneOrigine;
    }

    public String obtenirCaractere() {
        return caractere;
    }

    public String obtenirZoneOrigine() {
        return zoneOrigine;
    }

    @Override
    public void utiliser() {
        System.out.println("Vous utilisez la lettre : " + caractere);
    }

    @Override
    public void examiner() {
        System.out.println("Lettre : " + caractere + " (origine : " + zoneOrigine + ")");
    }

    public void afficher() {
        System.out.println("[Lettre] " + caractere + " — " + zoneOrigine);
    }
}


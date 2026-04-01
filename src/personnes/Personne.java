package personnes;

import zones.Zones;
import java.io.Serializable;

public abstract class Personne implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String nom;
    protected int x;
    protected int y;
    protected Zones zoneActuelle;

    public Personne(String nom, int x, int y) {
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.zoneActuelle = null;
    }

    public Personne(String nom, Zones zone) {
        this.nom = nom;
        this.x = 0;
        this.y = 0;
        this.zoneActuelle = zone;
    }

    public String getNom() {
        return nom;
    }

    public Zones getZoneActuelle() {
        return zoneActuelle;
    }

    public void seDeplacer(Zones zone) {
        this.zoneActuelle = zone;
    }
}

package personnes;

import zones.Zones;
import java.io.Serializable;

public abstract class Personne implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String nom;
    protected Zones zoneActuelle;

    public Personne(String nom, Zones zone) {
        this.nom = nom;
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

    public abstract void afficherInfo();
}

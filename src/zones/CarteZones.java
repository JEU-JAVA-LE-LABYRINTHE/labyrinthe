package zones;

import java.util.ArrayList;
import java.util.List;

public class CarteZones {
    private final Zaman zaman;
    private final List<Zones> zonesTemporelles;

    public CarteZones() {
        this.zaman = new Zaman();
        this.zonesTemporelles = new ArrayList<>();
        zonesTemporelles.add(new Prehistoire());
        zonesTemporelles.add(new EgypteAntique());
        zonesTemporelles.add(new MoyenAge());
        zonesTemporelles.add(new Futur());
    }

    public Zaman getZaman() {
        return zaman;
    }

    public Zones getZoneDepart() {
        return zaman;
    }

    public Zones obtenirZoneParNom(String nom) {
        if (nom == null) return null;
        if (zaman.getNom().equalsIgnoreCase(nom.trim())) return zaman;
        for (Zones z : zonesTemporelles) {
            if (z.getNom().equalsIgnoreCase(nom.trim())) return z;
        }
        return null;
    }

    private int indexDe(Zones zone) {
        if (zone == null) return -1;
        for (int i = 0; i < zonesTemporelles.size(); i++) {
            if (zonesTemporelles.get(i) == zone) return i;
        }
        return -1;
    }

    public Zones avancerTemps(Zones zoneActuelle) {
        if (zoneActuelle == null) return null;
        if (zoneActuelle == zaman) {
            return zonesTemporelles.get(0);
        }
        int idx = indexDe(zoneActuelle);
        if (idx < 0) return null;
        if (idx >= zonesTemporelles.size() - 1) return zonesTemporelles.get(idx);
        return zonesTemporelles.get(idx + 1);
    }

    public Zones reculerTemps(Zones zoneActuelle) {
        if (zoneActuelle == null) return null;
        if (zoneActuelle == zaman) {
            return zaman;
        }
        int idx = indexDe(zoneActuelle);
        if (idx <= 0) return zaman; 
        return zonesTemporelles.get(idx - 1);
    }
}


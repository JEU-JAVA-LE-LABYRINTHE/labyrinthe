package zones;

import java.util.ArrayList;
import java.util.List;

public class CarteZones {

    private static final String[][] MOTS_ENIGMES = {
        {"LION", "Quel est le roi des animaux ?"},
        {"LUNE", "Quel astre éclaire la nuit ?"},
        {"MIEL", "Quel produit sucré est fabriqué par les abeilles ?"},
        {"OURS", "Quel grand mammifère sauvage hiberne en hiver ?"},
        {"PAIX", "Quel mot désigne l'absence de guerre ?"},
        {"PAIN", "Quel aliment de base est fabriqué à partir de farine ?"},
        {"PONT", "Quelle construction permet de traverser une rivière ?"},
        {"ROSE", "Quelle fleur est connue pour ses épines ?"},
        {"VENT", "Quel phénomène fait tourner les moulins ?"},
        {"TOUR", "Quelle haute construction se dresse dans les châteaux forts ?"},
        {"LOUP", "Quel animal sauvage hurle à la lune ?"},
        {"DUNE", "Quelle colline de sable se trouve dans le désert ?"},
        {"EPEE", "Quelle arme blanche portait le chevalier médiéval ?"},
        {"CLEF", "Quel objet permet d'ouvrir une serrure ?"},
        {"SOIF", "Quelle sensation pousse à boire ?"}
    };

    private final Zaman zaman;
    private final List<Zones> zones;
    private final Zones zoneDepart;
    private final int nombreZones;

    public CarteZones() {
        int idx = (int) (Math.random() * MOTS_ENIGMES.length);
        String mot      = MOTS_ENIGMES[idx][0];
        String question = MOTS_ENIGMES[idx][1];

        this.zaman = new Zaman(mot, question);
        this.zones = new ArrayList<>();
        creerZones(mot);
        this.zoneDepart = zaman;
        this.nombreZones = zones.size() + 1;
    }

    private void creerZones(String mot) {
        Prehistoire p = new Prehistoire();
        EgypteAntique e = new EgypteAntique();
        MoyenAge m = new MoyenAge();
        Futur f = new Futur();

        p.definirLettreCollectable(String.valueOf(mot.charAt(0)));
        e.definirLettreCollectable(String.valueOf(mot.charAt(1)));
        m.definirLettreCollectable(String.valueOf(mot.charAt(2)));
        f.definirLettreCollectable(String.valueOf(mot.charAt(3)));

        // Seule la première zone est accessible au départ
        e.setBloquee(true);
        m.setBloquee(true);
        f.setBloquee(true);

        zones.add(p);
        zones.add(e);
        zones.add(m);
        zones.add(f);
    }

    public Zaman getZaman() {
        return zaman;
    }

    public Zones getZoneDepart() {
        return zoneDepart;
    }

    public int getNombreZones() {
        return nombreZones;
    }

    public Zones obtenirZone(String nom) {
        if (nom == null) return null;
        if (zaman.getNom().equalsIgnoreCase(nom.trim())) return zaman;
        for (Zones z : zones) {
            if (z.getNom().equalsIgnoreCase(nom.trim())) return z;
        }
        return null;
    }

    public void debloquerProchaineZone(Zones zoneTerminee) {
        int idx = indexDe(zoneTerminee);
        if (idx >= 0 && idx < zones.size() - 1) {
            zones.get(idx + 1).setBloquee(false);
        }
    }

    private int indexDe(Zones zone) {
        if (zone == null) return -1;
        for (int i = 0; i < zones.size(); i++) {
            if (zones.get(i) == zone) return i;
        }
        return -1;
    }

    public Zones avancerTemps(Zones zoneActuelle) {
        if (zoneActuelle == null) return null;
        if (zoneActuelle == zaman) {
            // Depuis Zaman, aller directement à la première zone accessible
            for (Zones z : zones) {
                if (!z.isTerminee() && !z.isBloquee()) return z;
            }
            return zaman; // toutes les zones sont terminées
        }
        int idx = indexDe(zoneActuelle);
        if (idx < 0) return null;
        if (idx >= zones.size() - 1) return zones.get(idx);
        return zones.get(idx + 1);
    }

    public Zones reculerTemps(Zones zoneActuelle) {
        if (zoneActuelle == null) return null;
        if (zoneActuelle == zaman) return zaman;
        int idx = indexDe(zoneActuelle);
        if (idx <= 0) return zaman;
        Zones prev = zones.get(idx - 1);
        // Si la zone précédente est terminée, retourner à Zaman directement
        if (prev.isTerminee()) return zaman;
        return prev;
    }
}

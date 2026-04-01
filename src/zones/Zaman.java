package zones;

import items.Objet;

import java.util.ArrayList;
import java.util.List;

public class Zaman extends Zones {
    private final List<Objet> objetsDisponibles;

    public Zaman() {
        super(
                "Zaman",
                "La zone centrale. Tu y récupères des objets utiles avant de traverser le temps.",
                null,
                null
        );
        this.objetsDisponibles = new ArrayList<>();
        objetsDisponibles.add(new Objet("Marteau", "Permet de casser le rocher.", true, "Préhistoire"));
        objetsDisponibles.add(new Objet("Clé ancienne", "Ouvre un coffre verrouillé.", true, "Moyen Âge"));
        objetsDisponibles.add(new Objet("Loupe", "Révèle un symbole caché.", true, "Égypte antique"));
        objetsDisponibles.add(new Objet("Puce holographique", "Active le terminal pour le mot de passe.", true, "Futur lointain"));
    }

    @Override
    public boolean chercherCoffre() {
        return false;
    }

    @Override
    public boolean ouvrirCoffre() {
        return false;
    }

    @Override
    public String afficherIndice() {
        return "Aucun coffre/énigme dans Zaman.";
    }

    @Override
    public boolean repondre(String rep) {
        return false;
    }

    public Objet prendreObjetDisponible(String nom) {
        if (nom == null) return null;
        String normNom = normalize(nom.trim());
        for (Objet o : new ArrayList<>(objetsDisponibles)) {
            if (normalize(o.getNom()).equals(normNom)) {
                objetsDisponibles.remove(o);
                return o;
            }
        }
        return null;
    }

    private static String normalize(String s) {
        return s == null ? "" : s.toLowerCase().trim();
    }
}


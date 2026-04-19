package zones;

import items.Objet;

import java.util.ArrayList;
import java.util.List;

public class Zaman extends Zones {
    private final List<Objet> objetsDisponibles;
    private String motSecret;
    private String questionFinale;

    public Zaman(String motSecret, String questionFinale) {
        super(
                "Zaman",
                "La zone centrale. Tu y récupères des objets utiles avant de traverser le temps.",
                null,
                null
        );
        this.objetsDisponibles = new ArrayList<>();
        this.motSecret = motSecret.toUpperCase();
        this.questionFinale = questionFinale;
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

    public String afficherObjetsDisponible() {
        if (objetsDisponibles.isEmpty()) return "La zone est vide. Tous les objets ont été pris.";
        StringBuilder sb = new StringBuilder("Objets disponibles dans la zone :\n");
        for (Objet o : objetsDisponibles) {
            sb.append("  - ").append(o.getNom()).append(" : ").append(o.getDescription()).append("\n");
        }
        return sb.toString().trim();
    }

    public void ajouterObjetDisponible(Objet o) {
        if (o != null) objetsDisponibles.add(o);
    }

    public boolean verifierMotSecret(String mot) {
        if (mot == null || motSecret == null || motSecret.isEmpty()) return false;
        return mot.equalsIgnoreCase(motSecret);
    }

    public void setMotSecret(String motSecret) {
        this.motSecret = motSecret;
    }

    public String getQuestionFinale() {
        return questionFinale;
    }

    private static String normalize(String s) {
        return s == null ? "" : s.toLowerCase().trim();
    }
}


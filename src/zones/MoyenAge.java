package zones;

import items.Lettre;

public class MoyenAge extends Zones {
    private static final long serialVersionUID = 1L;

    private static final String[][] ENIGMES = {
        {"Quelle longue arme blanche symbolisait l'honneur des chevaliers du Moyen Âge ?",
         "EPEE", "Elle est forgée dans le métal. Le chevalier ne s'en séparait jamais."},
        {"Comment appelle-t-on la demeure fortifiée d'un seigneur médiéval entourée de remparts ?",
         "CHATEAU", "Les rois y résidaient, protégés par de hautes murailles et des douves."}
    };

    private static final String[][] OBJETS_POOL = {
        {"Bouclier rouillé",      "Un vieux bouclier couvert de rouille."},
        {"Chandelier renversé",   "Un chandelier de fer tombé sur le sol."},
        {"Tonneau vide",          "Un tonneau en bois qui obstrue le passage."},
        {"Botte de paille",       "Une botte de paille sèche et poussiéreuse."},
        {"Table renversée",       "Une grande table de banquet couchée sur le côté."},
        {"Chaîne au mur",         "Une lourde chaîne ancrée dans la pierre."},
        {"Bannière de chevalier", "Une bannière brodée aux armes d'un seigneur."}
    };

    private boolean coffreVerrouille;

    public MoyenAge() {
        super("Moyen Âge",
              "La salle principale d'un château fort. Des torches éclairent les murs de pierre.",
              creerCoffre(), selectionnerEnigme());
        this.coffreVerrouille = true;
    }

    private static Coffre creerCoffre() {
        return new Coffre(new Lettre("S", "Moyen Âge"), "Clé ancienne", "dans un coin de la salle");
    }

    private static Enigme selectionnerEnigme() {
        int idx = (int) (Math.random() * ENIGMES.length);
        return new Enigme(ENIGMES[idx][0], ENIGMES[idx][1], ENIGMES[idx][2]);
    }

    @Override
    protected void creerObjet() {
        ajouterObjetsAleatoires(OBJETS_POOL, 5);
    }

    @Override
    public String afficherDescription() {
        return super.afficherDescription() + (coffreVerrouille ? "\nUn coffre-fort massif est posé dans un coin, fermé à clé." : "");
    }

    public void examinerArmure() {
        System.out.println("Un morceau d'armure médiévale rouillée est accroché au mur.");
    }

    public boolean isCoffreVerrouille() { return coffreVerrouille; }
}

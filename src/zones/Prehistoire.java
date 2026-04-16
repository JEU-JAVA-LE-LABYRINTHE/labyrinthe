package zones;

import items.Lettre;
import items.Objet;

public class Prehistoire extends Zones {
    private static final long serialVersionUID = 1L;

    private static final String[][] ENIGMES = {
        {"Quelle découverte a permis aux hommes préhistoriques de se réchauffer et de cuisiner ?",
         "FEU", "Chaud et lumineux, il illuminait les cavernes la nuit."},
        {"Quel immense animal velu à longues défenses vivait à l'ère glaciaire ?",
         "MAMMOUTH", "Il ressemblait à un éléphant, mais couvert de fourrure épaisse."}
    };

    private static final String[][] OBJETS_POOL = {
        {"Rocher immobile",   "Un gros rocher qui obstrue le chemin."},
        {"Branche de bois",   "Une lourde branche tombée d'un arbre."},
        {"Os pétrifié",       "Un os fossilisé d'un animal préhistorique."},
        {"Pierre tranchante", "Une pierre aux bords acérés."},
        {"Flaque de boue",    "Une flaque de boue épaisse et glissante."},
        {"Stalactite brisée", "Une stalactite tombée du plafond."},
        {"Toile d'araignée",  "Une immense toile qui obstrue le passage."}
    };

    private boolean rocherIntact;

    public Prehistoire() {
        super("Préhistoire",
              "Une caverne ornée de peintures rupestres. L'odeur de la terre humide emplit l'air.",
              creerCoffre(), selectionnerEnigme());
        this.rocherIntact = true;
    }

    private static Coffre creerCoffre() {
        return new Coffre(new Lettre("F", "Préhistoire"), "Marteau", "sous un rocher fendu");
    }

    private static Enigme selectionnerEnigme() {
        int idx = (int) (Math.random() * ENIGMES.length);
        return new Enigme(ENIGMES[idx][0], ENIGMES[idx][1], ENIGMES[idx][2]);
    }

    @Override
    protected void creerObjet() {
        ajouterObjet(new Objet("fossile cassé", "Un fossile d'animal préhistorique, brisé en deux.", true, null));
        ajouterObjetsAleatoires(OBJETS_POOL, 5);
    }

    @Override
    public String afficherDescription() {
        return super.afficherDescription() + (rocherIntact ? "\nUn rocher à moitié fendu se dresse ici." : "");
    }

    public void casserRocher() {
        if (!rocherIntact) return;
        rocherIntact = false;
        setCoffreTrouve(true);
    }

    public boolean isRocherIntact() { return rocherIntact; }
}

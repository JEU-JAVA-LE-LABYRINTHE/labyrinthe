package zones;

import items.Lettre;
import items.Objet;
import java.util.Arrays;
import java.util.List;

public class Prehistoire extends Zones {
    private static final long serialVersionUID = 1L;

    private static final List<Enigme> ENIGMES = Arrays.asList(
        new Enigme("Quelle découverte a permis aux hommes préhistoriques de se réchauffer et de cuisiner ?",
                   "FEU", "Chaud et lumineux, il illuminait les cavernes la nuit."),
        new Enigme("Quel immense animal velu à longues défenses vivait à l'ère glaciaire ?",
                   "MAMMOUTH", "Il ressemblait à un éléphant, mais couvert de fourrure épaisse."),
        new Enigme("Quel outil de pierre taillée permettait de couper et de gratter ?",
                   "SILEX", "Ces outils étaient essentiels pour la chasse et la préparation des aliments."),
        new Enigme("Quelle période de l'histoire humaine précède l'invention de l'écriture ?",
                   "PREHISTOIRE", "Elle couvre des millions d'années, depuis les premiers hominidés."),
        new Enigme("Quel animal prédateur chassait en meute et était redouté des premiers hommes ?",
                   "LOUP", "Il hurlait à la lune et vivait en groupe."),
        new Enigme("Quelle technique consistait à peindre sur les murs des cavernes ?",
                   "PEINTURE RUPESTRE", "Ces œuvres d'art racontent la vie des chasseurs-cueilleurs.")
    );

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
        int idx = (int) (Math.random() * ENIGMES.size());
        return ENIGMES.get(idx);
    }

    @Override
    protected void CreerObjet() {
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

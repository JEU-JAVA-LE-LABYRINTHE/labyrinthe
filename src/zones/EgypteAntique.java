package zones;

import items.Lettre;

public class EgypteAntique extends Zones {
    private static final long serialVersionUID = 1L;

    private static final String[][] ENIGMES = {
        {"Quelle créature mythologique mi-homme mi-lion gardait les pyramides d'Égypte ?",
         "SPHINX", "Il pose des devinettes à ceux qui osent le défier."},
        {"Quel titre portait le souverain divin de l'Égypte antique ?",
         "PHARAON", "Il était considéré comme un dieu vivant sur Terre."}
    };

    private static final String[][] OBJETS_POOL = {
        {"Vase en argile",         "Un vase ancien, fragile et poussiéreux."},
        {"Colonne brisée",         "Un fragment de colonne qui bloque le passage."},
        {"Bannière déchirée",      "Une bannière hiéroglyphique en lambeaux."},
        {"Statuette dorée",        "Une petite statuette de divinité égyptienne."},
        {"Urne funéraire",         "Une urne contenant des cendres millénaires."},
        {"Sarcophage entr'ouvert", "Un sarcophage dont le couvercle est glissé."},
        {"Papyrus roulé",          "Un rouleau de papyrus couvert de symboles."}
    };

    private boolean fresqueIntacte;

    public EgypteAntique() {
        super("Égypte antique",
              "La salle d'un sarcophage sacré. Des hiéroglyphes couvrent les murs.",
              creerCoffre(), selectionnerEnigme());
        this.fresqueIntacte = true;
    }

    private static Coffre creerCoffre() {
        return new Coffre(new Lettre("E", "Égypte antique"), "Loupe", "derrière une fresque");
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
        return super.afficherDescription() + (fresqueIntacte ? "\nUne fresque ancienne recouvre l'un des murs." : "");
    }

    public void casserFresque() {
        fresqueIntacte = false;
    }

    public void revelerSymbole() {
        casserFresque();
        setCoffreTrouve(true);
    }

    public boolean isFresqueIntacte() { return fresqueIntacte; }
}

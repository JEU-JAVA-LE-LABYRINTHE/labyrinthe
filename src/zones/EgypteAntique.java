package zones;

import items.Lettre;
import java.util.Arrays;
import java.util.List;

public class EgypteAntique extends Zones {
    private static final long serialVersionUID = 1L;

    private static final List<Enigme> ENIGMES = Arrays.asList(
        new Enigme("Quelle créature mythologique mi-homme mi-lion gardait les pyramides d'Égypte ?",
                   "SPHINX", "Il pose des devinettes à ceux qui osent le défier."),
        new Enigme("Quel titre portait le souverain divin de l'Égypte antique ?",
                   "PHARAON", "Il était considéré comme un dieu vivant sur Terre."),
        new Enigme("Quel fleuve était considéré comme sacré en Égypte antique ?",
                   "NIL", "Il apporte la vie au désert grâce à ses crues annuelles."),
        new Enigme("Quelle écriture hiéroglyphique utilisait des symboles pour représenter les mots ?",
                   "HIEROGLYPHES", "Ces symboles ornent les murs des temples et des pyramides."),
        new Enigme("Quel animal sacré était vénéré dans l'Égypte antique et représenté avec une tête de faucon ?",
                   "HORUS", "Il était le dieu du ciel et de la royauté."),
        new Enigme("Quelle structure funéraire monumentale servait de tombe aux pharaons ?",
                   "PYRAMIDE", "Elle pointe vers le ciel et défie le temps.")
    );

    private static final String[][] OBJETS_POOL = {
        {"Vase en argile",         "Un vase ancien, fragile et poussiéreux."},
        {"Colonne brisée",         "Un fragment de colonne qui bloque le passage."},
        {"Bannière déchirée",      "Une bannière hiéroglyphique en lambeaux."},
        {"Statuette dorée",        "Une petite statuette de divinité égyptienne."},
        {"Urne funéraire",         "Une urne contenant des cendres millénaires."},
        {"Sarcophage entr'ouvert", "Un sarcophage dont le couvercle est glissé."},
        {"Papyrus roulé",          "Un rouleau de papyrus couvert de symboles."}
    };

    private boolean fresqueIntact;

    public EgypteAntique() {
        super("Égypte antique",
              "La salle d'un sarcophage sacré. Des hiéroglyphes couvrent les murs.",
              creerCoffre(), selectionnerEnigme());
        this.fresqueIntact = true;
    }

    private static Coffre creerCoffre() {
        return new Coffre(new Lettre("E", "Égypte antique"), "Loupe", "derrière une fresque");
    }

    private static Enigme selectionnerEnigme() {
        int idx = (int) (Math.random() * ENIGMES.size());
        return ENIGMES.get(idx);
    }

    @Override
    protected void CreerObjet() {
        ajouterObjetsAleatoires(OBJETS_POOL, 5);
    }

    @Override
    public String afficherDescription() {
        return super.afficherDescription() + (fresqueIntact ? "\nUne fresque ancienne recouvre l'un des murs." : "");
    }

    public void casserFresque() {
        fresqueIntact = false;
    }

    public void revelerSymbole() {
        casserFresque();
        setCoffreTrouve(true);
    }

    public boolean isFresqueIntact() { return fresqueIntact; }
}

package zones;

import items.Lettre;
import java.util.Arrays;
import java.util.List;

public class Futur extends Zones {
    private static final long serialVersionUID = 1L;

    private static final List<Enigme> ENIGMES = Arrays.asList(
        new Enigme("Quel faisceau de lumière concentrée est utilisé dans les technologies du futur ?",
                   "LASER", "Il voyage à la vitesse de la lumière et peut tout couper."),
        new Enigme("Quel terme désigne des machines intelligentes capables d'effectuer des tâches humaines ?",
                   "ROBOT", "Ils travaillent sans se fatiguer et exécutent les ordres à la perfection."),
        new Enigme("Quelle technologie permet de voyager instantanément d'un point à un autre ?",
                   "TELEPORTATION", "Elle défie les lois de la physique actuelle."),
        new Enigme("Quel matériau révolutionnaire est plus léger que l'air et plus résistant que l'acier ?",
                   "GRAPHENE", "Il est composé d'une seule couche d'atomes de carbone."),
        new Enigme("Quelle source d'énergie propre utilise la fusion des atomes ?",
                   "ENERGIE NUCLEAIRE", "Elle reproduit le processus énergétique du Soleil."),
        new Enigme("Quel dispositif permet de communiquer avec n'importe qui sur Terre instantanément ?",
                   "INTERNET QUANTIQUE", "Il utilise les propriétés quantiques pour une transmission ultra-rapide.")
    );

    private static final String[][] OBJETS_POOL = {
        {"Écran brisé",        "Un écran holographique fissuré qui scintille."},
        {"Câble électrique",   "Un câble à haute tension qui traîne au sol."},
        {"Drone inerte",       "Un drone désactivé, posé à même le sol."},
        {"Boîte métallique",   "Une caisse de rangement verrouillée."},
        {"Hologramme glitché", "Un hologramme instable qui projette des images floues."},
        {"Capsule vide",       "Une capsule de cryogénisation ouverte et vide."},
        {"Console désactivée", "Un panneau de contrôle éteint et hors service."}
    };

    private boolean terminalActive;
    private String motDePasse;

    public Futur() {
        super("Futur lointain",
              "Une chambre technologique baignée de lumière bleue. Des écrans holographiques clignotent.",
              creerCoffre(), selectionnerEnigme());
        this.terminalActive = false;
    }

    private static Coffre creerCoffre() {
        return new Coffre(new Lettre("L", "Futur lointain"), "Puce holographique", "dans une armoire en verre");
    }

    private static Enigme selectionnerEnigme() {
        int idx = (int) (Math.random() * ENIGMES.size());
        return ENIGMES.get(idx);
    }

    @Override
    protected void creerObjet() {
        ajouterObjetsAleatoires(OBJETS_POOL, 5);
    }

    @Override
    public String afficherDescription() {
        return super.afficherDescription() + (terminalActive ? "\nTerminal actif." : "\nUn terminal holographique attend une entrée.");
    }

    public void activerTerminal() {
        terminalActive = true;
        setCoffreTrouve(true);
    }

    public boolean isTerminalActive() { return terminalActive; }

    public String obtenirMotPasse() { return motDePasse; }
}

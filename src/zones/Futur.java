package zones;

import items.Lettre;

public class Futur extends Zones {
    private static final long serialVersionUID = 1L;

    private static final String[][] ENIGMES = {
        {"Quel faisceau de lumière concentrée est utilisé dans les technologies du futur ?",
         "LASER", "Il voyage à la vitesse de la lumière et peut tout couper."},
        {"Quel terme désigne des machines intelligentes capables d'effectuer des tâches humaines ?",
         "ROBOT", "Ils travaillent sans se fatiguer et exécutent les ordres à la perfection."}
    };

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
        int idx = (int) (Math.random() * ENIGMES.length);
        return new Enigme(ENIGMES[idx][0], ENIGMES[idx][1], ENIGMES[idx][2]);
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

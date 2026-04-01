package zones;

import items.Lettre;

import java.io.Serializable;

public abstract class Zones implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String nom;
    private final String description;

    private boolean terminee;
    private boolean coffreTrouve;

    private final Coffre coffre;
    private final Enigme enigme;

    private Lettre lettreRecuperee;

    protected Zones(String nom, String description, Coffre coffre, Enigme enigme) {
        this.nom = nom;
        this.description = description;
        this.coffre = coffre;
        this.enigme = enigme;
        this.terminee = false;
        this.coffreTrouve = false;
        this.lettreRecuperee = null;
    }

    public String afficherDescription() {
        return description;
    }

    public boolean chercherCoffre() {
        return false;
    }

    public boolean ouvrirCoffre() {
        return false;
    }

    public String afficherIndice() {
        if (terminee) return "Rien à chercher ici : zone déjà terminée.";
        if (enigme == null) return "Aucune énigme dans cette zone.";
        return enigme.afficherIndice();
    }

    public boolean repondre(String rep) {
        if (terminee) return false;
        if (coffre == null || enigme == null) return false;
        if (!coffre.estOuvert()) return false;
        boolean ok = enigme.tenter(rep);
        if (ok) {
            lettreRecuperee = coffre.recupererLettre();
            terminee = true;
        }
        return ok;
    }

    public Lettre getLettreRecuperee() {
        return lettreRecuperee;
    }

    public boolean isTerminee() {
        return terminee;
    }

    public boolean isCoffreTrouve() {
        return coffreTrouve;
    }

    public String getNom() {
        return nom;
    }

    public Coffre getCoffre() {
        return coffre;
    }

    public Enigme getEnigme() {
        return enigme;
    }
}


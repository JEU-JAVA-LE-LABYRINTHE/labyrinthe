package personnes;

import zones.Zones;

public class PNJ extends Personne {
    private static final long serialVersionUID = 1L;
    private String dialogue;
    private boolean estHostile;

    public PNJ(String nom, Zones zone, String dialogue, boolean estHostile) {
        super(nom, zone);
        this.dialogue = dialogue;
        this.estHostile = estHostile;
    }

    public void parler() {
        System.out.println(nom + " dit : \"" + dialogue + "\"");
    }

    public String getDialogue() {
        return dialogue;
    }

    public boolean isEstHostile() {
        return estHostile;
    }

    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    public void interagir(personnes.Joueur joueur) {
        parler();
        if (estHostile) {
            joueur.perdreUneVie();
            System.out.println(nom + " est hostile ! " + joueur.getNom() + " perd une vie.");
        }
    }

    @Override
    public void afficherInfo() {
        System.out.println("PNJ: " + nom + " | hostile=" + estHostile);
    }
}

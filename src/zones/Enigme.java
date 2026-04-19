package zones;

import java.io.Serializable;

public class Enigme implements Serializable {
    private static final long serialVersionUID = 1L;
    private String question;
    private String reponseCorrecte;
    private String indice;
    private boolean resolue;
    private int tentativesRestantes;

    public Enigme(String question, String reponseCorrecte, String indice) {
        this.question = question;
        this.reponseCorrecte = reponseCorrecte;
        this.indice = indice;
        this.resolue = false;
        this.tentativesRestantes = 3;
    }

    public String getQuestion() {
        return question;
    }

    public String afficherIndice() {
        return indice;
    }

    public boolean tenter(String reponse) {
        if (reponse == null) return false;
        tentativesRestantes--;
        if (reponse.equalsIgnoreCase(reponseCorrecte)) {
            resolue = true;
            return true;
        }
        return false;
    }

    public boolean estResolue() {
        return resolue;
    }

    public int getTentativesRestantes() {
        return tentativesRestantes;
    }
}

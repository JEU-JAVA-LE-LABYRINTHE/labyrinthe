package utils;

public class Constants {
    
    // Temps
    public static final int TEMPS_LIMITE_DEFAUT = 300; // 5 minutes en secondes
    public static final int TEMPS_BONUS_OBJET = 30; // 30 secondes bonus par objet
    
    // Scores
    public static final int SCORE_SORTIE = 1000; // Points pour trouver la sortie
    public static final int SCORE_OBJET_DEFAUT = 100; // Points par défaut par objet
    public static final int SCORE_TEMPS_BONUS = 500; // Bonus si terminé rapidement
    
    // Interface
    public static final String TITRE_JEU = "Labyrinthe Temporel";
    public static final int FENETRE_LARGEUR = 800;
    public static final int FENETRE_HAUTEUR = 600;
    public static final int PANNEAU_LARGEUR = 700;
    public static final int PANNEAU_HAUTEUR = 400;
    
    // Fichiers
    public static final String DOSSIER_RESSOURCES = "resources/";
    public static final String DOSSIER_IMAGES = "resources/images/";
    public static final String DOSSIER_SAUVEGARDES = "sauvegardes/";
    public static final String DOSSIER_NIVEAUX = "resources/niveaux/";
    public static final String EXTENSION_SAUVEGARDE = ".ser";
    public static final String EXTENSION_NIVEAU = ".json";
    
    // Images par défaut
    public static final String IMAGE_FOND_DEFAUT = "tatooine.jpg";
    public static final String IMAGE_PERSONNAGE_DEFAUT = "ioda.png";
    public static final String IMAGE_FOND_ALTERNATIF = "coruscant.jpg";
    
    // Messages
    public static final String MESSAGE_BIENVENUE = "Bienvenue dans le Labyrinthe Temporel";
    public static final String MESSAGE_TEMPS_ECOULE = "Temps écoulé! Game Over!";
    public static final String MESSAGE_VICTOIRE = "Félicitations ! Vous avez trouvé la sortie !";
    public static final String MESSAGE_AUCUN_JEU = "Aucun jeu en cours";
    
    // Commandes
    public static final String[] COMMANDES_DEPLACEMENT = {"nord", "sud", "est", "ouest", "n", "s", "e", "o"};
    public static final String[] COMMANDES_SYSTEME = {"etat", "status", "aide", "?"};
    
    // Couleurs
    public static final java.awt.Color COULEUR_TEMPS_NORMAL = java.awt.Color.GREEN;
    public static final java.awt.Color COULEUR_TEMPS_ATTENTION = java.awt.Color.ORANGE;
    public static final java.awt.Color COULEUR_TEMPS_URGENT = java.awt.Color.RED;
    public static final java.awt.Color COULEUR_FOND = java.awt.Color.BLACK;
    public static final java.awt.Color COULEUR_TEXTE = java.awt.Color.WHITE;
    
    // Polices
    public static final String POLICE_DEFAUT = "Arial";
    public static final String POLICE_TITRE = "TimesRoman";
    public static final int TAILLE_POLICE_NORMAL = 14;
    public static final int TAILLE_POLICE_TITRE = 18;
    
    // Seuils d'avertissement temps
    public static final int SEUIL_TEMPS_ATTENTION = 60; // 1 minute
    public static final int SEUIL_TEMPS_URGENT = 30; // 30 secondes
    

    private Constants() {
        // Classe utilitaire, pas d'instanciation
    }
}

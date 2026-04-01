package helper;

/**
 * Classe Helper pour lister et afficher les commandes disponibles du jeu.
 * Centralize la documentation de toutes les commandes disponibles pour le joueur.
 */
public class CommandeHelper {

    /**
     * Affiche la liste complète des commandes disponibles dans le jeu.
     * @return String contenant la liste formatée des commandes
     */
    public static String afficherAide() {
        return getAideComplette();
    }

    /**
     * Retourne l'aide formatée pour le joueur.
     * @return String contenant l'aide formatée
     */
    public static String getAideComplette() {
        return formatSection("COMMANDES DISPONIBLES")
            + "\n\n" + construireSection("🕐 DÉPLACEMENTS TEMPORELS",
                "O / OUEST            : Avancer dans le temps",
                "E / EST              : Reculer dans le temps",
                "Z                    : Retour direct à Zaman"
            )
            + "\n" + construireSection("📦 OBJETS & INVENTAIRE",
                "PRENDRE <objet>      : Prendre un objet à Zaman",
                "  Exemples: PRENDRE Marteau  /  P Clé ancienne",
                "DEPOSER <objet>      : Déposer un artefact à Zaman",
                "  Exemples: DEPOSER Marteau  /  D Loupe",
                "SAC / INVENTAIRE     : Afficher le contenu du sac",
                "INV                  : Raccourci pour inventaire"
            )
            + "\n" + construireSection("🔍 EXPLORATION & ÉNIGMES",
                "REGARDER / L         : Décrire la zone actuelle",
                "CHERCHER / CH        : Chercher le coffre dans la zone",
                "OUVRIR               : Ouvrir le coffre (nécessite l'objet requis)",
                "INDICE / I           : Obtenir un indice pour l'énigme",
                "REPONDRE <réponse>   : Répondre à l'énigme",
                "  Exemples: REPONDRE Feu  /  R Sphinx  /  R Épée"
            )
            + "\n" + construireSection("ℹ️  INFORMATIONS",
                "TEMPS / T            : Afficher le temps restant dans la zone",
                "STATUS / STAT / ETAT : Afficher l'état du jeu (vies + lettres)"
            )
            + "\n" + construireSection("💾 PARTIE & SAUVEGARDE",
                "SAUVEGARDER / SAVE   : Sauvegarder la partie en cours",
                "CHARGER / LOAD       : Charger une partie précédente",
                "NOUVELLE / N         : Démarrer une nouvelle partie",
                "AIDE / H / ?         : Afficher cette aide"
            )
            + "\n" + construireSection("❌ QUITTER",
                "QUITTER / EXIT       : Quitter le jeu"
            )
            + "\n\n" + formatFooter();
    }

    /**
     * Formate un titre de section
     */
    private static String formatSection(String titre) {
        return "╔════════════════════════════════════════════════════════════════╗\n"
            + "║                    " + String.format("%-42s", titre) + "║\n"
            + "╚════════════════════════════════════════════════════════════════╝";
    }

    /**
     * Construit une section avec titre et lignes
     */
    private static String construireSection(String titre, String... lignes) {
        StringBuilder sb = new StringBuilder(titre).append("\n");
        for (String ligne : lignes) {
            sb.append("  ").append(ligne).append("\n");
        }
        return sb.toString();
    }

    /**
     * Formate le pied de page
     */
    private static String formatFooter() {
        return "═══════════════════════════════════════════════════════════════════\n"
            + "💡 RACCOURCIS : Utilisez P ou PRENDRE, D ou DEPOSER, L avec REGARDER\n"
            + "💡 OBJETS À ZAMAN: Marteau, Clé ancienne, Loupe, Puce holographique\n"
            + "═══════════════════════════════════════════════════════════════════\n";
    }

    /**
     * Retourne une aide simple et rapide (version courte).
     * @return String avec l'aide compact
     */
    public static String getAideSimple() {
        return ""
            + "Commandes scénario: O/OUEST, E/EST, Z\n"
            + "Objets: P/PRENDRE, D/DEPOSER, SAC/INVENTAIRE\n"
            + "Exploration: L/REGARDER, CH/CHERCHER, OUVRIR, I/INDICE, R/REPONDRE\n"
            + "Infos: TEMPS, STATUS, AIDE\n"
            + "Jeu: SAUVEGARDER, CHARGER, NOUVELLE, QUITTER\n";
    }

    /**
     * Retourne les commandes disponibles pour une catégorie spécifique.
     * @param categorie La catégorie des commandes
     * @return String avec les commandes de la catégorie
     */
    public static String getCommandesParCategorie(String categorie) {
        return switch (categorie.toLowerCase()) {
            case "deplacement", "temps", "movement" ->
                "O/OUEST : Avancer dans le temps\n" +
                "E/EST : Reculer dans le temps\n" +
                "Z : Retour à Zaman\n";

            case "objet", "items", "inventaire" ->
                "P/PRENDRE <objet> : Prendre un objet\n" +
                "D/DEPOSER <objet> : Déposer un objet\n" +
                "SAC/INVENTAIRE : Voir l'inventaire\n";

            case "enigme", "puzzle", "exploration" ->
                "L/REGARDER : Décrire la zone\n" +
                "CH/CHERCHER : Chercher le coffre\n" +
                "OUVRIR : Ouvrir le coffre\n" +
                "I/INDICE : Obtenir un indice\n" +
                "R/REPONDRE <reponse> : Répondre à l'énigme\n";

            case "info", "information", "status" ->
                "TEMPS/T : Temps restant\n" +
                "STATUS/ETAT : État du jeu\n" +
                "AIDE/H/? : Afficher l'aide\n";

            case "sauvegarde", "save", "load" ->
                "SAUVEGARDER/SAVE : Sauvegarder\n" +
                "CHARGER/LOAD : Charger\n" +
                "NOUVELLE/N : Nouvelle partie\n";

            default -> "Catégories : deplacement, objet, enigme, info, sauvegarde";
        };
    }

    /**
     * Retourne les raccourcis de commandes.
     * @return String avec les raccourcis clés
     */
    public static String getShortcuts() {
        return construireSection("RACCOURCIS CLÉS",
            "O / E / Z            (déplacements)",
            "P / D                (prendre/déposer)",
            "L                    (regarder)",
            "CH                   (chercher)",
            "I / R                (indice/répondre)",
            "T / STAT             (temps/état)",
            "H / ? / AIDE         (aide)"
        );
    }
}

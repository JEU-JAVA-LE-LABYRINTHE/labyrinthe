package view;

import controller.JeuController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanneauControle extends JPanel {
    private LabyrintheFrame frame;
    private JeuController jeuController;

    private JLabel invite;
    private JTextField zoneSaisie;
    private JButton boutonValider;
    private JButton boutonOuest;
    private JButton boutonEst;
    private JButton boutonZaman;
    private JButton boutonRamasser;
    private JButton boutonUtiliser;
    private JButton boutonObserver;
    private JButton boutonLaisser;
    private JButton boutonRecupererLettre;
    private JButton boutonRepondre;
    private JButton boutonEtat;
    private JButton boutonAide;
    private JButton boutonNouvellePartie;

    private JTextArea console;

    public PanneauControle(LabyrintheFrame frame) {
        this.frame = frame;
        initComposants();
        initListeners();
    }

    private void initComposants() {
        invite        = new JLabel("Commande:");
        zoneSaisie    = new JTextField(20);
        boutonValider = new JButton("Valider");
        boutonOuest   = new JButton("O (Avancer)");
        boutonEst     = new JButton("E (Reculer)");
        boutonZaman   = new JButton("Z (Zaman)");
        boutonRamasser = new JButton("Ramasser");
        boutonUtiliser = new JButton("Utiliser");
        boutonObserver = new JButton("Observer");
        boutonLaisser          = new JButton("Laisser");
        boutonRecupererLettre  = new JButton("Récupérer lettre");
        boutonRepondre         = new JButton("Répondre");
        boutonEtat             = new JButton("État");
        boutonAide             = new JButton("Aide");
        boutonNouvellePartie   = new JButton("Nouvelle partie");
        boutonNouvellePartie.setBackground(new Color(180, 60, 60));
        boutonNouvellePartie.setForeground(Color.WHITE);
        boutonNouvellePartie.setOpaque(true);

        // Ligne de commandes (haut)
        JPanel ligneCommandes = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4));
        ligneCommandes.add(invite);
        ligneCommandes.add(zoneSaisie);
        ligneCommandes.add(boutonValider);
        ligneCommandes.add(new JLabel("|"));
        ligneCommandes.add(boutonEst);
        ligneCommandes.add(boutonOuest);
        ligneCommandes.add(boutonZaman);
        ligneCommandes.add(new JLabel("|"));
        ligneCommandes.add(boutonRamasser);
        ligneCommandes.add(boutonUtiliser);
        ligneCommandes.add(boutonObserver);
        ligneCommandes.add(boutonLaisser);
        ligneCommandes.add(boutonRecupererLettre);
        ligneCommandes.add(boutonRepondre);
        ligneCommandes.add(new JLabel("|"));
        ligneCommandes.add(boutonEtat);
        ligneCommandes.add(new JLabel("|"));
        ligneCommandes.add(boutonAide);
        ligneCommandes.add(new JLabel("|"));
        ligneCommandes.add(boutonNouvellePartie);

        // Console (bas)
        console = new JTextArea(8, 0);
        console.setEditable(false);
        console.setBackground(new Color(15, 15, 15));
        console.setForeground(new Color(200, 255, 200));
        console.setFont(new Font("Monospaced", Font.PLAIN, 14));
        console.setMargin(new Insets(6, 10, 6, 10));
        console.setLineWrap(true);
        console.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(console);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(50, 80, 50)));

        setLayout(new BorderLayout());
        add(ligneCommandes, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        setPreferredSize(new Dimension(0, 286));
    }

    private void initListeners() {
        boutonValider.addActionListener(e -> executerCommande(zoneSaisie.getText().trim()));
        zoneSaisie.addActionListener(e -> executerCommande(zoneSaisie.getText().trim()));

        boutonEst.addActionListener(e -> executerCommande("E"));
        boutonOuest.addActionListener(e -> executerCommande("O"));
        boutonZaman.addActionListener(e -> executerCommande("Z"));

        boutonRamasser.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Nom de l'objet à ramasser :");
            if (input != null && !input.trim().isEmpty()) {
                executerCommande("PRENDRE " + input.trim());
            }
        });

        boutonUtiliser.addActionListener(e -> {
            if (!validerController()) return;
            List<String> items = jeuController.getInventaireItems();
            if (items.isEmpty()) {
                afficherDansConsole("Votre sac est vide.");
                return;
            }
            Object choix = JOptionPane.showInputDialog(frame,
                "Que voulez-vous utiliser ?", "Utiliser",
                JOptionPane.QUESTION_MESSAGE, null,
                items.toArray(), items.get(0));
            if (choix != null) {
                executerCommande("CH");
                executerCommande("OUVRIR");
            }
        });

        boutonObserver.addActionListener(e -> executerCommande("OB"));

        boutonLaisser.addActionListener(e -> {
            if (!validerController()) return;
            List<String> items = jeuController.getInventaireItems();
            if (items.isEmpty()) {
                afficherDansConsole("Votre sac est vide.");
                return;
            }
            Object choix = JOptionPane.showInputDialog(frame,
                "Quel objet voulez-vous laisser ici ?", "Laisser un objet",
                JOptionPane.QUESTION_MESSAGE, null,
                items.toArray(), items.get(0));
            if (choix != null) {
                executerCommande("LAISSER " + choix);
            }
        });

        boutonRecupererLettre.addActionListener(e -> {
            if (!validerController()) return;
            String nomLettre = jeuController.getLettreNomZoneCourante();
            if (nomLettre == null) {
                afficherDansConsole("Aucune lettre disponible dans cette zone.");
            } else {
                executerCommande("PRENDRE " + nomLettre);
            }
        });

        boutonRepondre.addActionListener(e -> demanderReponseEnigme());

        boutonEtat.addActionListener(e -> executerCommande("STATUS"));

        boutonAide.addActionListener(e -> {
            AideDialog dialog = new AideDialog(frame);
            dialog.setVisible(true);
        });

        boutonNouvellePartie.addActionListener(e -> {
            if (!validerController()) return;
            String nom = jeuController.getNomJoueur();
            jeuController.demarrerNouvellePartie(nom);
            afficherDansConsole("═══════════════════════════════");
            afficherDansConsole("  Nouvelle partie — Bonne chance, " + nom + " !");
            afficherDansConsole("═══════════════════════════════");
            afficherDansConsole(jeuController.traiterCommande("REGARDER"));
            frame.getLabyrinthePanel().repaint();
        });
    }

    private void executerCommande(String commande) {
        if (!validerController()) return;
        if (commande == null || commande.isEmpty()) return;

        boolean enigmeAvant  = jeuController.isEnigmeActive();
        boolean enCoursAvant = jeuController.isJeuEnCours();
        boolean aZamanAvant  = jeuController.estAZaman();

        afficherDansConsole("> " + commande);
        try {
            String msg = jeuController.traiterCommande(commande);
            afficherDansConsole(msg != null ? msg : "(pas de réponse)");
        } catch (Exception ex) {
            afficherDansConsole("[Erreur] " + ex.getMessage());
        }
        zoneSaisie.setText("");
        frame.getLabyrinthePanel().repaint();

        if (enCoursAvant && !jeuController.isJeuEnCours() && !jeuController.isVictoire()) {
            proposerNouvellePartie();
            return;
        }

        // Arrivée à Zaman avec toutes les lettres → demander le mot secret
        if (!aZamanAvant && jeuController.estAZaman() && jeuController.toutesLettresCollectees()) {
            demanderMotFinal();
            return;
        }

        if (!enigmeAvant && jeuController.isEnigmeActive()) {
            demanderReponseEnigme();
        }
    }

    private void proposerNouvellePartie() {
        String[] options = {"Nouvelle partie", "Charger une partie"};
        int choix = JOptionPane.showOptionDialog(frame,
            "☠ GAME OVER !\nQue souhaitez-vous faire ?",
            "Fin de partie",
            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
            null, options, options[0]);

        String nom = jeuController.getNomJoueur();
        if (choix == 1) {
            String msg = jeuController.charger();
            afficherDansConsole(msg);
        } else {
            jeuController.demarrerNouvellePartie(nom);
            afficherDansConsole("═══════════════════════════════");
            afficherDansConsole("  Nouvelle partie — Bonne chance, " + nom + " !");
            afficherDansConsole("═══════════════════════════════");
            afficherDansConsole(jeuController.traiterCommande("REGARDER"));
        }
        frame.getLabyrinthePanel().repaint();
    }

    private void demanderReponseEnigme() {
        while (jeuController.isEnigmeActive()) {
            String reponse = JOptionPane.showInputDialog(frame, jeuController.getQuestionEnigme());
            if (reponse == null || reponse.trim().isEmpty()) break;
            afficherDansConsole("> R " + reponse.trim());
            try {
                String msg = jeuController.traiterCommande("R " + reponse.trim());
                afficherDansConsole(msg != null ? msg : "(pas de réponse)");
            } catch (Exception ex) {
                afficherDansConsole("[Erreur] " + ex.getMessage());
            }
            frame.getLabyrinthePanel().repaint();
            if (!jeuController.isJeuEnCours() && !jeuController.isVictoire()) {
                proposerNouvellePartie();
                return;
            }
        }
        // Après résolution de la dernière énigme, proposer le retour à Zaman
        if (jeuController.toutesLettresCollectees() && jeuController.isJeuEnCours()) {
            proposerRetourZaman();
        }
    }

    private void proposerRetourZaman() {
        JOptionPane.showMessageDialog(frame,
            "Toutes les lettres collectées : " + jeuController.getLettresJoueur()
            + "\n\nRetournez à Zaman pour révéler le mot secret !",
            "Lettres complètes", JOptionPane.INFORMATION_MESSAGE);
        // Déplace automatiquement le joueur à Zaman
        afficherDansConsole("> Z");
        String msg = jeuController.traiterCommande("Z");
        afficherDansConsole(msg != null ? msg : "");
        frame.getLabyrinthePanel().repaint();
        demanderMotFinal();
    }

    private void demanderMotFinal() {
        while (jeuController.isJeuEnCours() && !jeuController.isVictoire()) {
            String mot = JOptionPane.showInputDialog(frame,
                "Vos lettres : " + jeuController.getLettresJoueur()
                + "\n\nFormez le mot secret avec ces lettres :");
            if (mot == null || mot.trim().isEmpty()) break;

            String resultat = jeuController.traiterCommande("DEVERROUILLER " + mot.trim());
            afficherDansConsole(resultat != null ? resultat : "");
            frame.getLabyrinthePanel().repaint();

            if (jeuController.isVictoire()) {
                JOptionPane.showMessageDialog(frame,
                    "Félicitations " + jeuController.getNomJoueur() + " !\n"
                    + "Vous avez trouvé le mot secret : " + mot.toUpperCase()
                    + "\n\nVous vous échappez du labyrinthe temporel !",
                    "★ VICTOIRE ★", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (!jeuController.isJeuEnCours()) {
                proposerNouvellePartie();
                return;
            }
            JOptionPane.showMessageDialog(frame,
                "Mauvaise réponse ! Vous perdez une vie.\nVies restantes : " + jeuController.getNombreVies(),
                "Mauvaise réponse", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void afficherDansConsole(String msg) {
        if (msg == null) return;
        console.append(msg + "\n");
        console.setCaretPosition(console.getDocument().getLength());
    }

    private boolean validerController() {
        if (jeuController != null) return true;
        afficherDansConsole("[Erreur] Aucun jeu en cours.");
        return false;
    }

    public void setJeuController(JeuController jeuController) {
        this.jeuController = jeuController;
    }
}

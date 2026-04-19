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

        boutonRepondre.addActionListener(e -> {
            String reponse = JOptionPane.showInputDialog(frame, "Votre réponse à l'énigme :");
            if (reponse != null && !reponse.trim().isEmpty()) {
                executerCommande("R " + reponse.trim());
            }
        });

        boutonEtat.addActionListener(e -> executerCommande("STATUS"));

        boutonAide.addActionListener(e -> {
            AideDialog dialog = new AideDialog(frame);
            dialog.setVisible(true);
        });
    }

    private void executerCommande(String commande) {
        if (!validerController()) return;
        if (commande == null || commande.isEmpty()) return;

        afficherDansConsole("> " + commande);
        try {
            String msg = jeuController.traiterCommande(commande);
            afficherDansConsole(msg != null ? msg : "(pas de réponse)");
        } catch (Exception ex) {
            afficherDansConsole("[Erreur] " + ex.getMessage());
        }
        zoneSaisie.setText("");
        frame.getLabyrinthePanel().repaint();
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

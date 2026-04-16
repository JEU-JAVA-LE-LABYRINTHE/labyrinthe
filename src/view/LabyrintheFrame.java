package view;

import controller.JeuController;

import javax.swing.*;
import java.awt.*;

public class LabyrintheFrame extends JFrame {
    private LabyrinthePanel labyrinthePanel;
    private PanneauControle panneauControle;
    private JeuController jeuController;
    
    public LabyrintheFrame() {
        setTitle("Labyrinthe Temporel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Initialisation des composants
        labyrinthePanel = new LabyrinthePanel();
        panneauControle = new PanneauControle(this);
        
        // Layout
        setLayout(new BorderLayout());
        add(labyrinthePanel, BorderLayout.CENTER);
        add(panneauControle, BorderLayout.SOUTH);
        
        // Menu
        creerMenu();
    }
    
    private void creerMenu() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menu Jeu
        JMenu menuJeu = new JMenu("Jeu");
        
        ajouterMenuItemJeu(menuJeu, "Nouveau jeu", e -> demarrerNouveauJeu());
        menuJeu.addSeparator();
        ajouterMenuItemJeu(menuJeu, "Sauvegarder", e -> sauvegarderJeu());
        ajouterMenuItemJeu(menuJeu, "Charger", e -> chargerJeu());
        menuJeu.addSeparator();
        ajouterMenuItemJeu(menuJeu, "Quitter", e -> System.exit(0));
        
        // Menu Aide
        JMenu menuAide = new JMenu("Aide");
        ajouterMenuItemJeu(menuAide, "À propos", e -> afficherApropos());
        
        menuBar.add(menuJeu);
        menuBar.add(menuAide);
        
        setJMenuBar(menuBar);
    }
    
    /**
     * Ajoute un item de menu avec un écouteur d'action
     */
    private void ajouterMenuItemJeu(JMenu menu, String label, java.awt.event.ActionListener actionListener) {
        JMenuItem item = new JMenuItem(label);
        item.addActionListener(actionListener);
        menu.add(item);
    }
    
    private void demarrerNouveauJeu() {
        executerAvecControleur(jeuController -> {
            String msg = jeuController.nouvellePartie();
            labyrinthePanel.repaint();
            panneauControle.afficherDansConsole(msg);
        });
    }

    private void sauvegarderJeu() {
        if (jeuController == null || !jeuController.isJeuEnCours()) {
            panneauControle.afficherDansConsole("[Erreur] Aucun jeu en cours.");
            return;
        }
        panneauControle.afficherDansConsole(jeuController.sauvegarder());
    }

    private void chargerJeu() {
        executerAvecControleur(jeuController -> {
            String msg = jeuController.charger();
            labyrinthePanel.repaint();
            panneauControle.afficherDansConsole(msg);
        });
    }
    
    private void afficherApropos() {
        afficherDialog(
            "Labyrinthe Temporel\nVersion 1.0\n\nUn jeu de labyrinthe avec gestion du temps",
            "À propos",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Exécute une action avec validation du contrôleur
     */
    private void executerAvecControleur(java.util.function.Consumer<JeuController> action) {
        if (jeuController != null) {
            action.accept(jeuController);
        } else {
            afficherDialog("Aucun contrôleur de jeu", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Affiche une boîte de dialogue standardisée
     */
    private void afficherDialog(String msg, String titre, int type) {
        JOptionPane.showMessageDialog(this, msg, titre, type);
    }
    
    public void setJeuController(JeuController jeuController) {
        this.jeuController = jeuController;
        labyrinthePanel.setJeuController(jeuController);
        panneauControle.setJeuController(jeuController);

        String[] options = {"Nouvelle partie", "Charger une partie"};
        int choix = JOptionPane.showOptionDialog(this,
            "Bienvenue dans le Labyrinthe Temporel !\nQue souhaitez-vous faire ?",
            "Labyrinthe Temporel",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]);

        if (choix == 1) {
            String msg = jeuController.charger();
            afficherIntroduction(jeuController.getNomJoueur());
            panneauControle.afficherDansConsole(msg);
        } else {
            String nom = JOptionPane.showInputDialog(this,
                "Quel est le nom de votre explorateur ?", "Kairox");
            if (nom == null || nom.trim().isEmpty()) nom = "Kairox";
            jeuController.demarrerNouvellePartie(nom.trim());
            afficherIntroduction(nom.trim());
        }
        panneauControle.afficherDansConsole(jeuController.traiterCommande("REGARDER"));
    }

    private void afficherIntroduction(String nom) {
        panneauControle.afficherDansConsole("Bienvenue, " + nom + " !");
        panneauControle.afficherDansConsole("Collectez une lettre dans chaque époque, formez le mot secret, déverrouillez la sortie.");
        panneauControle.afficherDansConsole("Tapez AIDE pour les actions disponibles.");
        panneauControle.afficherDansConsole("");
    }

    public LabyrinthePanel getLabyrinthePanel() {
        return labyrinthePanel;
    }
    
    public PanneauControle getPanneauControle() {
        return panneauControle;
    }
}

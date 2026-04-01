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
            panneauControle.repaint();
            labyrinthePanel.repaint();
            afficherDialog(msg, "Info", JOptionPane.INFORMATION_MESSAGE);
        });
    }
    
    private void sauvegarderJeu() {
        if (jeuController == null || !jeuController.isJeuEnCours()) {
            afficherDialog("Aucun jeu en cours", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String msg = jeuController.sauvegarder();
        afficherDialog(msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void chargerJeu() {
        executerAvecControleur(jeuController -> {
            String msg = jeuController.charger();
            labyrinthePanel.repaint();
            panneauControle.repaint();
            afficherDialog(msg, "Info", JOptionPane.INFORMATION_MESSAGE);
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
    }
    
    public LabyrinthePanel getLabyrinthePanel() {
        return labyrinthePanel;
    }
    
    public PanneauControle getPanneauControle() {
        return panneauControle;
    }
}

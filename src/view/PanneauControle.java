package view;

import controller.JeuController;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanneauControle extends JPanel {
    private LabyrintheFrame frame;
    private JeuController jeuController;
    
    private JLabel invite;
    private JTextField zoneSaisie;
    private JButton boutonValider;
    private JButton boutonOuest; // O (avancer dans le temps)
    private JButton boutonEst;   // E (reculer dans le temps)
    private JButton boutonZaman; // Z (retour direct)
    private JButton boutonRamasser;
    private JButton boutonEtat;
    private JButton boutonAide;
    
    public PanneauControle(LabyrintheFrame frame) {
        this.frame = frame;
        initComposants();
        initListeners();
    }
    
    private void initComposants() {
        invite = new JLabel("Commande:");
        zoneSaisie = new JTextField("entrez une commande", 20);
        
        boutonValider = new JButton("Valider");
        boutonOuest = new JButton("O (Avancer)");
        boutonEst = new JButton("E (Reculer)");
        boutonZaman = new JButton("Z (Zaman)");
        boutonRamasser = new JButton("Ramasser");
        boutonEtat = new JButton("État");
        boutonAide = new JButton("Aide");
        
        // Layout
        setLayout(new java.awt.FlowLayout());
        
        add(invite);
        add(zoneSaisie);
        add(boutonValider);
        add(new JLabel(" | "));
        add(boutonEst);
        add(boutonOuest);
        add(boutonZaman);
        add(new JLabel(" | "));
        add(boutonRamasser);
        add(new JLabel(" | "));
        add(boutonEtat);
        add(new JLabel(" | "));
        add(boutonAide);
    }
    
    private void initListeners() {
        boutonValider.addActionListener(e -> executerCommande(zoneSaisie.getText().trim()));
        
        boutonEst.addActionListener(e -> executerDeplacement("est"));
        boutonOuest.addActionListener(e -> executerDeplacement("ouest"));
        boutonZaman.addActionListener(e -> executerDeplacement("z"));
        
        boutonRamasser.addActionListener(e -> demanderEdAfficherResultat(
            "Nom de l'objet à ramasser:",
            nomObjet -> jeuController.ramasserObjet(nomObjet.trim())
        ));
        
        boutonEtat.addActionListener(e -> executerAvecValidation(() -> 
            jeuController.afficherEtatJeu()
        ));

        boutonAide.addActionListener(e -> {
            AideDialog dialog = new AideDialog(frame);
            dialog.setVisible(true);
        });
        
        zoneSaisie.addActionListener(e -> executerCommande(zoneSaisie.getText().trim()));
    }
    
    private void executerCommande(String commande) {
        if (!validerController()) return;
        
        String msg = jeuController.traiterCommande(commande);
        afficherDialog(msg, "Jeu", JOptionPane.INFORMATION_MESSAGE);
        zoneSaisie.setText("");
        frame.getLabyrinthePanel().repaint();
    }
    
    private void executerDeplacement(String direction) {
        if (!validerController()) return;
        
        String cmd = convertirDirectionEnCommande(direction);
        String msg = jeuController.traiterCommande(cmd);
        afficherDialog(msg, "Jeu", JOptionPane.INFORMATION_MESSAGE);
        frame.getLabyrinthePanel().repaint();
    }
    
    /**
     * Convertit une direction en commande de jeu
     */
    private String convertirDirectionEnCommande(String direction) {
        if (direction == null) return "";
        return switch (direction.toLowerCase().trim()) {
            case "ouest", "o" -> "O";
            case "est", "e" -> "E";
            case "z" -> "Z";
            default -> direction;
        };
    }
    
    /**
     * Exécute une action avec validation du contrôleur
     */
    private void executerAvecValidation(Runnable action) {
        if (!validerController()) return;
        action.run();
    }
    
    /**
     * Demande une saisie utilisateur et exécute une action avec le résultat
     */
    private void demanderEdAfficherResultat(String prompt, java.util.function.Function<String, String> action) {
        if (!validerController()) return;
        
        String input = JOptionPane.showInputDialog(frame, prompt);
        if (input != null && !input.trim().isEmpty()) {
            String msg = action.apply(input.trim());
            afficherDialog(msg, "Jeu", JOptionPane.INFORMATION_MESSAGE);
            frame.getLabyrinthePanel().repaint();
        }
    }
    
    /**
     * Valide que le contrôleur est disponible
     */
    private boolean validerController() {
        if (jeuController != null) return true;
        afficherDialog("Aucun jeu en cours", "Erreur", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    /**
     * Affiche une boîte de dialogue standardisée
     */
    private void afficherDialog(String msg, String titre, int type) {
        JOptionPane.showMessageDialog(frame, msg, titre, type);
    }
    
    public void setJeuController(JeuController jeuController) {
        this.jeuController = jeuController;
    }
}

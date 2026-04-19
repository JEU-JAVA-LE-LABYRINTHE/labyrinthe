package launcher;

import controller.JeuController;
import view.LabyrintheFrame;

import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Impossible de définir le look and feel: " + e.getMessage());
        }
        LancerJeu();
    }

    public static void LancerJeu() {
        SwingUtilities.invokeLater(() -> {
            LabyrintheFrame frame = new LabyrintheFrame();
            JeuController jeuController = new JeuController();
            frame.setJeuController(jeuController);
            jeuController.demarrerJeu();
            frame.setVisible(true);
        });
    }
}

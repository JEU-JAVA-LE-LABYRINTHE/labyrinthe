package view;

import controller.JeuController;
import zones.Zones;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabyrinthePanel extends JPanel {
    private JeuController jeuController;
    private Image imageFond;
    private Image imagePersonnage;
    private String message = "Bienvenue dans le Labyrinthe Temporel";
    private String zoneActuelleChargee = null;
    
    // Mapping entre les noms de zones et les fichiers images
    private static final Map<String, String> ZONE_IMAGE_MAP = new HashMap<>();
    
    // Mapping des images variantes selon les états
    private static final Map<String, Map<String, String>> ZONE_IMAGE_STATES = new HashMap<>();
    
    static {
        // Images principales par défaut
        ZONE_IMAGE_MAP.put("Zaman", "Zone0.jpg");
        ZONE_IMAGE_MAP.put("Préhistoire", "Zone 1.jpg");
        ZONE_IMAGE_MAP.put("Égypte antique", "Zone 3.jpg");
        ZONE_IMAGE_MAP.put("Moyen Âge", "Zone 2_1.jpg");
        ZONE_IMAGE_MAP.put("Futur lointain", "Zone 4.jpg");
        
        // Images variantes selon les états
        Map<String, String> prehistoireStates = new HashMap<>();
        prehistoireStates.put("default", "Zone 1.jpg");
        prehistoireStates.put("coffre_trouve", "Zone 1_1.jpg");
        prehistoireStates.put("terminee", "Zone 1_2.jpg");
        ZONE_IMAGE_STATES.put("Préhistoire", prehistoireStates);
        
        Map<String, String> egypteStates = new HashMap<>();
        egypteStates.put("default", "Zone 3.jpg");
        egypteStates.put("coffre_trouve", "Zone 3_1.jpg");
        egypteStates.put("terminee", "Zone 3_2.jpg");
        ZONE_IMAGE_STATES.put("Égypte antique", egypteStates);
        
        Map<String, String> moyenAgeStates = new HashMap<>();
        moyenAgeStates.put("default", "Zone 2_1.jpg");
        moyenAgeStates.put("coffre_trouve", "Zone 2_2.jpg");
        moyenAgeStates.put("terminee", "Zone 2_3.jpg");
        ZONE_IMAGE_STATES.put("Moyen Âge", moyenAgeStates);
        
        Map<String, String> futurStates = new HashMap<>();
        futurStates.put("default", "Zone 4.jpg");
        futurStates.put("coffre_trouve", "Zone 4_1.jpg");
        futurStates.put("terminee", "Zone 4_2.jpg");
        ZONE_IMAGE_STATES.put("Futur lointain", futurStates);
        
        // Zaman n'a pas de variantes (zone centrale)
        Map<String, String> zamanStates = new HashMap<>();
        zamanStates.put("default", "Zone0.jpg");
        ZONE_IMAGE_STATES.put("Zaman", zamanStates);
    }
    
    public LabyrinthePanel() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(700, 400));
        
        // Charger les images par défaut
        chargerImages();
    }
    
    private void chargerImages() {
        try {
            if (jeuController != null) {
                mettreAJourImageZone();
            } else {
                chargerImageParDefaut("Zaman");
            }
        } catch (Exception e) {
            System.out.println("Images non trouvées, utilisation du rendu par défaut");
        }
    }
    
    private void mettreAJourImageZone() {
        if (!isControllerValide()) return;

        String nomZone = jeuController.getNomZoneCourante();
        String etatZone = determinerEtatZone(nomZone);
        String nomImage = obtenirImageSelonEtat(nomZone, etatZone);

        if (nomImage == null) return;
        if (!nomImage.equals(zoneActuelleChargee)) {
            chargerImageParDefaut(nomImage);
            zoneActuelleChargee = nomImage;
        }
    }
    
    /**
     * Valide que le contrôleur est disponible
     */
    private boolean isControllerValide() {
        return jeuController != null;
    }
    
    /**
     * Détermine l'état actuel d'une zone selon les critères du jeu
     */
    private String determinerEtatZone(String nomZone) {
        if (jeuController == null) return "default";
        
        // Récupérer la zone actuelle depuis le contrôleur
        Zones zone = jeuController.getZoneCourante();
        if (zone == null) return "default";
        
        // Priorité : coffre trouvé > observée > défaut
        if (zone.isCoffreTrouve()) {
            return "terminee";
        } else if (zone.isObserve()) {
            return "coffre_trouve";
        } else {
            return "default";
        }
    }
    
    /**
     * Obtient le nom de l'image selon la zone et son état
     */
    private String obtenirImageSelonEtat(String nomZone, String etat) {
        Map<String, String> etatsZone = ZONE_IMAGE_STATES.get(nomZone);
        if (etatsZone != null && etatsZone.containsKey(etat)) {
            return etatsZone.get(etat);
        }
        String img = ZONE_IMAGE_MAP.get(nomZone);
        return img != null ? img : "Zone0.jpg";
    }
    
    /**
     * Charge une image par son nom de fichier ou sa zone
     * Essaie plusieurs chemins possibles pour trouver la ressource
     */
    private void chargerImageParDefaut(String nomImage) {
        try {
            URL imageUrl = chargerRessource(nomImage);
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                if (icon.getIconWidth() > 0) {
                    imageFond = icon.getImage();
                } else {
                    imageFond = null;
                }
            } else {
                imageFond = null;
            }
        } catch (Exception e) {
            System.out.println("Erreur chargement image: " + nomImage + " - " + e.getMessage());
            imageFond = null;
        }
    }
    
    /**
     * Essaie de localiser une ressource image avec plusieurs chemins possibles
     */
    private URL chargerRessource(String nomImage) {
        // Essayer les différents chemins possibles
        String[] cheminsPossibles = {
            "/resources/images/" + nomImage,
            "images/" + nomImage,
            "/images/" + nomImage
        };
        
        for (String chemin : cheminsPossibles) {
            URL url = getClass().getResource(chemin);
            if (url != null) return url;
        }
        return null;
    }
    
    public void setJeuController(JeuController jeuController) {
        this.jeuController = jeuController;
        // Recharger les images avec le nouveau contrôleur
        mettreAJourImageZone();
        repaint();
    }
    
    /**
     * Méthode publique pour forcer la mise à jour de l'image de zone
     * Utile quand l'état de la zone change (coffre trouvé, terminée, etc.)
     */
    public void rafraichirImageZone() {
        mettreAJourImageZone();
        repaint();
    }
    
    public void setMessage(String message) {
        this.message = message;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Mettre à jour l'image si la zone a changé
        mettreAJourImageZone();
        
        // Dessiner le fond
        if (imageFond != null) {
            g.drawImage(imageFond, 0, 0, getWidth(), getHeight(), this);
        }
        
        // Dessiner le personnage
        if (imagePersonnage != null && jeuController != null) {
            int persoX = getWidth() / 2 - 40;
            int persoY = getHeight() - 120;
            g.drawImage(imagePersonnage, persoX, persoY, 80, 60, this);
        }
        
        // Dessiner les informations du jeu
        dessinerInformations(g);
        
        // Dessiner le message
        dessinerMessage(g);

        // Overlay victoire
        if (jeuController != null && jeuController.isVictoire()) {
            dessinerVictoire(g);
        }
    }
    
    private void dessinerInformations(Graphics g) {
        if (!isControllerValide()) return;
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        
        String[] infos = {
            "Joueur: " + jeuController.getNomJoueur(),
            "Score: " + jeuController.getScore(),
            "Zone: " + jeuController.getNomZoneCourante(),
            "Vies: " + jeuController.getNombreVies()
        };

        for (int i = 0; i < infos.length; i++) {
            g.drawString(infos[i], 10, 25 + (i * 20));
        }

        // Inventaire — côté droit
        dessinerInventaire(g);
    }

    private void dessinerInventaire(Graphics g) {
        List<String> items = jeuController.getInventaireItems();
        int x = getWidth() - 170;
        int y = 25;

        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.setColor(new Color(255, 215, 0));
        g.drawString("[ SAC ]", x, y);

        g.setFont(new Font("Arial", Font.PLAIN, 12));
        if (items.isEmpty()) {
            g.setColor(Color.LIGHT_GRAY);
            g.drawString("(vide)", x, y + 18);
        } else {
            for (int i = 0; i < items.size(); i++) {
                g.setColor(Color.WHITE);
                g.drawString("• " + items.get(i), x, y + 18 + (i * 17));
            }
        }
    }

    private void dessinerMessage(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.BOLD + Font.ITALIC, 18));
        
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(message)) / 2;
        int y = getHeight() - 30;
        
        g.drawString(message, x, y);
    }
    
    public void changerFond() {
        // Changer dynamiquement l'image de fond
        if (imageFond != null) {
            // Logique pour alterner entre différentes images
            repaint();
        }
    }
}

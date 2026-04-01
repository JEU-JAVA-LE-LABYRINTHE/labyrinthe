package view;

import helper.CommandeHelper;

import javax.swing.*;
import java.awt.*;

public class AideDialog extends JDialog {
    
    public AideDialog(Frame parent) {
        super(parent, "Aide - Commandes du Jeu", true);
        
        initComposants();
        setSize(700, 600);
        setLocationRelativeTo(parent);
    }
    
    private void initComposants() {
        setLayout(new BorderLayout(10, 10));
        
        JTextArea textArea = new JTextArea(CommandeHelper.afficherAide());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setMargin(new Insets(10, 10, 10, 10));
        textArea.setBackground(new Color(240, 240, 240));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
}

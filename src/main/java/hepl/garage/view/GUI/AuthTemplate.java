package hepl.garage.view.GUI;

import javax.swing.*;
import java.awt.*;

public abstract class AuthTemplate extends JDialog {
    protected JTextField usernameField;
    protected JPasswordField passwordField;
    protected JButton actionButton;

    public AuthTemplate(JFrame parent, String title) {
        super(parent, title, true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        initUI();
    }

    // Méthode Template (structure commune)
    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Création des champs (étape commune)
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        JLabel lblUsername = new JLabel("Username:");
        JLabel lblPassword = new JLabel("Password:");

        // Ajout des composants (étape commune)
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblUsername, gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblPassword, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Bouton d'action (à définir par les sous-classes)
        actionButton = createActionButton();
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(actionButton, gbc);

        add(panel);
    }

    // Méthodes abstraites (étapes personnalisées)
    protected abstract JButton createActionButton();
    protected abstract void handleAction();
}
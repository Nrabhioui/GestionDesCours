package hepl.garage.view.GUI;
import hepl.garage.model.authentification.PropertiesAuthenticator;
import javax.swing.*;
import java.awt.*;

public class SignIn extends AuthTemplate {
    public SignIn(JFrame parent) {
        super(parent, "Sign In");
    }

    @Override
    protected JButton createActionButton() {
        JButton btn = new JButton("Sign In");
        btn.setBackground(new Color(0, 120, 215));
        btn.setForeground(Color.WHITE);
        btn.addActionListener(e -> handleAction());
        return btn;
    }

    @Override
    protected void handleAction() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        PropertiesAuthenticator authenticator = new PropertiesAuthenticator("users.properties");
        if (authenticator.authenticate(username, password)) {
            JOptionPane.showMessageDialog(this, "Connexion réussie !");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Identifiants invalides.");
        }
    }

    // Méthode main de test pour afficher la boîte de dialogue et récupérer les données
    public static void main(String[] args) {
        JFrame parent = new JFrame();
        parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parent.setSize(300, 200);
        parent.setVisible(true);

        SignIn dialog = new SignIn(parent);
        dialog.setVisible(true);

        String username = dialog.usernameField.getText();
        char[] password = dialog.passwordField.getPassword();
        System.out.println("Nom d'utilisateur saisi : " + username);
        System.out.println("Mot de passe saisi : " + String.valueOf(password));
    }
}
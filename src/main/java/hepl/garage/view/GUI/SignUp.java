package hepl.garage.view.GUI;

import hepl.garage.model.authentification.PropertiesAuthenticator;
import javax.swing.*;

public class SignUp extends AuthTemplate {
    public SignUp(JFrame parent) {
        super(parent, "Sign Up");
    }

    @Override
    protected JButton createActionButton() {
        JButton btn = new JButton("Sign Up");
        btn.addActionListener(e -> handleAction());
        return btn;
    }

    @Override
    protected void handleAction() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        PropertiesAuthenticator authenticator = new PropertiesAuthenticator("users.properties");
        if (authenticator.SignIn(username, password)) {
            JOptionPane.showMessageDialog(this, "Inscription réussie !");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Nom d'utilisateur déjà utilisé.");
        }
    }

    // Méthode main de test pour afficher la boîte de dialogue et récupérer les données
    public static void main(String[] args) {
        JFrame parent = new JFrame();
        parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parent.setSize(300, 200);
        parent.setVisible(true);

        SignUp dialog = new SignUp(parent);
        dialog.setVisible(true);

        String username = dialog.usernameField.getText();
        char[] password = dialog.passwordField.getPassword();
        System.out.println("Nom d'utilisateur saisi : " + username);
        System.out.println("Mot de passe saisi : " + String.valueOf(password));
    }
}
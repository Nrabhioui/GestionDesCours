package hepl.garage.view.GUI;
import javax.swing.*;
import java.awt.*;

public class JDialogIdentification extends JDialog {
    private final JButton identifierJButton = new JButton("s'identifier");
    private final JButton inscrireJButton = new JButton("s'inscrire");
    private final JTextField usernameJTextField = new JTextField(15); // Taille fixe
    private final JPasswordField passwordJPasswordField = new JPasswordField(15);

    public JDialogIdentification(JFrame parent, String title, boolean modal) {
        super(parent, title, modal);

        // 1. Layout principal simple (BorderLayout)
        this.setLayout(new BorderLayout());

        // 2. Panel pour les champs/boutons (FlowLayout pour un alignement naturel)
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.add(new JLabel("Username :"));
        inputPanel.add(usernameJTextField);
        inputPanel.add(new JLabel("Password :"));
        inputPanel.add(passwordJPasswordField);

        // 3. Panel pour les boutons (alignement horizontal)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(identifierJButton);
        buttonPanel.add(inscrireJButton);

        // 4. Assemblage
        this.add(inputPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // 5. Ajustement automatique de la taille
        this.pack();
        this.setLocationRelativeTo(null);

    }

    // Getters (inchangés)
    public JButton getIdentifierJButton() { return identifierJButton; }
    public JButton getInscrireJButton() { return inscrireJButton; }
    public String getUsernameJTextField() { return usernameJTextField.getText(); }
    public char[] getPasswordJPasswordField() { return passwordJPasswordField.getPassword(); }
    public void addShowMessage(JDialogIdentification parent , String message, String title) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // Méthode main de test pour afficher la boîte de dialogue et récupérer les données
    public static void main(String[] args) {
        // Créer une JFrame parent factice
        JFrame parent = new JFrame();
        parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parent.setSize(300, 200);
        parent.setVisible(true);

        // Créer et afficher la boîte de dialogue
        JDialogIdentification dialog = new JDialogIdentification(parent, "Test Identification", true);
        dialog.setVisible(true);

        // Récupérer les données après la fermeture
        String username = dialog.getUsernameJTextField();
        char[] password = dialog.getPasswordJPasswordField();
        System.out.println("Nom d'utilisateur saisi : " + username);
        System.out.println("Mot de passe saisi : " + String.valueOf(password));
    }
}

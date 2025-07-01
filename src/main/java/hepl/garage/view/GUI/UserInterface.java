package hepl.garage.view.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatDarculaLaf;

public class UserInterface extends JFrame {

    // Déclaration des JTextField
    private final JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
    public JSpinner getSpinner() { return spinner; }

    // Déclaration des JTextField
    private final JTextField debutJTextField = new JTextField();
    private final JTextField heureJTextField = new JTextField();
    private final JTextField dureeJTextField = new JTextField();
    private final JTextField intituleJTextField = new JTextField();
    private final JTextField nomProfJTextField = new JTextField();
    private final JTextField prenomProfJTextField = new JTextField();
    private final JTextField numGroupeJTextField = new JTextField();
    private final JTextField nomLocalJTextField = new JTextField();

    // Déclaration des boutons
    private final JButton planifierJButton = new JButton("Planifier");
    private final JButton selectionnerJButton = new JButton("Sélectionner");

    private final JButton btnAjouterProf = new JButton("Ajouter");
    private final JButton btnSupprimerProf = new JButton("Supprimer");
    private final JButton btnAjouterGroupe = new JButton("Ajouter");
    private final JButton btnSupprimerGroupe = new JButton("Supprimer");
    private final JButton btnAjouterLocal = new JButton("Ajouter");
    private final JButton btnSupprimerLocal = new JButton("Supprimer");

    // Déclaration des CheckBox
    private final JCheckBox jourCheckBox = new JCheckBox("Jour : ");
    private final JCheckBox professeurCheckBox = new JCheckBox("Professeur : ");
    private final JCheckBox groupeCheckBox = new JCheckBox("Groupe : ");
    private final JCheckBox localCheckBox = new JCheckBox("Local : ");

    // Déclaration des tables
    private final DefaultTableModel modeleCours = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "intitulé" ,"Jour", "Début", "durée", "Local", "Professeur", "Groupe"}
    );
    private final JTable tableCours = new JTable(modeleCours);
    private final JTable tableProfesseurs = new JTable();
    private final JTable tableGroupes = new JTable();
    private final JTable tableLocaux = new JTable();

    // Modèle de table
    private final DefaultTableModel modeleProfesseurs = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Nom", "Prénom"}
    );
    private final DefaultTableModel modeleGroupes = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Nom"}
    );
    private final DefaultTableModel modeleLocaux = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Capacity", "Nom"}
    );
    // Déclaration ComboBox
    private final JComboBox<String> comboJours = new JComboBox<>();
    private final JComboBox<String> profComboBox = new JComboBox<>();
    private final JComboBox<String> groupeComboBox = new JComboBox<>();
    private final JComboBox<String> localComboBox = new JComboBox<>();
    public Map<String, DayOfWeek> joursMap = new LinkedHashMap<>();

    // Maps pour accès simplifié
    private final Map<String, JTextField> champs = new HashMap<>();
    private final Map<String, JButton> boutons = new HashMap<>();
    private final Map<String, JTable> tables = new HashMap<>();
    private final Map<String, JCheckBox> cases = new HashMap<>();

    public UserInterface() {
        try {
            FlatIntelliJLaf.setup();
        } catch (Exception e) {
            System.err.println("FlatLaf initialization failed: " + e);
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Application Horaire");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        initialiserComposants();
        configurerMenu();
        configurerPanneaux();
    }

    private void initialiserComposants() {
        tableProfesseurs.setModel(modeleProfesseurs);
        tableGroupes.setModel(modeleGroupes);
        tableLocaux.setModel(modeleLocaux);
        tableCours.setModel(modeleCours);

        joursMap.put("Lundi", DayOfWeek.MONDAY);
        joursMap.put("Mardi", DayOfWeek.TUESDAY);
        joursMap.put("Mercredi", DayOfWeek.WEDNESDAY);
        joursMap.put("Jeudi", DayOfWeek.THURSDAY);
        joursMap.put("Vendredi", DayOfWeek.FRIDAY);
        joursMap.put("Samedi", DayOfWeek.SATURDAY);
        joursMap.put("Dimanche", DayOfWeek.SUNDAY);

        for (String nomJour : joursMap.keySet()) {
            comboJours.addItem(nomJour);
        }
        initialiserMaps();
    }

    private void initialiserMaps() {
        champs.put("debut", debutJTextField);
        champs.put("heure", heureJTextField);
        champs.put("duree", dureeJTextField);
        champs.put("intitule", intituleJTextField);
        champs.put("nomProf", nomProfJTextField);
        champs.put("prenomProf", prenomProfJTextField);
        champs.put("numGroupe", numGroupeJTextField);
        champs.put("nomLocal", nomLocalJTextField);

        boutons.put("Planifier", planifierJButton);
        boutons.put("Sélectionner", selectionnerJButton);
        boutons.put("AjouterProf", btnAjouterProf);
        boutons.put("SupprimerProf", btnSupprimerProf);
        boutons.put("AjouterGroupe", btnAjouterGroupe);
        boutons.put("SupprimerGroupe", btnSupprimerGroupe);
        boutons.put("AjouterLocal", btnAjouterLocal);
        boutons.put("SupprimerLocal", btnSupprimerLocal);

        cases.put("Jour", jourCheckBox);
        cases.put("Professeur", professeurCheckBox);
        cases.put("Groupe", groupeCheckBox);
        cases.put("Local", localCheckBox);

        tables.put("Professeurs", tableProfesseurs);
        tables.put("Groupes", tableGroupes);
        tables.put("Locaux", tableLocaux);
        tables.put("Cours", tableCours); // S'assure que la table des cours est bien dans la map
    }

    private void configurerMenu() {
        JMenuBar barreMenu = new JMenuBar();

        JMenu menuFichier = new JMenu("Fichier");
        
        JMenuItem associerItem = new JMenuItem("Thème");
        associerItem.addActionListener(e -> {
            boolean isLight = UIManager.getLookAndFeel().getName().toLowerCase().contains("intellij");
            try {
                if (isLight) {
                    UIManager.setLookAndFeel(new FlatDarculaLaf());
                } else {
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                }
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors du changement de thème : " + ex.getMessage());
            }
        });
        menuFichier.add(associerItem);
        menuFichier.addSeparator();
        
        String[] itemsFichier = {"Nouveau", "Ouvrir", "Enregistrer", "Quitter"};
        for (String item : itemsFichier) {
            JMenuItem menuItem = new JMenuItem(item);
            menuFichier.add(menuItem);
        }

        JMenu menuSupprimer = new JMenu("Supprimer");
        String[] itemsSupprimer = {"Professeurs", "Groupes", "Locaux"};
        for (String item : itemsSupprimer) {
            menuSupprimer.add(new JMenuItem(item));
        }

        JMenu menuImporter = new JMenu("Importer");
        String[] itemsImporter = {"Professeurs", "Groupes", "Locaux", "Cours"};
        for (String item : itemsImporter) {
            JMenuItem menuItem = new JMenuItem(item);
            menuImporter.add(menuItem);
            menuItem.setActionCommand("Importer_" + item);
        }

        JMenu menuExporter = new JMenu("Exporter");
        for (String item : itemsImporter) {
            JMenuItem menuItem = new JMenuItem(item);
            menuExporter.add(menuItem);
            menuItem.setActionCommand("Exporter_" + item);
        }

        barreMenu.add(menuFichier);
        barreMenu.add(menuSupprimer);
        barreMenu.add(menuImporter);
        barreMenu.add(menuExporter);

        setJMenuBar(barreMenu);
    }

    private void configurerPanneaux() {
        add(creerPanneauTables(), BorderLayout.NORTH);
        add(creerPanneauPlanification(), BorderLayout.CENTER);
        add(creerPanneauSelection(), BorderLayout.SOUTH);
    }

    private JPanel creerPanneauTables() {
        JPanel panneau = new JPanel(new GridLayout(1, 3));
        panneau.setPreferredSize(new Dimension(0, 450));

        panneau.add(creerPanneauProfesseurs());
        panneau.add(creerPanneauGroupes());
        panneau.add(creerPanneauLocaux());


        return panneau;
    }

    private JPanel creerPanneauProfesseurs() {
        JPanel panneau = new JPanel();
        panneau.setLayout(new BoxLayout(panneau, BoxLayout.Y_AXIS));
        panneau.setBorder(new EmptyBorder(20, 20, 20, 20));

        panneau.add(new JLabel("Professeurs :"));
        panneau.add(new JScrollPane(tableProfesseurs));

        JPanel panneauControle = new JPanel();
        panneauControle.setBorder(new EmptyBorder(10, 10, 10, 10));
        panneauControle.setLayout(new GridLayout(2, 3));

        panneauControle.add(new JLabel("Nom"));
        panneauControle.add(nomProfJTextField);
        panneauControle.add(btnAjouterProf);

        panneauControle.add(new JLabel("Prénom"));
        panneauControle.add(prenomProfJTextField);
        panneauControle.add(btnSupprimerProf);

        panneau.add(panneauControle);
        return panneau;
    }

    private JPanel creerPanneauGroupes() {
        JPanel panneau = new JPanel();
        panneau.setLayout(new BoxLayout(panneau, BoxLayout.Y_AXIS));
        panneau.setBorder(new EmptyBorder(20, 20, 20, 20));

        panneau.add(new JLabel("Groupes :"));
        panneau.add(new JScrollPane(tableGroupes));

        JPanel panneauControle = new JPanel();
        panneauControle.setBorder(new EmptyBorder(10, 10, 10, 10));
        panneauControle.setLayout(new GridLayout(2, 2));

        panneauControle.add(new JLabel("Numéro"));
        panneauControle.add(numGroupeJTextField);
        panneauControle.add(btnAjouterGroupe);
        panneauControle.add(btnSupprimerGroupe);

        panneau.add(panneauControle);
        return panneau;
    }

    // Panneau Locaux
    private JPanel creerPanneauLocaux() {
        JPanel panneau = new JPanel();
        panneau.setLayout(new BoxLayout(panneau, BoxLayout.Y_AXIS));
        panneau.setBorder(new EmptyBorder(20, 20, 20, 20));

        panneau.add(new JLabel("Locaux :"));
        panneau.add(new JScrollPane(tableLocaux));

        JPanel panneauControle = new JPanel();
        panneauControle.setBorder(new EmptyBorder(10, 10, 10, 10));
        panneauControle.setLayout(new GridLayout(2, 3));

        panneauControle.add(new JLabel("Nom"));
        panneauControle.add(nomLocalJTextField);
        panneauControle.add(btnAjouterLocal);

        panneauControle.add(new JLabel("Capacité"));
        panneauControle.add(spinner);
        panneauControle.add(btnSupprimerLocal);

        panneau.add(panneauControle);
        return panneau;
    }

    private JPanel creerPanneauPlanification() {
        JPanel panneau = new JPanel(new GridLayout(1, 11));
        panneau.setPreferredSize(new Dimension(0, 50));

        panneau.add(new JLabel("Jour : "));
        panneau.add(comboJours);
        panneau.add(new JLabel("Début : "));
        panneau.add(debutJTextField);
        panneau.add(new JLabel("h"));
        panneau.add(heureJTextField);
        panneau.add(new JLabel("Durée :"));
        panneau.add(dureeJTextField);
        panneau.add(new JLabel("Intitulé : "));
        panneau.add(intituleJTextField);
        panneau.add(planifierJButton);

        return panneau;
    }

    private JPanel creerPanneauSelection() {
        JPanel panneau = new JPanel();
        panneau.setLayout(new BoxLayout(panneau, BoxLayout.X_AXIS));
        panneau.setPreferredSize(new Dimension(0, 300));

        JPanel panneauGauche = new JPanel(new GridLayout(6, 1));
        panneauGauche.add(new JLabel("Sélection : "));
        panneauGauche.add(jourCheckBox);
        panneauGauche.add(professeurCheckBox);
        panneauGauche.add(groupeCheckBox);
        panneauGauche.add(localCheckBox);
        selectionnerJButton.setBorder(new EmptyBorder(20, 20, 20, 20));
        panneauGauche.add(selectionnerJButton);

        JPanel panneauDroit = new JPanel();
        panneauDroit.setLayout(new BoxLayout(panneauDroit, BoxLayout.Y_AXIS));
        panneauDroit.setPreferredSize(new Dimension(1200, 0));
        panneauDroit.add(new JLabel("Cours : "));
        tableCours.setModel(modeleCours);
        panneauDroit.add(new JScrollPane(tableCours));

        panneau.add(panneauGauche);
        panneau.add(panneauDroit);

        return panneau;
    }

    // Accesseurs publics
    public JTextField getChamp(String nom) { return champs.get(nom); }
    public JButton getBouton(String nom) { return boutons.get(nom); }
    public JTable getTable(String nom) { return tables.get(nom); }
    public JCheckBox getCase(String nom) { return cases.get(nom); }
    public JComboBox<String> getComboJours() { return comboJours; }
    public JComboBox<String> getProfComboBox() { return profComboBox; }
    public JComboBox<String> getGroupeComboBox() { return groupeComboBox; }
    public JComboBox<String> getLocalComboBox() { return localComboBox; }
    public DefaultTableModel getModeleCours() { return modeleCours; }

    public void fermerDialogue(JDialog dialog) {
        dialog.dispose();
    }
}

package hepl.garage.controller;

import hepl.garage.model.GestionFichiers.GestionObjetSerialise;
import hepl.garage.model.authentification.PropertiesAuthenticator;
import hepl.garage.model.entity.ObjetsContainer;
import hepl.garage.model.entity.*;
import hepl.garage.view.GUI.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class controllerClass implements ActionListener {
    private final JDialogIdentification identificationDialog;
    private final UserInterface ui;
    private final PropertiesAuthenticator authenticator = new PropertiesAuthenticator("users.properties");
    public controllerClass(UserInterface ui, JDialogIdentification identificationDialog) {
        this.ui = ui;
        this.identificationDialog = identificationDialog;

        identificationDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        identificationDialog.getIdentifierJButton().addActionListener(this);
        identificationDialog.getInscrireJButton().addActionListener(this);
        ui.getBouton("AjouterProf").addActionListener(this);
        ui.getBouton("SupprimerProf").addActionListener(this);
        ui.getBouton("AjouterGroupe").addActionListener(this);
        ui.getBouton("SupprimerGroupe").addActionListener(this);
        ui.getBouton("AjouterLocal").addActionListener(this);
        ui.getBouton("SupprimerLocal").addActionListener(this);
        ui.getBouton("Planifier").addActionListener(this);
        ui.getBouton("Sélectionner").addActionListener(this);

        // Ajout des listeners pour les menus, ComboBox, Checkbox et tables
        // 1. Menus
        JMenuBar menuBar = ui.getJMenuBar();
        if (menuBar != null) {
            for (int i = 0; i < menuBar.getMenuCount(); i++) {
                JMenu menu = menuBar.getMenu(i);
                if (menu != null) {
                    for (int j = 0; j < menu.getItemCount(); j++) {
                        JMenuItem item = menu.getItem(j);
                        if (item != null) {
                            item.addActionListener(this);
                        }
                    }
                }
            }
        }
        // 2. ComboBox
        ui.getComboJours().addActionListener(this);
        // 3. Checkbox
        ui.getCase("Jour").addItemListener(e -> System.out.println("Jour checkbox changée: " + e.getStateChange()));
        ui.getCase("Professeur").addItemListener(e -> System.out.println("Professeur checkbox changée: " + e.getStateChange()));
        ui.getCase("Groupe").addItemListener(e -> System.out.println("Groupe checkbox changée: " + e.getStateChange()));
        ui.getCase("Local").addItemListener(e -> System.out.println("Local checkbox changée: " + e.getStateChange()));
        // 4. Tables (sélection)
        ui.getTable("Professeurs").getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                System.out.println("Sélection Professeur changée");
            }
        });
        ui.getTable("Groupes").getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                System.out.println("Sélection Groupe changée");
            }
        });
        ui.getTable("Locaux").getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                System.out.println("Sélection Local changée");
            }
        });

        this.ui.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    GestionObjetSerialise gestionObjetSerialise = new GestionObjetSerialise();
                    ObjetsContainer.instance = gestionObjetSerialise.chargerObjects();
                } catch (Exception ex) {
                    throw new RuntimeException("Le fichier ne peut pas être ouvert : " + ex.getMessage());                }
            }

            @Override
            public void windowClosing(WindowEvent e) {

                try {
                    GestionObjetSerialise gestionObjetSerialise = new GestionObjetSerialise();
                    gestionObjetSerialise.EnregistrerObjects(ObjetsContainer.instance);
                } catch (Exception ex) {
                    throw new RuntimeException("fichiers peut pas étre ouvert.");
                }
                ui.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        ui.setVisible(true);
        identificationDialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        // Gestion des menus
        if (src instanceof JMenuItem) {
            JMenuItem item = (JMenuItem) src;
            String text = item.getText();
            String actionCommand = item.getActionCommand();
            try {
                // D'abord, gestion Importer/Exporter via actionCommand
                if (actionCommand != null && (actionCommand.startsWith("Importer_") || actionCommand.startsWith("Exporter_"))) {
                    JFileChooser fileChooser = new JFileChooser();
                    int result;
                    switch (actionCommand) {
                        case "Exporter_Cours":
                            fileChooser.setDialogTitle("Exporter tout (binaire)");
                            result = fileChooser.showSaveDialog(ui);
                            if (result == JFileChooser.APPROVE_OPTION) {
                                try {
                                    new GestionObjetSerialise().EnregistrerObjects(ObjetsContainer.instance, fileChooser.getSelectedFile().getAbsolutePath());
                                    JOptionPane.showMessageDialog(ui, "Exportation réussie !");
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(ui, "Erreur à l'export : " + ex.getMessage());
                                }
                            }
                            break;
                        case "Importer_Cours":
                            fileChooser.setDialogTitle("Importer tout (binaire)");
                            result = fileChooser.showOpenDialog(ui);
                            if (result == JFileChooser.APPROVE_OPTION) {
                                try {
                                    ObjetsContainer loaded = new hepl.garage.model.GestionFichiers.GestionObjetSerialise().chargerObjects(fileChooser.getSelectedFile().getAbsolutePath());
                                    ObjetsContainer.instance = loaded;
                                    dessignerProffesseurs(ObjetsContainer.instance.getProfessors(), ui);
                                    dessignerGroups(ObjetsContainer.instance.getGroups(), ui);
                                    dessignerLocaux(ObjetsContainer.instance.getClassrooms(), ui);
                                    dessignerCourses(ObjetsContainer.instance.getCourses(), ui);
                                    JOptionPane.showMessageDialog(ui, "Importation réussie !");
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(ui, "Erreur à l'import : " + ex.getMessage());
                                }
                            }
                            break;
                        case "Exporter_Professeurs":
                            fileChooser.setDialogTitle("Exporter Professeurs (CSV)");
                            result = fileChooser.showSaveDialog(ui);
                            if (result == JFileChooser.APPROVE_OPTION) {
                                try {
                                    new GestionObjetSerialise().exporterProfesseursCSV(fileChooser.getSelectedFile().getAbsolutePath() , ObjetsContainer.instance.getProfessors());
                                    JOptionPane.showMessageDialog(ui, "Export CSV Professeurs réussi !");
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(ui, "Erreur à l'export CSV : " + ex.getMessage());
                                }
                            }
                            break;
                        case "Importer_Professeurs":
                            fileChooser.setDialogTitle("Importer Professeurs (CSV)");
                            result = fileChooser.showOpenDialog(ui);
                            if (result == JFileChooser.APPROVE_OPTION) {
                                try {
                                    ObjetsContainer.instance.getProfessors().clear();
                                    ObjetsContainer.instance.setProfessors(new GestionObjetSerialise().importerProfesseursCSV(fileChooser.getSelectedFile().getAbsolutePath()));
                                    dessignerProffesseurs(ObjetsContainer.instance.getProfessors(), ui);
                                    JOptionPane.showMessageDialog(ui, "Import CSV Professeurs réussi !");
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(ui, "Erreur à l'import CSV : " + ex.getMessage());
                                }
                            }
                            break;
                        case "Exporter_Groupes":
                            fileChooser.setDialogTitle("Exporter Groupes (CSV)");
                            result = fileChooser.showSaveDialog(ui);
                            if (result == JFileChooser.APPROVE_OPTION) {
                                try {
                                    new GestionObjetSerialise().exporterGroupesCSV(fileChooser.getSelectedFile().getAbsolutePath(), ObjetsContainer.instance.getGroups());
                                    JOptionPane.showMessageDialog(ui, "Export CSV Groupes réussi !");
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(ui, "Erreur à l'export CSV : " + ex.getMessage());
                                }
                            }
                            break;
                        case "Importer_Groupes":
                            fileChooser.setDialogTitle("Importer Groupes (CSV)");
                            result = fileChooser.showOpenDialog(ui);
                            if (result == JFileChooser.APPROVE_OPTION) {
                                try {
                                    ObjetsContainer.instance.getGroups().clear();
                                    ObjetsContainer.instance.setGroups(new GestionObjetSerialise().importerGroupesCSV(fileChooser.getSelectedFile().getAbsolutePath()));
                                    dessignerGroups(ObjetsContainer.instance.getGroups(), ui);
                                    JOptionPane.showMessageDialog(ui, "Import CSV Groupes réussi !");
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(ui, "Erreur à l'import CSV : " + ex.getMessage());
                                }
                            }
                            break;
                        case "Exporter_Locaux":
                            fileChooser.setDialogTitle("Exporter Locaux (CSV)");
                            result = fileChooser.showSaveDialog(ui);
                            if (result == JFileChooser.APPROVE_OPTION) {
                                try {
                                    new GestionObjetSerialise().exporterLocauxCSV(fileChooser.getSelectedFile().getAbsolutePath(), ObjetsContainer.instance.getClassrooms());
                                    JOptionPane.showMessageDialog(ui, "Export CSV Locaux réussi !");
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(ui, "Erreur à l'export CSV : " + ex.getMessage());
                                }
                            }
                            break;
                        case "Importer_Locaux":
                            fileChooser.setDialogTitle("Importer Locaux (CSV)");
                            result = fileChooser.showOpenDialog(ui);
                            if (result == JFileChooser.APPROVE_OPTION) {
                                try {
                                    ObjetsContainer.instance.getClassrooms().clear();
                                    ObjetsContainer.instance.setClassrooms(new GestionObjetSerialise().importerLocauxCSV(fileChooser.getSelectedFile().getAbsolutePath()));
                                    dessignerLocaux(ObjetsContainer.instance.getClassrooms(), ui);
                                    JOptionPane.showMessageDialog(ui, "Import CSV Locaux réussi !");
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(ui, "Erreur à l'import CSV : " + ex.getMessage());
                                }
                            }
                            break;
                    }
                    return;
                }
                // Sinon, gestion classique des menus
                switch (text) {
                    case "Nouveau":
                        ObjetsContainer.instance.clear();
                        dessignerProffesseurs(ObjetsContainer.instance.getProfessors(), ui);
                        dessignerGroups(ObjetsContainer.instance.getGroups(), ui);
                        dessignerLocaux(ObjetsContainer.instance.getClassrooms(), ui);
                        dessignerCourses(ObjetsContainer.instance.getCourses(), ui);
                        JOptionPane.showMessageDialog(ui, "Nouveau projet créé.");
                        break;
                    case "Ouvrir":
                        ObjetsContainer loaded = new hepl.garage.model.GestionFichiers.GestionObjetSerialise().chargerObjects();
                        ObjetsContainer.instance = loaded;
                        dessignerProffesseurs(ObjetsContainer.instance.getProfessors(), ui);
                        dessignerGroups(ObjetsContainer.instance.getGroups(), ui);
                        dessignerLocaux(ObjetsContainer.instance.getClassrooms(), ui);
                        dessignerCourses(ObjetsContainer.instance.getCourses(), ui);
                        JOptionPane.showMessageDialog(ui, "Projet chargé.");
                        break;
                    case "Enregistrer":
                        new hepl.garage.model.GestionFichiers.GestionObjetSerialise().EnregistrerObjects(ObjetsContainer.instance);
                        JOptionPane.showMessageDialog(ui, "Projet enregistré.");
                        break;
                    case "Quitter":
                        System.exit(0);
                        break;
                    case "Professeurs":
                        ObjetsContainer.instance.getProfessors().clear();
                        dessignerProffesseurs(ObjetsContainer.instance.getProfessors(), ui);
                        JOptionPane.showMessageDialog(ui, "Tous les professeurs supprimés.");
                        break;
                    case "Groupes":
                        ObjetsContainer.instance.getGroups().clear();
                        dessignerGroups(ObjetsContainer.instance.getGroups(), ui);
                        JOptionPane.showMessageDialog(ui, "Tous les groupes supprimés.");
                        break;
                    case "Locaux":
                        ObjetsContainer.instance.getClassrooms().clear();
                        dessignerLocaux(ObjetsContainer.instance.getClassrooms(), ui);
                        JOptionPane.showMessageDialog(ui, "Tous les locaux supprimés.");
                        break;
                    case "Cours":
                        ObjetsContainer.instance.getCourses().clear();
                        dessignerCourses(ObjetsContainer.instance.getCourses(), ui);
                        JOptionPane.showMessageDialog(ui, "toutes les courses supprimés.");
                        break;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(ui, "Erreur : " + ex.getMessage());
            }
            return;
        }
        // Gestion boutons planifier/sélectionner
        if (src == ui.getBouton("Planifier")) {
            String Jour = (String) ui.getComboJours().getSelectedItem();
            int heure = Integer.parseInt(ui.getChamp("debut").getText());
            int minute = Integer.parseInt(ui.getChamp("heure").getText());
            int durree = Integer.parseInt(ui.getChamp("duree").getText());
            String nom = ui.getChamp("intitule").getText();
            JTable jTable = ui.getTable("Professeurs");
            int selectedRow = ligneSelectionnee(jTable);
            Professor professor = ObjetsContainer.instance.getProfesseur(selectedRow);
            jTable = ui.getTable("Locaux");
            selectedRow = ligneSelectionnee(jTable);
            Classroom classroom = ObjetsContainer.instance.getClassroom(selectedRow);
            jTable = ui.getTable("Groupes");
            int[] selectedRows = jTable.getSelectedRows();
            int[] selected = new int[selectedRows.length];

            List<Group> groupsSelectionnes = new ArrayList<>();
            if(selectedRows.length == 0){
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner au moins un groupe.");
            }else{
                for (int i : selected) {
                    selected[i] = (int) jTable.getValueAt(selectedRows[i], 0);
                    Group group = ObjetsContainer.instance.getGroup(selected[i]);
                    if (group != null) {
                        groupsSelectionnes.add(group);
                    } else {
                        System.err.println("Groupe null à l’index : " + i);
                    }
                }
            }
            Course course = new Course(nom, professor, classroom, groupsSelectionnes, LocalTime.of(heure,minute, 0), Duration.ofMinutes(durree), ui.joursMap.get(Jour));
            ObjetsContainer.instance.getCourses().add(course);
            System.out.println("Ajout de cours : ");
            dessignerCourses(ObjetsContainer.instance.getCourses(), ui);

        }
        if (src == ui.getBouton("Sélectionner")) {
            if(!ui.getCase("Professeur").isSelected() && !ui.getCase("Groupe").isSelected() && !ui.getCase("Local").isSelected() && !ui.getCase("Jour").isSelected()){
                dessignerCourses(ObjetsContainer.instance.getCourses(), ui);
                return;
            }
            List<Course> course = new ArrayList<>();
            course.clear();
            if (ui.getCase("Professeur").isSelected()) {
                int selectedRow = ligneSelectionnee(ui.getTable("Professeurs"));
                String nomProfesseur = ObjetsContainer.instance.getProfesseur(selectedRow).getFirstName();
                for (Course cours : ObjetsContainer.instance.getCourses()) {
                    if(cours.getProfessor().getFirstName().equals(nomProfesseur)){
                        course.add(cours);
                    }
                }
            }
            if (ui.getCase("Groupe").isSelected()) {
                int selectedRow = ligneSelectionnee(ui.getTable("Groupes"));
                String nomGroupe = ObjetsContainer.instance.getGroup(selectedRow).getName();
                for (Course cours : ObjetsContainer.instance.getCourses()) {
                    for (Group groupe : cours.getGroups()) {
                        if(groupe.getName().equals(nomGroupe)){
                            course.add(cours);
                        }
                    }
                }
            }
            if (ui.getCase("Local").isSelected()) {
                int selectedRow = ligneSelectionnee(ui.getTable("Locaux"));
                String nomLocal = ObjetsContainer.instance.getClassroom(selectedRow).getName();
                for (Course cours : ObjetsContainer.instance.getCourses()) {
                    if(cours.getClassroom().getName().equals(nomLocal)){
                        course.add(cours);
                    }
                }
            }
            if(ui.getCase("Jour").isSelected()){
                String Jour = (String) ui.getComboJours().getSelectedItem();
                for (Course cours : ObjetsContainer.instance.getCourses()) {
                    if(cours.getDay().equals(ui.joursMap.get(Jour))){
                        course.add(cours);
                    }
                }
            }
            DefaultTableModel modele = (DefaultTableModel) ui.getTable("Cours").getModel();
            modele.setRowCount(0);
            for (Course cours : course) {
                System.out.println(cours.getCode());
                for (Group group : cours.getGroups()) {
                    Object[] nouvelleLigne = {cours.getCode(), cours.getTitle(),  dayOfWeek(cours.getDay()), cours.getDate().toString(), cours.getDuree().toString(), cours.getClassroom().getName(), cours.getProfessor().getLastName(), group.getName()};
                    modele.addRow(nouvelleLigne);
                }
            }
        }
        String username = identificationDialog.getUsernameJTextField();
        String password = new String(identificationDialog.getPasswordJPasswordField());
        if (e.getSource() == identificationDialog.getIdentifierJButton()) {
            if(authenticator.authenticate(username, password)){
                identificationDialog.addShowMessage(identificationDialog, "Authentication successful", "Success");
                ui.fermerDialogue(identificationDialog);
                controllerClass.dessignerProffesseurs(ObjetsContainer.instance.getProfessors(), ui);
                controllerClass.dessignerGroups(ObjetsContainer.instance.getGroups(), ui);
                controllerClass.dessignerLocaux(ObjetsContainer.instance.getClassrooms(), ui);
                controllerClass.dessignerCourses(ObjetsContainer.instance.getCourses(), ui);
            }else{
                identificationDialog.addShowMessage(identificationDialog, "Authentication failed", "Error");
            }
        }
        else if(e.getSource() == identificationDialog.getInscrireJButton()){
                if(authenticator.CheckChechkBox(username, password)) {
                    if (authenticator.SignIn(username, password)) {
                        identificationDialog.addShowMessage(identificationDialog, "User added successfully", "Success");
                    } else {
                        identificationDialog.addShowMessage(identificationDialog, "il y a problème", "Error");
                    }
                }else{
                    identificationDialog.addShowMessage(identificationDialog, "Vous n'avez pas tous remplis !", "Error");
                }
        }
        else if(e.getSource() == ui.getBouton("AjouterProf")){
            String nom = ui.getChamp("nomProf").getText();
            String prenom = ui.getChamp("prenomProf").getText();
            ObjetsContainer.instance.add(new Professor(nom, prenom));
            dessignerProffesseurs(ObjetsContainer.instance.getProfessors(), ui);
        }
        else if(e.getSource() == ui.getBouton("SupprimerProf")){
            JTable jTable = ui.getTable("Professeurs");
            int selectedRow = ligneSelectionnee(jTable);
            ObjetsContainer.instance.removeProfesseur(selectedRow);
            dessignerProffesseurs(ObjetsContainer.instance.getProfessors(), ui);
        }
        else if(e.getSource() == ui.getBouton("AjouterGroupe")){
            String nom = ui.getChamp("numGroupe").getText();
            ObjetsContainer.instance.add(new Group(nom));
            dessignerGroups(ObjetsContainer.instance.getGroups(), ui);
        }
        else if(e.getSource() == ui.getBouton("SupprimerGroupe")){
            JTable jTable = ui.getTable("Groupes");
            int selectedRow = ligneSelectionnee(jTable);
            if(selectedRow == -1) return;
            ObjetsContainer.instance.removeGroupe(selectedRow);
            dessignerGroups(ObjetsContainer.instance.getGroups(), ui);
        }
        else if(e.getSource() == ui.getBouton("AjouterLocal")){
            String nom = ui.getChamp("nomLocal").getText();
            int spinnerValue = (int) ui.getSpinner().getValue();
            ObjetsContainer.instance.add( new Classroom(nom, spinnerValue));
            dessignerLocaux(ObjetsContainer.instance.getClassrooms(), ui);
        }
        else if(e.getSource() == ui.getBouton("SupprimerLocal")){
            JTable jTable = ui.getTable("Locaux");
            int selectedRow = ligneSelectionnee(jTable);
            if(selectedRow == -1) return;
            ObjetsContainer.instance.removeClassroom(selectedRow);
            dessignerLocaux(ObjetsContainer.instance.getClassrooms(), ui);
        }
    }

    public static void dessignerProffesseurs(List<Professor> list, UserInterface ui){
        DefaultTableModel modele = (DefaultTableModel) ui.getTable("Professeurs").getModel();
        modele.setRowCount(0);
        for(Professor professor : list){
            Object[] nouvelleLigne = {professor.getId(),professor.getFirstName(), professor.getLastName()};
            modele.addRow(nouvelleLigne);
        }
    }

    public static void dessignerGroups(List<Group> list, UserInterface ui){
        DefaultTableModel modele = (DefaultTableModel) ui.getTable("Groupes").getModel();
        modele.setRowCount(0);
        for(Group group : list){
            Object[] nouvelleLigne = {group.getId(),group.getName()};
            modele.addRow(nouvelleLigne);
        }
    }
    public static void dessignerLocaux(List<Classroom> list, UserInterface ui){
        DefaultTableModel modele = (DefaultTableModel) ui.getTable("Locaux").getModel();
        modele.setRowCount(0);
        for(Classroom classroom : list){
            Object[] nouvelleLigne = {classroom.getId(), classroom.getSeatingCapacity(), classroom.getName()};
            modele.addRow(nouvelleLigne);
        }
    }

    public static void dessignerCourses(List<Course> list, UserInterface ui) {
        DefaultTableModel modele = (DefaultTableModel) ui.getTable("Cours").getModel();
        modele.setRowCount(0);
        for (Course course : list) {
            for (Group group : course.getGroups()) {
                Object[] nouvelleLigne = {course.getCode(), course.getTitle(),  dayOfWeek(course.getDay()), course.getDate().toString(), course.getDuree().toString(), course.getClassroom().getName(), course.getProfessor().getLastName(), group.getName()};
                modele.addRow(nouvelleLigne);
            }
        }
    }

    public static int ligneSelectionnee(JTable jTable){
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow != -1) {
            return (int) jTable.getValueAt(selectedRow, 0);
        }
        return -1;
    }

    static String dayOfWeek(DayOfWeek day){
        switch(day){
            case DayOfWeek.MONDAY:
                return "Lundi";
            case DayOfWeek.TUESDAY:
                return "Mardi";
            case DayOfWeek.WEDNESDAY:
                return "Mercredi";
            case DayOfWeek.THURSDAY:
                return "Jeudi";
            case DayOfWeek.FRIDAY:
                return "Vendredi";
            case DayOfWeek.SATURDAY:
                return "Samedi";
            case DayOfWeek.SUNDAY:
                return "Dimanche";
            default:
                return "Erreur";
        }
    }
}

package hepl.garage.model.GestionFichiers;

import hepl.garage.model.entity.ObjetsContainer;
import hepl.garage.model.entity.Professor;
import hepl.garage.model.entity.Group;
import hepl.garage.model.entity.Classroom;

import java.io.*;
import java.util.List;
import java.util.Map;

public class GestionObjetSerialise implements GestionFichiers {
    @Override
    public void Enregistrer(Map<String, String> users, String FILENAME) throws Exception {

    }
    // Ajout d'une surcharge pour sérialiser dans un fichier par defaut
    @Override
    public void EnregistrerObjects(ObjetsContainer objects) throws Exception {
        File file = new File("container.ser");
        try (FileOutputStream fis = new FileOutputStream(file);
             ObjectOutputStream ois = new ObjectOutputStream(fis)) {

            ois.writeObject(objects);
            System.out.println("Objet sérialisé avec succès");
        } catch (Exception e) {
            System.err.println("Erreur lors de la sérialisation : " + e.getMessage());
            throw e; // Relancer l'exception pour gestion en amont si nécessaire.
        }
    }

    // Ajout d'une surcharge pour sérialiser dans un fichier choisi
    public void EnregistrerObjects(ObjetsContainer objects, String filePath) throws Exception {
        File file = new File(filePath);
        try (FileOutputStream fis = new FileOutputStream(file);
             ObjectOutputStream ois = new ObjectOutputStream(fis)){
            ois.writeObject(objects);
            System.out.println("Objet sérialisé avec succès dans " + filePath);
        } catch (Exception e) {
            System.err.println("Erreur lors de la sérialisation : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Map<String, String> Charger(String FILENAME) throws Exception {
        return Map.of();
    }
    // Ajout d'une surcharge pour désérialiser depuis un fichier par defaut
    @Override
    public ObjetsContainer chargerObjects() throws Exception {
        File file = new File("container.ser");

        if (!file.exists()) {
            System.out.println("Le fichier container.ser n'existe pas. Création d'une nouvelle instance.");
            ObjetsContainer objets = new ObjetsContainer(); // Nouvelle instance initialisée vide.
            EnregistrerObjects(objets); // Serialize et sauvegarde immédiatement.
            return objets;
        }

        // Charger l'objet depuis le fichier si le fichier existe.
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            ObjetsContainer objets = (ObjetsContainer) ois.readObject();
            System.out.println("Objet désérialisé avec succès");
            return objets;

        } catch (Exception e) {
            System.err.println("Erreur lors de la désérialisation : " + e.getMessage());
            throw e; // Relancer l'exception pour gestion en amont si nécessaire.
        }
    }

    // Ajout d'une surcharge pour désérialiser depuis un fichier choisi
    public ObjetsContainer chargerObjects(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Le fichier " + filePath + " n'existe pas. Création d'une nouvelle instance.");
            ObjetsContainer objets = new ObjetsContainer();
            EnregistrerObjects(objets, filePath);
            return objets;
        }
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            ObjetsContainer objets = (ObjetsContainer) ois.readObject();
            System.out.println("Objet désérialisé avec succès depuis " + filePath);
            return objets;
        } catch (Exception e) {
            System.err.println("Erreur lors de la désérialisation : " + e.getMessage());
            throw e;
        }
    }

    // --- Export CSV Professeurs ---
    public void exporterProfesseursCSV(String filePath, List<Professor> profs) throws Exception {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("id;lastName;firstName\n");
            for (Professor p : profs) {
                writer.write(p.getId() + ";" + p.getLastName() + ";" + p.getFirstName() + "\n");
            }
        }
    }

    // --- Import CSV Professeurs ---
    public List<Professor> importerProfesseursCSV(String filePath) throws Exception {
        List<Professor> profs = new java.util.ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");
                if (tokens.length >= 3) {
                    int id = Integer.parseInt(tokens[0]);
                    String lastName = tokens[1];
                    String firstName = tokens[2];
                    Professor p = new Professor(firstName, lastName);
                    p.setId(id);
                    profs.add(p);
                }
            }
        }
        return profs;
    }

    // --- Export CSV Groupes ---
    public void exporterGroupesCSV(String filePath, List<Group> groups) throws Exception {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("id;name\n");
            for (Group g : groups) {
                writer.write(g.getId() + ";" + g.getName() + "\n");
            }
        }
    }

    // --- Import CSV Groupes ---
    public List<Group> importerGroupesCSV(String filePath) throws Exception {
        List<Group> groups = new java.util.ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");
                if (tokens.length >= 2) {
                    int id = Integer.parseInt(tokens[0]);
                    String name = tokens[1];
                    Group g = new Group(name);
                    g.setId(id);
                    groups.add(g);
                }
            }
        }
        return groups;
    }

    // --- Export CSV Locaux ---
    public void exporterLocauxCSV(String filePath, List<Classroom> locaux) throws Exception {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("id;name;seatingCapacity\n");
            for (Classroom l : locaux) {
                writer.write(l.getId() + ";" + l.getName() + ";" + l.getSeatingCapacity() + "\n");
            }
        }
    }

    // --- Import CSV Locaux ---
    public List<Classroom> importerLocauxCSV(String filePath) throws Exception {
        List<Classroom> locaux = new java.util.ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");
                if (tokens.length >= 3) {
                    int id = Integer.parseInt(tokens[0]);
                    String name = tokens[1];
                    int seatingCapacity = Integer.parseInt(tokens[2]);
                    Classroom c = new Classroom(id, name, seatingCapacity);
                    locaux.add(c);
                }
            }
        }
        return locaux;
    }
}

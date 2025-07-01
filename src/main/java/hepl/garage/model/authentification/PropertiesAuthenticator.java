package hepl.garage.model.authentification;

import java.io.*;
import java.util.Properties;

public class PropertiesAuthenticator extends Authenticator {
    private final String propertiesFilePath;

    public PropertiesAuthenticator(String propertiesFilePath) {
        this.propertiesFilePath = propertiesFilePath;
    }

    @Override
    protected boolean isLoginExists(String username) {
        Properties props = loadProperties();
        return props.containsKey(username);
    }

    @Override
    protected String getPassword(String username) {
        Properties props = loadProperties();
        return props.getProperty(username);
    }

    @Override
    protected String AddUser(String username, String password) {
        Properties props = loadProperties();
        String old = (String) props.setProperty(username, password);
        saveProperties(props);
        return old;
    }

    private Properties loadProperties() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(propertiesFilePath)) {
            props.load(fis);
        } catch (IOException e) {
            // Fichier absent = aucun utilisateur
        }
        return props;
    }

    private void saveProperties(Properties props) {
        try (FileOutputStream fos = new FileOutputStream(propertiesFilePath)) {
            props.store(fos, "Utilisateurs");
        } catch (IOException e) {
            throw new RuntimeException("Impossible d'écrire dans le fichier properties", e);
        }
    }
}

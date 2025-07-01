package hepl.garage.model.GestionFichiers;
import hepl.garage.model.entity.ObjetsContainer;

import java.util.Map;

public interface GestionFichiers {
    public static final String FILENAME = "users.txt";
    void Enregistrer(Map<String, String> users, String FILENAME) throws Exception;
    void EnregistrerObjects(ObjetsContainer objects) throws Exception;
    Map<String, String> Charger(String FILENAME) throws Exception;
    ObjetsContainer chargerObjects () throws Exception;
}

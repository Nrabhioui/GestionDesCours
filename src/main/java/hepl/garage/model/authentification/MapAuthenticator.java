package hepl.garage.model.authentification;

import java.util.HashMap;
import java.util.Map;
//pas utiliser juste pour les tests
public class MapAuthenticator extends Authenticator {

    protected Map<String, String> users = new HashMap<>();

    public MapAuthenticator() {
        // Ajout user
        /*
        users.put("Daniel", "admin95");
        users.put("Anass", "admin95");
        users.put("Renaud", "LeSportCestTropBien95");
         */
    }

    @Override
    protected boolean isLoginExists(String username) {
        return users.containsKey(username);
    }

    @Override
    protected String getPassword(String username) {
        return users.get(username);
    }

    @Override
    public String AddUser(String username, String password) {
        return users.put(username, password);
    }
}

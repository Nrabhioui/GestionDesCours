package hepl.garage.model.authentification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MapAuthenticatorTest {

    private MapAuthenticator authenticator;

    @BeforeEach
    void setUp() {
        authenticator = new MapAuthenticator();

        authenticator.SignIn("Renaud", "LeSportCestTropBien95"); // Ajout d'un utilisateur pour les tests
    }

    @Test
    void testAuthenticateWithValidCredentials() {
        assertTrue(authenticator.authenticate("Renaud", "LeSportCestTropBien95"));
    }

    @Test
    void testAuthenticateWithInvalidPassword() {
        assertFalse(authenticator.authenticate("Daniel", "pasadmin95"));
        assertFalse(authenticator.authenticate("Anass", "pasadmin95"));
        assertFalse(authenticator.authenticate("Renaud", "LeSportCestNul95"));
    }

    @Test
    void testAuthenticateWithUnknownUser() {
        assertFalse(authenticator.authenticate("Moustafa", "Lou"));
        assertFalse(authenticator.authenticate("Louis", "Louis"));
    }

    @Test
    void testSignInAndAuthenticate() {
        authenticator.SignIn("Moustapha", "Lo");
        assertTrue(authenticator.authenticate("Moustapha", "Lo"));
        assertFalse(authenticator.authenticate("Moustapha", "Lau"));
    }
}


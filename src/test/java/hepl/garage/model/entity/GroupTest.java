package hepl.garage.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;  // Importation de JUnit 5
import static org.junit.jupiter.api.Assertions.*;  // Assertions JUnit 5

public class GroupTest {

    private Group group1;
    private Group group2;
    private Group group3;
    private Group g;

    @BeforeEach
    public void setUp() {
        // Initialisation des objets avant chaque test
        group1 = new Group("Group 1");
        group2 = new Group("Group 1");
        group3 = new Group("Group 2");
        g = new Group("INFO1");
        // Pour Group
    }

    // 2. Test setName()
    @Test
    public void testSetters() {
        g.setName("INFO2");
        assertEquals("INFO2", g.getName());
    }

    // Test de la méthode toString()
    @Test
    public void testToString() {
        assertEquals("Group 1", group1.toString());
        assertEquals("Group 2", group3.toString());
    }

    // Test de la méthode clone()
    @Test
    public void testClone() {
        Group clonedGroup = group1.clone();
        assertNotSame(group1, clonedGroup);  // Vérifie que ce n'est pas la même instance
        assertEquals(group1, clonedGroup);   // Vérifie que les objets sont égaux
    }

    // Test de l'égalité avec d'autres types d'objets
    @Test
    public void testEqualsWithOtherTypes() {
        assertFalse(group1.equals("Group 1")); // Comparaison avec un objet de type différent
    }

    // Test du constructeur par défaut
    @Test
    public void testDefaultConstructor() {
        Group g = new Group();
        assertEquals(" ", g.getName());
        assertTrue(g.getId() > 0); // L'ID doit être généré
    }

    // Test du constructeur d'initialisation
    @Test
    public void testConstructorWithParameters() {
        Group g = new Group("G4");
        assertEquals("G4", g.getName());
    }
}
package hepl.garage.model.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProfessorTest {

    private Professor p;

    @BeforeEach
    public void setUp() {
        p = new Professor( "Jean", "Dupont");
    }

    @Test
    public void testSetId() {
        Professor p = new Professor();
        p.setId(99); // Définir manuellement l'ID
        assertEquals(99, p.getId());
    }


    // 1. Test getId(), getFirstName(), getLastName()
    @Test
    public void testGetters() {
        assertEquals("Jean", p.getFirstName());
        assertEquals("Dupont", p.getLastName());
    }

    // 2. Test setFirstName() et setLastName()
    @Test
    public void testSetters() {
        p.setFirstName("Alice");
        p.setLastName("Martin");

        assertEquals("Alice", p.getFirstName());
        assertEquals("Martin", p.getLastName());
    }

    // 3. Test toString()
    @Test
    public void testToString() {
        assertEquals("Dupont Jean", p.toString());
    }

    // 5. Test equals() — id différents
    @Test
    public void testEqualsDifferentId() {
        Professor other = new Professor("Jean", "Dupont");
        assertFalse(p.equals(other));
    }

    // 6. Test clone()
    @Test
    public void testClone() {
        Professor clone = p.clone();

        assertNotSame(p, clone);                   // objets différents
        assertEquals(p.getId(), clone.getId());
        assertEquals(p.getFirstName(), clone.getFirstName());
        assertEquals(p.getLastName(), clone.getLastName());
        assertEquals(p, clone);                    // equals par id
        assertEquals(p.toString(), clone.toString());
        assertEquals(p.tulpe(), clone.tulpe());
    }

    // 7.
    @Test
    public void testDefaultConstructor() {
        Professor p = new Professor();
        assertEquals("nom", p.getLastName());
        assertEquals("prenom", p.getFirstName());
        assertTrue(p.getId() > 0); // L'ID doit être généré
    }

    // 8. Test du constructeur d'initialisation
    @Test
    public void testConstructorWithParameters() {
        Professor p = new Professor("Alan", "Turing");
        assertEquals("Alan", p.getFirstName());
        assertEquals("Turing", p.getLastName());
    }
}


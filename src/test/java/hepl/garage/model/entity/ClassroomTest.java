package hepl.garage.model.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClassroomTest {

    private Classroom c1;
    private Classroom c2;

    @BeforeEach
    public void setUp() {
        c1 = new Classroom(1, "A01", 30);
        c2 = new Classroom(1, "A01", 30);
    }

    // 1. Test des accesseurs (get/set)
    @Test
    public void testGettersAndSetters() {
        assertEquals("A01", c1.getName());
        assertEquals(30, c1.getSeatingCapacity());

        c1.setName("B02");
        c1.setSeatingCapacity(50);
        assertEquals("B02", c1.getName());
        assertEquals(50, c1.getSeatingCapacity());
    }

    // 2. Test toString()
    @Test
    public void testToString() {
        assertEquals("A01(30)", c1.toString());
    }

    // 3. Test tulpe()
    @Test
    public void testTulpe() {
        assertEquals("1;A01(30)", c1.tulpe());
    }

    // 4. Test equals()
    @Test
    public void testEquals() {
        assertTrue(c1.equals(c2));

        Classroom c3 = new Classroom(2, "A01", 30);
        assertFalse(c1.equals(c3));
    }

    // 5. Test clone()
    @Test
    public void testClone() {
        Classroom cloned = c1.clone();
        assertNotSame(c1, cloned); // pas la même instance
        assertEquals(c1, cloned);  // mais contenu identique
    }

    // 6. Test du constructeur d'initialisation
    @Test
    public void testConstructorWithParameters() {
        Classroom c = new Classroom(5, "C42", 40);
        assertEquals(5, c.getId());
        assertEquals("C42", c.getName());
        assertEquals(40, c.getSeatingCapacity());
    }

    // 7. Test du constructeur par défaut
    @Test
    public void testDefaultConstructor() {
        Classroom c = new Classroom();
        assertEquals(" ", c.getName());
        assertEquals(0, c.getSeatingCapacity());
        assertTrue(c.getId() > 0); // ID auto-généré
    }

}


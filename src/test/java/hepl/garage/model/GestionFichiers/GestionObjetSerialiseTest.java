package hepl.garage.model.GestionFichiers;

import hepl.garage.model.entity.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestionObjetSerialiseTest {
    private static final String TEST_FILE = "test_container.ser";
    private GestionObjetSerialise gestion;
    private ObjetsContainer container;

    @BeforeEach
    void setUp() {
        gestion = new GestionObjetSerialise();
        container = new ObjetsContainer();
    }

    @AfterEach
    void cleanUp() {
        new File(TEST_FILE).delete();
    }

    @Test
    void testCRUDProfessor() throws Exception {
        // CREATE
        Professor p = new Professor("John", "Doe");
        container.getProfessors().add(p);
        gestion.EnregistrerObjects(container, TEST_FILE);

        // READ
        ObjetsContainer loaded = gestion.chargerObjects(TEST_FILE);
        assertFalse(loaded.getProfessors().isEmpty());
        assertEquals("Doe", loaded.getProfessors().get(0).getLastName());

        // UPDATE
        loaded.getProfessors().get(0).setLastName("Smith");
        gestion.EnregistrerObjects(loaded, TEST_FILE);
        ObjetsContainer updated = gestion.chargerObjects(TEST_FILE);
        assertEquals("Smith", updated.getProfessors().get(0).getLastName());

        // DELETE
        updated.getProfessors().clear();
        gestion.EnregistrerObjects(updated, TEST_FILE);
        ObjetsContainer empty = gestion.chargerObjects(TEST_FILE);
        assertTrue(empty.getProfessors().isEmpty());
    }

    @Test
    void testCRUDGroup() throws Exception {
        Group g = new Group("G1");
        container.getGroups().add(g);
        gestion.EnregistrerObjects(container, TEST_FILE);

        ObjetsContainer loaded = gestion.chargerObjects(TEST_FILE);
        assertFalse(loaded.getGroups().isEmpty());
        assertEquals("G1", loaded.getGroups().get(0).getName());

        loaded.getGroups().get(0).setName("G2");
        gestion.EnregistrerObjects(loaded, TEST_FILE);
        ObjetsContainer updated = gestion.chargerObjects(TEST_FILE);
        assertEquals("G2", updated.getGroups().get(0).getName());

        updated.getGroups().clear();
        gestion.EnregistrerObjects(updated, TEST_FILE);
        ObjetsContainer empty = gestion.chargerObjects(TEST_FILE);
        assertTrue(empty.getGroups().isEmpty());
    }

    @Test
    void testCRUDClassroom() throws Exception {
        Classroom c = new Classroom(1, "A101", 30);
        container.getClassrooms().add(c);
        gestion.EnregistrerObjects(container, TEST_FILE);

        ObjetsContainer loaded = gestion.chargerObjects(TEST_FILE);
        assertFalse(loaded.getClassrooms().isEmpty());
        assertEquals("A101", loaded.getClassrooms().get(0).getName());

        loaded.getClassrooms().get(0).setName("B202");
        gestion.EnregistrerObjects(loaded, TEST_FILE);
        ObjetsContainer updated = gestion.chargerObjects(TEST_FILE);
        assertEquals("B202", updated.getClassrooms().get(0).getName());

        updated.getClassrooms().clear();
        gestion.EnregistrerObjects(updated, TEST_FILE);
        ObjetsContainer empty = gestion.chargerObjects(TEST_FILE);
        assertTrue(empty.getClassrooms().isEmpty());
    }

    /*
    @Test

    void testCRUDCourse() throws Exception {
        Course course = new Course(123, "Math", null, 1, 1, java.util.Collections.emptySet());
        container.getCourses().add(course);
        gestion.EnregistrerObjects(container, TEST_FILE);

        ObjetsContainer loaded = gestion.chargerObjects(TEST_FILE);
        assertFalse(loaded.getCourses().isEmpty());
        assertEquals("Math", loaded.getCourses().get(0).getTitle());

        loaded.getCourses().get(0).setTitle("Physique");
        gestion.EnregistrerObjects(loaded, TEST_FILE);
        ObjetsContainer updated = gestion.chargerObjects(TEST_FILE);
        assertEquals("Physique", updated.getCourses().get(0).getTitle());

        updated.getCourses().clear();
        gestion.EnregistrerObjects(updated, TEST_FILE);
        ObjetsContainer empty = gestion.chargerObjects(TEST_FILE);
        assertTrue(empty.getCourses().isEmpty());
    }

     */
}

package hepl.garage.model.entity;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {

        private Professor createTestProfessor(int id) {
                Professor prof = new Professor();
                prof.setId(id);
                prof.setFirstName("Test");
                prof.setLastName("Professor");
                return prof;
        }

        private Classroom createTestClassroom(int id) {
                Classroom classroom = new Classroom();
                classroom.setId(id);
                classroom.setName("Room " + id);
                return classroom;
        }

        private Group createTestGroup(String name) {
                return new Group(name);
        }

        // Test du constructeur par défaut
        @Test
        void testDefaultConstructor() {
                Course course = new Course();
                assertNotNull(course);
                assertNotNull(course.getProfessor());
                assertNotNull(course.getClassroom());
                assertNotNull(course.getGroups());
                assertNotNull(course.getDate());
                assertNotNull(course.getDuree());
                assertNotNull(course.getDay());
        }

        // Test du constructeur d'initialisation
        @Test
        void testConstructorWithInitialization() {
                List<Group> groups = new ArrayList<>();
                Group group = createTestGroup("Group A");
                groups.add(group);

                Professor professor = createTestProfessor(1);
                Classroom classroom = createTestClassroom(101);
                LocalTime time = LocalTime.of(10, 30);
                Duration duree = Duration.ofHours(2);
                DayOfWeek day = DayOfWeek.WEDNESDAY;

                Course course = new Course("Math", professor, classroom, groups, time, duree, day);
                course.setIdCourse(101);

                assertEquals(101, course.getCode());
                assertEquals("Math", course.getTitle());
                assertEquals(professor, course.getProfessor());
                assertEquals(classroom, course.getClassroom());
                assertEquals(groups, course.getGroups());
                assertEquals(time, course.getDate());
                assertEquals(duree, course.getDuree());
                assertEquals(day, course.getDay());
        }

        // Test de addGroup() et isGroupPresent()
        @Test
        void testAddAndIsGroupPresent() {
                Course course = new Course();
                Group group = createTestGroup("Group B");
                course.addGroup(group);
                assertTrue(course.isGroupPresent(group));
        }

        // Test des méthodes de comparaison de dates
        @Test
        void testCompareDates() {
                LocalTime time1 = LocalTime.of(9, 0);
                LocalTime time2 = LocalTime.of(11, 0);

                Course c1 = new Course("Course1", new Professor(), new Classroom(), new ArrayList<>(), time1, Duration.ofHours(1), DayOfWeek.MONDAY);
                Course c2 = new Course("Course2", new Professor(), new Classroom(), new ArrayList<>(), time2, Duration.ofHours(1), DayOfWeek.MONDAY);
                Course c3 = new Course("Course3", new Professor(), new Classroom(), new ArrayList<>(), time1, Duration.ofHours(1), DayOfWeek.MONDAY);

                assertTrue(c1.isEarlierThan(c2));
                assertTrue(c2.isLaterThan(c1));
                assertTrue(c1.isEqualTo(c3));
        }


        // Test de toString
        @Test
        void testToString() {
                List<Group> groups = new ArrayList<>();
                Group group = createTestGroup("Group D");
                groups.add(group);

                Course course = new Course("English", new Professor(), new Classroom(), groups, LocalTime.of(8, 0), Duration.ofHours(1), DayOfWeek.THURSDAY);
                course.setIdCourse(42);
                String result = course.toString();

                assertTrue(result.contains("42"));
                assertTrue(result.contains("English"));
                assertTrue(result.contains("08:00"));
                assertTrue(result.contains("Group D"));
        }

        // Test des setters
        @Test
        void testSetters() {
                Course course = new Course();
                course.setTitle("New Title");
                course.setIdCourse(123);
                course.setDuree(2, 30);
                course.setDay(DayOfWeek.SUNDAY);

                assertEquals("New Title", course.getTitle());
                assertEquals(123, course.getCode());
                assertEquals(Duration.ofMinutes(150), course.getDuree());
                assertEquals(DayOfWeek.SUNDAY, course.getDay());
        }
}
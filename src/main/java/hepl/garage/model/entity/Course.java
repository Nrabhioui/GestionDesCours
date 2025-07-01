package hepl.garage.model.entity;

import java.io.Serializable;
import java.time.*;
import java.util.*;

public class Course implements Cloneable, Serializable {
    public static int currentCode = 1;
    private int idCourse;
    private String title;
    private Professor professor;
    private Classroom classroom;
    private List<Group> groups; // Utilisation de Group au lieu de Integer
    private LocalTime date;
    private Duration Duree;
    private DayOfWeek day;


    // Constructeur par défaut
    public Course() {
        this.professor = new Professor();
        this.classroom = new Classroom();
        this.groups = new ArrayList<Group>();
        this.date = LocalTime.now();
        this.Duree = Duration.ofHours(1);
        this.day = DayOfWeek.MONDAY;
    }

    // Constructeur d'initialisation
    public Course(String title, Professor professor, Classroom classroom, List<Group> groups, LocalTime date, Duration Duree, DayOfWeek day) {
        this.idCourse = currentCode++;
        this.title = title;
        this.professor = professor;
        this.classroom = classroom;
        this.date = date;
        this.Duree = Duree;
        this.day = day;
        this.groups = groups != null ? groups : new ArrayList<>();
    }

    // Getter pour professorId
    public Professor getProfessor() {
        return professor;
    }

    // Getter pour classroomId
    public Classroom getClassroom() {
        return classroom;
    }

    // Getter pour groups
    public List<Group> getGroups() {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        return groups;
    }

    // Getter pour Date
    public LocalTime getDate() {
        return date;
    }
    public Duration getDuree() {
        return Duree;
    }
    public DayOfWeek getDay() {
        return day;
    }
    public void setDuree(Duration duree) {
        Duree = duree;
    }
    public void setDay(DayOfWeek day) {
        this.day = day;
    }
    public void setDuree(int hours, int minutes) {
        Duree = Duration.ofHours(hours).plusMinutes(minutes);
    }

    public int getCode() {
        return idCourse;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    // Ajouter un groupe à la liste des groupes
    public void addGroup(Group group) {
        groups.add(group);
    }

    // Vérifier si un groupe est présent dans la liste
    public boolean isGroupPresent(Group group) {
        return groups.contains(group);
    }

    // Méthode de comparaison pour trier les cours chronologiquement
    public int compareDate(Course other) {
        LocalTime thisTiming = getDate();
        LocalTime otherTiming = other.getDate();
        return thisTiming.compareTo(otherTiming);
    }

    // Redéfinir les opérateurs de comparaison
    public boolean isEarlierThan(Course other) {
        return compareDate(other) < 0;
    }

    public boolean isLaterThan(Course other) {
        return compareDate(other) > 0;
    }

    public boolean isEqualTo(Course other) {
        return compareDate(other) == 0;
    }

    // Méthode clone
    @Override
    public Course clone() {
        Course cloned = new Course(
                getTitle(),
                professor,
                classroom,
                getGroups(),
                getDate(),
                getDuree(),
                getDay()
        );
        return cloned;
    }


    // Redéfinir la méthode toString
    @Override
    public String toString() {
        return String.format("%d;%s;%s;%s", getCode(), getTitle(), getDate() != null ? getDate().toString() : "null", groups);
    }

    // Redéfinir la méthode equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Course course = (Course) obj;
        return professor == course.professor &&
                classroom == course.classroom &&
                groups.equals(course.groups) &&
                getCode() == course.getCode() &&
                getTitle().equals(course.getTitle()) &&
                getDate().equals(course.getDate());
    }
}
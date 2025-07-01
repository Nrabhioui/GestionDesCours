package hepl.garage.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ObjetsContainer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    protected List<Professor> professors  = new ArrayList<Professor>();
    protected List<Group> groups = new ArrayList<Group>();
    protected List<Classroom> classrooms = new ArrayList<Classroom>();
    protected List<Course> courses = new ArrayList<>();
    public static ObjetsContainer instance = new ObjetsContainer();
    public ObjetsContainer() {

    }
    public List<Professor> getProfessors() {
        return professors;
    }
    public List<Group> getGroups() {
        return groups;
    }
    public List<Classroom> getClassrooms() {
        return classrooms;
    }
    public List<Course> getCourses() {
        return courses;
    }
    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    public void add(Professor professor) {
        this.professors.add(professor);
    }
    public void add(Group group) {
        this.groups.add(group);
    }
    public void add(Classroom classroom) {
        this.classrooms.add(classroom);
    }
    public void add(Course course) {
        this.courses.add(course);
    }
    public void clear() {
        this.professors.clear();
        this.groups.clear();
        this.classrooms.clear();
        this.courses.clear();
    }
    public void clearCourses() {
        this.courses.clear();
    }
    public int size() {
        return this.professors.size() + this.groups.size() + this.classrooms.size() + this.courses.size();
    }
    public String toString() {
        return String.format("Professors: %s\nGroups: %s\nClassrooms: %s\nCourses: %s", this.professors, this.groups, this.classrooms, this.courses);
    }
    public String tulpe() {
        return String.format("%s;%s;%s;%s", this.professors, this.groups, this.classrooms, this.courses);
    }
    public void addAll(ObjetsContainer objetsContainer) {
        this.professors.addAll(objetsContainer.professors);
        this.groups.addAll(objetsContainer.groups);
        this.classrooms.addAll(objetsContainer.classrooms);
        this.courses.addAll(objetsContainer.courses);
    }
    public void removeAll(ObjetsContainer objetsContainer) {
        this.professors.removeAll(objetsContainer.professors);
        this.groups.removeAll(objetsContainer.groups);
        this.classrooms.removeAll(objetsContainer.classrooms);
        this.courses.removeAll(objetsContainer.courses);
    }
    public Professor getProfesseur(int id) {
        for(Professor professor : this.professors){
            if(professor.getId() == id){
                return professor;
            }
        }
        return null;
    };
    public void removeProfesseur(int id) {
        Professor professor = instance.getProfesseur(id);
        if(professor != null){
            this.professors.remove(professor);
        }
    }
    public void removeGroupe(int id) {
        Group group = instance.getGroup(id);
        if(group != null){
            this.groups.remove(group);
        }
    }
    public Group getGroup(int id) {
        for(Group group : this.groups){
            if(group.getId() == id){
                return group;
            }
        }
        return null;
    }
    public void remove(Group group) {
        this.groups.remove(group);
    }
    public void removeClassroom(int id) {
        Classroom classroom = instance.getClassroom(id);
        if(classroom != null){
            this.classrooms.remove(classroom);
        }
    }
    public Classroom getClassroom(int id) {
        for(Classroom classroom : this.classrooms){
            if(classroom.getId() == id){
                return classroom;
            }
        }
        return null;
    }
    public void removeClassromm(Classroom classroom) {
        this.classrooms.remove(classroom);
    }
    public void removeCourse(int code) {
        Course toRemove = null;
        for (Course c : courses) {
            if (c.getCode() == code) {
                toRemove = c;
                break;
            }
        }
        if (toRemove != null) courses.remove(toRemove);
    }
    public boolean isEmpty() {
        return this.professors.isEmpty() && this.groups.isEmpty() && this.classrooms.isEmpty() && this.courses.isEmpty();
    }

}

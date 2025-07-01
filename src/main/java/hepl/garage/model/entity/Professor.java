package hepl.garage.model.entity;
import java.io.*;

public class Professor extends Schedulable implements Serializable, Cloneable {

    private String lastName;
    private String firstName;

    // ----- Constructeurs -----

    public Professor() {
        super();
        lastName = "nom";
        firstName = "prenom";
        System.out.println("---Group: constructeur par défaut---");
    }

    public Professor( String firstName, String lastName) {
        super(Schedulable.currentId++);
        setFirstName(firstName);
        setLastName(lastName);
    }

    // ----- Accesseurs -----

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String i) {
        firstName = i;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String i) {
        lastName = i;
    }

    // ----- Affichage -----

    @Override
    public String toString() {
        return lastName + " " + firstName;
    }

    public String tulpe() {
        return getId() + ";" + lastName + ";" + firstName;
    }

    // ----- equals -----

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Professor other = (Professor) obj;
        return this.getId() == other.getId()
                && firstName.equals(other.firstName)
                && lastName.equals(other.lastName);
    }

    @Override
    public Professor clone() {
        Professor p = new Professor(getFirstName(), getLastName());
        p.setId(getId());
        return p;
    }
}

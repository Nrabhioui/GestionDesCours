package hepl.garage.model.entity;
import java.util.*;
import java.io.*;

public class Group extends Schedulable implements Serializable, Cloneable {

    // ***** Attributs *****
    private String name;

    // ***** Constructeurs *****
    public Group() {
        super();
        this.name = " ";
        System.out.println("---Group: constructeur par defaut---");
    }

    public Group(String name) {
        super(Schedulable.currentId++);
        setName(name);
        System.out.println("---Group: constructeur d'initialisation---");
    }


    // ***** Accesseurs *****
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ***** Affichage *****
    @Override
    public String toString() {
        return name;
    }

    // ***** Égalité *****
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Group group = (Group) obj;
        return Objects.equals(name, group.name);
    }

    // ***** Représentation textuelle *****
    @Override
    public String tulpe() {
        return getId() + ";" + name;
    }

    // ***** Clonage *****
    @Override
    public Group clone() {
        Group g = new Group(getName());
        g.setId(getId());
        return g;
    }
}

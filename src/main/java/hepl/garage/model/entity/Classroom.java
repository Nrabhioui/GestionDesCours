package hepl.garage.model.entity;
import java.io.Serializable;
import java.util.Objects;

public class Classroom extends Schedulable implements Cloneable, Serializable {

    // ***** Attributs *****
    private String name;
    private int seatingCapacity;

    // ***** Constructeurs *****
    public Classroom() {
        super();
        this.name = " ";
        this.seatingCapacity = 0;
        System.out.println("---Classroom: constructeur par defaut---");
    }

    public Classroom(int id, String name, int seatingCapacity) {
        super(id);
        setName(name);
        setSeatingCapacity(seatingCapacity);
        System.out.println("---Classroom: constructeur d'initialisation---");
    }
    public Classroom(String name, int seatingCapacity) {
        super(Schedulable.currentId++);
        setName(name);
        setSeatingCapacity(seatingCapacity);
        System.out.println("---Classroom: constructeur d'initialisation---");
    }

    // ***** Accesseurs *****
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    // ***** Affichage *****
    @Override
    public String toString() {
        return name + "(" + seatingCapacity + ")";
    }

    // ***** Égalité *****
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Classroom other = (Classroom) obj;
        return seatingCapacity == other.seatingCapacity &&
                Objects.equals(name, other.name);
    }

    // ***** Représentation textuelle *****
    @Override
    public String tulpe() {
        return getId() + ";" + name + "(" + seatingCapacity + ")";
    }

    // ***** Clonage *****
    @Override
    public Classroom clone() {
        Classroom c = new Classroom(getId(), getName(), getSeatingCapacity());
        return c;
    }
}

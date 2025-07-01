package hepl.garage.model.entity;

public abstract class Schedulable implements EstIdentifiable{
    protected int id;
    protected static int currentId = 1;


    public Schedulable() {
        this.id = currentId++;
    }

    public Schedulable(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public abstract String toString();


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Schedulable schedulable = (Schedulable) obj;
        return id == schedulable.id;
    }

    // Méthode abstraite supplémentaire
    public abstract String tulpe();

}

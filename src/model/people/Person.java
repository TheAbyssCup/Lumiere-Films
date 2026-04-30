package model.people;

public abstract class Person {
    protected String name;
    protected int id;
    private static int idCounter = 1;

    public Person(String name) {
        this.name = name;
        this.id = idCounter++;
    }

    public Person(String name, int id) {
        this.name = name;
        this.id = id;
        if (id >= idCounter) {
            idCounter = id + 1;
        }
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    // Abstract method required by university project
    public abstract void displayInfo();
}

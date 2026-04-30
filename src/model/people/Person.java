package model.people;

public abstract class Person {
    protected String name;
    protected int year; // Join Year

    public Person(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    // Abstract method required by university project
    public abstract void displayInfo();
}

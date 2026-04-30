package model.people;

public class Actor extends Person {
    private String role; // e.g. stunt performer, voice actor

    public Actor(String name, String role) {
        super(name);
        this.role = role;
    }

    public Actor(String name, int id, String role) {
        super(name, id);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void displayInfo() {
        System.out.println("Actor ID: " + id + ", Name: " + name + ", Role: " + role);
    }
}

package model.people;

import model.Searchable;

public class Actor extends Person implements Searchable {
    private String role; // e.g. stunt performer, voice actor

    public Actor(String name, int year, String role) {
        super(name, year);
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
        System.out.println("Actor: " + name + ", Role: " + role + " (Joined: " + year + ")");
    }

    @Override
    public boolean contains(String query) {
        String q = query.toLowerCase();
        return name.toLowerCase().contains(q) || role.toLowerCase().contains(q);
    }
}

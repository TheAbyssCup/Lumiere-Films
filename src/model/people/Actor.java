package model.people;

import model.Searchable;

public class Actor extends Person implements Searchable {
    private String role; // e.g. stunt performer, voice actor
    private String filmTitle; // Associated Movie

    public Actor(String name, int year, String role, double dailyPay, String filmTitle) {
        super(name, year, dailyPay);
        this.role = role;
        this.filmTitle = filmTitle;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void displayInfo() {
        System.out.println("Actor: " + name + ", Role: " + role + " (Movie: " + filmTitle + ")");
    }

    @Override
    public boolean contains(String query) {
        String q = query.toLowerCase();
        return name.toLowerCase().contains(q) || role.toLowerCase().contains(q) || filmTitle.toLowerCase().contains(q);
    }
}

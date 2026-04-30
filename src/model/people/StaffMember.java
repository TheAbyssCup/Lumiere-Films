package model.people;

import model.Searchable;

public class StaffMember extends Person implements Searchable {
    private String role;

    public StaffMember(String name, int year, String role, double dailyPay) {
        super(name, year, dailyPay);
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
        System.out.println("Staff Member: " + name + ", Role: " + role + " (Joined: " + year + ", Pay: $" + dailyPay + ")");
    }

    @Override
    public boolean contains(String query) {
        String q = query.toLowerCase();
        return name.toLowerCase().contains(q) || role.toLowerCase().contains(q);
    }
}

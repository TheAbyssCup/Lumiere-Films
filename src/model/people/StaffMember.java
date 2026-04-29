package model.people;

public class StaffMember {
    public String name;
    public String role;
    public static int staffSize = 0;

    public StaffMember(String name, String role)
    {
        this.name=name;
        this.role=role;
    }
}

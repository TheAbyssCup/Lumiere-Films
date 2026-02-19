import java.util.Scanner;

public class StaffManager {
    StaffMember[] staffMembers = new StaffMember[100];
    Scanner scanner = new Scanner(System.in);
    public void showSubMenu()
    {
        do {
            System.out.println("———————————————————"+"\n\tStaff submenu:");
            System.out.println("1. View All Staff");
            System.out.println("2. Add Staff Member");
            System.out.println("3. Edit Staff Member");
            System.out.println("4. Delete Staff Member");
            System.out.println("5. Search Staff");
            System.out.println("6. Back to Main Menu");
            System.out.print("———————————————————");
        } while (selectSubMenu());
    }
    boolean selectSubMenu()
    {
        switch (MyUtils.selectChoice(6))
        {
            case 1:
                viewAllStaff();
                break;
            case 2:
                addStaffMember();
                break;
            case 3:
                editStaffMember();
                break;
            case 4:
                deleteStaffMember();
                break;
            case 5:
                SearchStaffMember();
                break;
            case 6:
                return false;
        }
        return true;
    }
    void viewAllStaff()
    {
        System.out.println("———————————————————"+"\nAll Staff Members");
        if(StaffMember.staffSize == 0)
        {
            System.out.println("No staff members found.");
        }else {
            System.out.println("\nNo. | Name                    | Role");
            System.out.println("----+-------------------------+-------------------------");
            for (int i = 0; i < StaffMember.staffSize; i++) {
                if(staffMembers[i] != null)
                {
                    System.out.printf("%-3d | %-23s | %-23s\n",
                            (i + 1), staffMembers[i].name, staffMembers[i].role);
                }
            }
        }
    }
    void addStaffMember()
    {
        if (StaffMember.staffSize == 100)
        {
            System.out.println("Your staff is full");
        } else{
            System.out.print("Add staff's name (or type X to cancel): ");
            String newStaffName = scanner.nextLine();
            if (newStaffName.equalsIgnoreCase("x"))
            {
                return;
            }
            System.out.print("Add staff's role (or type X to cancel): ");
            String newStaffRole = scanner.nextLine();
            if (newStaffRole.equalsIgnoreCase("x"))
            {
                return;
            }
            System.out.print("Are you sure? (y/n): ");
            String choiceValidation = scanner.nextLine();
            if (choiceValidation.equalsIgnoreCase("y") || choiceValidation.equalsIgnoreCase("yes"))
            {
                staffMembers[StaffMember.staffSize] = new StaffMember(newStaffName,  newStaffRole);
                StaffMember.staffSize++;
                System.out.println("Staff member added successfully!");
            } else {
                System.out.println("Invalid choice or user moved back");
            }
        }
    }
    void deleteStaffMember()
    {
        if (StaffMember.staffSize == 0) {
            System.out.println("No staff member found to DELETE");
            return;
        }

        viewAllStaff();
        int staffIndex = MyUtils.selectChoice(StaffMember.staffSize) - 1;

        System.out.print("Are you sure? (y/n): ");
        String choiceValidation = scanner.nextLine();
        if (choiceValidation.equalsIgnoreCase("y") || choiceValidation.equalsIgnoreCase("yes"))
        {
            for (int i = staffIndex; i < StaffMember.staffSize - 1; i++) {
                staffMembers[i] = staffMembers[i + 1];
            }

            staffMembers[StaffMember.staffSize - 1] = null;
            StaffMember.staffSize--;

            System.out.println("Staff member removed successfully!");
        } else {
            System.out.println("Invalid choice or user moved back");
        }
    }
    void editStaffMember()
    {
        if (StaffMember.staffSize == 0) {
            System.out.println("No staff member found to EDIT");
            return;
        }

        viewAllStaff();

        int indexToEdit = MyUtils.selectChoice(StaffMember.staffSize) - 1;

        System.out.println("Type 1 = to edit the name");
        System.out.println("Type 2 = to edit the role");
        System.out.println("Type 3 = to edit both");

        int editChoice = MyUtils.selectChoice(3);

        String editedName = "";
        String editedRole = "";

        if (editChoice == 1)
        {
            System.out.print("Enter your staff's edited name: ");
            editedName = scanner.nextLine();
        } else if (editChoice == 2) {
            System.out.print("Enter your staff's edited role: ");
            editedRole = scanner.nextLine();
        } else {
            System.out.print("Enter your staff's edited name: ");
            editedName = scanner.nextLine();
            System.out.print("Enter your staff's edited role: ");
            editedRole = scanner.nextLine();
        }

        System.out.print("Are you sure? (y/n): ");
        String choiceValidation = scanner.nextLine();
        if (choiceValidation.equalsIgnoreCase("y") || choiceValidation.equalsIgnoreCase("yes"))
        {
            if(editChoice == 1)
            {
                staffMembers[indexToEdit].name = editedName;
            } else if (editChoice == 2) {
                staffMembers[indexToEdit].role = editedRole;
            } else {
                staffMembers[indexToEdit].name = editedName;
                staffMembers[indexToEdit].role = editedRole;
            }
            System.out.println("Staff member edited successfully!");
        } else {
            System.out.println("Invalid choice or user moved back");
        }
    }
    void SearchStaffMember()
    {
        if (StaffMember.staffSize == 0) {
            System.out.println("No staff member found to SEARCH");
            return;
        }

        System.out.print("Search: ");
        String searchInput = scanner.nextLine().toLowerCase();
        if (searchInput.trim().length() == 0)
        {
            System.out.println("Invalid search input");
            return;
        }

        System.out.println("\nID | Name                    | Role");
        System.out.println("----+-------------------------+-------------------------");

        for (int i = 0; i < StaffMember.staffSize; i++)
        {
            boolean foundInNames = isSubstring(staffMembers[i].name.toLowerCase(), searchInput);
            boolean foundInRoles = isSubstring(staffMembers[i].role.toLowerCase(), searchInput);

            if (foundInNames || foundInRoles)
            {
                System.out.printf("%-3d | %-23s | %-23s\n",
                        (i + 1), staffMembers[i].name, staffMembers[i].role);
            }
        }
    }
    boolean isSubstring(String target, String searchInput)
    {
        if(searchInput.length() > target.length())
        {
            return false;
        }

        for(int i = 0; i <= target.length() - searchInput.length(); i++)
        {
            boolean isMatching = true;
            for (int j = 0; j < searchInput.length(); j++)
            {
                if (target.toCharArray()[i + j] != searchInput.toCharArray()[j])
                {
                    isMatching = false;
                    break;
                }
            }
            if (isMatching)
            {
                return true;
            }
        }
        return false;
    }
}

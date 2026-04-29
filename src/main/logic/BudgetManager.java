package main.logic;

public class BudgetManager {
    public void showSubMenu()
    {
        do {
            System.out.println("———————————————————"+"\n\tBudget Calculator submenu:");
            System.out.println("1. Calculate Project Budget");
            System.out.println("2. View Rate Information");
            System.out.println("3. Back to Main Menu");
            System.out.print("———————————————————");
        } while (selectSubMenu());
    }
    boolean selectSubMenu()
    {
        switch (MyUtils.selectChoice(3))
        {
            case 1:
                break;
            case 2:
                break;
            case 3:
                return false;
        }
        return true;
    }
}

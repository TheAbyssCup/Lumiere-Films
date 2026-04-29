package main;

import main.logic.ActorManager;
import main.logic.CastingManager;
import main.logic.FilmManager;
import main.logic.StaffManager;
import main.logic.BudgetManager;
import main.logic.MyUtils;


public class Main {
    StaffManager staff = new StaffManager();
    ActorManager actor = new ActorManager();
    FilmManager film = new FilmManager();
    BudgetManager budget = new BudgetManager();

    public static void main(String[] args) {
        Main main = new Main();
        do {
            main.displayMainMenu();
        } while (main.selectMenu());
    }
    void displayMainMenu()
    {
        System.out.println("———————————————————"+"\n\tMain menu:");
        System.out.println("1. Lumiere Staff");
        System.out.println("2. Casting / Actors");
        System.out.println("3. Film Catalogue");
        System.out.println("4. Budget Calculator");
        System.out.println("5. Exit");
        System.out.print("———————————————————");
    }
    boolean selectMenu()
    {
            switch (MyUtils.selectChoice(5))
            {
                case 1:
                    staff.showSubMenu();
                    break;
                case 2:
                    actor.showSubMenu();
                    break;
                case 3:
                    film.showSubMenu();
                    break;
                case 4:
                    budget.showSubMenu();
                    break;
                case 5:
                    return false;
            }
        return true;
    }
}

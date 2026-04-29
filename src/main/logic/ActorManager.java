package main.logic;

import model.people.Actors;

public class ActorManager {
    private Actors actors = new Actors();
    private PortfolioItem portfolio = new PortfolioItem();

    public void showSubMenu()
    {
        actors.initializeActors();
        do {
            System.out.println("———————————————————"+"\n\tCasting / Actors submenu:");
            System.out.println("1. View All Actors");
            System.out.println("2. Add Actor");
            System.out.println("3. Manage Actor Portfolio");
            System.out.println("4. Back to Main Menu");
            System.out.print("———————————————————");
        } while (selectSubMenu());
    }
    boolean selectSubMenu()
    {
        switch (MyUtils.selectChoice(4))
        {
            case 1:
                actors.viewActors();
                break;
            case 2:
                actors.addActor();
                break;
            case 3:
                showPortfolioMenu();
                break;
            case 4:
                return false;
        }
        return true;
    }
    public void showPortfolioMenu() {
        do {
            System.out.println("———————————————————"+"\n\tManage Portfolio Items:");
            System.out.println("1. List All Portfolio Items");
            System.out.println("2. Add Portfolio Item");
            System.out.println("3. Remove Portfolio Item");
            System.out.println("4. Back to Casting / Actors Submenu");
            System.out.print("———————————————————");
        } while (selectPortfolioMenu());
    }
    boolean selectPortfolioMenu()
    {
        switch (MyUtils.selectChoice(4))
        {
            case 1:
                portfolio.listPortfolioItems(askActorNum());
                break;
            case 2:
                portfolio.addPortfolioItems(askActorNum());
                break;
            case 3:
                portfolio.removePortfolioItems(askActorNum());
                break;
            case 4:
                return false;
        }
        return true;
    }
    int askActorNum() {
        System.out.println("Please enter actor number: ");
        actors.viewActors();
        return MyUtils.selectChoice(actors.getActorCount());
    }
}

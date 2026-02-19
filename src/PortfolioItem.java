import java.util.Scanner;

public class PortfolioItem {
    private String[][][] portfolioItems = new String[100][50][3];
    private int[] counts = new int[100];
    Scanner scanner = new Scanner(System.in);

    public void addPortfolioItems(int actorNum) {
        if (counts[actorNum] < 50) {
            System.out.println("Please enter actor's project (or type X to cancel): ");
            String actorProject = scanner.nextLine();
            if (actorProject.equalsIgnoreCase("x"))
            {
                return;
            }
            System.out.print("Please enter actor's role in that project (or type X to cancel): ");
            String actorProjectRole = scanner.nextLine();
            if (actorProjectRole.equalsIgnoreCase("x"))
            {
                return;
            }
            System.out.print("Please enter actor's project year (or type X to cancel): ");
            String actorProjectYear = scanner.nextLine();
            if (actorProjectYear.equalsIgnoreCase("x"))
            {
                return;
            }
            System.out.print("Are you sure? (y/n): ");
            String choiceValidation = scanner.nextLine();
            if (choiceValidation.equalsIgnoreCase("y") || choiceValidation.equalsIgnoreCase("yes"))
            {
                portfolioItems[actorNum][counts[actorNum]][0] = actorProject;
                portfolioItems[actorNum][counts[actorNum]][1] = actorProjectRole;
                portfolioItems[actorNum][counts[actorNum]][2] = actorProjectYear;
                counts[actorNum]++;
                System.out.println("Portfolio Items added successfully.");
            } else {
                System.out.println("Invalid choice or user moved back");
            }
        }
        else {
            System.out.println("Portfolio Items list of this actor is full.");
        }
    }
    public void listPortfolioItems(int actorNum) {
        if (counts[actorNum] == 0) {
            System.out.println("Portfolio Item's list of this actor is empty.");
        } else {
            System.out.println("\nNo. | Project Name                    | Role                     | Year");
            System.out.println("————+—————————————————————————————————+——————————————————————————+———————————————————");
            for (int i = 0; i < counts[actorNum]; i++) {
                System.out.printf(
                        "%-3d | %-23s         | %-23s  | %-23s\n",
                        (i + 1), portfolioItems[actorNum][i][0], portfolioItems[actorNum][i][1],
                        portfolioItems[actorNum][i][2]
                );
            }
        }
    }
    public void removePortfolioItems(int actorNum) {
        if (counts[actorNum] == 0) {
            System.out.println("This actor does not have any portfolio items.");
        }
        else {
            System.out.println("Please enter actor's portfolio item number to remove: ");
            listPortfolioItems(actorNum);
            int portfolioItemNumber = MyUtils.selectChoice(counts[actorNum]);
            for (int i = portfolioItemNumber; i < counts[actorNum]; i++) {
                portfolioItems[actorNum][i][0] = portfolioItems[actorNum][i+1][0];
                portfolioItems[actorNum][i][1] = portfolioItems[actorNum][i+1][1];
                portfolioItems[actorNum][i][2] = portfolioItems[actorNum][i+1][2];
            }
            counts[actorNum]--;
            System.out.println("Portfolio Item removed successfully.");
        }
    }
}

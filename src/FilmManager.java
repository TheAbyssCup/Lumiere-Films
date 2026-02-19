import java.util.Scanner;

public class FilmManager {
    Scanner scanner = new Scanner(System.in);

    public void showSubMenu()
    {
        Films.initializeFilms();
        do {
            System.out.println("———————————————————"+"\n\tFilm Catalogue submenu:");
            System.out.println("1. View All Films");
            System.out.println("2. Add Film");
            System.out.println("3. Edit Film");
            System.out.println("4. Delete Film");
            System.out.println("5. Sort Films - not working");
            System.out.println("6. Filter Films Films - not working");
            System.out.println("7. Back to Main Menu");
            System.out.print("———————————————————");
        } while (selectSubMenu());
    }
    boolean selectSubMenu()
    {
        switch (MyUtils.selectChoice(7))
        {
            case 1:
                viewAllFilms();
                break;
            case 2:
                addFilm();
                break;
            case 3:
                editFilm();
                break;
            case 4:
                deleteFilm();
                break;
            case 5:
                sortFilms();
                break;
            case 6:
                filterFilms();
                break;
            case 7:
                return false;
        }
        return true;
    }

    void viewAllFilms()
    {
        {
        System.out.println("———————————————————"+"\nAll Films");
        if(Films.filmCount == 0)
        {
            System.out.println("No films found.");
        }
        else
        {
            System.out.println("\nNo. | Name                    | Genre                     | Release date");
            System.out.println("----+-------------------------+-------------------------+-------------------------");
            for (int i = 0; i < Films.filmCount; i++) {
                if(Films.films[i] != null)
                {
                    System.out.printf("%-3d | %-23s | %-23s | %-23s\n",
                            (i + 1), Films.films[i].name, Films.films[i].genre, Films.films[i].dateOfRelease
                    );
                }
            }
        }
    }
    }

    void addFilm()
    {
        if (Films.filmCount == 100) {
            System.out.println("Your films is full");
        }
        else
        {
            System.out.print("Add film's name (or type X to cancel): ");
            String newFilmName = scanner.nextLine();
            if (newFilmName.equalsIgnoreCase("x"))
            {
                return;
            }

            System.out.print("Add film's genre (or type X to cancel): ");
            String newFilmGenre = scanner.nextLine();
            if (newFilmName.equalsIgnoreCase("x"))
            {
                return;
            }

            System.out.print("Add film's release date (YYYY-MM-DD) or type X to cancel: ");
            String newFilmReleaseDate = scanner.nextLine();
            if (newFilmName.equalsIgnoreCase("x"))
            {
                return;
            }

            System.out.print("Are you sure? (y/n): ");
            String choiceValidation = scanner.nextLine();
            if (choiceValidation.equalsIgnoreCase("y") || choiceValidation.equalsIgnoreCase("yes"))
            {
                Films.addFilm(newFilmName, newFilmGenre, newFilmGenre);
                System.out.println("Film added successfully");
            }
            else
            {
                System.out.println("Invalid choice or user moved back");
            }


        }
    }

    void editFilm()
     {
        if (Films.filmCount == 0) {
            System.out.println("No films found to EDIT");
            return;
        }

        viewAllFilms();


        int filmIndexToEdit = MyUtils.selectChoice(Films.filmCount) - 1;

        System.out.print("Enter your film's edited name (write 'pass' to keep unchanged): ");
        String editedName = scanner.nextLine();

        System.out.print("Enter your film's edited genre (write 'pass' to keep unchanged): ");
        String editedGenre = scanner.nextLine();

        System.out.print("Enter your film's edited date of release (write 'pass' to keep unchanged): ");
        String editedReleaseDate = scanner.nextLine();

        System.out.print("Are you sure? (y/n): ");
        String choiceValidation = scanner.nextLine();

        if (choiceValidation.equalsIgnoreCase("y") || choiceValidation.equalsIgnoreCase("yes"))
        {
            Films.editFilm(filmIndexToEdit, editedName, editedGenre, editedReleaseDate);
            System.out.println("Film edited successfully!");

        }
        else
        {
            System.out.println("Invalid choice or user moved back");

        }

    }

    void deleteFilm()
    {
        viewAllFilms();
        int filmIndex = MyUtils.selectChoice(Films.filmCount) - 1;

        System.out.print("Are you sure? (y/n): ");
        String choiceValidation = scanner.nextLine();
        if (choiceValidation.equalsIgnoreCase("y") || choiceValidation.equalsIgnoreCase("yes"))
        {
            Films.deleteFilm(filmIndex);

            System.out.println("Film removed successfully!");

        }
        else
        {
            System.out.println("Invalid choice or user moved back");

        }
    }

    void sortFilms()
    {
        if (Films.filmCount == 0)
        {
            System.out.println("No films found.");
            return;
        }

        System.out.println("Choose an option to sort by:");
        System.out.println("1. Name (Alphabetical)");
        System.out.println("2. Date (Chronological)");
        System.out.print("Enter choice (1 or 2): ");

        Films[] sortedFilms = null;

        if (scanner.hasNextInt()) {
            int sortChoice = scanner.nextInt();
            if (sortChoice == 1 || sortChoice == 2) {
                sortedFilms = Films.sortFilms(sortChoice);
            }
            else
            {
                System.out.println("Invalid option! ");
                return;
            }
        }
        else
        {
            System.out.println("That's not a number.");
            scanner.next();
            return;

        }

        if(sortedFilms == null)
        {
            System.out.println("No films found.");
        }
        else {
            System.out.println("\nNo. | Name                    | Genre                     | Release date");
            System.out.println("----+-------------------------+-------------------------+-------------------------");
            for (int i = 0; i < Films.filmCount; i++) {
                if (sortedFilms[i] != null) {
                    System.out.printf("%-3d | %-23s | %-23s | %-23s\n",
                            (i + 1), sortedFilms[i].name, sortedFilms[i].genre, sortedFilms[i].dateOfRelease
                    );
                }
            }
        }
    }

    void filterFilms()
    {
        return;
    }

}

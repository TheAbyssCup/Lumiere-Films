# Lumiere Films - Movie Management System

Lumiere Films is a console application built in Java to help movie studios manage their staff, actors, and film catalogs.

## How to Install

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/TheAbyssCup/Lumiere-Films.git
    ```
2.  **Open in IDE**: Open the folder in IntelliJ IDEA or any Java IDE.
3.  **Check Java**: Make sure you have JDK 8 or higher installed.
4.  **Run**: Find the `Main.java` file in the `src` folder and run the `main` method.

## How to Use

When you run the program, you will see a Main Menu. You can navigate through the options by typing the number of the action you want to perform:

1.  **Lumiere Staff**: Manage the people working behind the scenes.
2.  **Casting / Actors**: Manage the actors and their portfolios.
3.  **Film Catalogue**: View and edit the list of movies.
4.  **Budget Calculator**: Check the project costs (Work in Progress).
5.  **Exit**: Close the application.

## Code Structure & Methods

### 1. Staff Management (`StaffManager.java`)
This class handles all the logic for managing staff members.
*   `showSubMenu()`: Shows the staff management options.
*   `viewAllStaff()`: Prints a table of all staff members and their roles.
*   `addStaffMember()`: Adds a new staff member to the system.
*   `editStaffMember()`: Updates an existing staff member's name or role.
*   `deleteStaffMember()`: Removes a staff member.
*   `SearchStaffMember()`: Find staff using a keyword (uses a custom substring search).

### 2. Actor Management (`ActorManager.java` & `Actors.java`)
Handles the talent side of the studio.
*   `showSubMenu()`: Navigates actor-related actions.
*   `viewActors()`: Lists all registered actors.
*   `addActor(name, role)`: Registers a new actor.
*   `showPortfolioMenu()`: Sub-menu for managing specific actor projects.
*   `addPortfolioItems(actorNum, project, role, year)`: Adds a past project to an actor's history.

### 3. Film Catalogue (`FilmManager.java` & `Films.java`)
Manages the movie database.
*   `viewAllFilms()`: Shows all movies with their genre and release date.
*   `addFilm()`: Prompts for details to add a new movie.
*   `editFilm()`: Allows changing movie details (type 'pass' to keep current values).
*   `deleteFilm()`: Removes a movie from the catalog.
*   `sortFilms(choice)`: Sorts movies by Name or Date (uses `Arrays.sort` and `Comparator`).

### 4. Utilities (`MyUtils.java`)
*   `selectChoice(max)`: A helper method to handle user input and ensure it's within a valid range.

## OOP Concepts Used

In this project, we applied several core Object-Oriented Programming concepts:

### 1. Encapsulation
We use **private** and **public** access modifiers to protect data. 
*   **Where**: In `Actors.java` and `PortfolioItem.java`, variables like `actorMembers` and `portfolioItems` are private. We only interact with them through public methods like `addActor()` or `listPortfolioItems()`.

### 2. Inheritance (Extends)
Inheritance allows one class to acquire properties of another.
*   **Where**: Although the current version uses composition (one class containing another), we've designed `StaffMember.java` and `Films.java` as base classes that could be extended for specific types of staff or movies in the future.

### 3. Loops & Logic
We used different types of loops to handle data efficiently.
*   **Where**: 
    *   `for` loops: Used in `FilmManager.java` and `StaffManager.java` to iterate through arrays and display all items.
    *   `while` and `do-while` loops: Used in `Main.java` and all "Manager" classes to keep the menus running until the user chooses to exit.
    *   Nested loops: Used in `StaffManager.java` inside the `isSubstring` method to perform manual string searching.

### 4. Abstraction
We hide complex implementation details and only show the necessary features to the user.
*   **Where**: The `Main.java` class doesn't know *how* films are sorted or *how* staff are searched; it just calls `film.showSubMenu()` or `staff.showSubMenu()`. The complexity is hidden inside the Manager classes.

### 5. Static Members
Used for data that should be shared across all instances of a class.
*   **Where**: `filmCount` in `Films.java` and `staffSize` in `StaffMember.java` are static so the system can track the total number of items globally.

## License
This project is for educational purposes under the AUT Object Oriented Programming course.

package main.logic;

import model.media.Film;
import java.io.*;
import java.util.*;

public class FilmManager implements ICRUDManager<Film>, IFilePersistence {
    private List<Film> films = new ArrayList<>();

    public FilmManager() {
        loadFromFile("src/db/films.txt");
    }

    @Override
    public void add(Film item) {
        films.add(item);
        saveToFile("src/db/films.txt");
    }

    @Override
    public void update(int index, Film item) {
        if (index >= 0 && index < films.size()) {
            films.set(index, item);
            saveToFile("src/db/films.txt");
        }
    }

    @Override
    public void delete(int index) {
        if (index >= 0 && index < films.size()) {
            films.remove(index);
            saveToFile("src/db/films.txt");
        }
    }

    @Override
    public List<Film> getAll() {
        return films;
    }

    // Method Overloading: Search by Title
    public List<Film> search(String title) {
        List<Film> results = new ArrayList<>();
        for (Film f : films) {
            if (f.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(f);
            }
        }
        return results;
    }

    // Method Overloading: Search by Year
    public List<Film> search(int year) {
        List<Film> results = new ArrayList<>();
        for (Film f : films) {
            if (f.getYear() == year) {
                results.add(f);
            }
        }
        return results;
    }

    @Override
    public void saveToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Film f : films) {
                writer.write(f.getTitle() + "," + f.getYear() + "," + f.getGenre() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving films: " + e.getMessage());
        }
    }

    @Override
    public void loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) return;

        films.clear();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String title = parts[0];
                    int year = Integer.parseInt(parts[1]);
                    String genre = parts[2];
                    films.add(new Film(title, year, genre));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing year in file: " + filename);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while loading films: " + e.getMessage());
        }
    }
}

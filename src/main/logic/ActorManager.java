package main.logic;

import model.people.Actor;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ActorManager implements ICRUDManager<Actor>, IFilePersistence {
    private List<Actor> actors = new ArrayList<>();

    public ActorManager() {
        loadFromFile("src/db/Actors.txt");
    }

    @Override
    public void add(Actor item) {
        actors.add(item);
        saveToFile("src/db/Actors.txt");
    }

    @Override
    public void update(int index, Actor item) {
        if (index >= 0 && index < actors.size()) {
            actors.set(index, item);
            saveToFile("src/db/Actors.txt");
        }
    }

    @Override
    public void delete(int index) {
        if (index >= 0 && index < actors.size()) {
            actors.remove(index);
            saveToFile("src/db/Actors.txt");
        }
    }

    @Override
    public List<Actor> getAll() {
        return actors;
    }

    public List<Actor> getSortedByYear() {
        List<Actor> sorted = new ArrayList<>(actors);
        sorted.sort(Comparator.comparingInt(Actor::getYear));
        return sorted;
    }

    public List<Actor> getSortedByName() {
        List<Actor> sorted = new ArrayList<>(actors);
        sorted.sort(Comparator.comparing(Actor::getName, String.CASE_INSENSITIVE_ORDER));
        return sorted;
    }

    public List<Actor> search(String query) {
        if (query == null || query.isEmpty()) return actors;
        return actors.stream()
                .filter(a -> a.contains(query))
                .collect(Collectors.toList());
    }

    @Override
    public void saveToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Actor a : actors) {
                writer.write(a.getName() + "," + a.getYear() + "," + a.getRole() + "," + a.getDailyPay() + "," + a.getFilmTitle() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving actors: " + e.getMessage());
        }
    }

    @Override
    public void loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) return;

        actors.clear();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String name = parts[0];
                    int year = Integer.parseInt(parts[1]);
                    String role = parts[2];
                    double pay = Double.parseDouble(parts[3]);
                    String movie = parts[4];
                    actors.add(new Actor(name, year, role, pay, movie));
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading actors: " + e.getMessage());
        }
    }
}

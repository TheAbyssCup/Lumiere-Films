package main.logic;

import model.people.Actor;
import java.io.*;
import java.util.*;

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

    @Override
    public void saveToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Actor a : actors) {
                writer.write(a.getId() + "," + a.getName() + "," + a.getRole() + "\n");
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
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String role = parts[2];
                    actors.add(new Actor(name, id, role));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing ID in file: " + filename);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while loading actors: " + e.getMessage());
        }
    }
}

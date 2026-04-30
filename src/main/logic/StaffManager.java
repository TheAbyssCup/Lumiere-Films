package main.logic;

import model.people.StaffMember;
import java.io.*;
import java.util.*;

public class StaffManager implements ICRUDManager<StaffMember>, IFilePersistence {
    private List<StaffMember> staff = new ArrayList<>();

    public StaffManager() {
        loadFromFile("src/db/staff.txt");
    }

    @Override
    public void add(StaffMember item) {
        staff.add(item);
        saveToFile("src/db/staff.txt");
    }

    @Override
    public void update(int index, StaffMember item) {
        if (index >= 0 && index < staff.size()) {
            staff.set(index, item);
            saveToFile("src/db/staff.txt");
        }
    }

    @Override
    public void delete(int index) {
        if (index >= 0 && index < staff.size()) {
            staff.remove(index);
            saveToFile("src/db/staff.txt");
        }
    }

    @Override
    public List<StaffMember> getAll() {
        return staff;
    }

    @Override
    public void saveToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (StaffMember s : staff) {
                writer.write(s.getId() + "," + s.getName() + "," + s.getRole() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving staff: " + e.getMessage());
        }
    }

    @Override
    public void loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) return;

        staff.clear();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String role = parts[2];
                    staff.add(new StaffMember(name, id, role));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing ID in file: " + filename);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while loading staff: " + e.getMessage());
        }
    }
}

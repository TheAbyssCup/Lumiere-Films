package main.logic;

import model.people.Staff;
import model.CRUD;
import model.FileOperations;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class StaffManager implements CRUD<Staff>, FileOperations {
    private List<Staff> staff = new ArrayList<>();

    public StaffManager() {
        loadFromFile("src/db/staff.txt");
    }

    @Override
    public void add(Staff item) {
        staff.add(item);
        saveToFile("src/db/staff.txt");
    }

    @Override
    public void update(int index, Staff item) {
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
    public List<Staff> getAll() {
        return staff;
    }

    public List<Staff> getSortedByYear() {
        List<Staff> sorted = new ArrayList<>(staff);
        sorted.sort(Comparator.comparingInt(Staff::getYear));
        return sorted;
    }

    public List<Staff> getSortedByName() {
        List<Staff> sorted = new ArrayList<>(staff);
        sorted.sort(Comparator.comparing(Staff::getName, String.CASE_INSENSITIVE_ORDER));
        return sorted;
    }

    public List<Staff> search(String query) {
        if (query == null || query.isEmpty()) return staff;
        return staff.stream()
                .filter(s -> s.contains(query))
                .collect(Collectors.toList());
    }

    @Override
    public void saveToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Staff s : staff) {
                writer.write(s.getName() + "," + s.getYear() + "," + s.getRole() + "," + s.getDailyPay() + "\n");
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
                if (parts.length == 4) {
                    String name = parts[0];
                    int year = Integer.parseInt(parts[1]);
                    String role = parts[2];
                    double pay = Double.parseDouble(parts[3]);
                    staff.add(new Staff(name, year, role, pay));
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading staff: " + e.getMessage());
        }
    }
}

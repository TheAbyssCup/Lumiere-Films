package main.logic;

public interface IFilePersistence {
    void saveToFile(String filename);
    void loadFromFile(String filename);
}

package main.logic;

import java.util.List;

public interface ICRUDManager<T> {
    void add(T item);
    void update(int id, T item);
    void delete(int id);
    List<T> getAll();
}

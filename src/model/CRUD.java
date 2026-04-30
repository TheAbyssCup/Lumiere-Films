package model;

import java.util.List;

public interface CRUD<T> {
    void add(T item);
    void update(int id, T item);
    void delete(int id);
    List<T> getAll();
}

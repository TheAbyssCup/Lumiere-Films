package main.ui;

import main.logic.FilmManager;
import model.media.Film;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FilmPanel extends JPanel {
    private FilmManager manager;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> genreFilter;
    private JComboBox<String> sortOptions;
    private JLabel countLabel;

    public FilmPanel(FilmManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top Search/Filter/Sort Bar
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(12);

        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> searchFilms());

        genreFilter = new JComboBox<>();
        updateFilterOptions();
        genreFilter.addActionListener(e -> filterFilms());

        String[] sorts = { "None", "Name (A-Z)", "Year (Oldest)" };
        sortOptions = new JComboBox<>(sorts);
        sortOptions.addActionListener(e -> applySort());

        topBar.add(new JLabel("Search:"));
        topBar.add(searchField);
        topBar.add(searchBtn);
        topBar.add(new JLabel(" Filter:"));
        topBar.add(genreFilter);
        topBar.add(new JLabel(" Sort:"));
        topBar.add(sortOptions);

        add(topBar, BorderLayout.NORTH);

        // Table
        String[] columnNames = { "Title", "Year", "Genre" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom Panel (Buttons + Count)
        JPanel bottomPanel = new JPanel(new BorderLayout());

        countLabel = new JLabel("Total Movies: 0");
        countLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bottomPanel.add(countLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Film");
        JButton editButton = new JButton("Edit Film");
        JButton deleteButton = new JButton("Delete Film");

        addButton.addActionListener(e -> showAddDialog());
        editButton.addActionListener(e -> showEditDialog());
        deleteButton.addActionListener(e -> deleteFilm());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        refreshTable(manager.getAll());
    }

    private void updateFilterOptions() {
        genreFilter.removeAllItems();
        genreFilter.addItem("All Genres");
        Set<String> genres = manager.getAll().stream()
                .map(Film::getGenre)
                .collect(Collectors.toSet());
        for (String g : genres)
            genreFilter.addItem(g);
    }

    private void refreshTable(List<Film> films) {
        tableModel.setRowCount(0);
        for (Film f : films) {
            tableModel.addRow(new Object[] { f.getTitle(), f.getYear(), f.getGenre() });
        }
        countLabel.setText("Total Movies: " + films.size());
    }

    private void searchFilms() {
        refreshTable(manager.search(searchField.getText()));
    }

    private void filterFilms() {
        String genre = (String) genreFilter.getSelectedItem();
        if (genre == null || genre.equals("All Genres")) {
            refreshTable(manager.getAll());
        } else {
            refreshTable(manager.filterByGenre(genre));
        }
    }

    private void applySort() {
        String selected = (String) sortOptions.getSelectedItem();
        if (selected.equals("Name (A-Z)")) {
            refreshTable(manager.getSortedByName());
        } else if (selected.equals("Year (Oldest)")) {
            refreshTable(manager.getSortedByYear());
        } else {
            refreshTable(manager.getAll());
        }
    }

    private void showAddDialog() {
        JTextField titleField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField genreField = new JTextField();

        Object[] message = { "Title:", titleField, "Year:", yearField, "Genre:", genreField };

        int option = JOptionPane.showConfirmDialog(null, message, "Add New Film", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String title = titleField.getText();
                int year = Integer.parseInt(yearField.getText());
                String genre = genreField.getText();
                manager.add(new Film(title, year, genre));
                updateFilterOptions();
                refreshTable(manager.getAll());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid year!");
            }
        }
    }

    private void showEditDialog() {
        int row = table.getSelectedRow();
        if (row == -1)
            return;
        Film f = manager.getAll().get(row);

        JTextField titleField = new JTextField(f.getTitle());
        JTextField yearField = new JTextField(String.valueOf(f.getYear()));
        JTextField genreField = new JTextField(f.getGenre());

        Object[] message = { "Title:", titleField, "Year:", yearField, "Genre:", genreField };

        int option = JOptionPane.showConfirmDialog(null, message, "Edit Film", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                f.setTitle(titleField.getText());
                f.setYear(Integer.parseInt(yearField.getText()));
                f.setGenre(genreField.getText());
                manager.update(row, f);
                updateFilterOptions();
                refreshTable(manager.getAll());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid year!");
            }
        }
    }

    private void deleteFilm() {
        int row = table.getSelectedRow();
        if (row == -1)
            return;
        if (JOptionPane.showConfirmDialog(this, "Delete?", "Confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            manager.delete(row);
            updateFilterOptions();
            refreshTable(manager.getAll());
        }
    }
}

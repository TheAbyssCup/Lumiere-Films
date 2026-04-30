package main.ui;

import main.logic.FilmManager;
import model.media.Film;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FilmPanel extends JPanel {
    private FilmManager manager;
    private JTable table;
    private DefaultTableModel tableModel;

    public FilmPanel(FilmManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Film Catalogue");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"Title", "Year", "Genre"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
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
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Film> films = manager.getAll();
        for (Film f : films) {
            tableModel.addRow(new Object[]{f.getTitle(), f.getYear(), f.getGenre()});
        }
    }

    private void showAddDialog() {
        JTextField titleField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField genreField = new JTextField();

        Object[] message = {
            "Title:", titleField,
            "Year:", yearField,
            "Genre:", genreField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add New Film", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String title = titleField.getText();
                int year = Integer.parseInt(yearField.getText());
                String genre = genreField.getText();
                manager.add(new Film(title, year, genre));
                refreshTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid year format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a film to edit.");
            return;
        }

        Film selectedFilm = manager.getAll().get(selectedRow);

        JTextField titleField = new JTextField(selectedFilm.getTitle());
        JTextField yearField = new JTextField(String.valueOf(selectedFilm.getYear()));
        JTextField genreField = new JTextField(selectedFilm.getGenre());

        Object[] message = {
            "Title:", titleField,
            "Year:", yearField,
            "Genre:", genreField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Edit Film", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                selectedFilm.setTitle(titleField.getText());
                selectedFilm.setYear(Integer.parseInt(yearField.getText()));
                selectedFilm.setGenre(genreField.getText());
                manager.update(selectedRow, selectedFilm);
                refreshTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid year format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteFilm() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a film to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this film?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            manager.delete(selectedRow);
            refreshTable();
        }
    }
}

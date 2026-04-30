package main.ui;

import main.logic.ActorManager;
import model.people.Actor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ActorPanel extends JPanel {
    private ActorManager manager;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> sortOptions;
    private JLabel countLabel;

    public ActorPanel(ActorManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top Search/Sort Bar
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(15);
        
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> refreshTable(manager.search(searchField.getText())));

        String[] sorts = {"None", "Name (A-Z)", "Date (Oldest)"};
        sortOptions = new JComboBox<>(sorts);
        sortOptions.addActionListener(e -> applySort());

        topBar.add(new JLabel("Search:"));
        topBar.add(searchField);
        topBar.add(searchBtn);
        topBar.add(new JLabel(" Sort:"));
        topBar.add(sortOptions);

        add(topBar, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"Name", "Join Year", "Role", "Daily Pay"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        countLabel = new JLabel("Total Actors: 0");
        countLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bottomPanel.add(countLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Actor");
        JButton editButton = new JButton("Edit Actor");
        JButton deleteButton = new JButton("Delete Actor");

        addButton.addActionListener(e -> showAddDialog());
        editButton.addActionListener(e -> showEditDialog());
        deleteButton.addActionListener(e -> deleteActor());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(bottomPanel, BorderLayout.SOUTH);

        refreshTable(manager.getAll());
    }

    private void refreshTable(List<Actor> actors) {
        tableModel.setRowCount(0);
        for (Actor a : actors) {
            tableModel.addRow(new Object[]{a.getName(), a.getYear(), a.getRole(), String.format("$%.2f", a.getDailyPay())});
        }
        countLabel.setText("Total Actors: " + actors.size());
    }

    private void applySort() {
        String selected = (String) sortOptions.getSelectedItem();
        if (selected.equals("Name (A-Z)")) {
            refreshTable(manager.getSortedByName());
        } else if (selected.equals("Date (Oldest)")) {
            refreshTable(manager.getSortedByYear());
        } else {
            refreshTable(manager.getAll());
        }
    }

    private void showAddDialog() {
        JTextField nameField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField roleField = new JTextField();
        JTextField payField = new JTextField("0");

        Object[] message = { 
            "Name:", nameField, 
            "Join Year:", yearField, 
            "Role:", roleField,
            "Daily Pay ($):", payField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add New Actor", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                int year = Integer.parseInt(yearField.getText());
                String role = roleField.getText();
                double pay = Double.parseDouble(payField.getText());
                manager.add(new Actor(name, year, role, pay));
                refreshTable(manager.getAll());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid number format!");
            }
        }
    }

    private void showEditDialog() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        Actor a = manager.getAll().get(row);

        JTextField nameField = new JTextField(a.getName());
        JTextField yearField = new JTextField(String.valueOf(a.getYear()));
        JTextField roleField = new JTextField(a.getRole());
        JTextField payField = new JTextField(String.valueOf(a.getDailyPay()));

        Object[] message = { 
            "Name:", nameField, 
            "Join Year:", yearField, 
            "Role:", roleField,
            "Daily Pay ($):", payField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Edit Actor", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                a.setName(nameField.getText());
                a.setRole(roleField.getText());
                a.setDailyPay(Double.parseDouble(payField.getText()));
                manager.update(row, a);
                refreshTable(manager.getAll());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid number format!");
            }
        }
    }

    private void deleteActor() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        if (JOptionPane.showConfirmDialog(this, "Delete?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            manager.delete(row);
            refreshTable(manager.getAll());
        }
    }
}

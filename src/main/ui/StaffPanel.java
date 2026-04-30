package main.ui;

import main.logic.StaffManager;
import model.people.StaffMember;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StaffPanel extends JPanel {
    private StaffManager manager;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> sortOptions;
    private JLabel countLabel;

    public StaffPanel(StaffManager manager) {
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

        // Table (No ID column)
        String[] columnNames = {"Name", "Join Year", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        countLabel = new JLabel("Total Staff: 0");
        countLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bottomPanel.add(countLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Staff");
        JButton editButton = new JButton("Edit Staff");
        JButton deleteButton = new JButton("Delete Staff");

        addButton.addActionListener(e -> showAddDialog());
        editButton.addActionListener(e -> showEditDialog());
        deleteButton.addActionListener(e -> deleteStaff());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(bottomPanel, BorderLayout.SOUTH);

        refreshTable(manager.getAll());
    }

    private void refreshTable(List<StaffMember> staff) {
        tableModel.setRowCount(0);
        for (StaffMember s : staff) {
            tableModel.addRow(new Object[]{s.getName(), s.getYear(), s.getRole()});
        }
        countLabel.setText("Total Staff: " + staff.size());
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

        Object[] message = { "Name:", nameField, "Join Year:", yearField, "Role:", roleField };

        int option = JOptionPane.showConfirmDialog(null, message, "Add New Staff", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                int year = Integer.parseInt(yearField.getText());
                String role = roleField.getText();
                manager.add(new StaffMember(name, year, role));
                refreshTable(manager.getAll());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid year!");
            }
        }
    }

    private void showEditDialog() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        StaffMember s = manager.getAll().get(row);

        JTextField nameField = new JTextField(s.getName());
        JTextField yearField = new JTextField(String.valueOf(s.getYear()));
        JTextField roleField = new JTextField(s.getRole());

        Object[] message = { "Name:", nameField, "Join Year:", yearField, "Role:", roleField };

        int option = JOptionPane.showConfirmDialog(null, message, "Edit Staff", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                s.setName(nameField.getText());
                s.setRole(roleField.getText());
                manager.update(row, s);
                refreshTable(manager.getAll());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid year!");
            }
        }
    }

    private void deleteStaff() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        if (JOptionPane.showConfirmDialog(this, "Delete?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            manager.delete(row);
            refreshTable(manager.getAll());
        }
    }
}

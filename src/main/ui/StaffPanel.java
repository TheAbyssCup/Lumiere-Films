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

    public StaffPanel(StaffManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Lumiere Staff");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"ID", "Name", "Role"};
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
        JButton addButton = new JButton("Add Staff");
        JButton editButton = new JButton("Edit Staff");
        JButton deleteButton = new JButton("Delete Staff");

        addButton.addActionListener(e -> showAddDialog());
        editButton.addActionListener(e -> showEditDialog());
        deleteButton.addActionListener(e -> deleteStaff());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<StaffMember> staff = manager.getAll();
        for (StaffMember s : staff) {
            tableModel.addRow(new Object[]{s.getId(), s.getName(), s.getRole()});
        }
    }

    private void showAddDialog() {
        JTextField nameField = new JTextField();
        JTextField roleField = new JTextField();

        Object[] message = {
            "Name:", nameField,
            "Role:", roleField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add New Staff Member", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String role = roleField.getText();
            if (!name.isEmpty()) {
                manager.add(new StaffMember(name, role));
                refreshTable();
            }
        }
    }

    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a staff member to edit.");
            return;
        }

        StaffMember selectedStaff = manager.getAll().get(selectedRow);

        JTextField nameField = new JTextField(selectedStaff.getName());
        JTextField roleField = new JTextField(selectedStaff.getRole());

        Object[] message = {
            "Name:", nameField,
            "Role:", roleField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Edit Staff Member", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            selectedStaff.setName(nameField.getText());
            selectedStaff.setRole(roleField.getText());
            manager.update(selectedRow, selectedStaff);
            refreshTable();
        }
    }

    private void deleteStaff() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a staff member to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this staff member?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            manager.delete(selectedRow);
            refreshTable();
        }
    }
}

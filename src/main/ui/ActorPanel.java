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

    public ActorPanel(ActorManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Actor Registry");
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
        JButton addButton = new JButton("Add Actor");
        JButton editButton = new JButton("Edit Actor");
        JButton deleteButton = new JButton("Delete Actor");

        addButton.addActionListener(e -> showAddDialog());
        editButton.addActionListener(e -> showEditDialog());
        deleteButton.addActionListener(e -> deleteActor());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Actor> actors = manager.getAll();
        for (Actor a : actors) {
            tableModel.addRow(new Object[]{a.getId(), a.getName(), a.getRole()});
        }
    }

    private void showAddDialog() {
        JTextField nameField = new JTextField();
        JTextField roleField = new JTextField();

        Object[] message = {
            "Name:", nameField,
            "Role:", roleField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add New Actor", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String role = roleField.getText();
            if (!name.isEmpty()) {
                manager.add(new Actor(name, role));
                refreshTable();
            }
        }
    }

    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an actor to edit.");
            return;
        }

        Actor selectedActor = manager.getAll().get(selectedRow);

        JTextField nameField = new JTextField(selectedActor.getName());
        JTextField roleField = new JTextField(selectedActor.getRole());

        Object[] message = {
            "Name:", nameField,
            "Role:", roleField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Edit Actor", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            selectedActor.setName(nameField.getText());
            selectedActor.setRole(roleField.getText());
            manager.update(selectedRow, selectedActor);
            refreshTable();
        }
    }

    private void deleteActor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an actor to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this actor?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            manager.delete(selectedRow);
            refreshTable();
        }
    }
}

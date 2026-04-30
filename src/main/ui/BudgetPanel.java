package main.ui;

import javax.swing.*;
import java.awt.*;

public class BudgetPanel extends JPanel {
    private JTextArea resultArea;

    public BudgetPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Budget Calculator");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton actorBudgetBtn = new JButton("Calculate Actor Budget");
        JButton productionBudgetBtn = new JButton("Calculate Production Budget");
        JButton marketingBudgetBtn = new JButton("Calculate Marketing Budget");
        JButton clearBtn = new JButton("Clear Results");

        actorBudgetBtn.addActionListener(e -> calculateActorBudget());
        productionBudgetBtn.addActionListener(e -> calculateProductionBudget());
        marketingBudgetBtn.addActionListener(e -> calculateMarketingBudget());
        clearBtn.addActionListener(e -> resultArea.setText(""));

        buttonPanel.add(actorBudgetBtn);
        buttonPanel.add(productionBudgetBtn);
        buttonPanel.add(marketingBudgetBtn);
        buttonPanel.add(clearBtn);

        add(buttonPanel, BorderLayout.WEST);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    private void calculateActorBudget() {
        JTextField actorCountField = new JTextField();
        JTextField avgRateField = new JTextField();

        Object[] message = {
            "Number of Actors:", actorCountField,
            "Average Rate per Actor:", avgRateField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Actor Budget Calculation", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int count = Integer.parseInt(actorCountField.getText());
                double rate = Double.parseDouble(avgRateField.getText());
                double total = count * rate;
                resultArea.append(String.format("Actor Budget: %d actors * $%.2f = $%.2f\n", count, rate, total));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please enter numbers only.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (ArithmeticException e) {
                JOptionPane.showMessageDialog(this, "Mathematical error occurred.", "Math Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void calculateProductionBudget() {
        JTextField daysField = new JTextField();
        JTextField dailyCostField = new JTextField();

        Object[] message = {
            "Shooting Days:", daysField,
            "Daily Production Cost:", dailyCostField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Production Budget Calculation", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int days = Integer.parseInt(daysField.getText());
                double daily = Double.parseDouble(dailyCostField.getText());
                double total = days * daily;
                resultArea.append(String.format("Production Budget: %d days * $%.2f = $%.2f\n", days, daily, total));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values for days and cost.", "Format Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void calculateMarketingBudget() {
        JTextField adsField = new JTextField();
        JTextField eventsField = new JTextField();

        Object[] message = {
            "Advertising Budget:", adsField,
            "Event/Premiere Budget:", eventsField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Marketing Budget Calculation", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                double ads = Double.parseDouble(adsField.getText());
                double events = Double.parseDouble(eventsField.getText());
                double total = ads + events;
                resultArea.append(String.format("Marketing Budget: Ads($%.2f) + Events($%.2f) = $%.2f\n", ads, events, total));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid currency format.", "Format Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

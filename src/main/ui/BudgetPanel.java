package main.ui;

import main.logic.BudgetManager;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BudgetPanel extends JPanel {
    private BudgetManager manager;
    private JLabel balanceLabel;

    public BudgetPanel() {
        this.manager = new BudgetManager();
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // 1. Balance Header
        JPanel header = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Project Wallet Balance");
        title.setFont(new Font("Arial", Font.PLAIN, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        balanceLabel = new JLabel("$" + String.format("%.2f", manager.getBalance()));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 48));
        balanceLabel.setForeground(new Color(0, 102, 204));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        header.add(title, BorderLayout.NORTH);
        header.add(balanceLabel, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // 2. Action Buttons (Wrapped to prevent stretching)
        JPanel gridWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel grid = new JPanel(new GridLayout(2, 2, 15, 15));
        grid.setPreferredSize(new Dimension(500, 120)); // Set a reasonable fixed size

        JButton topUpBtn = new JButton("Top Up Money");
        JButton withdrawBtn = new JButton("Withdraw Money");
        JButton monitoringBtn = new JButton("Monitoring (History)");
        JButton calcBtn = new JButton("Calculate Movie Cost");

        // Styling
        Font btnFont = new Font("Arial", Font.BOLD, 16);
        topUpBtn.setFont(btnFont);
        withdrawBtn.setFont(btnFont);
        monitoringBtn.setFont(btnFont);
        calcBtn.setFont(btnFont);

        // Actions
        topUpBtn.addActionListener(e -> showTopUpDialog());
        withdrawBtn.addActionListener(e -> showWithdrawDialog());
        monitoringBtn.addActionListener(e -> showHistory());
        calcBtn.addActionListener(e -> showCalculator());

        grid.add(topUpBtn);
        grid.add(withdrawBtn);
        grid.add(monitoringBtn);
        grid.add(calcBtn);

        gridWrapper.add(grid);
        add(gridWrapper, BorderLayout.CENTER);
    }

    private void updateBalanceDisplay() {
        balanceLabel.setText("$" + String.format("%.2f", manager.getBalance()));
    }

    private void showTopUpDialog() {
        String amountStr = JOptionPane.showInputDialog(this, "Enter amount to top up:");
        if (amountStr != null) {
            try {
                double amount = Double.parseDouble(amountStr);
                manager.topUp(amount);
                updateBalanceDisplay();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount!");
            }
        }
    }

    private void showWithdrawDialog() {
        JTextField amountField = new JTextField();
        JTextField reasonField = new JTextField();
        Object[] message = { "Amount:", amountField, "Reason/Movie Name:", reasonField };

        int option = JOptionPane.showConfirmDialog(this, message, "Withdraw Money", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String reason = reasonField.getText();
                if (manager.withdraw(amount, reason)) {
                    updateBalanceDisplay();
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient funds or invalid amount!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount!");
            }
        }
    }

    private void showHistory() {
        List<String> history = manager.getHistory();
        JTextArea area = new JTextArea(20, 50);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));

        if (history.isEmpty()) {
            area.setText("No transactions found.");
        } else {
            for (String line : history) {
                area.append(line + "\n");
            }
        }

        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Transaction History", JOptionPane.PLAIN_MESSAGE);
    }

    private void showCalculator() {
        // We reuse the calculator logic we built
        JTextField daysField = new JTextField();
        JTextField crewField = new JTextField("0");
        JTextField crewPayField = new JTextField("0");
        JTextField leadPayField = new JTextField("0");
        JTextField equipField = new JTextField("0");
        JTextField travelField = new JTextField("0");

        Object[] message = {
                "Filming Days:", daysField,
                "Crew Count:", crewField,
                "Crew Daily Pay:", crewPayField,
                "Lead Actor Pay:", leadPayField,
                "Equipment Rental:", equipField,
                "Food & Transport:", travelField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Movie Cost Calculator",
                JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int days = Integer.parseInt(daysField.getText());
                int crew = Integer.parseInt(crewField.getText());
                double cp = Double.parseDouble(crewPayField.getText());
                double lp = Double.parseDouble(leadPayField.getText());
                double eq = Double.parseDouble(equipField.getText());
                double tr = Double.parseDouble(travelField.getText());

                double total = manager.calculateTotal(days, crew, cp, lp, eq, tr);
                JOptionPane.showMessageDialog(this, String.format("Estimated Movie Cost: $%.2f", total));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers!");
            }
        }
    }
}

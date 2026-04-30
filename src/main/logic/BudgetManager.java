package main.logic;

import java.io.*;
import java.util.*;

public class BudgetManager {
    private double balance = 0.0;
    private final String BALANCE_FILE = "src/db/balance.txt";
    private final String HISTORY_FILE = "src/db/budget_history.txt";

    public BudgetManager() {
        loadBalance();
    }

    public double getBalance() {
        return balance;
    }

    public void topUp(double amount) {
        if (amount > 0) {
            balance += amount;
            saveBalance();
            logTransaction("TOP UP", amount, "N/A");
        }
    }

    public boolean withdraw(double amount, String reason) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            saveBalance();
            logTransaction("WITHDRAWAL", amount, reason);
            return true;
        }
        return false;
    }

    private void logTransaction(String type, double amount, String reason) {
        try (FileWriter fw = new FileWriter(HISTORY_FILE, true)) {
            String date = new java.util.Date().toString();
            fw.write(String.format("%s | %s | $%.2f | Reason: %s | New Balance: $%.2f\n",
                    date, type, amount, reason, balance));
        } catch (IOException e) {
            System.err.println("Error logging transaction: " + e.getMessage());
        }
    }

    public List<String> getHistory() {
        List<String> history = new ArrayList<>();
        File file = new File(HISTORY_FILE);
        if (!file.exists())
            return history;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                history.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("History file not found.");
        }
        return history;
    }

    private void saveBalance() {
        try (FileWriter fw = new FileWriter(BALANCE_FILE)) {
            fw.write(String.valueOf(balance));
        } catch (IOException e) {
            System.err.println("Error saving balance: " + e.getMessage());
        }
    }

    private void loadBalance() {
        File file = new File(BALANCE_FILE);
        if (!file.exists()) return;
        try (Scanner sc = new Scanner(file)) {
            if (sc.hasNextLine()) {
                balance = Double.parseDouble(sc.nextLine());
            }
        } catch (Exception e) {
            balance = 0.0;
        }
    }

    // --- Calculator Logic (Overloaded Methods) ---
    public double calculateTotal(int days, int crewCount, double crewPay, double leadPay) {
        return (days * crewCount * crewPay) + (days * leadPay);
    }

    public double calculateTotal(int days, int crewCount, double crewPay, double leadPay, double equipFees) {
        return calculateTotal(days, crewCount, crewPay, leadPay) + (days * equipFees);
    }

    public double calculateTotal(int days, int crewCount, double crewPay, double leadPay, double equipFees,
            double travelCosts) {
        return calculateTotal(days, crewCount, crewPay, leadPay, equipFees) + travelCosts;
    }
}

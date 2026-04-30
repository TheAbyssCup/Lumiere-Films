package main.ui;

import main.logic.ActorManager;
import main.logic.FilmManager;
import main.logic.StaffManager;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;

    private FilmManager filmManager;
    private ActorManager actorManager;
    private StaffManager staffManager;

    public MainFrame() {
        setTitle("Lumiere Films Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize Managers
        filmManager = new FilmManager();
        actorManager = new ActorManager();
        staffManager = new StaffManager();

        setLayout(new BorderLayout());

        // Sidebar
        SidebarPanel sidebar = new SidebarPanel(this);
        add(sidebar, BorderLayout.WEST);

        // Content Area with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Add Panels
        contentPanel.add(new FilmPanel(filmManager), "Movies");
        contentPanel.add(new ActorPanel(actorManager), "Actors");
        contentPanel.add(new StaffPanel(staffManager), "Staff");
        contentPanel.add(new BudgetPanel(), "Budget");
        contentPanel.add(createWelcomePanel(), "Welcome");

        add(contentPanel, BorderLayout.CENTER);

        showPanel("Welcome");
    }

    public void showPanel(String name) {
        cardLayout.show(contentPanel, name);
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        JLabel label = new JLabel("Welcome to Lumiere Films Management System");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label);
        return panel;
    }
}

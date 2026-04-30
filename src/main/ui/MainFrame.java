package main.ui;

import main.logic.ActorManager;
import main.logic.FilmManager;
import main.logic.StaffManager;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContent;

    public MainFrame() {
        setTitle("Lumiere Films - Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Managers
        FilmManager filmManager = new FilmManager();
        ActorManager actorManager = new ActorManager();
        StaffManager staffManager = new StaffManager();

        // Layout
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);

        // Panels
        mainContent.add(new WelcomePanel(), "Welcome");
        mainContent.add(new FilmPanel(filmManager), "Movies");
        mainContent.add(new ActorPanel(actorManager), "Actors");
        mainContent.add(new StaffPanel(staffManager), "Staff");
        mainContent.add(new JPanel(), "Budget"); // Placeholder

        // Sidebar
        SidebarPanel sidebar = new SidebarPanel(this);
        add(sidebar, BorderLayout.WEST);
        add(mainContent, BorderLayout.CENTER);
    }

    public void showPanel(String name) {
        cardLayout.show(mainContent, name);
    }
}

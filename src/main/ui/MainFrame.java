package main.ui;

import main.logic.ActorManager;
import main.logic.FilmManager;
import main.logic.StaffManager;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContent;
    private JLabel sectionTitle;

    public MainFrame() {
        setTitle("Lumiere Films - Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Managers
        FilmManager filmManager = new FilmManager();
        ActorManager actorManager = new ActorManager();
        StaffManager staffManager = new StaffManager();

        // Main Layout
        setLayout(new BorderLayout());

        // Right Side Container (Header + CardLayout)
        JPanel rightSideContainer = new JPanel(new BorderLayout());
        
        // Header Label (strictly on the right side)
        sectionTitle = new JLabel("Welcome Page");
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 24));
        sectionTitle.setHorizontalAlignment(SwingConstants.CENTER);
        sectionTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        rightSideContainer.add(sectionTitle, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);

        // Panels
        mainContent.add(new WelcomePanel(), "Welcome");
        mainContent.add(new FilmPanel(filmManager), "Movies");
        mainContent.add(new ActorPanel(actorManager, filmManager), "Actors");
        mainContent.add(new StaffPanel(staffManager), "Staff");
        mainContent.add(new BudgetPanel(), "Budget");

        rightSideContainer.add(mainContent, BorderLayout.CENTER);

        // Sidebar (Left Side)
        SidebarPanel sidebar = new SidebarPanel(this);
        
        add(sidebar, BorderLayout.WEST);
        add(rightSideContainer, BorderLayout.CENTER);
    }

    public void showPanel(String name) {
        cardLayout.show(mainContent, name);
        // Update the header label text
        if (name.equals("Welcome")) {
            sectionTitle.setText("Welcome Page");
        } else {
            sectionTitle.setText(name + " Section");
        }
    }
}

package main.ui;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
    public WelcomePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel title = new JLabel("Welcome to Lumiere Films");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("University Project: Production Management System");
        subtitle.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea description = new JTextArea(
            "\nLumiere Films is a comprehensive management system designed to streamline \n" +
            "the operations of a film production company.\n\n" +
            "Key Features:\n" +
            "- Manage your film and commercial database\n" +
            "- Keep track of staff members and cast (actors)\n" +
            "- Search, filter, and sort through records efficiently\n" +
            "- Calculate production budgets (Coming Soon)\n\n" +
            "Use the navigation sidebar on the left to get started!"
        );
        description.setFont(new Font("Arial", Font.PLAIN, 16));
        description.setEditable(false);
        description.setOpaque(false);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(title);
        content.add(Box.createRigidArea(new Dimension(0, 10)));
        content.add(subtitle);
        content.add(Box.createRigidArea(new Dimension(0, 30)));
        content.add(description);

        add(content, BorderLayout.CENTER);
    }
}

package main.ui;

import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends JPanel {
    public SidebarPanel(MainFrame mainFrame) {
        setLayout(new GridLayout(10, 1, 5, 5));
        setPreferredSize(new Dimension(200, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] menuItems = {"Staff", "Actors", "Movies", "Budget"};

        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setFocusPainted(false);
            button.addActionListener(e -> mainFrame.showPanel(item));
            add(button);
        }
    }
}

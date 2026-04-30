package main.ui;

import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends JPanel {
    private MainFrame mainFrame;

    public SidebarPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridLayout(6, 1, 5, 5));
        setPreferredSize(new Dimension(150, 0));
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));

        JButton homeBtn = new JButton("Home");
        JButton staffBtn = new JButton("Staff");
        JButton actorBtn = new JButton("Actors");
        JButton filmBtn = new JButton("Movies");
        JButton budgetBtn = new JButton("Budget");

        homeBtn.addActionListener(e -> mainFrame.showPanel("Welcome"));
        staffBtn.addActionListener(e -> mainFrame.showPanel("Staff"));
        actorBtn.addActionListener(e -> mainFrame.showPanel("Actors"));
        filmBtn.addActionListener(e -> mainFrame.showPanel("Movies"));
        budgetBtn.addActionListener(e -> mainFrame.showPanel("Budget"));

        add(homeBtn);
        add(staffBtn);
        add(actorBtn);
        add(filmBtn);
        add(budgetBtn);
    }
}

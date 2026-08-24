import javax.swing.*;
import java.awt.*;

public class MuseumApp extends JFrame {

    public MuseumApp(String username) {
        setTitle("Museum Management System - Logged in as " + username);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 1, 10));

        // Buttons for Navigation
        JButton visitorButton = new JButton("Manage Visitors");
        JButton specimenButton = new JButton("Manage Specimens");
        JButton sectionButton = new JButton("Manage Sections");

        // Add Action Listeners
        visitorButton.addActionListener(e -> new VisitorPanel());
        specimenButton.addActionListener(e -> new SpecimenPanel());
        sectionButton.addActionListener(e -> new SectionPanel());

        // Add Buttons to Main Panel
        mainPanel.add(visitorButton);
        mainPanel.add(specimenButton);
        mainPanel.add(sectionButton);

        add(mainPanel);
    }

    public static void main(String[] args) {
        // Start with the Login Frame
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}

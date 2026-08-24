import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class SpecimenManagementGUI extends JFrame {
    private JTextField nameField, descriptionField, developmentDateField, creatorIdField, sectionIdField;
    private JButton addSpecimenButton, viewSpecimensButton;

    public SpecimenManagementGUI() {
        setTitle("Specimen Management");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel nameLabel = new JLabel("Specimen Name:");
        nameLabel.setBounds(20, 20, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 20, 200, 25);
        add(nameField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(20, 60, 100, 25);
        add(descriptionLabel);

        descriptionField = new JTextField();
        descriptionField.setBounds(120, 60, 200, 25);
        add(descriptionField);

        JLabel developmentDateLabel = new JLabel("Development Date (YYYY-MM-DD):");
        developmentDateLabel.setBounds(20, 100, 200, 25);
        add(developmentDateLabel);

        developmentDateField = new JTextField();
        developmentDateField.setBounds(220, 100, 100, 25);
        add(developmentDateField);

        JLabel creatorIdLabel = new JLabel("Creator ID:");
        creatorIdLabel.setBounds(20, 140, 100, 25);
        add(creatorIdLabel);

        creatorIdField = new JTextField();
        creatorIdField.setBounds(120, 140, 200, 25);
        add(creatorIdField);

        JLabel sectionIdLabel = new JLabel("Section ID:");
        sectionIdLabel.setBounds(20, 180, 100, 25);
        add(sectionIdLabel);

        sectionIdField = new JTextField();
        sectionIdField.setBounds(120, 180, 200, 25);
        add(sectionIdField);

        addSpecimenButton = new JButton("Add Specimen");
        addSpecimenButton.setBounds(20, 220, 140, 30);
        add(addSpecimenButton);

        viewSpecimensButton = new JButton("View Specimens");
        viewSpecimensButton.setBounds(180, 220, 140, 30);
        add(viewSpecimensButton);

        // Add Action Listeners
        addSpecimenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSpecimen();
            }
        });

        viewSpecimensButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewSpecimens();
            }
        });
    }

    private void addSpecimen() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        String developmentDate = developmentDateField.getText();
        int creatorId = Integer.parseInt(creatorIdField.getText());
        int sectionId = Integer.parseInt(sectionIdField.getText());

        String query = "INSERT INTO Specimen (Name, Description, DevelopmentDate, CreatorID, SectionID) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, developmentDate);
            stmt.setInt(4, creatorId);
            stmt.setInt(5, sectionId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Specimen added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding specimen!");
        }
    }

    private void viewSpecimens() {
        String query = "SELECT sp.Name AS SpecimenName, sp.Description, sp.DevelopmentDate, s.Name AS SectionName, c.Name AS CreatorName " +
                       "FROM Specimen sp " +
                       "JOIN Section s ON sp.SectionID = s.SectionID " +
                       "JOIN Creator c ON sp.CreatorID = c.CreatorID";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            StringBuilder result = new StringBuilder("Specimen List:\n");
            while (rs.next()) {
                result.append("Specimen Name: ").append(rs.getString("SpecimenName"))
                      .append(", Description: ").append(rs.getString("Description"))
                      .append(", Development Date: ").append(rs.getDate("DevelopmentDate"))
                      .append(", Section: ").append(rs.getString("SectionName"))
                      .append(", Creator: ").append(rs.getString("CreatorName"))
                      .append("\n");
            }
            JOptionPane.showMessageDialog(this, result.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving specimens!");
        }
    }

    public static void main(String[] args) {
        new SpecimenManagementGUI().setVisible(true);
    }
}

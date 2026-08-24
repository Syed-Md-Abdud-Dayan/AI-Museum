import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class VisitorManagementGUI extends JFrame {
    private JTextField nameField, nidField, contactField, visitDateField;
    private JButton addVisitorButton, viewVisitorsButton;

    public VisitorManagementGUI() {
        setTitle("Visitor Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 20, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 20, 200, 25);
        add(nameField);

        JLabel nidLabel = new JLabel("NID:");
        nidLabel.setBounds(20, 60, 100, 25);
        add(nidLabel);

        nidField = new JTextField();
        nidField.setBounds(120, 60, 200, 25);
        add(nidField);

        JLabel contactLabel = new JLabel("Contact:");
        contactLabel.setBounds(20, 100, 100, 25);
        add(contactLabel);

        contactField = new JTextField();
        contactField.setBounds(120, 100, 200, 25);
        add(contactField);

        JLabel visitDateLabel = new JLabel("Visit Date (YYYY-MM-DD):");
        visitDateLabel.setBounds(20, 140, 200, 25);
        add(visitDateLabel);

        visitDateField = new JTextField();
        visitDateField.setBounds(220, 140, 100, 25);
        add(visitDateField);

        addVisitorButton = new JButton("Add Visitor");
        addVisitorButton.setBounds(20, 180, 140, 30);
        add(addVisitorButton);

        viewVisitorsButton = new JButton("View Visitors");
        viewVisitorsButton.setBounds(180, 180, 140, 30);
        add(viewVisitorsButton);

        // Add Action Listeners
        addVisitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addVisitor();
            }
        });

        viewVisitorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewVisitors();
            }
        });
    }

    private void addVisitor() {
        String name = nameField.getText();
        String nid = nidField.getText();
        String contact = contactField.getText();
        String visitDate = visitDateField.getText();

        String query = "INSERT INTO Visitor (Name, NID, Contact, VisitDate) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, nid);
            stmt.setString(3, contact);
            stmt.setString(4, visitDate);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Visitor added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding visitor!");
        }
    }

    private void viewVisitors() {
        String query = "SELECT * FROM Visitor";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            StringBuilder result = new StringBuilder("Visitor List:\n");
            while (rs.next()) {
                result.append("ID: ").append(rs.getInt("VisitorID"))
                      .append(", Name: ").append(rs.getString("Name"))
                      .append(", NID: ").append(rs.getString("NID"))
                      .append(", Contact: ").append(rs.getString("Contact"))
                      .append(", Visit Date: ").append(rs.getDate("VisitDate"))
                      .append("\n");
            }
            JOptionPane.showMessageDialog(this, result.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving visitors!");
        }
    }

    public static void main(String[] args) {
        new VisitorManagementGUI().setVisible(true);
    }
}

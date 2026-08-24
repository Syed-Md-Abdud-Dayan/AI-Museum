import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;  // Add this import

public class AdminLoginGUI {
    private JFrame frame;
    private JTextField usernameField;

    public AdminLoginGUI() {
        frame = new JFrame("Admin Login");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JButton loginButton = new JButton("Login");

        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(new JLabel()); // Empty placeholder
        frame.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        frame.setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM admin WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(frame, "Login successful!");
                frame.dispose();
                new AdminDashboard(); // Replace with the class that contains the admin options
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new AdminLoginGUI();
    }
}

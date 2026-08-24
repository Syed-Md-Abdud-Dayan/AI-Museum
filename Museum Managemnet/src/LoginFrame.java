import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JTextField userField;
    private JButton loginButton;

    public LoginFrame() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create UI Components
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        JLabel userLabel = new JLabel("Enter Username:");
        userField = new JTextField();
        loginButton = new JButton("Login");

        panel.add(userLabel);
        panel.add(userField);
        panel.add(loginButton);

        add(panel);

        // Add Listener for Login Button
        loginButton.addActionListener(new LoginListener());

        setVisible(true);
    }

    private class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = userField.getText().trim();

            if (!username.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Login Successful! Welcome, " + username + ".");
                
                // Launch MuseumApp with the username
                new MuseumApp(username).setVisible(true);
                dispose(); // Close the LoginFrame
            } else {
                JOptionPane.showMessageDialog(null, "Username cannot be empty. Please enter a valid username.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}

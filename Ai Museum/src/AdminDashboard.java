import javax.swing.*;

public class AdminDashboard {
    public AdminDashboard() {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Welcome to the Admin Dashboard!", SwingConstants.CENTER);
        frame.add(label);

        frame.setVisible(true);
    }
}

import javax.swing.*;
import java.sql.*;

public class ViewSpecimenDetailsGUI extends JFrame {
    public ViewSpecimenDetailsGUI() {
        setTitle("Specimen Details");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String query = "SELECT sp.Name AS SpecimenName, sp.Description, sp.DevelopmentDate, " +
                       "s.Name AS SectionName, c.Name AS CreatorName " +
                       "FROM Specimen sp " +
                       "JOIN Section s ON sp.SectionID = s.SectionID " +
                       "JOIN Creator c ON sp.CreatorID = c.CreatorID";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            StringBuilder result = new StringBuilder("Specimen Details:\n");
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
            JOptionPane.showMessageDialog(this, "Error retrieving specimen details!");
        }
    }

    public static void main(String[] args) {
        new ViewSpecimenDetailsGUI().setVisible(true);
    }
}

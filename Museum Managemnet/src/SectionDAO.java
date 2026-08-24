import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionDAO {

    public List<Section> getAllSections() throws SQLException {
        List<Section> sections = new ArrayList<>();
        String query = "SELECT * FROM Section";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Section section = new Section(
                    resultSet.getInt("SectionID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Description")
                );
                sections.add(section);
            }
        }
        return sections;
    }
    public void addSection(Section section) throws SQLException {
        String query = "INSERT INTO Section (Name, Description) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, section.getName());
            stmt.setString(2, section.getDescription());
            stmt.executeUpdate();
        }
    }
}

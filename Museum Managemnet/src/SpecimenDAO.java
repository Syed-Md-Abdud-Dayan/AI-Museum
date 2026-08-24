import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecimenDAO {

    public List<Specimen> getAllSpecimens() throws SQLException {
        List<Specimen> specimens = new ArrayList<>();
        String query = "SELECT * FROM Specimen";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Specimen specimen = new Specimen(
                    resultSet.getInt("SpecimenID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Description"),
                    resultSet.getString("DevelopmentDate"),
                    resultSet.getInt("CreatorID"),
                    resultSet.getInt("SectionID")
                );
                specimens.add(specimen);
            }
        }
        return specimens;
    }
    public void addSpecimen(Specimen specimen) throws SQLException {
        String query = "INSERT INTO Specimen (Name, Description, DevelopmentDate, CreatorID, SectionID) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, specimen.getName());
            stmt.setString(2, specimen.getDescription());
            stmt.setString(3, specimen.getDevelopmentDate());
            stmt.setInt(4, specimen.getCreatorID());
            stmt.setInt(5, specimen.getSectionID());
            stmt.executeUpdate();
        }
    }
}

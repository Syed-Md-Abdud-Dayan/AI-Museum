import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VisitorDAO {

    public void addVisitor(Visitor visitor) throws SQLException {
        String sql = "INSERT INTO visitor (name, nid, contact, visitDate) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, visitor.getName());
            preparedStatement.setString(2, visitor.getNid());
            preparedStatement.setString(3, visitor.getContact());
            preparedStatement.setDate(4, java.sql.Date.valueOf(visitor.getVisitDate()));

            preparedStatement.executeUpdate();
        }
    }

    public List<Visitor> getAllVisitors() throws SQLException {
        String sql = "SELECT * FROM visitor";
        List<Visitor> visitors = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Visitor visitor = new Visitor();
                visitor.setVisitorID(resultSet.getInt("visitorID")); // Ensure your table has this column
                visitor.setName(resultSet.getString("name"));
                visitor.setNid(resultSet.getString("nid"));
                visitor.setContact(resultSet.getString("contact"));
                visitor.setVisitDate(resultSet.getDate("visitDate").toLocalDate());
                visitors.add(visitor);
            }
        }
        return visitors;
    }
}

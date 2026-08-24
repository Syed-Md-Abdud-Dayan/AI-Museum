import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class VisitorPanel extends JFrame {

    private DefaultTableModel tableModel;
    private JTable visitorTable;
    private JScrollPane scrollPane;
    private JLabel placeholderLabel;
    private VisitorDAO visitorDAO;

    public VisitorPanel() {
        setTitle("Visitor Management");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        visitorDAO = new VisitorDAO();

        // Form Panel
        JPanel formPanel = new JPanel(new FlowLayout());
        JButton viewButton = new JButton("View All Visitors");
        JButton addButton = new JButton("Add Visitor");
        formPanel.add(viewButton);
        formPanel.add(addButton);

        // Placeholder label
        placeholderLabel = new JLabel("No data to display.");
        placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Initialize table and scroll pane (hidden initially)
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "NID", "Contact", "Visit Date"}, 0);
        visitorTable = new JTable(tableModel);
        scrollPane = new JScrollPane(visitorTable);
        scrollPane.setVisible(false); // Hide the table initially

        // Add components to the frame
        add(formPanel, BorderLayout.NORTH);
        add(placeholderLabel, BorderLayout.CENTER); // Add placeholder
        add(scrollPane, BorderLayout.CENTER);       // Add table (hidden)

        // Add Listeners
        viewButton.addActionListener(new ViewVisitorsListener());
        addButton.addActionListener(new AddVisitorListener());

        setVisible(true);
    }

    // Listener for Viewing All Visitors
    private class ViewVisitorsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                List<Visitor> visitors = visitorDAO.getAllVisitors();
                tableModel.setRowCount(0); // Clear existing rows

                if (visitors.isEmpty()) {
                    JOptionPane.showMessageDialog(VisitorPanel.this, "No visitors found.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    scrollPane.setVisible(false); // Hide the table
                    placeholderLabel.setVisible(true); // Show placeholder
                    return;
                }

                // Populate the table with data
                for (Visitor visitor : visitors) {
                    tableModel.addRow(new Object[]{
                            visitor.getVisitorID(),
                            visitor.getName(),
                            visitor.getNid(),
                            visitor.getContact(),
                            visitor.getVisitDate()
                    });
                }

                // Show the table and hide the placeholder
                scrollPane.setVisible(true);
                placeholderLabel.setVisible(false);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(VisitorPanel.this, "Error fetching visitors: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Listener for Adding a Visitor
    private class AddVisitorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Create a new dialog to input visitor information
            JTextField nameField = new JTextField(20);
            JTextField nidField = new JTextField(20);
            JTextField contactField = new JTextField(20);
            JTextField visitDateField = new JTextField(10);

            JPanel addVisitorPanel = new JPanel(new GridLayout(5, 2));
            addVisitorPanel.add(new JLabel("Name:"));
            addVisitorPanel.add(nameField);
            addVisitorPanel.add(new JLabel("NID:"));
            addVisitorPanel.add(nidField);
            addVisitorPanel.add(new JLabel("Contact:"));
            addVisitorPanel.add(contactField);
            addVisitorPanel.add(new JLabel("Visit Date (YYYY-MM-DD):"));
            addVisitorPanel.add(visitDateField);

            int option = JOptionPane.showConfirmDialog(null, addVisitorPanel, "Add Visitor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                // Get the data entered by the user
                String name = nameField.getText();
                String nid = nidField.getText();
                String contact = contactField.getText();
                String visitDate = visitDateField.getText();

                // Validate input and add visitor to the database
                if (name.isEmpty() || nid.isEmpty() || contact.isEmpty() || visitDate.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                } else {
                    try {
                        visitorDAO.addVisitor(new Visitor(name, nid, contact, visitDate));
                        JOptionPane.showMessageDialog(null, "Visitor added successfully!");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error adding visitor: " + ex.getMessage());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new VisitorPanel();
    }
}

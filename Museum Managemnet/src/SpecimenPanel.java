import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class SpecimenPanel extends JFrame {

    private DefaultTableModel tableModel;
    private JTable specimenTable;
    private JScrollPane scrollPane;
    private JLabel placeholderLabel;
    private SpecimenDAO specimenDAO;

    public SpecimenPanel() {
        setTitle("Specimen Management");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        specimenDAO = new SpecimenDAO();

        // Form Panel
        JPanel formPanel = new JPanel(new FlowLayout());
        JButton viewButton = new JButton("View All Specimens");
        JButton addButton = new JButton("Add Specimen");
        formPanel.add(viewButton);
        formPanel.add(addButton);

        // Placeholder label
        placeholderLabel = new JLabel("No data to display.");
        placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Initialize table and scroll pane (hidden initially)
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Description", "Dev Date", "CreatorID", "SectionID"}, 0);
        specimenTable = new JTable(tableModel);
        scrollPane = new JScrollPane(specimenTable);
        scrollPane.setVisible(false); // Hide the table initially

        // Add components to the frame
        add(formPanel, BorderLayout.NORTH);
        add(placeholderLabel, BorderLayout.CENTER); // Add placeholder
        add(scrollPane, BorderLayout.CENTER);       // Add table (hidden)

        // Add Listeners
        viewButton.addActionListener(new ViewSpecimensListener());
        addButton.addActionListener(new AddSpecimenListener());

        setVisible(true);
    }

    // Listener for Viewing All Specimens
    private class ViewSpecimensListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                List<Specimen> specimens = specimenDAO.getAllSpecimens();
                tableModel.setRowCount(0); // Clear existing rows

                if (specimens.isEmpty()) {
                    JOptionPane.showMessageDialog(SpecimenPanel.this, "No specimens found.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    scrollPane.setVisible(false); // Hide the table
                    placeholderLabel.setVisible(true); // Show placeholder
                    return;
                }

                // Populate the table with data
                for (Specimen specimen : specimens) {
                    tableModel.addRow(new Object[]{
                            specimen.getSpecimenID(),
                            specimen.getName(),
                            specimen.getDescription(),
                            specimen.getDevelopmentDate(),
                            specimen.getCreatorID(),
                            specimen.getSectionID()
                    });
                }

                // Show the table and hide the placeholder
                scrollPane.setVisible(true);
                placeholderLabel.setVisible(false);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(SpecimenPanel.this, "Error fetching specimens: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Listener for Adding a Specimen
    private class AddSpecimenListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Create a new dialog to input specimen information
            JTextField nameField = new JTextField(20);
            JTextField descriptionField = new JTextField(30);
            JTextField devDateField = new JTextField(10);
            JTextField creatorIdField = new JTextField(10);
            JTextField sectionIdField = new JTextField(10);

            JPanel addSpecimenPanel = new JPanel(new GridLayout(6, 2));
            addSpecimenPanel.add(new JLabel("Name:"));
            addSpecimenPanel.add(nameField);
            addSpecimenPanel.add(new JLabel("Description:"));
            addSpecimenPanel.add(descriptionField);
            addSpecimenPanel.add(new JLabel("Development Date (YYYY-MM-DD):"));
            addSpecimenPanel.add(devDateField);
            addSpecimenPanel.add(new JLabel("Creator ID:"));
            addSpecimenPanel.add(creatorIdField);
            addSpecimenPanel.add(new JLabel("Section ID:"));
            addSpecimenPanel.add(sectionIdField);

            int option = JOptionPane.showConfirmDialog(null, addSpecimenPanel, "Add Specimen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                // Get the data entered by the user
                String name = nameField.getText();
                String description = descriptionField.getText();
                String devDate = devDateField.getText();
                String creatorId = creatorIdField.getText();
                String sectionId = sectionIdField.getText();

                // Validate input and add specimen to the database
                if (name.isEmpty() || description.isEmpty() || devDate.isEmpty() || creatorId.isEmpty() || sectionId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                } else {
                    try {
                        Specimen specimen = new Specimen(name, description, devDate, Integer.parseInt(creatorId), Integer.parseInt(sectionId));
                        specimenDAO.addSpecimen(specimen);
                        JOptionPane.showMessageDialog(null, "Specimen added successfully!");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error adding specimen: " + ex.getMessage());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Creator ID and Section ID must be integers.");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new SpecimenPanel();
    }
}

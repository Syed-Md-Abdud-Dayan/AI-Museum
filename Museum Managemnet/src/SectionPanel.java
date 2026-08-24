import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class SectionPanel extends JFrame {

    private DefaultTableModel tableModel;
    private JTable sectionTable;
    private JScrollPane scrollPane;
    private JLabel placeholderLabel;
    private SectionDAO sectionDAO;

    public SectionPanel() {
        setTitle("Section Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        sectionDAO = new SectionDAO();

        // Form Panel
        JPanel formPanel = new JPanel(new FlowLayout());
        JButton viewButton = new JButton("View All Sections");
        JButton addButton = new JButton("Add Section");
        formPanel.add(viewButton);
        formPanel.add(addButton);

        // Placeholder label
        placeholderLabel = new JLabel("No data to display.");
        placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Initialize table and scroll pane (hidden initially)
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Description"}, 0);
        sectionTable = new JTable(tableModel);
        scrollPane = new JScrollPane(sectionTable);
        scrollPane.setVisible(false); // Hide the table initially

        // Add components to the frame
        add(formPanel, BorderLayout.NORTH);
        add(placeholderLabel, BorderLayout.CENTER); // Add placeholder
        add(scrollPane, BorderLayout.CENTER);       // Add table (hidden)

        // Add Listeners
        viewButton.addActionListener(new ViewSectionsListener());
        addButton.addActionListener(new AddSectionListener());

        setVisible(true);
    }

    // Listener for Viewing All Sections
    private class ViewSectionsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                List<Section> sections = sectionDAO.getAllSections();
                tableModel.setRowCount(0); // Clear existing rows

                if (sections.isEmpty()) {
                    JOptionPane.showMessageDialog(SectionPanel.this, "No sections found.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    scrollPane.setVisible(false); // Hide the table
                    placeholderLabel.setVisible(true); // Show placeholder
                    return;
                }

                // Populate the table with data
                for (Section section : sections) {
                    tableModel.addRow(new Object[]{
                            section.getSectionID(),
                            section.getName(),
                            section.getDescription()
                    });
                }

                // Show the table and hide the placeholder
                scrollPane.setVisible(true);
                placeholderLabel.setVisible(false);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(SectionPanel.this, "Error fetching sections: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Listener for Adding a Section
    private class AddSectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Create a new dialog to input section information
            JTextField nameField = new JTextField(20);
            JTextField descriptionField = new JTextField(30);

            JPanel addSectionPanel = new JPanel(new GridLayout(3, 2));
            addSectionPanel.add(new JLabel("Name:"));
            addSectionPanel.add(nameField);
            addSectionPanel.add(new JLabel("Description:"));
            addSectionPanel.add(descriptionField);

            int option = JOptionPane.showConfirmDialog(null, addSectionPanel, "Add Section", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                // Get the data entered by the user
                String name = nameField.getText();
                String description = descriptionField.getText();

                // Validate input and add section to the database
                if (name.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                } else {
                    try {
                        Section section = new Section(name, description);
                        sectionDAO.addSection(section);
                        JOptionPane.showMessageDialog(null, "Section added successfully!");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error adding section: " + ex.getMessage());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new SectionPanel();
    }
}

import javax.swing.*;
import java.awt.event.*;

public class AdminMenuGUI extends JFrame {
    private String professorId;

    public AdminMenuGUI(String professorId) {
        this.professorId = professorId;

        setTitle("Admin Menu");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton viewVisitorsButton = new JButton("View Visitors");
        viewVisitorsButton.setBounds(100, 40, 200, 30);
        add(viewVisitorsButton);

        JButton addVisitorButton = new JButton("Add Visitor");
        addVisitorButton.setBounds(100, 80, 200, 30);
        add(addVisitorButton);

        JButton deleteVisitorButton = new JButton("Delete Visitor");
        deleteVisitorButton.setBounds(100, 120, 200, 30);
        add(deleteVisitorButton);

        JButton viewSpecimensButton = new JButton("View Specimens");
        viewSpecimensButton.setBounds(100, 160, 200, 30);
        add(viewSpecimensButton);

        JButton addSpecimenButton = new JButton("Add Specimen");
        addSpecimenButton.setBounds(100, 200, 200, 30);
        add(addSpecimenButton);

        JButton deleteSpecimenButton = new JButton("Delete Specimen");
        deleteSpecimenButton.setBounds(100, 240, 200, 30);
        add(deleteSpecimenButton);

        JButton viewSpecimenDetailsButton = new JButton("View Specimen Details");
        viewSpecimenDetailsButton.setBounds(100, 280, 200, 30);
        add(viewSpecimenDetailsButton);

        // Button Actions
        addVisitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VisitorManagementGUI().setVisible(true);
            }
        });

        viewVisitorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewVisitorsGUI().setVisible(true);
            }
        });

        deleteVisitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteVisitorGUI().setVisible(true);
            }
        });

        addSpecimenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SpecimenManagementGUI().setVisible(true);
            }
        });

        viewSpecimensButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewSpecimensGUI().setVisible(true);
            }
        });

        

        viewSpecimenDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewSpecimenDetailsGUI().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        new AdminMenuGUI("adminId").setVisible(true);
    }
}


package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class MaintainVendorDetail extends JFrame implements ActionListener {
    private Choice vendorIdChoice;
    private JTable vendorTable;
    private JButton searchButton, printButton, updateButton, addButton, cancelButton, deleteButton;
    private JLabel heading;

    MaintainVendorDetail() {
        // Frame properties
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setSize(600, 400);
        setLocation(400, 150);
        setTitle("Vendor Records");

        // Initialize components
        heading = new JLabel("Search by Vendor ID");
        heading.setBounds(20, 20, 150, 20);
        add(heading);

        vendorIdChoice = new Choice();
        vendorIdChoice.setBounds(180, 20, 150, 20);
        add(vendorIdChoice);

        // Populate vendor IDs in the choice dropdown
        try {
            ConnectionDatabase db = new ConnectionDatabase();
            ResultSet rs = db.s.executeQuery("SELECT id FROM vendors");
            while (rs.next()) {
                vendorIdChoice.add(rs.getString("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize the table
        vendorTable = new JTable();
        vendorTable.setBackground(new Color(225, 245, 254));
        vendorTable.setForeground(Color.BLACK);

        // Populate the table with initial data
        loadVendorDetails();

        JScrollPane scrollPane = new JScrollPane(vendorTable);
        scrollPane.setBounds(20, 100, 550, 200);
        add(scrollPane);

        // Buttons
        searchButton = new JButton("Search");
        searchButton.setBounds(340, 20, 80, 20);
        searchButton.setBackground(new Color(31, 97, 141));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(this);
        add(searchButton);

        printButton = new JButton("Print");
        printButton.setBounds(20, 70, 80, 20);
        printButton.setBackground(new Color(31, 97, 141));
        printButton.setForeground(Color.WHITE);
        printButton.addActionListener(this);
        add(printButton);

        updateButton = new JButton("Update");
        updateButton.setBounds(110, 70, 80, 20);
        updateButton.setBackground(new Color(31, 97, 141));
        updateButton.setForeground(Color.WHITE);
        updateButton.addActionListener(this);
        add(updateButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(200, 70, 80, 20);
        deleteButton.setBackground(new Color(31, 97, 141));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(this);
        add(deleteButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(290, 70, 80, 20);
        cancelButton.setBackground(new Color(31, 97, 141));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(this);
        add(cancelButton);

        setVisible(true);
    }

    // Method to load vendor details into the table
    private void loadVendorDetails() {
        try {
            ConnectionDatabase db = new ConnectionDatabase();
            String query = "SELECT id, name, email FROM vendors";
            ResultSet rs = db.s.executeQuery(query);
            vendorTable.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Action event handling
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String query = "SELECT * FROM vendors WHERE id = '" + vendorIdChoice.getSelectedItem() + "'";
            try {
                ConnectionDatabase db = new ConnectionDatabase();
                ResultSet rs = db.s.executeQuery(query);
                vendorTable.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == printButton) {
            try {
                vendorTable.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == updateButton) {
            // Open the update/delete form
            new VendorUpdateDelete(); 
        } else if (e.getSource() == deleteButton) {
            // Open the update/delete form
            new VendorUpdateDelete(); 
        } else if (e.getSource() == cancelButton) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new MaintainVendorDetail().setVisible(true);
    }
}

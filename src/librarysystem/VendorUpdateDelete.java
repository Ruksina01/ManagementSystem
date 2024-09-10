
package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class VendorUpdateDelete extends JFrame {
    private JComboBox<String> vendorDropdown;
    private JTextField nameField, emailField, passwordField;
    private JButton updateButton, deleteButton, cancelButton;
    private String selectedVendorId;

    VendorUpdateDelete() {
        // Frame properties
        setTitle("Update/Delete Vendors");
        setSize(500, 350);
        setLocation(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Use null layout for manual positioning

        // Initialize components
        vendorDropdown = new JComboBox<>();
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField = new JTextField(20);
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        cancelButton = new JButton("Cancel");

        // Customize button styles
        updateButton.setBackground(new Color(31, 97, 141));
        updateButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(204, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(128, 128, 128));
        cancelButton.setForeground(Color.WHITE);

        // Populate vendor dropdown
        fetchVendors();

        // Add action listener to vendor dropdown
        vendorDropdown.addActionListener(e -> {
            String selectedVendor = (String) vendorDropdown.getSelectedItem();
            if (selectedVendor != null && !selectedVendor.isEmpty()) {
                selectedVendorId = selectedVendor.split(" - ")[0]; // Extract vendor ID
                loadVendorDetails(selectedVendorId); // Load selected vendor details into text fields
            }
        });

        // Update vendor details
        updateButton.addActionListener(e -> {
            if (selectedVendorId != null) {
                updateVendor(selectedVendorId);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a vendor.");
            }
        });

        // Delete vendor
        deleteButton.addActionListener(e -> {
            if (selectedVendorId != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this vendor?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    deleteVendor(selectedVendorId);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a vendor.");
            }
        });
        
        // Cancel button
        cancelButton.addActionListener(e -> {
            // Close the frame
            this.dispose();
        });

        // Layout for vendor form using setBounds
        JLabel selectVendorLabel = new JLabel("Select Vendor:");
        selectVendorLabel.setBounds(30, 30, 120, 25);
        add(selectVendorLabel);

        vendorDropdown.setBounds(160, 30, 250, 25);
        add(vendorDropdown);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 70, 120, 25);
        add(nameLabel);

        nameField.setBounds(160, 70, 250, 25);
        add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 110, 120, 25);
        add(emailLabel);

        emailField.setBounds(160, 110, 250, 25);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 150, 120, 25);
        add(passwordLabel);

        passwordField.setBounds(160, 150, 250, 25);
        add(passwordField);

        // Buttons panel
        updateButton.setBounds(130, 200, 100, 30);
        add(updateButton);

        deleteButton.setBounds(240, 200, 100, 30);
        add(deleteButton);
        
        cancelButton.setBounds(350, 200, 100, 30);
        add(cancelButton);

        setVisible(true);
    }

    // Fetch vendors and populate dropdown
    private void fetchVendors() {
        try {
            ConnectionDatabase db = new ConnectionDatabase();
            String query = "SELECT id, name FROM vendors";
            PreparedStatement pstmt = db.c.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String vendorId = rs.getString("id");
                String vendorName = rs.getString("name");
                vendorDropdown.addItem(vendorId + " - " + vendorName);
            }

            pstmt.close();
            db.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load vendor details into the text fields
    private void loadVendorDetails(String vendorId) {
        try {
            ConnectionDatabase db = new ConnectionDatabase();
            String query = "SELECT * FROM vendors WHERE id = ?";
            PreparedStatement pstmt = db.c.prepareStatement(query);
            pstmt.setString(1, vendorId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                emailField.setText(rs.getString("email"));
                passwordField.setText(rs.getString("password"));
            }

            pstmt.close();
            db.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update vendor details
    private void updateVendor(String vendorId) {
        try {
            ConnectionDatabase db = new ConnectionDatabase();
            String query = "UPDATE vendors SET name = ?, email = ?, password = ? WHERE id = ?";
            PreparedStatement pstmt = db.c.prepareStatement(query);
            pstmt.setString(1, nameField.getText());
            pstmt.setString(2, emailField.getText());
            pstmt.setString(3, passwordField.getText());
            pstmt.setString(4, vendorId);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Vendor updated successfully!");
            pstmt.close();
            db.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete vendor
    private void deleteVendor(String vendorId) {
        try {
            ConnectionDatabase db = new ConnectionDatabase();
            String query = "DELETE FROM vendors WHERE id = ?";
            PreparedStatement pstmt = db.c.prepareStatement(query);
            pstmt.setString(1, vendorId);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Vendor deleted successfully!");
            vendorDropdown.removeAllItems(); // Clear and refresh dropdown
            fetchVendors();

            pstmt.close();
            db.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new VendorUpdateDelete();
    }
}

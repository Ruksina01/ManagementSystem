package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UserUpdateDelete extends JFrame {
    private JComboBox<String> userDropdown;
    private JTextField nameField, emailField, passwordField;
    private JButton updateButton, deleteButton, cancelButton;
    private String selectedUserId;

    UserUpdateDelete() {
        // Frame properties
        setTitle("Update/Delete Users");
        setSize(500, 300);
        setLocation(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Use null layout for manual positioning

        // Initialize components
        userDropdown = new JComboBox<>();
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

        // Populate user dropdown
        fetchUsers();

        // Add action listener to user dropdown
        userDropdown.addActionListener(e -> {
            String selectedUser = (String) userDropdown.getSelectedItem();
            if (selectedUser != null && !selectedUser.isEmpty()) {
                selectedUserId = selectedUser.split(" - ")[0]; // Extract user ID
                loadUserDetails(selectedUserId); // Load selected user details into text fields
            }
        });

        // Update user details
        updateButton.addActionListener(e -> {
            if (selectedUserId != null) {
                updateUser(selectedUserId);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a user.");
            }
        });

        // Delete user
        deleteButton.addActionListener(e -> {
            if (selectedUserId != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    deleteUser(selectedUserId);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a user.");
            }
        });
        
        // Cancel button
        cancelButton.addActionListener(e -> {
            // Close the frame
            this.dispose();
        });

        // Layout for user form using setBounds
        JLabel selectUserLabel = new JLabel("Select User:");
        selectUserLabel.setBounds(20, 20, 100, 25);
        add(selectUserLabel);

        userDropdown.setBounds(130, 20, 200, 25);
        add(userDropdown);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 60, 100, 25);
        add(nameLabel);

        nameField.setBounds(130, 60, 200, 25);
        add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 100, 100, 25);
        add(emailLabel);

        emailField.setBounds(130, 100, 200, 25);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 140, 100, 25);
        add(passwordLabel);

        passwordField.setBounds(130, 140, 200, 25);
        add(passwordField);

        // Buttons panel
        updateButton.setBounds(110, 180, 100, 30);
        add(updateButton);

        deleteButton.setBounds(220, 180, 100, 30);
        add(deleteButton);
        
        cancelButton.setBounds(330, 180, 100, 30);
        add(cancelButton);

        setVisible(true);
    }

    // Fetch users and populate dropdown
    private void fetchUsers() {
        try {
            ConnectionDatabase db = new ConnectionDatabase();
            String query = "SELECT id, name FROM users_customer";
            PreparedStatement pstmt = db.c.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String userId = rs.getString("id");
                String userName = rs.getString("name");
                userDropdown.addItem(userId + " - " + userName);
            }

            pstmt.close();
            db.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load user details into the text fields
    private void loadUserDetails(String userId) {
        try {
            ConnectionDatabase db = new ConnectionDatabase();
            String query = "SELECT * FROM users_customer WHERE id = ?";
            PreparedStatement pstmt = db.c.prepareStatement(query);
            pstmt.setString(1, userId);
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

    // Update user details
    private void updateUser(String userId) {
        try {
            ConnectionDatabase db = new ConnectionDatabase();
            String query = "UPDATE users_customer SET name = ?, email = ?, password = ? WHERE id = ?";
            PreparedStatement pstmt = db.c.prepareStatement(query);
            pstmt.setString(1, nameField.getText());
            pstmt.setString(2, emailField.getText());
            pstmt.setString(3, passwordField.getText());
            pstmt.setString(4, userId);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "User updated successfully!");
            pstmt.close();
            db.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete user
    private void deleteUser(String userId) {
        try {
            ConnectionDatabase db = new ConnectionDatabase();
            String query = "DELETE FROM users_customer WHERE id = ?";
            PreparedStatement pstmt = db.c.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "User deleted successfully!");
            userDropdown.removeAllItems(); // Clear and refresh dropdown
            fetchUsers();

            pstmt.close();
            db.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UserUpdateDelete();
    }
}

package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminLogin extends JFrame implements ActionListener {
    // Components for the login form
    private JLabel titleLabel, userIdLabel, passwordLabel;
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JButton loginButton, cancelButton;

    AdminLogin() {
        // Set the frame properties
        setTitle("Library Management System - Login");
        setSize(500, 300);
        setLocation(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Set background color
        Color backgroundColor = new Color(240, 248, 255); // AliceBlue color
        getContentPane().setBackground(backgroundColor);

        // Title label
        titleLabel = new JLabel("Admin Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(50, 20, 400, 40);
        add(titleLabel);

        // User Id label and text field
        userIdLabel = new JLabel("User Id:");
        userIdLabel.setBounds(50, 80, 100, 30);
        add(userIdLabel);

        userIdField = new JTextField();
        userIdField.setBounds(160, 80, 250, 30);
        add(userIdField);

        // Password label and password field
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 130, 100, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 130, 250, 30);
        add(passwordField);


        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(160, 180, 100, 40);
        cancelButton.setBackground(new Color(255, 69, 0)); // Red
        cancelButton.setForeground(Color.WHITE); // Button text color
        cancelButton.setFocusPainted(false); // Remove focus border
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16)); // Font style
        cancelButton.addActionListener(this);
        add(cancelButton);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setBounds(310, 180, 100, 40);
        loginButton.setBackground(new Color(25, 25, 112)); // Dark blue
        loginButton.setForeground(Color.WHITE); // Button text color
        loginButton.setFocusPainted(false); // Remove focus border
        loginButton.setFont(new Font("Arial", Font.BOLD, 16)); // Font style
        loginButton.addActionListener(this);
        add(loginButton);

        // Make the frame visible
        setVisible(true);
    }

    // Handle button click events
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userId = userIdField.getText();
            String password = new String(passwordField.getPassword());

            // Check user credentials from database
            if (authenticateUser(userId, password)) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                setVisible(false); // Hide the login form
                new AdminDashboard(userId);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials, please try again.");
            }
        } else if (e.getSource() == cancelButton) {
            // Clear the fields
            setVisible(false);
            new HomePage(); // Redirect to home page or previous screen
        }
    }

    // Method to authenticate user from the database
    private boolean authenticateUser(String userId, String password) {
        try {
            // Establish connection using ConnectionDatabase class
            ConnectionDatabase conn = new ConnectionDatabase();
            String query = "SELECT * FROM users WHERE name = ? AND password = ?";

            PreparedStatement stmt = conn.c.prepareStatement(query);
            stmt.setString(1, userId);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            // If result set is not empty, user credentials are correct
            return rs.next();

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        new AdminLogin();
    }
}

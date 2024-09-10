package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UserLogin extends JFrame implements ActionListener {
    // Components for the login form
    private JLabel titleLabel, emailLabel, passwordLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, cancelButton;

    UserLogin() {
        // Set the frame properties
        setTitle("User Login");
        setSize(500, 300);
        setLocation(400, 150);;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background color
        Color backgroundColor = new Color(51, 204, 255); // Light blue
        getContentPane().setBackground(backgroundColor);

        // Title label
        titleLabel = new JLabel("User Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(50, 20, 400, 40);
        add(titleLabel);

        // Email label and text field
        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 80, 100, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(160, 80, 250, 30);
        add(emailField);

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
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(this);
        add(cancelButton);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setBounds(310, 180, 100, 40);
        loginButton.setBackground(new Color(25, 25, 112)); // Dark blue
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(this);
        add(loginButton);

        // Make the frame visible
        setVisible(true);
    }

    // Handle button click events
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Check user credentials from the database
            if (authenticateUser(email, password)) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                setVisible(false); // Hide the login form
                new UserDashboard(email);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password, please try again.");
            }
        } else if (e.getSource() == cancelButton) {
            // Close the application or return to the previous screen
            setVisible(false);
            new HomePage(); // Redirect to home page or previous screen
        }
    }

    // Method to authenticate user from the database
    private boolean authenticateUser(String email, String password) {
        try {
            // Establish connection using ConnectionDatabase class
            ConnectionDatabase conn = new ConnectionDatabase();
            String query = "SELECT * FROM users_customer WHERE email = ? AND password = ?";

            PreparedStatement stmt = conn.c.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            // If result set is not empty, user credentials are correct
            if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        new UserLogin();
    }
}

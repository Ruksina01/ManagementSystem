package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class VendorLogin extends JFrame implements ActionListener {

    // Declare the components
    private JLabel emailLabel, passwordLabel, titleLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, cancelButton;

    VendorLogin() {
        // Set the frame properties
        setTitle("Vendor Login");
        setSize(500, 300);
        setLocation(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Set background color
        Color backgroundColor = new Color(240, 248, 255); // AliceBlue color
        getContentPane().setBackground(backgroundColor);

        // Title label
        titleLabel = new JLabel("Vendor Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(50, 20, 400, 30);
        add(titleLabel);

        // Email label and text field
        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 70, 100, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 70, 250, 30);
        add(emailField);

        // Password label and password field
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 110, 100, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 110, 250, 30);
        add(passwordField);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setBounds(270, 160, 100, 40);
        loginButton.setBackground(new Color(25, 25, 112)); // DarkBlue color
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(this);
        add(loginButton);

        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(150, 160, 100, 40);
        cancelButton.setBackground(new Color(255, 69, 0)); // Red color
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(this);
        add(cancelButton);

        // Make the frame visible
        setVisible(true);
    }

    // Handle button click event
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Validate input before proceeding
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verify the login credentials
            try {
                ConnectionDatabase db = new ConnectionDatabase();  // Use your ConnectionDatabase class
                String query = "SELECT * FROM vendors WHERE email = ? AND password = ?";
                PreparedStatement pstmt = db.c.prepareStatement(query);
                pstmt.setString(1, email);
                pstmt.setString(2, password);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    // Proceed to vendor dashboard or other functionality
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Close the connection
                rs.close();
                pstmt.close();
                db.c.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: Unable to log in.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == cancelButton) {
            setVisible(false); // Hide the login page
            new HomePage(); // Redirect to home page
        }
    }

    public static void main(String[] args) {
        new VendorLogin();
    }
}

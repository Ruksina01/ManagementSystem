package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

public class VendorSignup extends JFrame implements ActionListener {

    // Declare the components
    private JLabel nameLabel, emailLabel, passwordLabel, titleLabel;
    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JButton signUpButton, cancelButton;

    VendorSignup() {
        // Set the frame properties
        setTitle("Vendor Signup");
        setSize(500, 300);
        setLocation(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Set background color
        Color backgroundColor = new Color(240, 248, 255); // AliceBlue color
        getContentPane().setBackground(backgroundColor);

        // Title label
        titleLabel = new JLabel("Vendor Signup", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(50, 20, 400, 30);
        add(titleLabel);

        // Name label and text field
        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 70, 100, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 70, 250, 30);
        add(nameField);

        // Email label and text field
        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 110, 100, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 110, 250, 30);
        add(emailField);

        // Password label and password field
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 150, 100, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 150, 250, 30);
        add(passwordField);

        // Sign Up button
        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(270, 200, 100, 40);
        signUpButton.setBackground(new Color(25, 25, 112)); // DarkBlue color
        signUpButton.setForeground(Color.WHITE);
        signUpButton.addActionListener(this);
        add(signUpButton);

        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(150, 200, 100, 40);
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
        if (e.getSource() == signUpButton) {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Validate input before proceeding
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insert the sign-up details into the database
            try {
                ConnectionDatabase db = new ConnectionDatabase();  // Use your ConnectionDatabase class
                String query = "INSERT INTO vendors (name, email, password) VALUES (?, ?, ?)";
                PreparedStatement pstmt = db.c.prepareStatement(query);
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setString(3, password);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Sign-up successful!");
                }

                // Close the connection (optional)
                pstmt.close();
                db.c.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: Unable to sign up.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == cancelButton) {
            setVisible(false); // Hide the signup page
            new HomePage(); // Redirect to home page
        }
    }

    public static void main(String[] args) {
        new VendorSignup();
    }
}

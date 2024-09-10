package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

public class AdminSignup extends JFrame implements ActionListener {

    // Declare the components
    private JLabel nameLabel, emailLabel, passwordLabel, categoryLabel, titleLabel;
    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JComboBox<String> categoryComboBox;
    private JButton signUpButton, cancelButton;

    AdminSignup() {
        // Set the frame properties
        setTitle("Library Management System");
        setSize(500, 450);
        setLocation(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Set background color
        Color backgroundColor = new Color(240, 248, 255); // AliceBlue color
        getContentPane().setBackground(backgroundColor);

        // Title label
        titleLabel = new JLabel("Admin Signup", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(50, 20, 400, 40);
        add(titleLabel);

        // Name label and text field
        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 80, 100, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(160, 80, 250, 30);
        add(nameField);

        // Email label and text field
        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 130, 100, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(160, 130, 250, 30);
        add(emailField);

        // Password label and password field
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 180, 100, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 180, 250, 30);
        add(passwordField);

        // Category label and drop-down
        categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(50, 230, 100, 30);
        add(categoryLabel);

        String[] categories = {"Vendor", "Customer", "Organizer"}; // Dropdown options
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setBounds(160, 230, 250, 30);
        add(categoryComboBox);

        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(50, 280, 150, 40);
        cancelButton.setBackground(new Color(255, 69, 0)); // Red color
        cancelButton.setForeground(Color.WHITE); // Button text color
        cancelButton.setFocusPainted(false); // Remove focus border
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16)); // Font style
        cancelButton.addActionListener(this);
        add(cancelButton);
        
        // Sign Up button
        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(250, 280, 150, 40);
        signUpButton.setBackground(new Color(25, 25, 112)); // DarkBlue color
        signUpButton.setForeground(Color.WHITE); // Button text color
        signUpButton.setFocusPainted(false); // Remove focus border
        signUpButton.setFont(new Font("Arial", Font.BOLD, 16)); // Font style
        signUpButton.addActionListener(this);
        add(signUpButton);

        // Make the frame visible
        setVisible(true);
    }

    // Handle button click events
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String category = (String) categoryComboBox.getSelectedItem();

            // Validate input before proceeding
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || category.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insert the sign-up details into the database
            try {
                ConnectionDatabase db = new ConnectionDatabase();  // Use your ConnectionDatabase class
                String query = "INSERT INTO users (name, email, password, category) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = db.c.prepareStatement(query);
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setString(3, password);
                pstmt.setString(4, category);

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
            // Close the signup form
            setVisible(false);
            new HomePage(); // Redirect to home page or previous screen
        }
    }

    public static void main(String[] args) {
        new AdminSignup();
    }
}

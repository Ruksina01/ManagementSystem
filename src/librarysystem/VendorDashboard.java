package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VendorDashboard extends JFrame implements ActionListener {
    private JLabel welcomeLabel;
    private JButton yourItemsButton, addItemsButton, logoutButton;
    private String vendorName;
    
    VendorDashboard(String vendorName) {
        this.vendorName = vendorName; // Store the logged-in vendor's name

        // Set the frame properties
        setTitle("Vendor Dashboard");
        setSize(600, 400);
        setLocation(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background color
        Color backgroundColor = new Color(100, 149, 237); // Cornflower Blue
        getContentPane().setBackground(backgroundColor);

        // Welcome label
        welcomeLabel = new JLabel("Welcome, " + vendorName);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setBounds(200, 30, 300, 30);
        add(welcomeLabel);

        // Your Items button
        yourItemsButton = new JButton("Your Items");
        yourItemsButton.setBounds(200, 100, 150, 40);
        yourItemsButton.setBackground(new Color(25, 25, 112)); // Dark blue
        yourItemsButton.setForeground(Color.WHITE);
        yourItemsButton.addActionListener(this);
        add(yourItemsButton);

        // Add Items button
        addItemsButton = new JButton("Add Items");
        addItemsButton.setBounds(200, 160, 150, 40);
        addItemsButton.setBackground(new Color(25, 25, 112)); // Dark blue
        addItemsButton.setForeground(Color.WHITE);
        addItemsButton.addActionListener(this);
        add(addItemsButton);

        // Logout button
        logoutButton = new JButton("Logout");
        logoutButton.setBounds(200, 220, 150, 40);
        logoutButton.setBackground(new Color(255, 69, 0)); // Red
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(this);
        add(logoutButton);

        // Make the frame visible
        setVisible(true);
    }

    // Handle button click events
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == yourItemsButton) {
            setVisible(false);
            new YourItems();
        } else if (e.getSource() == addItemsButton) {
            setVisible(false);
            new AddItem();
        } else if (e.getSource() == logoutButton) {
            // Log out and return to the login page
            JOptionPane.showMessageDialog(this, "Logging out...");
            setVisible(false); // Hide dashboard
            new VendorLogin(); // Redirect to vendor login form
        }
    }

    public static void main(String[] args) {
        // For testing purposes, launch with vendorName "Vendor"
        new VendorDashboard("Vendor");
    }
}

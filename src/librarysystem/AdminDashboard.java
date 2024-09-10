package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame implements ActionListener {
    private JLabel welcomeLabel;
    private JButton homeButton, logoutButton, maintainVendorButton, maintainUserButton;
    private String adminName;

    AdminDashboard(String adminName) {
        this.adminName = adminName; // Store the logged-in admin's name

        // Set the frame properties
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setLocation(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Set background color
        Color backgroundColor = new Color(100, 149, 237); // Cornflower Blue
        getContentPane().setBackground(backgroundColor);

        // Welcome label
        welcomeLabel = new JLabel("Welcome, " + adminName);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setBounds(200, 30, 300, 30);
        add(welcomeLabel);

        // Home button
        homeButton = new JButton("Home");
        homeButton.setBounds(200, 100, 150, 40);
        homeButton.setBackground(new Color(25, 25, 112)); // Dark blue
        homeButton.setForeground(Color.WHITE);
        homeButton.addActionListener(this);
        add(homeButton);

        // Maintain Vendor button
        maintainVendorButton = new JButton("Maintain Vendor");
        maintainVendorButton.setBounds(200, 160, 150, 40);
        maintainVendorButton.setBackground(new Color(25, 25, 112)); // Dark blue
        maintainVendorButton.setForeground(Color.WHITE);
        maintainVendorButton.addActionListener(this);
        add(maintainVendorButton);

        // Maintain User button
        maintainUserButton = new JButton("Maintain User");
        maintainUserButton.setBounds(200, 220, 150, 40);
        maintainUserButton.setBackground(new Color(25, 25, 112)); // Dark blue
        maintainUserButton.setForeground(Color.WHITE);
        maintainUserButton.addActionListener(this);
        add(maintainUserButton);

        // Logout button
        logoutButton = new JButton("Logout");
        logoutButton.setBounds(200, 280, 150, 40);
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
        if (e.getSource() == homeButton) {
            JOptionPane.showMessageDialog(this, "You clicked on Home!");
            setVisible(false); // Hide dashboard
            new HomePage();   // Redirect to login form
        } else if (e.getSource() == maintainVendorButton) {
            JOptionPane.showMessageDialog(this, "Maintain Vendor functionality goes here!");
            setVisible(false);
            new MaintainVendorDetail();
        } else if (e.getSource() == maintainUserButton) {
            JOptionPane.showMessageDialog(this, "Maintain User functionality goes here!");
            setVisible(false);
            new MaintainUserDetail();
        } else if (e.getSource() == logoutButton) {
            // Log out and go back to the login page
            JOptionPane.showMessageDialog(this, "Logging out...");
            setVisible(false); // Hide dashboard
            new AdminLogin();   // Redirect to login form
        }
    }

    public static void main(String[] args) {
        new AdminDashboard("Admin");
    }
}

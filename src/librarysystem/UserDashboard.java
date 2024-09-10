package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserDashboard extends JFrame implements ActionListener {
    private JLabel welcomeLabel;
    private JButton homeButton, cartButton, userStatusButton, vendorButton, guestListButton, logoutButton;
    private String userName;

    UserDashboard(String userName) {
        this.userName = userName; // Store the logged-in user's name

        // Set the frame properties
        setTitle("User Dashboard");
        setSize(600, 400);
        setLocation(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background color
        Color backgroundColor = new Color(100,149,237);
        getContentPane().setBackground(backgroundColor);

        // Welcome label
        welcomeLabel = new JLabel("Welcome, " + userName);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setBounds(200, 30, 300, 30);
        add(welcomeLabel);

        // Home button
        homeButton = new JButton("Home");
        homeButton.setBounds(50, 100, 150, 40);
        homeButton.setBackground(new Color(25, 25, 112)); // Dark blue
        homeButton.setForeground(Color.WHITE);
        homeButton.addActionListener(this);
        add(homeButton);

        // Cart button
        cartButton = new JButton("Cart");
        cartButton.setBounds(50, 160, 150, 40);
        cartButton.setBackground(new Color(25, 25, 112)); // Dark blue
        cartButton.setForeground(Color.WHITE);
        cartButton.addActionListener(this);
        add(cartButton);

        // User Status button
        userStatusButton = new JButton("User Status");
        userStatusButton.setBounds(50, 220, 150, 40);
        userStatusButton.setBackground(new Color(25, 25, 112)); // Dark blue
        userStatusButton.setForeground(Color.WHITE);
        userStatusButton.addActionListener(this);
        add(userStatusButton);

        // Vendor button
        vendorButton = new JButton("Vendor");
        vendorButton.setBounds(250, 100, 150, 40);
        vendorButton.setBackground(new Color(25, 25, 112)); // Dark blue
        vendorButton.setForeground(Color.WHITE);
        vendorButton.addActionListener(this);
        add(vendorButton);

        // Guest List button
        guestListButton = new JButton("Guest List");
        guestListButton.setBounds(250, 220, 150, 40);
        guestListButton.setBackground(new Color(25, 25, 112)); // Dark blue
        guestListButton.setForeground(Color.WHITE);
        guestListButton.addActionListener(this);
        add(guestListButton);

        // Logout button
        logoutButton = new JButton("Logout");
        logoutButton.setBounds(250, 160, 150, 40);
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
            // Redirect to home page
            setVisible(false);
            new HomePage();
        } else if (e.getSource() == cartButton) {
            // Display cart functionality
            JOptionPane.showMessageDialog(this, "Cart functionality goes here!");
        } else if (e.getSource() == userStatusButton) {
            // Display user status functionality
            JOptionPane.showMessageDialog(this, "User status functionality goes here!");
        } else if (e.getSource() == vendorButton) {
            // Display vendor information
            JOptionPane.showMessageDialog(this, "Vendor functionality goes here!");
        } else if (e.getSource() == guestListButton) {
            // Display guest list
            JOptionPane.showMessageDialog(this, "Guest list functionality goes here!");
        } else if (e.getSource() == logoutButton) {
            // Log out and return to the login page
            JOptionPane.showMessageDialog(this, "Logging out...");
            setVisible(false); // Hide dashboard
            new UserLogin();   // Redirect to login form
        }
    }

    public static void main(String[] args) {
        // For testing purposes, launch with userName "User"
        new UserDashboard("User");
    }
}

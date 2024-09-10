package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePage extends JFrame implements ActionListener {

    private JButton adminButton, userButton, vendorButton;
    private final Color buttonColor = new Color(25, 25, 112); // Dark Blue
    private final Color buttonHoverColor = new Color(0, 0, 139); // Darker Blue

    HomePage() {
        setTitle("Library Management System - Home Page");
        setLocation(300, 100);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setting background image
        ImageIcon backgroundImage = new ImageIcon(ClassLoader.getSystemResource("icon/background.png"));
        Image scaledImage = backgroundImage.getImage().getScaledInstance(900, 600, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledIcon);
        backgroundLabel.setBounds(0, 0, 900, 600);

        // Creating buttons
        adminButton = new JButton("Admin");
        userButton = new JButton("User");
        vendorButton = new JButton("Vendor");

        // Set button colors
        adminButton.setBackground(buttonColor);
        adminButton.setForeground(Color.WHITE); // Set text color to white
        userButton.setBackground(buttonColor);
        userButton.setForeground(Color.WHITE);
        vendorButton.setBackground(buttonColor);
        vendorButton.setForeground(Color.WHITE);

        // Adding mouse listeners for interactive effects
        addButtonHoverEffect(adminButton);
        addButtonHoverEffect(userButton);
        addButtonHoverEffect(vendorButton);

        // Setting button properties
        adminButton.setBounds(100, 500, 150, 50);
        userButton.setBounds(350, 500, 150, 50);
        vendorButton.setBounds(600, 500, 150, 50);

        // Adding action listeners to buttons
        adminButton.addActionListener(this);
        userButton.addActionListener(this);
        vendorButton.addActionListener(this);

        // Adding components to the JFrame
        setLayout(null);  // Using absolute layout
        add(adminButton);
        add(userButton);
        add(vendorButton);
        add(backgroundLabel);

        setVisible(true);
    }

    private void addButtonHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(buttonHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(buttonColor);
            }
        });
    }

    // Action listener for button clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == adminButton) {
            // Show choice dialog
            Object[] options = {"Sign Up", "Log In"};
            int choice = JOptionPane.showOptionDialog(this,
                "Would you like to Sign Up or Log In?",
                "Admin Options",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

            if (choice == JOptionPane.YES_OPTION) {
                // Open Signup Form
                new AdminSignup();
            } else if (choice == JOptionPane.NO_OPTION) {
                // Open Login Form
                new AdminLogin();
            }
        } else if (e.getSource() == userButton) {
            // Show choice dialog for user
            Object[] options = {"Sign Up", "Log In"};
            int choice = JOptionPane.showOptionDialog(this,
                "Would you like to Sign Up or Log In?",
                "User Options",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

            if (choice == JOptionPane.YES_OPTION) {
                // Open Signup Form
                new UserSignup();
            } else if (choice == JOptionPane.NO_OPTION) {
                // Open Login Form
                new UserLogin();
            }
        } else if (e.getSource() == vendorButton) {
            // Open Vendor Page
            Object[] options = {"Sign Up", "Log In"};
            int choice = JOptionPane.showOptionDialog(this,
                "Would you like to Sign Up or Log In?",
                "User Options",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

            if (choice == JOptionPane.YES_OPTION) {
                // Open Signup Form
                new VendorSignup();
            } else if (choice == JOptionPane.NO_OPTION) {
                // Open Login Form
                new VendorLogin();
            }
        }
    }

    public static void main(String[] args) {
        new HomePage();
    }
}

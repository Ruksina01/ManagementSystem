package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

public class AddItem extends JFrame implements ActionListener {
    private JLabel nameLabel, priceLabel, imageLabel;
    private JTextField nameField, priceField, imageField;
    private JButton addButton, cancelButton;

    AddItem() {
        // Set the frame properties
        setTitle("Add Item");
        setSize(500, 300);
        setLocation(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background color
        Color backgroundColor = new Color(255, 255, 255); // White
        getContentPane().setBackground(backgroundColor);

        // Product Name label and text field
        nameLabel = new JLabel("Product Name:");
        nameLabel.setBounds(50, 30, 150, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(200, 30, 250, 30);
        add(nameField);

        // Price label and text field
        priceLabel = new JLabel("Price:");
        priceLabel.setBounds(50, 80, 150, 30);
        add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(200, 80, 250, 30);
        add(priceField);

        // Image URL label and text field
        imageLabel = new JLabel("Image File Name:");
        imageLabel.setBounds(50, 130, 150, 30);
        add(imageLabel);

        imageField = new JTextField();
        imageField.setBounds(200, 130, 250, 30);
        add(imageField);

        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(200, 200, 100, 40);
        cancelButton.setBackground(new Color(255, 69, 0)); // Red
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(this);
        add(cancelButton);

        // Add button
        addButton = new JButton("Add");
        addButton.setBounds(320, 200, 100, 40);
        addButton.setBackground(new Color(25, 25, 112)); // Dark blue
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(this);
        add(addButton);

        // Make the frame visible
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String productName = nameField.getText();
            String priceStr = priceField.getText();
            String imageFileName = imageField.getText();

            // Validate input before proceeding
            if (productName.isEmpty() || priceStr.isEmpty() || imageFileName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price format", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insert the item into the database
            try {
                ConnectionDatabase db = new ConnectionDatabase();
                String query = "INSERT INTO vendor_products (product_name, price, image_url) VALUES (?, ?, ?)";
                PreparedStatement pstmt = db.c.prepareStatement(query);
                pstmt.setString(1, productName);
                pstmt.setDouble(2, price);
                pstmt.setString(3, "icon/" + imageFileName); // Store the relative path

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Item added successfully!");
                    // Clear the fields after successful addition
                    nameField.setText("");
                    priceField.setText("");
                    imageField.setText("");
                }

                pstmt.close();
                db.c.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: Unable to add item.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == cancelButton) {
            setVisible(false); // Hide the frame
            new VendorDashboard("Vendor"); // Redirect to vendor dashboard
        }
    }

    public static void main(String[] args) {
        new AddItem();
    }
}

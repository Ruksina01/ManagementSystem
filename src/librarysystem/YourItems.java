//
//package librarysystem;
//
//import javax.swing.*;
//import java.awt.*;
//import java.sql.*;
//
//public class YourItems extends JFrame {
//    private JPanel itemsPanel;
//
//    YourItems() {
//        // Set the frame properties
//        setTitle("Your Items");
//        setSize(800, 600);
//        setLocation(300, 150);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // Initialize panel to hold items
//        itemsPanel = new JPanel();
//        itemsPanel.setLayout(new GridLayout(0, 1)); // GridLayout for displaying items vertically
//
//        // Fetch items from the database
//        fetchItems();
//
//        // Add panel to frame
//        JScrollPane scrollPane = new JScrollPane(itemsPanel);
//        add(scrollPane, BorderLayout.CENTER);
//
//        // Make the frame visible
//        setVisible(true);
//    }
//
//    private void fetchItems() {
//        try {
//            ConnectionDatabase db = new ConnectionDatabase();
//            String query = "SELECT * FROM vendor_products";
//            PreparedStatement pstmt = db.c.prepareStatement(query);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                String productName = rs.getString("product_name");
//                double price = rs.getDouble("price");
//                String imageFilePath = rs.getString("image_url");
//
//                // Create a panel for each product
//                JPanel itemPanel = new JPanel();
//                itemPanel.setLayout(new BorderLayout());
//                itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around each item
//
//                // Create a panel for the image and details
//                JPanel imageAndDetailsPanel = new JPanel();
//                imageAndDetailsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // FlowLayout to align components horizontally
//                itemPanel.add(imageAndDetailsPanel, BorderLayout.CENTER);
//
//                // Add product image
//                JLabel imageLabel = new JLabel();
//                if (imageFilePath != null && !imageFilePath.isEmpty()) {
//                    try {
//                        // Adjust path to use a leading "/"
//                        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/" + imageFilePath));
//                        if (imageIcon.getIconWidth() == -1) {
//                            throw new Exception("Image not found");
//                        }
//                        // Resize the image to a smaller size (e.g., 100x100)
//                        Image img = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
//                        imageLabel.setIcon(new ImageIcon(img));
//                    } catch (Exception e) {
//                        imageLabel.setText("Image not found");
//                        e.printStackTrace();
//                    }
//                } else {
//                    imageLabel.setText("No Image");
//                }
//                imageAndDetailsPanel.add(imageLabel);
//
//                // Add product details
//                JPanel detailsPanel = new JPanel();
//                detailsPanel.setLayout(new GridLayout(2, 1));
//                detailsPanel.add(new JLabel("Product Name: " + productName));
//                detailsPanel.add(new JLabel("Price: $" + price));
//                imageAndDetailsPanel.add(detailsPanel);
//
//                // Add item panel to itemsPanel
//                itemsPanel.add(itemPanel);
//            }
//
//            pstmt.close();
//            db.c.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        new YourItems();
//    }
//}
package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class YourItems extends JFrame {
    private JPanel itemsPanel;
    private JComboBox<String> productDropdown;
    private JButton updateButton, deleteButton, addItemButton;
    private String selectedProductId;

    YourItems() {
        // Set the frame properties
        setTitle("Your Items");
        setSize(800, 600);
        setLocation(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize panel to hold items
        itemsPanel = new JPanel();
        itemsPanel.setLayout(new GridLayout(0, 1)); // GridLayout for displaying items vertically

        // Initialize dropdown and buttons
        productDropdown = new JComboBox<>();
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        addItemButton = new JButton("Add Item");

        // Add action listeners
        productDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) productDropdown.getSelectedItem();
                if (selectedItem != null && !selectedItem.isEmpty()) {
                    selectedProductId = selectedItem.split(" - ")[0]; // Extract product ID
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedProductId != null) {
                    new UpdateItem(selectedProductId); // Open update page
                } else {
                    JOptionPane.showMessageDialog(YourItems.this, "Please select a product.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedProductId != null) {
                    int confirm = JOptionPane.showConfirmDialog(YourItems.this, 
                        "Are you sure you want to delete this product?", 
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        deleteProduct(selectedProductId); // Delete product
                    }
                } else {
                    JOptionPane.showMessageDialog(YourItems.this, "Please select a product.");
                }
            }
        });
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddItem(); // Call method to add new item
            }
        });

        // Fetch items from the database
        fetchItems();

        // Add components to the frame
        JPanel controlPanel = new JPanel();
        controlPanel.add(productDropdown);
        controlPanel.add(updateButton);
        controlPanel.add(deleteButton);
        controlPanel.add(addItemButton);

        add(controlPanel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Make the frame visible
        setVisible(true);
    }

    private void fetchItems() {
        try {
            ConnectionDatabase db = new ConnectionDatabase();
            String query = "SELECT * FROM vendor_products";
            PreparedStatement pstmt = db.c.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            // Clear existing items in dropdown and panel
            productDropdown.removeAllItems();
            itemsPanel.removeAll();

            while (rs.next()) {
                String productId = rs.getString("id");
                String productName = rs.getString("product_name");
                double price = rs.getDouble("price");
                String imageFilePath = rs.getString("image_url");

                // Add product ID to dropdown
                productDropdown.addItem(productId + " - " + productName);
                
                // Create a panel for each product
                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new BorderLayout());
                itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around each item

                // Add product image
                JLabel imageLabel = new JLabel();
                if (imageFilePath != null && !imageFilePath.isEmpty()) {
                    try {
                        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/" + imageFilePath));
                        if (imageIcon.getIconWidth() == -1) {
                            throw new Exception("Image not found");
                        }
                        Image img = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        imageLabel.setIcon(new ImageIcon(img));
                    } catch (Exception e) {
                        imageLabel.setText("Image not found");
                        e.printStackTrace();
                    }
                } else {
                    imageLabel.setText("No Image");
                }
                itemPanel.add(imageLabel, BorderLayout.WEST);

                // Add product details
                JPanel detailsPanel = new JPanel();
                detailsPanel.setLayout(new GridLayout(4, 1));
                detailsPanel.add(new JLabel("Product ID: " + productId));
                detailsPanel.add(new JLabel("Product Name: " + productName));
                detailsPanel.add(new JLabel("Price: $" + price));
                itemPanel.add(detailsPanel, BorderLayout.CENTER);

                // Add item panel to itemsPanel
                itemsPanel.add(itemPanel);
            }

            pstmt.close();
            db.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteProduct(String productId) {
        try {
            ConnectionDatabase db = new ConnectionDatabase();
            String query = "DELETE FROM vendor_products WHERE id = ?";
            PreparedStatement pstmt = db.c.prepareStatement(query);
            pstmt.setString(1, productId);
            pstmt.executeUpdate();

            // Refresh the items list
            itemsPanel.removeAll();
            fetchItems();

            pstmt.close();
            db.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new YourItems();
    }
}

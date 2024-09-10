
package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateItem extends JFrame {
    private JTextField productNameField, priceField;
    private JButton saveButton;
    private String productId;

    UpdateItem(String productId) {
        this.productId = productId;

        // Set the frame properties
        setTitle("Update Product");
        setSize(400, 250);
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null); // Use null layout for manual positioning

        // Initialize components
        JLabel productNameLabel = new JLabel("Product Name:");
        productNameLabel.setBounds(30, 30, 120, 30);
        add(productNameLabel);

        productNameField = new JTextField();
        productNameField.setBounds(160, 30, 200, 30);
        add(productNameField);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(30, 80, 120, 30);
        add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(160, 80, 200, 30);
        add(priceField);

        saveButton = new JButton("Save");
        saveButton.setBounds(200, 130, 100, 40);
        saveButton.setBackground(new Color(25, 25, 112)); // Dark blue
        saveButton.setForeground(Color.WHITE);
        add(saveButton);

        // Load existing product details
        loadProductDetails();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateItem();
            }
        });

        // Make the frame visible
        setVisible(true);
    }

    private void loadProductDetails() {
        try {
            ConnectionDatabase db = new ConnectionDatabase();
            String query = "SELECT * FROM vendor_products WHERE id = ?";
            PreparedStatement pstmt = db.c.prepareStatement(query);
            pstmt.setString(1, productId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                productNameField.setText(rs.getString("product_name"));
                priceField.setText(String.valueOf(rs.getDouble("price")));
            }

            pstmt.close();
            db.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateItem() {
        try {
            ConnectionDatabase db = new ConnectionDatabase();
            String query = "UPDATE vendor_products SET product_name = ?, price = ? WHERE id = ?";
            PreparedStatement pstmt = db.c.prepareStatement(query);
            pstmt.setString(1, productNameField.getText());
            pstmt.setDouble(2, Double.parseDouble(priceField.getText()));
            pstmt.setString(3, productId);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Product updated successfully!");

            pstmt.close();
            db.c.close();
            dispose(); // Close the update page
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Example usage: pass an example product ID
        new UpdateItem("1");
    }
}

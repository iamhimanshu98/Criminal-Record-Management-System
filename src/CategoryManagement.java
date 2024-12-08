import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoryManagement extends JFrame {
    private JTextField categoryNameField;
    private JTextArea descriptionArea;

    public CategoryManagement() {
        setTitle("Crime Categories Management");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout setup
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Verdana", Font.PLAIN, 16);

        // Components
        JLabel categoryLabel = new JLabel("Category Name:");
        categoryLabel.setFont(labelFont);
        categoryNameField = new JTextField(20);
        categoryNameField.setFont(labelFont);
        categoryNameField.setMargin(new Insets(5, 5, 5, 5));

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(labelFont);
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setFont(labelFont);
        descriptionArea.setMargin(new Insets(5, 5, 5, 5));
        JScrollPane scrollPane = new JScrollPane(descriptionArea);

        JButton submitButton = new JButton("Add Category");
        submitButton.setFont(labelFont);

        // Add components to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(categoryLabel, gbc);
        gbc.gridx = 1;
        panel.add(categoryNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(descriptionLabel, gbc);
        gbc.gridx = 1;
        panel.add(scrollPane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(submitButton, gbc);

        add(panel);

        // Button Action
        submitButton.addActionListener((ActionEvent e) -> addCategory());
    }

    private void addCategory() {
        String name = categoryNameField.getText();
        String description = descriptionArea.getText();

        if (name.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database Logic
        try (Connection conn = DbConnection.getConnection()) {
            String sql = "INSERT INTO crime_categories (name, description) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, description);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Category added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add category.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        categoryNameField.setText("");
        descriptionArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CategoryManagement().setVisible(true));
    }
}

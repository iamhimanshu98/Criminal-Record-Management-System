import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class SuspectManagement extends JFrame {
    private JTextField nameField, ageField, cityField;
    private JComboBox<String> genderComboBox, crimeTypeComboBox;

    public SuspectManagement() {
        setTitle("Suspect Management");
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
        JLabel nameLabel = new JLabel("Suspect Name:");
        nameLabel.setFont(labelFont);
        nameField = new JTextField(20);
        nameField.setFont(labelFont);
        nameField.setMargin(new Insets(5, 5, 5, 5));

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(labelFont);
        ageField = new JTextField(20);
        ageField.setFont(labelFont);
        ageField.setMargin(new Insets(5, 5, 5, 5));

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(labelFont);
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        genderComboBox.setFont(labelFont);

        JLabel cityLabel = new JLabel("City:");
        cityLabel.setFont(labelFont);
        cityField = new JTextField(20);
        cityField.setFont(labelFont);
        cityField.setMargin(new Insets(5, 5, 5, 5));

        JLabel crimeLabel = new JLabel("Crime Type:");
        crimeLabel.setFont(labelFont);
        crimeTypeComboBox = new JComboBox<>(new String[]{"Theft", "Assault", "Fraud", "Robbery", "Harassment"});
        crimeTypeComboBox.setFont(labelFont);

        JButton submitButton = new JButton("Add Suspect");
        submitButton.setFont(labelFont);

        // Add components to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(ageLabel, gbc);
        gbc.gridx = 1;
        panel.add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(genderLabel, gbc);
        gbc.gridx = 1;
        panel.add(genderComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(cityLabel, gbc);
        gbc.gridx = 1;
        panel.add(cityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(crimeLabel, gbc);
        gbc.gridx = 1;
        panel.add(crimeTypeComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(submitButton, gbc);

        add(panel);

        // Button Action
        submitButton.addActionListener((ActionEvent e) -> addSuspect());
    }

    private void addSuspect() {
        String name = nameField.getText();
        String age = ageField.getText();
        String gender = (String) genderComboBox.getSelectedItem();
        String city = cityField.getText();
        String crimeType = (String) crimeTypeComboBox.getSelectedItem();

        if (name.isEmpty() || age.isEmpty() || gender == null || city.isEmpty() || crimeType.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database Logic
        try (Connection conn = DbConnection.getConnection()) {
            String sql = "INSERT INTO suspects (name, age, gender, city, crime_type) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, age);
            stmt.setString(3, gender);
            stmt.setString(4, city);
            stmt.setString(5, crimeType);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Suspect added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add suspect.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        cityField.setText("");
        genderComboBox.setSelectedIndex(0);
        crimeTypeComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SuspectManagement().setVisible(true));
    }
}

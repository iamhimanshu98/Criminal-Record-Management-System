import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VictimManagement extends JFrame {
    private JTextField nameField, ageField, contactField, locationField;
    private JComboBox<String> genderComboBox, crimeTypeComboBox;

    public VictimManagement() {
        setTitle("Victim Management");
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
        JLabel nameLabel = new JLabel("Victim Name:");
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
        genderComboBox = new JComboBox<>(new String[] {"Male", "Female", "Other"});
        genderComboBox.setFont(labelFont);

        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(labelFont);
        locationField = new JTextField(20);
        locationField.setFont(labelFont);
        locationField.setMargin(new Insets(5, 5, 5, 5));

        JLabel contactLabel = new JLabel("Contact Number:");
        contactLabel.setFont(labelFont);
        contactField = new JTextField(20);
        contactField.setFont(labelFont);
        contactField.setMargin(new Insets(5, 5, 5, 5));

        // Crime Type Dropdown
        JLabel crimeTypeLabel = new JLabel("Crime Type:");
        crimeTypeLabel.setFont(labelFont);
        crimeTypeComboBox = new JComboBox<>(new String[] {"", "Theft", "Assault", "Fraud", "Robbery", "Harassment"});
        crimeTypeComboBox.setFont(labelFont);

        JButton submitButton = new JButton("Add Victim");
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
        panel.add(locationLabel, gbc);
        gbc.gridx = 1;
        panel.add(locationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(contactLabel, gbc);
        gbc.gridx = 1;
        panel.add(contactField, gbc);

        // Crime type
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(crimeTypeLabel, gbc);
        gbc.gridx = 1;
        panel.add(crimeTypeComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(submitButton, gbc);

        add(panel);

        // Button Action
        submitButton.addActionListener((ActionEvent e) -> addVictim());
    }

    private void addVictim() {
        String name = nameField.getText();
        String age = ageField.getText();
        String gender = (String) genderComboBox.getSelectedItem();
        String location = locationField.getText();
        String contact = contactField.getText();
        String crimeType = (String) crimeTypeComboBox.getSelectedItem();

        if (name.isEmpty() || age.isEmpty() || contact.isEmpty() || gender == null || location.isEmpty() || crimeType.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database Logic
        try (Connection conn = DbConnection.getConnection()) {
            String sql = "INSERT INTO victims (name, age, gender, city, contact, crime_type) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, age);
            stmt.setString(3, gender);
            stmt.setString(4, location);
            stmt.setString(5, contact);
            stmt.setString(6, crimeType);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Victim added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add victim.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        contactField.setText("");
        genderComboBox.setSelectedIndex(0);
        locationField.setText("");
        crimeTypeComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VictimManagement().setVisible(true));
    }
}

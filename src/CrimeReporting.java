import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrimeReporting extends JFrame {
    private JTextField victimIdField, crimeLocationField, descriptionField, reportedByField, crimeDateField;
    private JComboBox<String> crimeTypeComboBox;
    private JTextArea descriptionArea;

    public CrimeReporting() {
        setTitle("Crime Reporting");
        setSize(1000, 800); // Adjusted page size
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout setup
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Verdana", Font.PLAIN, 16); // Increased font size

        // Components
        JLabel crimeTypeLabel = new JLabel("Crime Type:");
        crimeTypeLabel.setFont(labelFont);
        crimeTypeComboBox = new JComboBox<>(new String[]{"Theft", "Assault", "Fraud", "Robbery", "Harassment"});
        crimeTypeComboBox.setFont(labelFont);

        JLabel victimIdLabel = new JLabel("Victim ID:");
        victimIdLabel.setFont(labelFont);
        victimIdField = new JTextField(20);
        victimIdField.setFont(labelFont);
        victimIdField.setMargin(new Insets(5, 5, 5, 5)); // Input padding

        JLabel crimeLocationLabel = new JLabel("Location:");
        crimeLocationLabel.setFont(labelFont);
        crimeLocationField = new JTextField(20);
        crimeLocationField.setFont(labelFont);
        crimeLocationField.setMargin(new Insets(5, 5, 5, 5)); // Input padding

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(labelFont);
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setFont(labelFont);
        descriptionArea.setMargin(new Insets(5, 5, 5, 5)); // Input padding
        JScrollPane scrollPane = new JScrollPane(descriptionArea);

        JLabel crimeDateLabel = new JLabel("Crime Date (YYYY-MM-DD):");
        crimeDateLabel.setFont(labelFont);
        crimeDateField = new JTextField(20);
        crimeDateField.setFont(labelFont);
        crimeDateField.setMargin(new Insets(5, 5, 5, 5)); // Input padding

        JLabel reportingDateLabel = new JLabel("Reporting Date:");
        reportingDateLabel.setFont(labelFont);
        JTextField reportingDateField = new JTextField(20);
        reportingDateField.setFont(labelFont);
        reportingDateField.setText(getCurrentDate());
        reportingDateField.setEditable(false); // This field is not editable

        JLabel reportedByLabel = new JLabel("Reported By:");
        reportedByLabel.setFont(labelFont);
        reportedByField = new JTextField(20);
        reportedByField.setFont(labelFont);
        reportedByField.setMargin(new Insets(5, 5, 5, 5)); // Input padding

        JButton submitButton = new JButton("Report Crime");
        submitButton.setFont(labelFont);

        // Add components to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(crimeTypeLabel, gbc);
        gbc.gridx = 1;
        panel.add(crimeTypeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(victimIdLabel, gbc);
        gbc.gridx = 1;
        panel.add(victimIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(crimeLocationLabel, gbc);
        gbc.gridx = 1;
        panel.add(crimeLocationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(descriptionLabel, gbc);
        gbc.gridx = 1;
        panel.add(scrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(crimeDateLabel, gbc);
        gbc.gridx = 1;
        panel.add(crimeDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(reportedByLabel, gbc);
        gbc.gridx = 1;
        panel.add(reportedByField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(submitButton, gbc);

        add(panel);

        // Button Action
        submitButton.addActionListener((ActionEvent e) -> reportCrime());
    }

    private void reportCrime() {
        String crimeType = (String) crimeTypeComboBox.getSelectedItem();
        String victimId = victimIdField.getText();
        String crimeLocation = crimeLocationField.getText();
        String description = descriptionArea.getText();
        String crimeDate = crimeDateField.getText();
//        String reportingDate = getCurrentDate();  // Current date (auto-filled)
        String reportedBy = reportedByField.getText();

        if (victimId.isEmpty() || crimeLocation.isEmpty() || description.isEmpty() || crimeDate.isEmpty() || reportedBy.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database Logic
        try (Connection conn = DbConnection.getConnection()) {
            String sql = "INSERT INTO crimereports (crime_type, crime_date, crime_location, victim_id, description, reported_by, reporting_date, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, CURDATE(), 'Reported')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, crimeType);
            stmt.setString(2, crimeDate);  // User input crime date
            stmt.setString(3, crimeLocation);
            stmt.setInt(4, Integer.parseInt(victimId));  // Assuming victim_id is an integer
            stmt.setString(5, description);
            stmt.setString(6, reportedBy);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Crime reported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to report crime.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        victimIdField.setText("");
        crimeLocationField.setText("");
        descriptionArea.setText("");
        crimeTypeComboBox.setSelectedIndex(0);
        reportedByField.setText("");
        crimeDateField.setText("");
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CrimeReporting().setVisible(true));
    }
}

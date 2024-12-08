import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class SearchDialog extends JDialog {

    private JTextField nameField;  // Input field for victim/suspect name
    private JComboBox<String> crimeTypeComboBox;  // Dropdown for crime types
    private JButton searchButton;

    public SearchDialog(Frame parent) {
        super(parent, "Search Victims and Suspects", true);
        setSize(800, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Layout setup
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Verdana", Font.PLAIN, 16);

        // Components
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        nameField = new JTextField(20);
        nameField.setFont(labelFont);
        nameField.setMargin(new Insets(5, 5, 5, 5));

        JLabel crimeTypeLabel = new JLabel("Crime Type:");
        crimeTypeLabel.setFont(labelFont);
        crimeTypeComboBox = new JComboBox<>(new String[]{"", "Theft", "Assault", "Fraud", "Robbery", "Harassment"});
        crimeTypeComboBox.setFont(labelFont);

        searchButton = new JButton("Search");
        searchButton.setFont(labelFont);

        // Add components to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(crimeTypeLabel, gbc);
        gbc.gridx = 1;
        panel.add(crimeTypeComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(searchButton, gbc);

        add(panel);

        // Button Action
        searchButton.addActionListener((ActionEvent e) -> searchRecords());
    }

    private void searchRecords() {
        String name = nameField.getText();
        String crimeType = (String) crimeTypeComboBox.getSelectedItem();

        StringBuilder query = new StringBuilder("SELECT name, age, gender, crime_type, 'Victim' AS role FROM victims WHERE 1=1");

        // Append conditions to query based on user input
        if (!name.isEmpty()) {
            query.append(" AND name LIKE '%").append(name).append("%'");
        }
        if (!crimeType.isEmpty()) {
            query.append(" AND crime_type LIKE '%").append(crimeType).append("%'");
        }

        query.append(" UNION ");

        query.append("SELECT name, age, gender, crime_type, 'Suspect' AS role FROM suspects WHERE 1=1");

        if (!name.isEmpty()) {
            query.append(" AND name LIKE '%").append(name).append("%'");
        }
        if (!crimeType.isEmpty()) {
            query.append(" AND crime_type LIKE '%").append(crimeType).append("%'");
        }

        // Execute the query and update the table with results
        try (Connection conn = DbConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query.toString());

            // Check if the result set is empty
            if (!rs.isBeforeFirst()) {  // No results found
                JOptionPane.showMessageDialog(this, "No records found for this search.", "No Results", JOptionPane.INFORMATION_MESSAGE);
                return;  // Exit the method if no results are found
            }

            // Open new window for results
            JFrame resultsFrame = new JFrame("Search Results");
            resultsFrame.setSize(1000, 800);
            resultsFrame.setLocationRelativeTo(null);
            resultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            resultsFrame.setAlwaysOnTop(true); // Ensure the results window appears over the search dialog

            // Create a table for results
            String[] columnNames = {"Name", "Age", "Gender", "Crime Type", "Role"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            JTable resultsTable = new JTable(tableModel);
            resultsTable.setFont(new Font("Verdana", Font.PLAIN, 14));
            resultsTable.setRowHeight(30);  // Decent row height

            // Set margin for the table by adding a scroll pane with some padding
            JScrollPane scrollPane = new JScrollPane(resultsTable);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin

            // Add results to the table model
            while (rs.next()) {
                String resultName = rs.getString("name");
                String age = rs.getString("age");
                String gender = rs.getString("gender");
                String crime = rs.getString("crime_type");
                String role = rs.getString("role");

                tableModel.addRow(new Object[]{resultName, age, gender, crime, role});
            }

            // Add the table inside the scroll pane
            resultsFrame.add(scrollPane);

            // Make the results window visible
            resultsFrame.setVisible(true);

            // Close the search dialog
            dispose();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SearchDialog(null).setVisible(true));
    }
}

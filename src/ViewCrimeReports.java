import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.*;

public class ViewCrimeReports extends JFrame {

    public ViewCrimeReports() {
        setTitle("View Crime Reports");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout setup
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Create the table for displaying crime reports
        String[] columnNames = {"Crime ID", "Crime Type", "Crime Date", "Location", "Victim ID", "Description", "Reported By", "Reporting Date", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Verdana", Font.PLAIN, 14));
        table.setRowHeight(30);

        // Set custom column widths
        setColumnWidths(table);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add the panel to the frame
        add(panel);

        // Fetch crime reports and populate the table
        fetchCrimeReports(tableModel);

        setVisible(true);
    }

    private void setColumnWidths(JTable table) {
        TableColumn column;

        // Set smaller width for Crime ID and Victim ID columns
        column = table.getColumnModel().getColumn(0);  // Crime ID
        column.setPreferredWidth(80);

        column = table.getColumnModel().getColumn(4);  // Victim ID
        column.setPreferredWidth(80);

        // Set larger width for Description column
        column = table.getColumnModel().getColumn(5);  // Description
        column.setPreferredWidth(200);  // Increase the width for description column

        // Optionally, adjust other columns if needed
        column = table.getColumnModel().getColumn(1);  // Crime Type
        column.setPreferredWidth(120);

        column = table.getColumnModel().getColumn(2);  // Crime Date
        column.setPreferredWidth(120);

        column = table.getColumnModel().getColumn(3);  // Location
        column.setPreferredWidth(150);

        column = table.getColumnModel().getColumn(6);  // Reported By
        column.setPreferredWidth(120);

        column = table.getColumnModel().getColumn(7);  // Reporting Date
        column.setPreferredWidth(120);

        column = table.getColumnModel().getColumn(8);  // Status
        column.setPreferredWidth(100);
    }

    private void fetchCrimeReports(DefaultTableModel tableModel) {
        // Update the query according to the schema
        String query = "SELECT * FROM crimereports";  // Using provided schema to fetch all crime reports

        try (Connection conn = DbConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Loop through the result set and add rows to the table model
            while (rs.next()) {
                int crimeId = rs.getInt("crime_id");
                String crimeType = rs.getString("crime_type");
                Date crimeDate = rs.getDate("crime_date");
                String crimeLocation = rs.getString("crime_location");
                int victimId = rs.getInt("victim_id");
                String description = rs.getString("description");
                String reportedBy = rs.getString("reported_by");
                Date reportingDate = rs.getDate("reporting_date");
                String status = rs.getString("status");

                // Add the row to the table
                tableModel.addRow(new Object[]{crimeId, crimeType, crimeDate, crimeLocation, victimId, description, reportedBy, reportingDate, status});
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewCrimeReports::new);
    }
}

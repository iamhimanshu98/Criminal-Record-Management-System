import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViewRecords {

    // Assuming DbConnection is a class that manages your database connection
    private Connection conn;

    // Constructor to initialize the database connection
    public ViewRecords() {
        try {
            conn = DbConnection.getConnection(); // Use your DbConnection class here
            System.out.println("Connected to the database");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to view victims
    public void viewVictims() {
        try {
            String query = "SELECT * FROM victims";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            String[] columnNames = {"Victim ID", "Name", "Age", "Gender", "Location", "Contact", "Crime Type"};
            List<Object[]> data = new ArrayList<>();
            while (rs.next()) {
                data.add(new Object[]{
                        rs.getInt("victim_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("city"),
                        rs.getString("contact"),
                        rs.getString("crime_type")
                });
            }
            Object[][] dataArray = data.toArray(new Object[0][]);
            JTable table = new JTable(dataArray, columnNames);
            table.setRowHeight(26);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(1000, 600));
            JOptionPane.showMessageDialog(null, scrollPane, "View Victims", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error viewing victims: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Method to view suspects
    public void viewSuspects() {
        try {
            String query = "SELECT * FROM suspects";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            String[] columnNames = {"Suspect ID", "Name", "Age", "Gender", "City", "Crime Type"};
            List<Object[]> data = new ArrayList<>();
            while (rs.next()) {
                data.add(new Object[]{
                        rs.getInt("suspect_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("city"),
                        rs.getString("crime_type")
                });
            }
            Object[][] dataArray = data.toArray(new Object[0][]);
            JTable table = new JTable(dataArray, columnNames);
            table.setRowHeight(26);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(1000, 600));
            JOptionPane.showMessageDialog(null, scrollPane, "View Suspects", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error viewing suspects: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Main method to demonstrate viewing victims and suspects
    public static void main(String[] args) {
        ViewRecords records = new ViewRecords();
        records.viewVictims(); // Display victims records
        records.viewSuspects(); // Display suspects records
    }
}

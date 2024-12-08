import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainDashboard extends JFrame {

    public MainDashboard() {
        // Set up the frame
        setTitle("Criminal Record Management System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create main panel with background image
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    ImageIcon icon = new ImageIcon(getClass().getResource("/img/bg1.png"));
                    Image img = icon.getImage();
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception ex) {
                    System.err.println("Background image not found!");
                }
            }
        };

        mainPanel.setOpaque(false);

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false); // Transparent background
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0)); // Top and bottom margin
        JLabel welcomeLabel = new JLabel("Welcome to the Dashboard", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Verdana", Font.PLAIN, 26));
        welcomeLabel.setForeground(Color.WHITE); // Adjust for visibility on the background
        headerPanel.add(welcomeLabel);

        // Center panel for feature buttons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false); // Transparent background
        GridBagConstraints gbc = new GridBagConstraints();

        // Reordered buttons
        JButton victimManagementButton = createButton("Victim Management");
        JButton suspectManagementButton = createButton("Suspect Management");
        JButton crimeReportingButton = createButton("Crime Reporting");
        JButton viewVictimsButton = createButton("View Victims");
        JButton viewSuspectsButton = createButton("View Suspects");
        JButton viewCrimeReportsButton = createButton("View Crime Reports");
        JButton searchFiltersButton = createButton("Search & Filters");
//        JButton crimeCategoriesButton = createButton("Crime Categories");
        JButton logoutButton = createButton("Logout");

        gbc.insets = new Insets(10, 10, 10, 10); // Padding between buttons
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(victimManagementButton, gbc);

        gbc.gridy++;
        centerPanel.add(suspectManagementButton, gbc);

        gbc.gridy++;
        centerPanel.add(crimeReportingButton, gbc);

        gbc.gridy++;
        centerPanel.add(viewVictimsButton, gbc);

        gbc.gridy++;
        centerPanel.add(viewSuspectsButton, gbc);

        gbc.gridy++;
        centerPanel.add(viewCrimeReportsButton, gbc);

        gbc.gridy++;
        centerPanel.add(searchFiltersButton, gbc);

//        gbc.gridy++;
//        centerPanel.add(crimeCategoriesButton, gbc);

        gbc.gridy++;
        centerPanel.add(logoutButton, gbc);

        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Add main panel to frame
        add(mainPanel);
        setVisible(true);

        // Button actions
        crimeReportingButton.addActionListener((ActionEvent e) -> {
            new CrimeReporting().setVisible(true);
        });

        suspectManagementButton.addActionListener((ActionEvent e) -> {
            new SuspectManagement().setVisible(true);
        });

        victimManagementButton.addActionListener((ActionEvent e) -> {
            new VictimManagement().setVisible(true);
        });

//        crimeCategoriesButton.addActionListener((ActionEvent e) -> {
//            new CategoryManagement().setVisible(true);
//        });

        searchFiltersButton.addActionListener((ActionEvent e) -> {
            new SearchDialog(this).setVisible(true);
        });

        // New actions for View Victims and View Suspects
        viewVictimsButton.addActionListener((ActionEvent e) -> {
            new ViewRecords().viewVictims();  // Open view victims records
        });

        viewSuspectsButton.addActionListener((ActionEvent e) -> {
            new ViewRecords().viewSuspects();  // Open view suspects records
        });

        viewCrimeReportsButton.addActionListener((ActionEvent e) -> {
            new ViewCrimeReports().setVisible(true);
        });

        logoutButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                dispose();
                new LoginPage(); // Redirect to login page
            }
        });
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Verdana", Font.PLAIN, 16));
        button.setPreferredSize(new Dimension(300, 50));
        button.setBackground(new Color(85, 123, 143));
        button.setForeground(Color.WHITE);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainDashboard::new);
    }
}

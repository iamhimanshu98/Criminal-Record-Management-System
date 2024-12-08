import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginPage extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        AppFont.setGlobalFont(new FontUIResource(new Font("Verdana", Font.PLAIN, 16)));

        // Set up the frame
        setTitle("Criminal Record Management System - Login");
        setSize(width, height);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create UI elements
        JPanel panel = new JPanel(new GridBagLayout()) {
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

        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        usernameField = new JTextField(20);
        usernameField.setPreferredSize(new Dimension(200, 30));
        usernameField.setMargin(new Insets(5, 5, 5, 5));

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setMargin(new Insets(5, 5, 5, 5));

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(222, 243, 255));
        loginButton.setForeground(Color.BLACK);

        usernameField.setToolTipText("Enter your username");
        passwordField.setToolTipText("Enter your password");

        // Set grid bag constraints for each component
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(passLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        // Add panel to frame
        add(panel);
        setVisible(true);

        // Set login button action
        loginButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (validateLogin(username, password)) {
                // Replace with your system's main screen
                new MainDashboard();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password.");
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        // Replace with actual database validation logic
        return "admin".equals(username) && "admin".equals(password);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginPage().setVisible(true);
        });
    }
}

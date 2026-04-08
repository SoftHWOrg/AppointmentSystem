package org.example.presentation;

import org.example.service.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * The login screen panel (US1.1).
 *
 * @author
 * @version 1.0
 */
public class LoginPanel extends JPanel {

    private final MainFrame mainFrame;
    private final AuthService authService;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel errorLabel;

    public LoginPanel(MainFrame mainFrame, AuthService authService) {
        this.mainFrame = mainFrame;
        this.authService = authService;
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 244, 248));

        // ── Title bar ──────────────────────────────────────────
        JLabel titleLabel = new JLabel("Appointment Scheduling System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(25, 80, 170));
        titleLabel.setBorder(new EmptyBorder(30, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // ── Center card ────────────────────────────────────────
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 230), 1),
                new EmptyBorder(30, 40, 30, 40)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Login sub-title
        JLabel loginTitle = new JLabel("Sign In", SwingConstants.CENTER);
        loginTitle.setFont(new Font("SansSerif", Font.BOLD, 17));
        loginTitle.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        card.add(loginTitle, gbc);

        // Email row
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        card.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(20);
        emailField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        gbc.gridx = 1;
        card.add(emailField, gbc);

        // Password row
        gbc.gridy = 2; gbc.gridx = 0;
        card.add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        gbc.gridx = 1;
        card.add(passwordField, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        styleButton(loginButton, new Color(25, 100, 200));
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        card.add(loginButton, gbc);

        // Error label
        errorLabel = new JLabel(" ", SwingConstants.CENTER);
        errorLabel.setForeground(new Color(200, 40, 40));
        errorLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        gbc.gridy = 4;
        card.add(errorLabel, gbc);

        // Center the card in the panel
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(new Color(240, 244, 248));
        wrapper.add(card);
        add(wrapper, BorderLayout.CENTER);

        // Allow pressing Enter to log in
        loginButton.addActionListener(e -> handleLogin());
        passwordField.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter both email and password.");
            return;
        }

        try {
            authService.login(email, password);
            errorLabel.setText(" ");
            passwordField.setText("");
            mainFrame.showPanel(MainFrame.DASHBOARD_PANEL);
        } catch (IllegalArgumentException ex) {
            errorLabel.setText(ex.getMessage());
        }
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 35));
    }
}

package org.example.presentation;

import org.example.service.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * The main dashboard shown after login.
 *
 * @author
 * @version 1.0
 */
public class DashboardPanel extends JPanel {

    private final MainFrame mainFrame;
    private final AuthService authService;
    private JLabel welcomeLabel;
    private JButton adminButton;

    public DashboardPanel(MainFrame mainFrame, AuthService authService) {
        this.mainFrame = mainFrame;
        this.authService = authService;
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 244, 248));

        // ── Top: welcome label ─────────────────────────────────
        welcomeLabel = new JLabel("Welcome!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(25, 80, 170));
        welcomeLabel.setBorder(new EmptyBorder(30, 0, 10, 0));
        add(welcomeLabel, BorderLayout.NORTH);

        // ── Center: navigation buttons ─────────────────────────
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(4, 1, 0, 14));
        btnPanel.setBackground(new Color(240, 244, 248));
        btnPanel.setBorder(new EmptyBorder(20, 120, 20, 120));

        JButton bookButton = new JButton("Book an Appointment");
        styleButton(bookButton, new Color(25, 100, 200));
        bookButton.addActionListener(e -> mainFrame.showPanel(MainFrame.APPOINTMENT_PANEL));

        JButton myApptsButton = new JButton("My Appointments");
        styleButton(myApptsButton, new Color(25, 100, 200));
        myApptsButton.addActionListener(e -> mainFrame.showPanel(MainFrame.MY_APPTS_PANEL));

        adminButton = new JButton("Admin Panel");
        styleButton(adminButton, new Color(100, 40, 160));
        adminButton.addActionListener(e -> mainFrame.showPanel(MainFrame.ADMIN_PANEL));
        adminButton.setVisible(false);

        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton, new Color(160, 40, 40));
        logoutButton.addActionListener(e -> {
            authService.logout();
            mainFrame.showPanel(MainFrame.LOGIN_PANEL);
        });

        btnPanel.add(bookButton);
        btnPanel.add(myApptsButton);
        btnPanel.add(adminButton);
        btnPanel.add(logoutButton);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(new Color(240, 244, 248));
        wrapper.add(btnPanel);
        add(wrapper, BorderLayout.CENTER);
    }

    /**
     * Refreshes the welcome label and admin button visibility for the current user.
     */
    public void refresh() {
        if (authService.getCurrentUser() != null) {
            welcomeLabel.setText("Welcome, " + authService.getCurrentUser().getName() + "!");
            adminButton.setVisible(authService.isAdmin());
        }
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(260, 42));
    }
}

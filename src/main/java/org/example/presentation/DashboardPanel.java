package org.example.presentation;

import org.example.service.AuthService;
import org.example.service.ScheduleService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private final MainFrame mainFrame;
    private final AuthService authService;
    private final ScheduleService scheduleService;

    private JLabel welcomeLabel;
    private JButton bookButton;
    private JButton myApptsButton;
    private JButton adminButton;

    public DashboardPanel(MainFrame mainFrame, AuthService authService, ScheduleService scheduleService) {
        this.mainFrame = mainFrame;
        this.authService = authService;
        this.scheduleService = scheduleService;
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 244, 248));

        welcomeLabel = new JLabel("Welcome!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        welcomeLabel.setForeground(new Color(25, 80, 170));
        welcomeLabel.setBorder(new EmptyBorder(40, 0, 20, 0));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
        btnPanel.setBackground(new Color(240, 244, 248));

        bookButton = new JButton("Book an Appointment");
        styleButton(bookButton, new Color(25, 140, 60));
        bookButton.addActionListener(e -> mainFrame.showPanel(MainFrame.APPOINTMENT_PANEL));

        myApptsButton = new JButton("My Appointments");
        styleButton(myApptsButton, new Color(25, 100, 200));
        myApptsButton.addActionListener(e -> mainFrame.showPanel(MainFrame.MY_APPTS_PANEL));

        adminButton = new JButton("Admin Panel");
        styleButton(adminButton, new Color(100, 40, 160));
        adminButton.addActionListener(e -> mainFrame.showPanel(MainFrame.ADMIN_PANEL));

        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton, new Color(160, 40, 40));
        logoutButton.addActionListener(e -> {
            authService.logout();
            mainFrame.showPanel(MainFrame.LOGIN_PANEL);
        });

        btnPanel.add(bookButton);
        btnPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        btnPanel.add(myApptsButton);
        btnPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        btnPanel.add(adminButton);
        btnPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        btnPanel.add(logoutButton);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(new Color(240, 244, 248));
        wrapper.add(btnPanel);
        add(wrapper, BorderLayout.CENTER);
    }

    public void refresh() {
        if (authService.getCurrentUser() == null) return;

        welcomeLabel.setText("Welcome, " + authService.getCurrentUser().getName() + "!");

        boolean isAdmin = authService.isAdmin();
        bookButton.setVisible(!isAdmin);
        myApptsButton.setVisible(true); // Users see their appts, Admins see theirs (usually empty)
        adminButton.setVisible(isAdmin);

        revalidate();
        repaint();
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(280, 45));
        button.setPreferredSize(new Dimension(280, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}

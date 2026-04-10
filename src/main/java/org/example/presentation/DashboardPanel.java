package org.example.presentation;

import org.example.service.AuthService;
import org.example.service.ScheduleService;
import org.example.domain.valueobject.TimeSlot;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class DashboardPanel extends JPanel {

    private final MainFrame mainFrame;
    private final AuthService authService;
    private final ScheduleService scheduleService;

    private JLabel welcomeLabel;
    private JButton myApptsButton;
    private JButton bookButton;

    public DashboardPanel(MainFrame mainFrame, AuthService authService, ScheduleService scheduleService) {
        this.mainFrame = mainFrame;
        this.authService = authService;
        this.scheduleService = scheduleService;
        buildUI();
    }

    private void handleCreateSlot() {
        try {
            String dateStr = JOptionPane.showInputDialog(this, "Enter Date (YYYY-MM-DD):", LocalDate.now().toString());
            if (dateStr == null) return;
            String startStr = JOptionPane.showInputDialog(this, "Enter Start Time (HH:MM):", "09:00");
            if (startStr == null) return;
            String endStr = JOptionPane.showInputDialog(this, "Enter End Time (HH:MM):", "10:00");
            if (endStr == null) return;

            LocalDate date = LocalDate.parse(dateStr);
            LocalTime start = LocalTime.parse(startStr);
            LocalTime end = LocalTime.parse(endStr);

            TimeSlot slot = new TimeSlot(0, date, start, end, true);
            scheduleService.addSlot(slot);
            JOptionPane.showMessageDialog(this, "New slot created successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 244, 248));

        welcomeLabel = new JLabel("Welcome!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(25, 80, 170));
        welcomeLabel.setBorder(new EmptyBorder(30, 0, 10, 0));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
        btnPanel.setBackground(new Color(240, 244, 248));

        myApptsButton = new JButton("My Appointments");
        styleButton(myApptsButton, new Color(25, 100, 200));
        myApptsButton.addActionListener(e -> mainFrame.showPanel(MainFrame.MY_APPTS_PANEL));

        bookButton = new JButton("Book an Appointment");
        styleButton(bookButton, new Color(25, 140, 60));
        bookButton.addActionListener(e -> {
            mainFrame.getCustomAppointmentPanel().clearFields();
            mainFrame.showPanel(MainFrame.CUSTOM_APPOINTMENT_PANEL);
        });

        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton, new Color(160, 40, 40));
        logoutButton.addActionListener(e -> {
            authService.logout();
            mainFrame.showPanel(MainFrame.LOGIN_PANEL);
        });

        myApptsButton.setVisible(false);
        bookButton.setVisible(false);

        btnPanel.add(myApptsButton);
        btnPanel.add(Box.createRigidArea(new Dimension(0, 14)));
        
        if (authService.isAdmin()) {
            JButton createSlotButton = new JButton("Create New Slot");
            styleButton(createSlotButton, new Color(130, 80, 200));
            createSlotButton.addActionListener(e -> handleCreateSlot());
            btnPanel.add(createSlotButton);
            btnPanel.add(Box.createRigidArea(new Dimension(0, 14)));
        }

        btnPanel.add(bookButton);
        btnPanel.add(Box.createRigidArea(new Dimension(0, 14)));
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
        myApptsButton.setVisible(true);
        myApptsButton.setText(isAdmin ? "Manage All Appointments" : "My Appointments");
        bookButton.setVisible(!isAdmin);

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
        button.setMaximumSize(new Dimension(260, 42));
        button.setPreferredSize(new Dimension(260, 42));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}

package org.example.presentation;

import org.example.domain.appointment.CustomAppointment;
import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.valueobject.TimeSlot;
import org.example.service.AppointmentService;
import org.example.service.AuthService;
import org.example.service.ScheduleService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class CustomAppointmentPanel extends JPanel {

    private final MainFrame mainFrame;
    private final AuthService authService;
    private final AppointmentService appointmentService;
    private final ScheduleService scheduleService;

    private JTextField dateField;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JSpinner participantsSpinner;
    private JLabel statusLabel;

    public CustomAppointmentPanel(MainFrame mainFrame,
                                  AuthService authService,
                                  AppointmentService appointmentService,
                                  ScheduleService scheduleService) {
        this.mainFrame = mainFrame;
        this.authService = authService;
        this.appointmentService = appointmentService;
        this.scheduleService = scheduleService;
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 244, 248));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(240, 244, 248));

        JLabel title = new JLabel("Book Custom Appointment");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(new Color(25, 80, 170));
        topBar.add(title, BorderLayout.WEST);

        JButton backButton = new JButton("← Back");
        styleButton(backButton, new Color(100, 100, 100));
        backButton.addActionListener(e -> mainFrame.showPanel(MainFrame.APPOINTMENT_PANEL));
        topBar.add(backButton, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(180, 200, 230)),
                        "Create Your Time Slot",
                        TitledBorder.LEFT, TitledBorder.TOP,
                        new Font("SansSerif", Font.BOLD, 12),
                        new Color(25, 80, 170)),
                new EmptyBorder(15, 15, 15, 15)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Date (YYYY-MM-DD):"), gbc);
        dateField = new JTextField(15);
        dateField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        gbc.gridx = 1;
        formPanel.add(dateField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Start Time (HH:MM):"), gbc);
        startTimeField = new JTextField(15);
        startTimeField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        gbc.gridx = 1;
        formPanel.add(startTimeField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("End Time (HH:MM):"), gbc);
        endTimeField = new JTextField(15);
        endTimeField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        gbc.gridx = 1;
        formPanel.add(endTimeField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Participants:"), gbc);
        participantsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        participantsSpinner.setFont(new Font("SansSerif", Font.PLAIN, 13));
        gbc.gridx = 1;
        formPanel.add(participantsSpinner, gbc);

        JButton bookButton = new JButton("Create & Book");
        styleButton(bookButton, new Color(25, 140, 60));
        bookButton.addActionListener(e -> handleCustomBooking());
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(bookButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
        add(statusLabel, BorderLayout.SOUTH);
    }

    public void clearFields() {
        dateField.setText("");
        startTimeField.setText("");
        endTimeField.setText("");
        participantsSpinner.setValue(1);
        statusLabel.setText(" ");
    }

    private void handleCustomBooking() {
        try {
            LocalDate date = LocalDate.parse(dateField.getText().trim());
            LocalTime start = LocalTime.parse(startTimeField.getText().trim());
            LocalTime end = LocalTime.parse(endTimeField.getText().trim());
            int participants = (int) participantsSpinner.getValue();

            TimeSlot slot = new TimeSlot(0, date, start, end, true);
            scheduleService.addSlot(slot);

            User currentUser = authService.getCurrentUser();
            Appointment appointment = new CustomAppointment(0, currentUser, slot, AppointmentStatus.PENDING, participants);

            appointmentService.bookAppointment(appointment);
            setStatus("Custom appointment created and booked successfully!", true);

        } catch (DateTimeParseException ex) {
            setStatus("Please use correct formats (YYYY-MM-DD for date, HH:MM for time).", false);
        } catch (IllegalArgumentException ex) {
            setStatus(ex.getMessage(), false);
        } catch (Exception ex) {
            setStatus("An unexpected error occurred.", false);
        }
    }

    private void setStatus(String message, boolean success) {
        statusLabel.setText(message);
        statusLabel.setForeground(success ? new Color(30, 140, 60) : new Color(200, 40, 40));
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

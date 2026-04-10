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
    private Appointment editingAppointment;
    private JLabel titleLabel;
    private JButton submitButton;
    private JList<String> slotList;
    private DefaultListModel<String> slotModel;
    private java.util.List<TimeSlot> availableSlots;

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

        titleLabel = new JLabel("Book Appointment");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(new Color(25, 80, 170));
        topBar.add(titleLabel, BorderLayout.WEST);

        JButton backButton = new JButton("← Back");
        styleButton(backButton, new Color(100, 100, 100));
        backButton.addActionListener(e -> {
            if (editingAppointment != null) {
                mainFrame.showPanel(MainFrame.MY_APPTS_PANEL);
            } else {
                mainFrame.showPanel(MainFrame.DASHBOARD_PANEL);
            }
        });
        topBar.add(backButton, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        // LEFT: Slot list
        slotModel = new DefaultListModel<>();
        slotList = new JList<>(slotModel);
        slotList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        slotList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        slotList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleSlotSelection();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(slotList);
        scrollPane.setPreferredSize(new Dimension(280, 0));
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 230)),
                "Available Slots",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 11),
                new Color(25, 80, 170)));
        add(scrollPane, BorderLayout.WEST);

        // CENTER: Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(180, 200, 230)),
                        "Appointment Details",
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

        submitButton = new JButton("Book Appointment");
        styleButton(submitButton, new Color(25, 140, 60));
        submitButton.addActionListener(e -> handleBooking());
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(submitButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
        add(statusLabel, BorderLayout.SOUTH);
    }

    private void handleSlotSelection() {
        int index = slotList.getSelectedIndex();
        if (index >= 0 && availableSlots != null && index < availableSlots.size()) {
            TimeSlot s = availableSlots.get(index);
            dateField.setText(s.getDate().toString());
            startTimeField.setText(s.getStartTime().toString());
            endTimeField.setText(s.getEndTime().toString());
        }
    }

    public void loadAvailableSlots() {
        slotModel.clear();
        availableSlots = scheduleService.getAvailableSlots();
        for (TimeSlot s : availableSlots) {
            slotModel.addElement(String.format("%s  |  %s - %s", s.getDate(), s.getStartTime(), s.getEndTime()));
        }
    }

    public void clearFields() {
        editingAppointment = null;
        titleLabel.setText("Book Appointment");
        submitButton.setText("Book Appointment");
        submitButton.setBackground(new Color(25, 140, 60));
        dateField.setText("");
        startTimeField.setText("");
        endTimeField.setText("");
        participantsSpinner.setValue(1);
        statusLabel.setText(" ");
        loadAvailableSlots();
    }

    public void setEditAppointment(Appointment a) {
        editingAppointment = a;
        titleLabel.setText("Edit Appointment #" + a.getId());
        submitButton.setText("Update Appointment");
        submitButton.setBackground(new Color(25, 100, 200));
        dateField.setText(a.getTimeSlot().getDate().toString());
        startTimeField.setText(a.getTimeSlot().getStartTime().toString());
        endTimeField.setText(a.getTimeSlot().getEndTime().toString());
        participantsSpinner.setValue(a.getParticipants());
        statusLabel.setText("Modifying existing reservation.");
    }

    private void handleBooking() {
        try {
            LocalDate date  = LocalDate.parse(dateField.getText().trim());
            LocalTime start = LocalTime.parse(startTimeField.getText().trim());
            LocalTime end   = LocalTime.parse(endTimeField.getText().trim());
            int participants = (int) participantsSpinner.getValue();

            if (editingAppointment == null) {
                // Creation flow
                TimeSlot slot;
                int selectedIndex = slotList.getSelectedIndex();
                
                if (selectedIndex >= 0 && availableSlots.get(selectedIndex).getDate().equals(date) &&
                    availableSlots.get(selectedIndex).getStartTime().equals(start)) {
                    // Reuse selected slot
                    slot = availableSlots.get(selectedIndex);
                } else {
                    // Create new/custom slot
                    slot = new TimeSlot(0, date, start, end, true);
                    scheduleService.addSlot(slot);
                }

                Appointment appointment = new CustomAppointment(0, authService.getCurrentUser(), slot, AppointmentStatus.PENDING, participants);
                appointmentService.bookAppointment(appointment);
                setStatus("Appointment booked successfully!", true);
                loadAvailableSlots();
            } else {
                // Update flow
                TimeSlot slot = editingAppointment.getTimeSlot();
                if (!slot.getDate().equals(date) || !slot.getStartTime().equals(start) || !slot.getEndTime().equals(end)) {
                    // Time changed, need to free old and book new/existing
                    scheduleService.freeSlot(slot);
                    TimeSlot newSlot = new TimeSlot(0, date, start, end, true);
                    scheduleService.addSlot(newSlot);
                    editingAppointment.setTimeSlot(newSlot);
                }
                
                editingAppointment.setParticipants(participants);
                appointmentService.modifyAppointment(editingAppointment);
                setStatus("Appointment updated successfully!", true);
                loadAvailableSlots();
            }
        } catch (DateTimeParseException ex) {
            setStatus("Format error: YYYY-MM-DD and HH:MM.", false);
        } catch (IllegalArgumentException ex) {
            setStatus(ex.getMessage(), false);
        } catch (Exception ex) {
            setStatus("Error: " + ex.getMessage(), false);
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

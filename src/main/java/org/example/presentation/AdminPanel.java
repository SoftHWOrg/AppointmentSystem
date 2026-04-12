package org.example.presentation;

import org.example.domain.entity.Appointment;
import org.example.domain.valueobject.TimeSlot;
import org.example.service.AppointmentService;
import org.example.service.AuthService;
import org.example.service.ScheduleService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AdminPanel extends JPanel {

    private final MainFrame mainFrame;
    private final AuthService authService;
    private final AppointmentService appointmentService;
    private final ScheduleService scheduleService;
    private JTable allAppointmentsTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;

    public AdminPanel(MainFrame mainFrame,
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

        JLabel title = new JLabel("Admin Panel");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(new Color(100, 40, 160));
        topBar.add(title, BorderLayout.WEST);

        JButton backButton = new JButton("← Back");
        styleButton(backButton, new Color(100, 100, 100));
        backButton.addActionListener(e -> mainFrame.showPanel(MainFrame.DASHBOARD_PANEL));
        topBar.add(backButton, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        String[] columns = {"ID", "User", "Type", "Date", "Start Time", "Status", "Participants"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        allAppointmentsTable = new JTable(tableModel);
        allAppointmentsTable.setRowHeight(26);
        allAppointmentsTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        allAppointmentsTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        allAppointmentsTable.getTableHeader().setBackground(new Color(220, 200, 250));
        allAppointmentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        allAppointmentsTable.setGridColor(new Color(220, 230, 245));
        add(new JScrollPane(allAppointmentsTable), BorderLayout.CENTER);

        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        bottomBar.setBackground(new Color(240, 244, 248));

        JButton refreshButton = new JButton("Refresh");
        styleButton(refreshButton, new Color(25, 100, 200));
        refreshButton.addActionListener(e -> loadAllAppointments());

        JButton editButton = new JButton("Edit Selected");
        styleButton(editButton, new Color(200, 150, 20));
        editButton.addActionListener(e -> handleAdminEdit());

        JButton cancelButton = new JButton("Cancel Selected");
        styleButton(cancelButton, new Color(200, 40, 40));
        cancelButton.addActionListener(e -> handleAdminCancel());

        JButton addSlotButton = new JButton("Add Time Slot");
        styleButton(addSlotButton, new Color(25, 140, 60));
        addSlotButton.addActionListener(e -> handleAddSlot());

        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        bottomBar.add(refreshButton);
        bottomBar.add(editButton);
        bottomBar.add(cancelButton);
        bottomBar.add(addSlotButton);
        bottomBar.add(statusLabel);
        add(bottomBar, BorderLayout.SOUTH);
    }

    
    public void loadAllAppointments() {
        tableModel.setRowCount(0);
        if (authService.getCurrentUser() == null) return;

        try {
            List<Appointment> appointments =
                    appointmentService.getAllAppointments(authService.getCurrentUser());

            for (Appointment a : appointments) {
                tableModel.addRow(new Object[]{
                        a.getId(),
                        a.getUser().getName(),
                        a.getType(),
                        a.getTimeSlot().getDate(),
                        a.getTimeSlot().getStartTime(),
                        a.getStatus(),
                        a.getParticipants()
                });
            }
            setStatus(appointments.isEmpty() ? "No appointments in the system." : " ", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            setStatus("Error: " + ex.getMessage(), false);
        }
    }

    private void handleAdminEdit() {
        int selectedRow = allAppointmentsTable.getSelectedRow();
        if (selectedRow < 0) {
            setStatus("Please select an appointment to edit.", false);
            return;
        }

        int appointmentId = (int) tableModel.getValueAt(selectedRow, 0);
        try {
            List<Appointment> apps = appointmentService.getAllAppointments(authService.getCurrentUser());
            Appointment target = apps.stream().filter(a -> a.getId() == appointmentId).findFirst().orElse(null);

            if (target != null) {
                mainFrame.getEditAppointmentPanel().setEditAppointment(target);
                mainFrame.showPanel(MainFrame.EDIT_APPOINTMENT_PANEL);
            }
        } catch (Exception ex) {
            setStatus(ex.getMessage(), false);
        }
    }

    private void handleAdminCancel() {
        int selectedRow = allAppointmentsTable.getSelectedRow();
        if (selectedRow < 0) {
            setStatus("Please select an appointment to cancel.", false);
            return;
        }

        int appointmentId = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Cancel appointment #" + appointmentId + "?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                appointmentService.cancelAppointment(appointmentId, authService.getCurrentUser());
                setStatus("Appointment #" + appointmentId + " cancelled.", true);
                loadAllAppointments();
            } catch (Exception ex) {
                setStatus(ex.getMessage(), false);
            }
        }
    }

    private void handleAddSlot() {
        JTextField dateField = new JTextField("2025-12-31");
        JTextField startField = new JTextField("09:00");
        JTextField endField = new JTextField("09:30");

        Object[] fields = {
                "Date (YYYY-MM-DD):", dateField,
                "Start Time (HH:mm):", startField,
                "End Time (HH:mm):", endField
        };

        int result = JOptionPane.showConfirmDialog(this, fields,
                "Add New Time Slot", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                LocalDate date = LocalDate.parse(dateField.getText().trim());
                LocalTime start = LocalTime.parse(startField.getText().trim());
                LocalTime end = LocalTime.parse(endField.getText().trim());

                TimeSlot slot = new TimeSlot(0, date, start, end, true);
                scheduleService.addSlot(slot);
                setStatus("Time slot added: " + date + " " + start + "–" + end, true);
            } catch (DateTimeParseException ex) {
                setStatus("Invalid date/time format. Use YYYY-MM-DD and HH:mm.", false);
            } catch (IllegalArgumentException ex) {
                setStatus(ex.getMessage(), false);
            }
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
        button.setPreferredSize(new Dimension(150, 32));
    }
}

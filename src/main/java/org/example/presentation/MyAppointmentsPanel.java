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
import java.util.List;

public class MyAppointmentsPanel extends JPanel {

    private final MainFrame mainFrame;
    private final AuthService authService;
    private final AppointmentService appointmentService;
    private final ScheduleService scheduleService;
    private JTable appointmentsTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;
    private JButton addSlotButton;

    public MyAppointmentsPanel(MainFrame mainFrame,
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

        JLabel title = new JLabel("Appointments Management");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(new Color(25, 80, 170));
        topBar.add(title, BorderLayout.WEST);

        JButton backButton = new JButton("← Back");
        styleButton(backButton, new Color(100, 100, 100));
        backButton.addActionListener(e -> mainFrame.showPanel(MainFrame.DASHBOARD_PANEL));
        topBar.add(backButton, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        String[] columns = {"ID", "Type", "Date", "Start Time", "Status", "Participants"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        appointmentsTable = new JTable(tableModel);
        appointmentsTable.setRowHeight(26);
        appointmentsTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        appointmentsTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        appointmentsTable.getTableHeader().setBackground(new Color(210, 225, 250));
        appointmentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        appointmentsTable.setGridColor(new Color(220, 230, 245));
        add(new JScrollPane(appointmentsTable), BorderLayout.CENTER);

        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        bottomBar.setBackground(new Color(240, 244, 248));

        JButton refreshButton = new JButton("Refresh");
        styleButton(refreshButton, new Color(25, 100, 200));
        refreshButton.addActionListener(e -> loadAppointments());

        JButton cancelButton = new JButton("Cancel Selected");
        styleButton(cancelButton, new Color(200, 50, 50));
        cancelButton.addActionListener(e -> handleCancel());

        JButton editButton = new JButton("Full Edit");
        styleButton(editButton, new Color(200, 150, 20));
        editButton.addActionListener(e -> handleEdit());

        addSlotButton = new JButton("Add Time Slot");
        styleButton(addSlotButton, new Color(130, 80, 200));
        addSlotButton.addActionListener(e -> handleCreateSlot());
        addSlotButton.setVisible(false);

        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        bottomBar.add(refreshButton);
        bottomBar.add(cancelButton);
        bottomBar.add(editButton);
        bottomBar.add(addSlotButton);
        bottomBar.add(statusLabel);
        add(bottomBar, BorderLayout.SOUTH);
    }

    private void handleEdit() {
        int row = appointmentsTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        
        // Find appointment object
        List<Appointment> apps;
        if (authService.isAdmin()) {
            apps = appointmentService.getAllAppointments(authService.getCurrentUser());
        } else {
            apps = appointmentService.getAppointmentsForUser(authService.getCurrentUser().getId());
        }
        
        Appointment target = apps.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
        if (target != null) {
            if ("CANCELLED".equals(target.getStatus().name())) {
                JOptionPane.showMessageDialog(this, "Cannot edit a cancelled appointment.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            mainFrame.getCustomAppointmentPanel().setEditAppointment(target);
            mainFrame.showPanel(MainFrame.CUSTOM_APPOINTMENT_PANEL);
        }
    }

    private void handleCancel() {
        int row = appointmentsTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to cancel.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        String status = tableModel.getValueAt(row, 4).toString();

        if ("CANCELLED".equals(status)) {
            JOptionPane.showMessageDialog(this, "This appointment is already cancelled.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel appointment #" + id + "?", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                appointmentService.cancelAppointment(id, authService.getCurrentUser());
                loadAppointments();
                JOptionPane.showMessageDialog(this, "Appointment cancelled successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
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

    public void loadAppointments() {
        tableModel.setRowCount(0);
        if (authService.getCurrentUser() == null) return;

        boolean isAdmin = authService.isAdmin();
        addSlotButton.setVisible(isAdmin);
        List<Appointment> appointments;

        if (isAdmin) {
            appointments = appointmentService.getAllAppointments(authService.getCurrentUser());
        } else {
            appointments = appointmentService.getAppointmentsForUser(authService.getCurrentUser().getId());
        }

        for (Appointment a : appointments) {
            tableModel.addRow(new Object[]{
                    a.getId(),
                    a.getType(),
                    a.getTimeSlot().getDate(),
                    a.getTimeSlot().getStartTime(),
                    a.getStatus(),
                    a.getParticipants()
            });
        }
        
        String userType = isAdmin ? "All" : "My";
        statusLabel.setForeground(new Color(60, 60, 60));
        statusLabel.setText(appointments.isEmpty() ? "No appointments found." : userType + " appointments loaded (" + appointments.size() + ").");
        
        revalidate();
        repaint();
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

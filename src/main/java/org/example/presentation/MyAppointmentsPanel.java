package org.example.presentation;

import org.example.domain.entity.Appointment;
import org.example.service.AppointmentService;
import org.example.service.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MyAppointmentsPanel extends JPanel {

    private final MainFrame mainFrame;
    private final AuthService authService;
    private final AppointmentService appointmentService;
    private JTable appointmentsTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;

    public MyAppointmentsPanel(MainFrame mainFrame,
                               AuthService authService,
                               AppointmentService appointmentService) {
        this.mainFrame = mainFrame;
        this.authService = authService;
        this.appointmentService = appointmentService;
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 244, 248));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(240, 244, 248));

        JLabel title = new JLabel("My Appointments");
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
        styleButton(cancelButton, new Color(200, 40, 40));
        cancelButton.addActionListener(e -> handleCancel());

        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        bottomBar.add(refreshButton);
        bottomBar.add(cancelButton);
        bottomBar.add(statusLabel);
        add(bottomBar, BorderLayout.SOUTH);
    }

    
    public void loadAppointments() {
        tableModel.setRowCount(0);
        if (authService.getCurrentUser() == null) return;

        List<Appointment> appointments =
                appointmentService.getAppointmentsForUser(authService.getCurrentUser().getId());

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
        statusLabel.setForeground(new Color(60, 60, 60));
        statusLabel.setText(appointments.isEmpty() ? "No appointments found." : " ");
    }

    private void handleCancel() {
        int selectedRow = appointmentsTable.getSelectedRow();
        if (selectedRow < 0) {
            setStatus("Please select an appointment to cancel.", false);
            return;
        }

        int appointmentId = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel appointment #" + appointmentId + "?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                appointmentService.cancelAppointment(appointmentId, authService.getCurrentUser());
                setStatus("Appointment #" + appointmentId + " cancelled.", true);
                loadAppointments();
            } catch (IllegalStateException | IllegalArgumentException | SecurityException ex) {
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

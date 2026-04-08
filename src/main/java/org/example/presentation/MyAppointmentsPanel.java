package org.example.presentation;

import org.example.service.AppointmentService;
import org.example.service.AuthService;

import javax.swing.*;
import java.awt.*;

/**
 * Panel for viewing, modifying, and cancelling the user's own appointments (US4.1).
 *
 * Layout:
 *  - Top: "My Appointments" title + "Back" button
 *  - Center: JTable showing the user's appointments
 *      Columns: ID | Type | Date | Time | Status | Participants
 *  - Bottom: "Cancel Selected" button + status label
 *
 * @author
 * @version 1.0
 */
public class MyAppointmentsPanel extends JPanel {

    // TODO: Add field: MainFrame mainFrame
    // TODO: Add field: AuthService authService
    // TODO: Add field: AppointmentService appointmentService
    // TODO: Add field: JTable appointmentsTable
    // TODO: Add field: JLabel statusLabel

    /**
     * Constructor — builds the "My Appointments" UI.
     *
     * TODO:
     *  1. Assign all fields
     *  2. Create a JTable with columns: ID, Type, Date, Time, Status, Participants
     *  3. Wrap table in JScrollPane and add to center
     *  4. Add "Refresh" button → call loadAppointments()
     *  5. Add "Cancel Selected" button → call handleCancel()
     *  6. Add "Back" button → mainFrame.showPanel(MainFrame.DASHBOARD_PANEL)
     *
     * @param mainFrame          the parent window
     * @param authService        to get the current user
     * @param appointmentService to load and cancel appointments
     */
    public MyAppointmentsPanel(MainFrame mainFrame,
                               AuthService authService,
                               AppointmentService appointmentService) {
        // TODO: build the UI
    }

    /**
     * Loads the current user's appointments into the table.
     *
     * TODO:
     *  1. Get userId from authService.getCurrentUser().getId()
     *  2. Call appointmentService.getAppointmentsForUser(userId)
     *  3. Clear the table model and populate with appointment data
     *     Each row: [id, type, date, startTime, status, participants]
     */
    public void loadAppointments() {
        // TODO: implement appointment loading into table
    }

    /**
     * Handles the "Cancel Selected" button click.
     *
     * TODO:
     *  1. Get the selected row from appointmentsTable
     *  2. If no row selected → show warning dialog
     *  3. Get the appointment ID from the selected row
     *  4. Show confirmation dialog: "Are you sure you want to cancel this appointment?"
     *  5. If confirmed → call appointmentService.cancelAppointment(id, currentUser)
     *  6. If successful → show success message, reload table
     *  7. If exception (e.g. past appointment) → show error in statusLabel
     */
    private void handleCancel() {
        // TODO: implement cancel handler
    }
}

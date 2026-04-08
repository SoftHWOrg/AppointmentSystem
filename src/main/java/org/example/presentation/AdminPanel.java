package org.example.presentation;

import org.example.service.AppointmentService;
import org.example.service.AuthService;
import org.example.service.ScheduleService;

import javax.swing.*;
import java.awt.*;

/**
 * Admin-only panel for managing all reservations in the system (US4.2).
 * Only accessible when the logged-in user has role = "ADMIN".
 *
 * Layout:
 *  - Top: "Admin Panel" title + "Back" button
 *  - Center: JTable showing ALL appointments in the system
 *      Columns: ID | User | Type | Date | Time | Status | Participants
 *  - Bottom: "Cancel Selected" button + "Add Slot" button + status label
 *
 * @author
 * @version 1.0
 */
public class AdminPanel extends JPanel {

    // TODO: Add field: MainFrame mainFrame
    // TODO: Add field: AuthService authService
    // TODO: Add field: AppointmentService appointmentService
    // TODO: Add field: ScheduleService scheduleService
    // TODO: Add field: JTable allAppointmentsTable
    // TODO: Add field: JLabel statusLabel

    /**
     * Constructor — builds the admin panel UI.
     *
     * TODO:
     *  1. Assign all fields
     *  2. Create JTable with columns: ID, User, Type, Date, Time, Status, Participants
     *  3. Wrap in JScrollPane, add to center
     *  4. Add "Refresh" button → call loadAllAppointments()
     *  5. Add "Cancel Selected" button → call handleAdminCancel()
     *  6. Add "Add Time Slot" button → open a dialog to add a new slot (call scheduleService.addSlot())
     *  7. Add "Back" button → mainFrame.showPanel(MainFrame.DASHBOARD_PANEL)
     *
     * @param mainFrame          the parent window
     * @param authService        to verify admin role and get current user
     * @param appointmentService to load and cancel any appointment
     * @param scheduleService    to add new time slots
     */
    public AdminPanel(MainFrame mainFrame,
                      AuthService authService,
                      AppointmentService appointmentService,
                      ScheduleService scheduleService) {
        // TODO: build the admin UI
    }

    /**
     * Loads ALL appointments into the table (admin can see everyone's).
     *
     * TODO:
     *  1. Call appointmentService.getAllAppointments(authService.getCurrentUser())
     *  2. Clear table model
     *  3. For each appointment, add a row:
     *     [id, user.getName(), type, date, startTime, status, participants]
     */
    public void loadAllAppointments() {
        // TODO: implement
    }

    /**
     * Handles admin cancellation of any selected appointment (US4.2).
     *
     * TODO:
     *  1. Get selected row from allAppointmentsTable
     *  2. If none selected → show warning
     *  3. Get appointment ID from selected row
     *  4. Show confirmation dialog
     *  5. Call appointmentService.cancelAppointment(id, authService.getCurrentUser())
     *  6. Show result message, reload table
     */
    private void handleAdminCancel() {
        // TODO: implement admin cancel
    }

    /**
     * Opens a dialog to let the admin add a new time slot to the schedule.
     *
     * TODO:
     *  1. Show a JDialog or JOptionPane with:
     *       - Date picker (or JTextField for date string)
     *       - Start time field
     *       - End time field
     *  2. On confirm → create a TimeSlot object and call scheduleService.addSlot(slot)
     *  3. Show success/error message
     */
    private void handleAddSlot() {
        // TODO: implement add slot dialog
    }
}

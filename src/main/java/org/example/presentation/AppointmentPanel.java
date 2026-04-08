package org.example.presentation;

import org.example.service.AppointmentService;
import org.example.service.AuthService;
import org.example.service.ScheduleService;

import javax.swing.*;
import java.awt.*;

/**
 * Panel for viewing available time slots and booking a new appointment (US1.3, US2.1).
 *
 * Layout:
 *  - Top: "Book an Appointment" title + "Back" button
 *  - Left: list of available time slots (JList or JTable)
 *  - Right: booking form:
 *      - Appointment Type dropdown (JComboBox with all 7 types)
 *      - Participants spinner (JSpinner)
 *      - "Book" button
 *  - Bottom: status/error label
 *
 * @author
 * @version 1.0
 */
public class AppointmentPanel extends JPanel {

    // TODO: Add field: MainFrame mainFrame
    // TODO: Add field: AuthService authService
    // TODO: Add field: AppointmentService appointmentService
    // TODO: Add field: ScheduleService scheduleService
    // TODO: Add field: JList<String> slotList       (shows available time slots)
    // TODO: Add field: JComboBox<String> typeCombo  (appointment type selector)
    // TODO: Add field: JSpinner participantsSpinner
    // TODO: Add field: JLabel statusLabel

    /**
     * Constructor — builds the appointment booking UI.
     *
     * TODO:
     *  1. Assign all fields
     *  2. Build the layout with a slot list on the left and booking form on the right
     *  3. Populate slotList with available slots from scheduleService.getAvailableSlots()
     *  4. Populate typeCombo with all AppointmentType values
     *  5. Add "Book" button listener → call handleBooking()
     *  6. Add "Back" button listener → mainFrame.showPanel(MainFrame.DASHBOARD_PANEL)
     *
     * @param mainFrame          the parent window
     * @param authService        to get the currently logged-in user
     * @param appointmentService to perform the booking
     * @param scheduleService    to get available time slots
     */
    public AppointmentPanel(MainFrame mainFrame,
                            AuthService authService,
                            AppointmentService appointmentService,
                            ScheduleService scheduleService) {
        // TODO: build the booking UI
    }

    /**
     * Loads available time slots into the slotList.
     * Call this when the panel is first shown or after a booking.
     *
     * TODO:
     *  1. Call scheduleService.getAvailableSlots()
     *  2. Convert each TimeSlot to a display string (date + time range)
     *  3. Set them into slotList using a DefaultListModel
     */
    public void loadAvailableSlots() {
        // TODO: implement slot loading
    }

    /**
     * Handles the "Book" button click.
     *
     * TODO:
     *  1. Check a slot is selected in slotList — if not, show error
     *  2. Get selected AppointmentType from typeCombo
     *  3. Get participants count from participantsSpinner
     *  4. Get current user from authService.getCurrentUser()
     *  5. Create the correct Appointment subclass based on type
     *     (e.g. if type == URGENT → new UrgentAppointment(...))
     *  6. Call appointmentService.bookAppointment(appointment)
     *  7. If successful → show success message, refresh slot list
     *  8. If exception → show error in statusLabel
     */
    private void handleBooking() {
        // TODO: implement booking handler
    }
}

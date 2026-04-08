package org.example.presentation;

import org.example.service.AuthService;

import javax.swing.*;
import java.awt.*;

/**
 * The main dashboard shown after login.
 * Displays a welcome message and navigation buttons to all features.
 *
 * Layout:
 *  - Top: "Welcome, [username]!" label
 *  - Center: navigation buttons
 *      - "View / Book Appointment"  → AppointmentPanel
 *      - "My Appointments"          → MyAppointmentsPanel
 *      - "Admin Panel" (only shown if user is ADMIN) → AdminPanel
 *  - Bottom: "Logout" button
 *
 * @author
 * @version 1.0
 */
public class DashboardPanel extends JPanel {

    // TODO: Add field: MainFrame mainFrame
    // TODO: Add field: AuthService authService

    /**
     * Constructor — builds the dashboard UI.
     *
     * TODO:
     *  1. Assign mainFrame and authService fields
     *  2. Set layout (e.g. BorderLayout)
     *  3. Add a welcome label at the top:
     *       "Welcome, " + authService.getCurrentUser().getName() + "!"
     *       NOTE: refresh this label each time the panel is shown (override paintComponent or use a refresh() method)
     *  4. Add navigation buttons in the center:
     *       - "Book Appointment"  → mainFrame.showPanel(MainFrame.APPOINTMENT_PANEL)
     *       - "My Appointments"   → mainFrame.showPanel(MainFrame.MY_APPTS_PANEL)
     *       - "Admin Panel"       → mainFrame.showPanel(MainFrame.ADMIN_PANEL)
     *         (only add Admin Panel button if authService.isAdmin() == true)
     *  5. Add a "Logout" button at the bottom:
     *       - Call authService.logout()
     *       - Navigate to: mainFrame.showPanel(MainFrame.LOGIN_PANEL)
     *
     * @param mainFrame   the parent window
     * @param authService the authentication service
     */
    public DashboardPanel(MainFrame mainFrame, AuthService authService) {
        // TODO: build the dashboard UI
    }

    /**
     * Refreshes the dashboard content (e.g. welcome label) when navigated to.
     * Call this from MainFrame.showPanel() if you want live updates.
     *
     * TODO:
     *  - Update welcome label with current user's name
     *  - Show/hide admin button based on role
     */
    public void refresh() {
        // TODO: implement refresh
    }
}

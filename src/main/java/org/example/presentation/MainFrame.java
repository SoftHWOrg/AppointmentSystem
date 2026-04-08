package org.example.presentation;

import org.example.service.AppointmentService;
import org.example.service.AuthService;
import org.example.service.ScheduleService;
import org.example.service.ReminderService;

import javax.swing.*;
import java.awt.*;

/**
 * The main application window.
 * Uses CardLayout to switch between panels (Login, Dashboard, Book, etc.)
 * without opening multiple windows.
 *
 * Flow:
 *   App starts → shows LoginPanel
 *   After login → shows DashboardPanel
 *   From dashboard → navigate to other panels
 *   Logout → back to LoginPanel
 *
 * @author
 * @version 1.0
 */
public class MainFrame extends JFrame {

    // TODO: Add field: CardLayout cardLayout     (for switching panels)
    // TODO: Add field: JPanel mainPanel          (the container that holds all panels)

    // Services — passed down to each panel
    // TODO: Add field: AuthService authService
    // TODO: Add field: AppointmentService appointmentService
    // TODO: Add field: ScheduleService scheduleService
    // TODO: Add field: ReminderService reminderService

    // Panel name constants — used with cardLayout.show(...)
    public static final String LOGIN_PANEL       = "LOGIN";
    public static final String DASHBOARD_PANEL   = "DASHBOARD";
    public static final String APPOINTMENT_PANEL = "APPOINTMENT";
    public static final String MY_APPTS_PANEL    = "MY_APPOINTMENTS";
    public static final String ADMIN_PANEL       = "ADMIN";

    /**
     * Constructor — sets up the window and adds all panels.
     *
     * TODO:
     *  1. Call super("Appointment Scheduling System")
     *  2. Set default close operation: EXIT_ON_CLOSE
     *  3. Set size: e.g. setSize(900, 600)
     *  4. Center on screen: setLocationRelativeTo(null)
     *  5. Create cardLayout = new CardLayout()
     *  6. Create mainPanel = new JPanel(cardLayout)
     *  7. Create each panel and add to mainPanel with its constant name:
     *       mainPanel.add(new LoginPanel(this, authService), LOGIN_PANEL)
     *       mainPanel.add(new DashboardPanel(this, authService), DASHBOARD_PANEL)
     *       ... and so on for each panel
     *  8. Add mainPanel to the frame: add(mainPanel)
     *  9. Show login first: cardLayout.show(mainPanel, LOGIN_PANEL)
     * 10. setVisible(true)
     *
     * @param authService        injected auth service
     * @param appointmentService injected appointment service
     * @param scheduleService    injected schedule service
     * @param reminderService    injected reminder service
     */
    public MainFrame(AuthService authService,
                     AppointmentService appointmentService,
                     ScheduleService scheduleService,
                     ReminderService reminderService) {
        // TODO: implement window setup
    }

    /**
     * Switches the visible panel using CardLayout.
     * Called by panels to navigate (e.g. after login → show dashboard).
     *
     * TODO:
     *  - cardLayout.show(mainPanel, panelName)
     *
     * @param panelName one of the constants defined above (e.g. DASHBOARD_PANEL)
     */
    public void showPanel(String panelName) {
        // TODO: cardLayout.show(mainPanel, panelName);
    }
}

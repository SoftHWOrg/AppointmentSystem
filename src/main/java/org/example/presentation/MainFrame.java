package org.example.presentation;

import org.example.service.AppointmentService;
import org.example.service.AuthService;
import org.example.service.ReminderService;
import org.example.service.ScheduleService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static final String LOGIN_PANEL       = "LOGIN";
    public static final String DASHBOARD_PANEL   = "DASHBOARD";
    public static final String APPOINTMENT_PANEL = "APPOINTMENT";
    public static final String MY_APPTS_PANEL    = "MY_APPOINTMENTS";
    public static final String ADMIN_PANEL       = "ADMIN";

    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    private final DashboardPanel dashboardPanel;
    private final AppointmentPanel appointmentPanel;
    private final MyAppointmentsPanel myApptsPanel;
    private final AdminPanel adminPanel;

    public MainFrame(AuthService authService,
                     AppointmentService appointmentService,
                     ScheduleService scheduleService,
                     ReminderService reminderService) {

        super("Appointment Scheduling System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setMinimumSize(new Dimension(700, 480));
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        LoginPanel loginPanel          = new LoginPanel(this, authService);
        dashboardPanel                 = new DashboardPanel(this, authService);
        appointmentPanel               = new AppointmentPanel(this, authService, appointmentService, scheduleService);
        myApptsPanel                   = new MyAppointmentsPanel(this, authService, appointmentService);
        adminPanel                     = new AdminPanel(this, authService, appointmentService, scheduleService);

        mainPanel.add(loginPanel,      LOGIN_PANEL);
        mainPanel.add(dashboardPanel,  DASHBOARD_PANEL);
        mainPanel.add(appointmentPanel, APPOINTMENT_PANEL);
        mainPanel.add(myApptsPanel,    MY_APPTS_PANEL);
        mainPanel.add(adminPanel,      ADMIN_PANEL);

        add(mainPanel);
        cardLayout.show(mainPanel, LOGIN_PANEL);
        setVisible(true);
    }

    
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);

        switch (panelName) {
            case DASHBOARD_PANEL   -> dashboardPanel.refresh();
            case APPOINTMENT_PANEL -> appointmentPanel.loadAvailableSlots();
            case MY_APPTS_PANEL    -> myApptsPanel.loadAppointments();
            case ADMIN_PANEL       -> adminPanel.loadAllAppointments();
        }
    }
}

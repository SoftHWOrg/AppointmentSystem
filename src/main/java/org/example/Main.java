package org.example;

import org.example.config.DatabaseConnection;
import org.example.observer.EmailNotificationObserver;
import org.example.observer.SMSNotificationObserver;
import org.example.observer.CalendarNotificationObserver;
import org.example.presentation.MainFrame;
import org.example.repository.PostgresAppointmentRepository;
import org.example.repository.PostgresUserRepository;
import org.example.domain.entity.Schedule;
import org.example.service.AppointmentService;
import org.example.service.AuthService;
import org.example.service.ReminderService;
import org.example.service.ScheduleService;
import org.example.strategy.AppointmentTypeRuleStrategy;
import org.example.strategy.BookingRuleStrategy;
import org.example.strategy.DurationRuleStrategy;
import org.example.strategy.ParticipantLimitRuleStrategy;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * Application entry point.
 * Wires up all dependencies and launches the Swing GUI.
 *
 * This is the only place where all layers connect together.
 * All other classes receive their dependencies through constructors (dependency injection).
 *
 * @author
 * @version 1.0
 */
public class Main {

    /**
     * Application entry point.
     *
     * TODO:
     *  1. Create repositories:
     *       PostgresUserRepository userRepo = new PostgresUserRepository();
     *       PostgresAppointmentRepository appointmentRepo = new PostgresAppointmentRepository();
     *
     *  2. Create booking rule strategies:
     *       List<BookingRuleStrategy> rules = Arrays.asList(
     *           new DurationRuleStrategy(),
     *           new ParticipantLimitRuleStrategy(),
     *           new AppointmentTypeRuleStrategy()
     *       );
     *
     *  3. Create ReminderService and register observers:
     *       ReminderService reminderService = new ReminderService();
     *       reminderService.registerObserver(new EmailNotificationObserver());
     *       reminderService.registerObserver(new SMSNotificationObserver());
     *       reminderService.registerObserver(new CalendarNotificationObserver());
     *
     *  4. Create services:
     *       AuthService authService = new AuthService(userRepo);
     *       ScheduleService scheduleService = new ScheduleService(new Schedule());
     *       AppointmentService appointmentService = new AppointmentService(
     *           appointmentRepo, scheduleService, reminderService, rules);
     *
     *  5. Launch the Swing GUI on the Event Dispatch Thread:
     *       SwingUtilities.invokeLater(() -> {
     *           new MainFrame(authService, appointmentService, scheduleService, reminderService);
     *       });
     *
     *  6. Add a shutdown hook to close the DB connection pool when app exits:
     *       Runtime.getRuntime().addShutdownHook(new Thread(DatabaseConnection::close));
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // TODO: implement wiring and launch
    }
}

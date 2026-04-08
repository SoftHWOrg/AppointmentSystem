package org.example;

import org.example.domain.entity.Schedule;
import org.example.observer.CalendarNotificationObserver;
import org.example.observer.EmailNotificationObserver;
import org.example.observer.SMSNotificationObserver;
import org.example.presentation.MainFrame;
import org.example.repository.TxtAppointmentRepository;
import org.example.repository.TxtTimeSlotRepository;
import org.example.repository.TxtUserRepository;
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
 * @author
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {

        // 1. Text-file repositories
        TxtUserRepository userRepo        = new TxtUserRepository();
        TxtAppointmentRepository apptRepo = new TxtAppointmentRepository();
        TxtTimeSlotRepository slotRepo    = new TxtTimeSlotRepository();

        // 2. Booking rule strategies (Strategy Pattern)
        List<BookingRuleStrategy> rules = Arrays.asList(
                new DurationRuleStrategy(),
                new ParticipantLimitRuleStrategy(),
                new AppointmentTypeRuleStrategy()
        );

        // 3. Reminder service + observers (Observer Pattern)
        ReminderService reminderService = new ReminderService();
        reminderService.registerObserver(new EmailNotificationObserver());
        reminderService.registerObserver(new SMSNotificationObserver());
        reminderService.registerObserver(new CalendarNotificationObserver());

        // 4. Services
        AuthService authService           = new AuthService(userRepo);
        ScheduleService scheduleService   = new ScheduleService(new Schedule(), slotRepo);
        AppointmentService apptService    = new AppointmentService(
                apptRepo, scheduleService, reminderService, rules);

        // 5. Launch Swing GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() ->
                new MainFrame(authService, apptService, scheduleService, reminderService));
    }
}

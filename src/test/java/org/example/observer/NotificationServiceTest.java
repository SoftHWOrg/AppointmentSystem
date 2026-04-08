package org.example.observer;

import org.example.domain.appointment.UrgentAppointment;
import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.valueobject.TimeSlot;
import org.example.service.ReminderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link ReminderService} using Mockito-mocked Observers.
 * US3.1 — Send appointment reminders.
 *
 * Mockito is used here to mock the Observer implementations (Email, SMS, Calendar)
 * so that no real messages are sent during testing.
 * We only verify that notify() was called with the correct arguments.
 *
 * @author
 * @version 1.0
 */
class NotificationServiceTest {

    // TODO: Add field: ReminderService reminderService
    // TODO: Add field: Observer mockEmailObserver     (Mockito mock)
    // TODO: Add field: Observer mockSmsObserver       (Mockito mock)

    /**
     * TODO:
     *  1. reminderService = new ReminderService()
     *  2. mockEmailObserver = Mockito.mock(Observer.class)
     *  3. mockSmsObserver   = Mockito.mock(Observer.class)
     *  4. reminderService.registerObserver(mockEmailObserver)
     *  5. reminderService.registerObserver(mockSmsObserver)
     */
    @BeforeEach
    void setUp() {
        // TODO: initialize reminderService and mock observers
    }

    /**
     * US3.1 — sendReminder() should call notify() on all registered observers.
     *
     * TODO:
     *  1. Create a User and a future Appointment
     *  2. Call reminderService.sendReminder(appointment)
     *  3. verify(mockEmailObserver, times(1)).notify(any(User.class), anyString())
     *  4. verify(mockSmsObserver,   times(1)).notify(any(User.class), anyString())
     */
    @Test
    void testSendReminder_notifiesAllObservers() {
        // TODO: implement test
    }

    /**
     * After removing an observer, it should NOT receive notifications.
     *
     * TODO:
     *  1. Call reminderService.removeObserver(mockSmsObserver)
     *  2. Call reminderService.sendReminder(appointment)
     *  3. verify(mockEmailObserver, times(1)).notify(any(), anyString())  ← still notified
     *  4. verify(mockSmsObserver,   never()).notify(any(), anyString())    ← NOT notified
     */
    @Test
    void testRemoveObserver_removedObserverNotNotified() {
        // TODO: implement test
    }

    /**
     * Sending a custom reminder string should pass the exact message to all observers.
     *
     * TODO:
     *  1. Create a user
     *  2. Call reminderService.sendReminder(user, "Your appointment is tomorrow!")
     *  3. verify(mockEmailObserver).notify(user, "Your appointment is tomorrow!")
     *  4. verify(mockSmsObserver).notify(user, "Your appointment is tomorrow!")
     */
    @Test
    void testSendCustomReminder_correctMessagePassed() {
        // TODO: implement test
    }
}

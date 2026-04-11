package org.example.observer;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;
import org.example.service.ReminderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationServiceTest {

    private ReminderService reminderService;
    private TestObserver testObserver;

    @BeforeEach
    void setUp() {
        reminderService = new ReminderService();
        testObserver = new TestObserver();
    }

    @Test
    void testSendReminder_notifiesAllObservers() {
        reminderService.registerObserver(testObserver);
        
        User user = new User(1, "Test", "test@test.com", "pass", "USER");
        TimeSlot slot = new TimeSlot(1, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), false);
        // Using real Appointment objects to avoid Mockito issues
        Appointment appt = new org.example.domain.appointment.UrgentAppointment(1, user, slot, AppointmentStatus.CONFIRMED, 1);

        reminderService.sendReminder(appt);
        
        assertEquals(1, testObserver.getCount());
        assertEquals(user, testObserver.notifications.get(0).user);
        assertTrue(testObserver.notifications.get(0).message.contains("URGENT"));
    }

    @Test
    void testRemoveObserver_removedObserverNotNotified() {
        reminderService.registerObserver(testObserver);
        reminderService.removeObserver(testObserver);
        
        User user = new User(1, "Test", "test@test.com", "pass", "USER");
        reminderService.notifyAllObservers(user, "Test Message");
        
        assertEquals(0, testObserver.getCount());
    }

    @Test
    void testSendReminder_nullCases() {
        reminderService.registerObserver(testObserver);
        
        // Null appointment
        reminderService.sendReminder(null);
        assertEquals(0, testObserver.getCount());
        
        // Null user
        TimeSlot slot = new TimeSlot(1, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), false);
        Appointment apptPara = new org.example.domain.appointment.UrgentAppointment(1, null, slot, AppointmentStatus.CONFIRMED, 1);
        reminderService.sendReminder(apptPara);
        assertEquals(0, testObserver.getCount());
    }

    @Test
    void testDoubleRegistration() {
        reminderService.registerObserver(testObserver);
        reminderService.registerObserver(testObserver); // Should not duplicate
        
        User user = new User(1, "Test", "test@test.com", "pass", "USER");
        reminderService.notifyAllObservers(user, "Hello");
        
        assertEquals(1, testObserver.getCount());
        assertEquals("Hello", testObserver.notifications.get(0).message);
    }
}

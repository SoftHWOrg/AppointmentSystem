package org.example.observer;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.valueobject.TimeSlot;
import org.example.service.ReminderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    private ReminderService reminderService;
    private Observer mockObserver;

    @BeforeEach
    void setUp() {
        reminderService = new ReminderService();
        mockObserver = Mockito.mock(Observer.class);
    }

    @Test
    void testSendReminder_notifiesAllObservers() {
        reminderService.registerObserver(mockObserver);
        
        User user = new User(1, "Test", "test@test.com", "pass", "USER");
        TimeSlot slot = new TimeSlot(1, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), false);
        Appointment mockAppointment = Mockito.mock(Appointment.class);
        when(mockAppointment.getUser()).thenReturn(user);
        when(mockAppointment.getTimeSlot()).thenReturn(slot);

        reminderService.sendReminder(mockAppointment);
        verify(mockObserver, times(1)).notify(eq(user), anyString());
    }

    @Test
    void testRemoveObserver_removedObserverNotNotified() {
        reminderService.registerObserver(mockObserver);
        reminderService.removeObserver(mockObserver);
        
        User user = new User(1, "Test", "test@test.com", "pass", "USER");
        reminderService.notifyAllObservers(user, "Test Message");
        
        verify(mockObserver, never()).notify(any(User.class), anyString());
    }

    @Test
    void testSendReminder_nullCases() {
        reminderService.registerObserver(mockObserver);
        
        // Null appointment
        reminderService.sendReminder(null);
        verify(mockObserver, never()).notify(any(User.class), anyString());
        
        // Null user
        Appointment mockAppointment = Mockito.mock(Appointment.class);
        when(mockAppointment.getUser()).thenReturn(null);
        reminderService.sendReminder(mockAppointment);
        verify(mockObserver, never()).notify(any(User.class), anyString());
    }

    @Test
    void testDoubleRegistration() {
        reminderService.registerObserver(mockObserver);
        reminderService.registerObserver(mockObserver); // Should not duplicate
        
        User user = new User(1, "Test", "test@test.com", "pass", "USER");
        reminderService.notifyAllObservers(user, "Hello");
        
        verify(mockObserver, times(1)).notify(eq(user), eq("Hello"));
    }
}

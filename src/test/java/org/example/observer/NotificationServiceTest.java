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

class NotificationServiceTest {

    
    @BeforeEach
    void setUp() {

    }

    
    @Test
    void testSendReminder_notifiesAllObservers() {
<<<<<<< Updated upstream
=======
        reminderService.registerObserver(mockObserver);
        
        User user = new User(1, "Test", "test@test.com", "pass", "USER");
        TimeSlot slot = new TimeSlot(1, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), false);
        Appointment mockAppointment = Mockito.mock(Appointment.class);
        when(mockAppointment.getUser()).thenReturn(user);
        when(mockAppointment.getTimeSlot()).thenReturn(slot);
        when(mockAppointment.getStatus()).thenReturn(AppointmentStatus.CONFIRMED);
        when(mockAppointment.getId()).thenReturn(1);
>>>>>>> Stashed changes

    }

    
    @Test
    void testRemoveObserver_removedObserverNotNotified() {

    }

    
    @Test
    void testSendCustomReminder_correctMessagePassed() {

    }
}

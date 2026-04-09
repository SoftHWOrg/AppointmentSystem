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

    }

    
    @Test
    void testRemoveObserver_removedObserverNotNotified() {

    }

    
    @Test
    void testSendCustomReminder_correctMessagePassed() {

    }
}

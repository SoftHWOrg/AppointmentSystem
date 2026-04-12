package org.example.service;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;
import org.example.observer.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

public class ReminderServiceTest {

    private ReminderService reminderService;
    private Observer mockEmailObserver;
    private Observer mockSmsObserver;

    @BeforeEach
    public void setUp() {
        reminderService = new ReminderService();
        mockEmailObserver = Mockito.mock(Observer.class);
        mockSmsObserver = Mockito.mock(Observer.class);

        reminderService.registerObserver(mockEmailObserver);
        reminderService.registerObserver(mockSmsObserver);
    }

    @Test
    public void testSendReminder() {
        User user = new User(1, "Test User", "test@test.com", "pass", "USER");
        TimeSlot slot = new TimeSlot(1, LocalDate.of(2025, 1, 1), LocalTime.of(10, 0), LocalTime.of(11, 0), false);

        Appointment mockAppointment = Mockito.mock(Appointment.class);
        Mockito.when(mockAppointment.getUser()).thenReturn(user);
        Mockito.when(mockAppointment.getTimeSlot()).thenReturn(slot);
        Mockito.when(mockAppointment.getId()).thenReturn(123);
        Mockito.when(mockAppointment.getType()).thenReturn(AppointmentType.URGENT);
        Mockito.when(mockAppointment.getStatus()).thenReturn(AppointmentStatus.CONFIRMED);

        reminderService.sendReminder(mockAppointment);

        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockEmailObserver, times(1)).notify(eq(user), messageCaptor.capture());
        verify(mockSmsObserver, times(1)).notify(eq(user), messageCaptor.capture());

        String capturedMessage = messageCaptor.getValue();
        
        assertTrue(capturedMessage.contains("URGENT"));
        assertTrue(capturedMessage.contains("123"));
        assertTrue(capturedMessage.contains("2025-01-01"));
        assertTrue(capturedMessage.contains("10:00"));
        assertTrue(capturedMessage.contains("CONFIRMED"));
    }

    @Test
    void testSendReminder_NullSafety() {
        reminderService.sendReminder(null);
        reminderService.sendReminder(new org.example.domain.appointment.DefaultAppointment(1, null, null, null, 1));
        
        verify(mockEmailObserver, never()).notify(any(), anyString());
    }
}


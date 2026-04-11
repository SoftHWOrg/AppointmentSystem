package org.example.service;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;
import org.example.observer.Observer;
import org.example.observer.TestObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReminderServiceTest {

    private ReminderService reminderService;
    private TestObserver testEmailObserver;
    private TestObserver testSmsObserver;

    @BeforeEach
    public void setUp() {
        reminderService = new ReminderService();
        testEmailObserver = new TestObserver();
        testSmsObserver = new TestObserver();

        reminderService.registerObserver(testEmailObserver);
        reminderService.registerObserver(testSmsObserver);
    }

    @Test
    public void testSendReminder() {
        // Arrange
        User user = new User(1, "Test User", "test@test.com", "pass", "USER");
        TimeSlot slot = new TimeSlot(1, LocalDate.of(2025, 1, 1), LocalTime.of(10, 0), LocalTime.of(11, 0), false);
        // Using real Appointment objects to avoid Mockito issues
        Appointment appt = new org.example.domain.appointment.UrgentAppointment(1, user, slot, AppointmentStatus.CONFIRMED, 1);

        // Act
        reminderService.sendReminder(appt);

        // Assert
        assertEquals(1, testEmailObserver.getCount());
        assertEquals(1, testSmsObserver.getCount());

        String capturedMessage = testEmailObserver.notifications.get(0).message;
        assertTrue(capturedMessage.contains("10:00"), "Message should contain start time");
        assertTrue(capturedMessage.contains("2025-01-01"), "Message should contain date");
        assertTrue(capturedMessage.toLowerCase().contains("urgent"), "Message should contain appointment type");
    }
}

package org.example.service;

import org.example.domain.appointment.UrgentAppointment;
import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.valueobject.TimeSlot;
import org.example.repository.AppointmentRepository;
import org.example.strategy.BookingRuleStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link AppointmentService}.
 * All dependencies are mocked with Mockito.
 *
 * @author
 * @version 1.0
 */
class AppointmentServiceTest {

    // TODO: Add field: AppointmentRepository mockRepo
    // TODO: Add field: ScheduleService mockScheduleService
    // TODO: Add field: ReminderService mockReminderService
    // TODO: Add field: BookingRuleStrategy mockRule
    // TODO: Add field: AppointmentService appointmentService

    /**
     * TODO:
     * - Create all mocks with Mockito.mock(...)
     * - Create appointmentService = new AppointmentService(
     * mockRepo, mockScheduleService, mockReminderService, List.of(mockRule))
     */
    @BeforeEach
    void setUp() {
        // TODO: initialize all mocks
    }

    /**
     * US2.1 — Booking should call repository.save() and scheduleService.bookSlot().
     *
     * TODO:
     * 1. Create a valid appointment (e.g. UrgentAppointment with a future TimeSlot)
     * 2. Stub mockRule.isValid(appointment) → true
     * 3. Call appointmentService.bookAppointment(appointment)
     * 4. verify(mockRepo, times(1)).save(appointment)
     * 5. verify(mockScheduleService, times(1)).bookSlot(anyInt())
     */
    @Test
    void testBookAppointment_validAppointment_savesCalled() {
        // TODO: implement test
    }

    /**
     * US2.2/US2.3 — Booking should throw if any rule returns false.
     *
     * TODO:
     * 1. Stub mockRule.isValid(any()) → false
     * 2. Stub mockRule.getErrorMessage() → "Rule violated"
     * 3. assertThrows(IllegalArgumentException.class, () ->
     * appointmentService.bookAppointment(appointment))
     * 4. verify(mockRepo, never()).save(any()) ← save must NOT be called
     */
    @Test
    void testBookAppointment_ruleViolated_throwsAndDoesNotSave() {
        // TODO: implement test
    }

    /**
     * US4.1 — Cancelling a future appointment should update status to CANCELLED.
     *
     * TODO:
     * 1. Create a mock appointment in the future
     * 2. Stub mockRepo.findById(1) → appointment
     * 3. Call appointmentService.cancelAppointment(1, user)
     * 4. verify(mockRepo).update(appointment)
     * 5. assertEquals(AppointmentStatus.CANCELLED, appointment.getStatus())
     */
    @Test
    void testCancelAppointment_futureAppointment_statusCancelled() {
        // TODO: implement test
    }

    /**
     * US4.2 — Non-admin user cancelling another user's appointment should throw
     * SecurityException.
     *
     * TODO:
     * 1. Create appointment belonging to user A
     * 2. Create user B (non-admin)
     * 3. Call cancelAppointment(id, userB) inside
     * assertThrows(SecurityException.class, ...)
     */
    @Test
    void testCancelAppointment_nonAdminOtherUser_throwsSecurityException() {
        // TODO: implement test
    }
}

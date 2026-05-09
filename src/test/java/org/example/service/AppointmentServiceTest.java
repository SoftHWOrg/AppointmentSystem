package org.example.service;

import org.example.domain.appointment.DefaultAppointment;
import org.example.domain.entity.Administrator;
import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.valueobject.TimeSlot;
import org.example.repository.AppointmentRepository;
import org.example.strategy.BookingRuleStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Appointment Service Tests")
class AppointmentServiceTest {

    private AppointmentRepository appointmentRepo;
    private ScheduleService scheduleService;
    private ReminderService reminderService;
    private AppointmentService appointmentService;
    private List<BookingRuleStrategy> rules;

    private User regularUser;
    private Administrator adminUser;
    private TimeSlot testSlot;

    @BeforeEach
    void setUp() {
        appointmentRepo = mock(AppointmentRepository.class);
        scheduleService = mock(ScheduleService.class);
        reminderService = mock(ReminderService.class);
        rules = new ArrayList<>();
        
        appointmentService = new AppointmentService(appointmentRepo, scheduleService, reminderService, rules);
        
        regularUser = new User(1, "Regular User", "user@test.com", "pass", "USER");
        adminUser = new Administrator(99, "Admin User", "admin@test.com", "adminpass");
        testSlot = new TimeSlot(10, LocalDate.of(2026, 5, 20), LocalTime.of(10, 0), LocalTime.of(11, 0), false);
    }

    @Test
    @DisplayName("Admin Visibility: Should return all appointments for Admin")
    void testAdminCanSeeAllAppointments() {
        List<Appointment> allApps = new ArrayList<>();
        allApps.add(new DefaultAppointment(1, regularUser, testSlot, AppointmentStatus.CONFIRMED, 1));
        allApps.add(new DefaultAppointment(2, adminUser, testSlot, AppointmentStatus.CONFIRMED, 1));
        when(appointmentRepo.findAll()).thenReturn(allApps);

        List<Appointment> results = appointmentService.getAllAppointments(adminUser);

        assertEquals(2, results.size());
        verify(appointmentRepo).findAll();
    }

    @Test
    @DisplayName("Cancellation: Should permanently delete appointment and free timeslot")
    void testCancelAppointment_PermanentlyDeletesAndFreesSlot() {
        Appointment appt = new DefaultAppointment(1, regularUser, testSlot, AppointmentStatus.CONFIRMED, 1);
        when(appointmentRepo.findById(1)).thenReturn(appt);

        appointmentService.cancelAppointment(1, regularUser);

        verify(scheduleService).freeSlot(testSlot);
        verify(appointmentRepo).delete(1);
        verify(reminderService).sendReminder(any(Appointment.class));
        assertEquals(AppointmentStatus.CANCELLED, appt.getStatus()); 
    }

    @Test
    @DisplayName("Modification: Should re-book the slot when an appointment is modified")
    void testModifyAppointment_BooksSlot() {
        Appointment appt = new DefaultAppointment(1, regularUser, testSlot, AppointmentStatus.CONFIRMED, 1);

        appointmentService.modifyAppointment(appt);

        verify(appointmentRepo).update(appt);
        verify(scheduleService).bookSlot(testSlot.getId());
    }

    @Test
    @DisplayName("Permissions: Admin should be able to delete any user's appointment")
    void testAdminCanCancelAnyUserAppointment() {
        Appointment userAppt = new DefaultAppointment(5, regularUser, testSlot, AppointmentStatus.CONFIRMED, 1);
        when(appointmentRepo.findById(5)).thenReturn(userAppt);

        appointmentService.cancelAppointment(5, adminUser);

        verify(appointmentRepo).delete(5);
        verify(scheduleService).freeSlot(testSlot);
        verify(reminderService).sendReminder(any(Appointment.class));
    }

    @Test
    @DisplayName("Permissions: Regular user should NOT be able to cancel someone else's appointment")
    void testRegularUserCannotCancelOtherUserAppointment() {
        User otherUser = new User(2, "Other User", "other@test.com", "pass", "USER");
        Appointment userAppt = new DefaultAppointment(5, regularUser, testSlot, AppointmentStatus.CONFIRMED, 1);
        when(appointmentRepo.findById(5)).thenReturn(userAppt);

        assertThrows(SecurityException.class, () -> appointmentService.cancelAppointment(5, otherUser));
    }

    @Test
    @DisplayName("Permissions: Regular user should NOT be able to see all appointments")
    void testRegularUserCannotSeeAllAppointments() {
        assertThrows(SecurityException.class, () -> appointmentService.getAllAppointments(regularUser));
    }

    @Test
    @DisplayName("Booking: Should fail if a rule is violated")
    void testBookAppointment_FailsOnRuleViolation() {
        BookingRuleStrategy mockRule = mock(BookingRuleStrategy.class);
        when(mockRule.isValid(any())).thenReturn(false);
        when(mockRule.getErrorMessage()).thenReturn("Rule Violated");
        
        rules.add(mockRule);
        
        Appointment appt = new DefaultAppointment(0, regularUser, testSlot, AppointmentStatus.PENDING, 1);
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> appointmentService.bookAppointment(appt));
        assertEquals("Rule Violated", ex.getMessage());
        verify(appointmentRepo, never()).save(any());
    }

    @Test
    @DisplayName("Booking: Should succeed if all rules pass")
    void testBookAppointment_Success() {
        BookingRuleStrategy mockRule = mock(BookingRuleStrategy.class);
        when(mockRule.isValid(any())).thenReturn(true);
        rules.add(mockRule);

        Appointment appt = new DefaultAppointment(0, regularUser, testSlot, AppointmentStatus.PENDING, 1);
        
        appointmentService.bookAppointment(appt);
        
        verify(appointmentRepo).save(appt);
        verify(scheduleService).bookSlot(testSlot.getId());
        verify(reminderService).sendReminder(appt);
    }

    @Test
    @DisplayName("Retrieval: Should find appointments by User ID")
    void testGetAppointmentsForUser() {
        List<Appointment> apps = Collections.singletonList(new DefaultAppointment(1, regularUser, testSlot, AppointmentStatus.CONFIRMED, 1));
        when(appointmentRepo.findByUserId(1)).thenReturn(apps);

        List<Appointment> results = appointmentService.getAppointmentsForUser(1);

        assertEquals(1, results.size());
        verify(appointmentRepo).findByUserId(1);
    }

    @Test
    @DisplayName("Modification: Should fail if appointment is null")
    void testModifyAppointment_Null() {
        assertThrows(IllegalArgumentException.class, () -> appointmentService.modifyAppointment(null));
    }

    @Test
    @DisplayName("Modification: Should fail if appointment is in the past")
    void testModifyAppointment_PastDate() {
        TimeSlot pastSlot = new TimeSlot(11, LocalDate.now().minusDays(1), LocalTime.of(10, 0), LocalTime.of(11, 0), false);
        Appointment pastAppt = new DefaultAppointment(1, regularUser, pastSlot, AppointmentStatus.CONFIRMED, 1);
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> appointmentService.modifyAppointment(pastAppt));
        assertEquals("Only future appointments can be modified.", ex.getMessage());
    }

    @Test
    @DisplayName("Cancellation: Should fail if appointment is in the past")
    void testCancelAppointment_PastDate() {
        TimeSlot pastSlot = new TimeSlot(11, LocalDate.now().minusDays(1), LocalTime.of(10, 0), LocalTime.of(11, 0), false);
        Appointment pastAppt = new DefaultAppointment(1, regularUser, pastSlot, AppointmentStatus.CONFIRMED, 1);
        when(appointmentRepo.findById(1)).thenReturn(pastAppt);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> appointmentService.cancelAppointment(1, regularUser));
        assertEquals("Only future appointments can be cancelled.", ex.getMessage());
    }

    @Test
    @DisplayName("Cancellation: Should fail if appointment not found")
    void testCancelAppointment_NotFound() {
        when(appointmentRepo.findById(999)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> appointmentService.cancelAppointment(999, regularUser));
    }

    @Test
    @DisplayName("Booking: Should handle null rules")
    void testBookAppointment_NullRules() {
        AppointmentService serviceNoRules = new AppointmentService(appointmentRepo, scheduleService, reminderService, null);
        Appointment appt = new DefaultAppointment(1, regularUser, testSlot, AppointmentStatus.PENDING, 1);
        
        serviceNoRules.bookAppointment(appt);
        verify(appointmentRepo).save(appt);
    }
}

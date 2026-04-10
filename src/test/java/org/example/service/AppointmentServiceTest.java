package org.example.service;

import org.example.domain.appointment.CustomAppointment;
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
        allApps.add(new CustomAppointment(1, regularUser, testSlot, AppointmentStatus.CONFIRMED, 1));
        allApps.add(new CustomAppointment(2, adminUser, testSlot, AppointmentStatus.CONFIRMED, 1));
        when(appointmentRepo.findAll()).thenReturn(allApps);

        List<Appointment> results = appointmentService.getAllAppointments(adminUser);

        assertEquals(2, results.size());
        verify(appointmentRepo).findAll();
    }

    @Test
    @DisplayName("Cancellation: Should permanently delete appointment and free timeslot")
    void testCancelAppointment_PermanentlyDeletesAndFreesSlot() {
        Appointment appt = new CustomAppointment(1, regularUser, testSlot, AppointmentStatus.CONFIRMED, 1);
        when(appointmentRepo.findById(1)).thenReturn(appt);

        appointmentService.cancelAppointment(1, regularUser);

        verify(scheduleService).freeSlot(testSlot);
        verify(appointmentRepo).delete(1);
        verify(reminderService).sendReminder(any());
        assertEquals(AppointmentStatus.CANCELLED, appt.getStatus()); 
    }

    @Test
    @DisplayName("Modification: Should re-book the slot when an appointment is modified")
    void testModifyAppointment_BooksSlot() {
        Appointment appt = new CustomAppointment(1, regularUser, testSlot, AppointmentStatus.CONFIRMED, 1);

        appointmentService.modifyAppointment(appt);

        verify(appointmentRepo).update(appt);
        verify(scheduleService).bookSlot(testSlot.getId());
    }

    @Test
    @DisplayName("Permissions: Admin should be able to delete any user's appointment")
    void testAdminCanCancelAnyUserAppointment() {
        Appointment userAppt = new CustomAppointment(5, regularUser, testSlot, AppointmentStatus.CONFIRMED, 1);
        when(appointmentRepo.findById(5)).thenReturn(userAppt);

        appointmentService.cancelAppointment(5, adminUser);

        verify(appointmentRepo).delete(5);
        verify(scheduleService).freeSlot(testSlot);
    }
}

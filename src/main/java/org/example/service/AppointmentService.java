package org.example.service;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.Administrator;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.repository.AppointmentRepository;
import org.example.strategy.BookingRuleStrategy;

import java.util.List;

public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ScheduleService scheduleService;
    private final ReminderService reminderService;
    private final List<BookingRuleStrategy> rules;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              ScheduleService scheduleService,
                              ReminderService reminderService,
                              List<BookingRuleStrategy> rules) {
        this.appointmentRepository = appointmentRepository;
        this.scheduleService = scheduleService;
        this.reminderService = reminderService;
        this.rules = rules;
    }

    /** Validates booking rules then persists the appointment. */
    public void bookAppointment(Appointment appointment) {
        System.out.println("[DEBUG] bookAppointment started for type: " + appointment.getType());
        if (rules != null) {
            for (BookingRuleStrategy rule : rules) {
                if (!rule.isValid(appointment)) {
                    System.out.println("[DEBUG] Rule failed: " + rule.getClass().getSimpleName());
                    throw new IllegalArgumentException(rule.getErrorMessage());
                }
            }
        }
        System.out.println("[DEBUG] Rules passed. Saving appointment...");
        appointmentRepository.save(appointment);
        System.out.println("[DEBUG] Appointment saved. Booking slot...");
        scheduleService.bookSlot(appointment.getTimeSlot().getId());
        System.out.println("[DEBUG] Slot booked. Sending reminder...");
        reminderService.sendReminder(appointment);
        System.out.println("[DEBUG] bookAppointment completed.");
    }

    /** Updates an existing appointment record. */
    public void modifyAppointment(Appointment appointment) {
        appointmentRepository.update(appointment);
    }

    /**
     * Cancels an appointment.  Only the owning user or an admin may cancel.
     */
    public void cancelAppointment(int id, User requestingUser) {
        System.out.println("[DEBUG] cancelAppointment started for ID: " + id);
        Appointment appointment = appointmentRepository.findById(id);
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment #" + id + " not found.");
        }

        boolean isAdmin = requestingUser instanceof Administrator
                || "ADMIN".equalsIgnoreCase(requestingUser.getRole());
        boolean isOwner = appointment.getUser().getId() == requestingUser.getId();

        if (!isAdmin && !isOwner) {
            throw new SecurityException("You do not have permission to cancel this appointment.");
        }

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Appointment #" + id + " is already cancelled.");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.update(appointment);
        scheduleService.freeSlot(appointment.getTimeSlot().getId());
        reminderService.sendReminder(requestingUser, "Appointment #" + id + " has been cancelled.");
        System.out.println("[DEBUG] cancelAppointment completed.");
    }

    /** Returns all appointments belonging to the given user. */
    public List<Appointment> getAppointmentsForUser(int userId) {
        return appointmentRepository.findByUserId(userId);
    }

    /** Returns all appointments in the system (admin-only). */
    public List<Appointment> getAllAppointments(User requestingUser) {
        boolean isAdmin = requestingUser instanceof Administrator
                || "ADMIN".equalsIgnoreCase(requestingUser.getRole());
        if (!isAdmin) {
            throw new SecurityException("Only administrators can view all appointments.");
        }
        return appointmentRepository.findAll();
    }
}


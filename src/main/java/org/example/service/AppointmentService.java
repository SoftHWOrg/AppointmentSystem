package org.example.service;

import org.example.domain.entity.Administrator;
import org.example.domain.entity.Appointment;
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

        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointmentRepository.save(appointment);
        scheduleService.bookSlot(appointment.getTimeSlot().getId());
        
        if (reminderService != null) {
            reminderService.sendReminder(appointment);
        }
        System.out.println("[DEBUG] bookAppointment completed.");
    }

    /** Updates an existing appointment record (Robust version). */
    public void modifyAppointment(Appointment appointment) {
        if (appointment == null) throw new IllegalArgumentException("Appointment cannot be null");
        
        // Re-validate booking rules
        if (rules != null) {
            for (BookingRuleStrategy rule : rules) {
                if (!rule.isValid(appointment)) {
                    throw new IllegalArgumentException(rule.getErrorMessage());
                }
            }
        }

        appointmentRepository.update(appointment);
        
        // Ensure the slot is booked if it was just changed/added
        if (appointment.getTimeSlot() != null) {
            scheduleService.bookSlot(appointment.getTimeSlot().getId());
        }

        if (reminderService != null) {
            reminderService.sendReminder(appointment);
        }
    }

    /**
     * Cancels an appointment. Only the owning user or an admin may cancel.
     * Uses permanent deletion as per Sprint 3 requirements.
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

        // Free the timeslot and sync the object state
        if (appointment.getTimeSlot() != null) {
            scheduleService.freeSlot(appointment.getTimeSlot());
        }

        // Delete the appointment permanently (Sprint 3 Requirement)
        appointmentRepository.delete(id);

        // Notify user about deletion/cancellation
        if (reminderService != null) {
            appointment.setStatus(AppointmentStatus.CANCELLED);
            reminderService.sendReminder(appointment);
        }
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

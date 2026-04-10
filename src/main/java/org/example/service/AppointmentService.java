package org.example.service;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.valueobject.TimeSlot;
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

    public void bookAppointment(Appointment appointment) {
        if (rules != null) {
            for (BookingRuleStrategy rule : rules) {
                if (!rule.isValid(appointment)) {
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
    }

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

    public void cancelAppointment(int id, User requestingUser) {
        Appointment appointment = appointmentRepository.findById(id);
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment not found");
        }

        // Security check: only the owner or an admin can cancel
        if (requestingUser.getId() != appointment.getUser().getId() && !"ADMIN".equals(requestingUser.getRole())) {
            throw new SecurityException("You do not have permission to cancel this appointment");
        }

        // Free the timeslot and sync the object state
        if (appointment.getTimeSlot() != null) {
            scheduleService.freeSlot(appointment.getTimeSlot());
        }

        // Delete the appointment permanently
        appointmentRepository.delete(id);

        // Notify user about deletion/cancellation
        if (reminderService != null) {
            // We set status to CANCELLED just for the notification message
            appointment.setStatus(AppointmentStatus.CANCELLED);
            reminderService.sendReminder(appointment);
        }
    }

    public List<Appointment> getAppointmentsForUser(int userId) {
        return appointmentRepository.findByUserId(userId);
    }

    public List<Appointment> getAllAppointments(User requestingUser) {
        return appointmentRepository.findAll();
    }
}

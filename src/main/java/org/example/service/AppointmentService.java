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

    public void modifyAppointment(Appointment appointment) {
        if (appointment == null) throw new IllegalArgumentException("Appointment cannot be null");
        
        if (appointment.getTimeSlot() != null && appointment.getTimeSlot().getDate() != null && !appointment.getTimeSlot().getDate().isAfter(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Only future appointments can be modified.");
        }
        
        if (rules != null) {
            for (BookingRuleStrategy rule : rules) {
                if (!rule.isValid(appointment)) {
                    throw new IllegalArgumentException(rule.getErrorMessage());
                }
            }
        }

        appointmentRepository.update(appointment);
        
        if (appointment.getTimeSlot() != null) {
            scheduleService.bookSlot(appointment.getTimeSlot().getId());
        }

        if (reminderService != null) {
            reminderService.sendReminder(appointment);
        }
    }

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

        if (appointment.getTimeSlot() != null && appointment.getTimeSlot().getDate() != null && !appointment.getTimeSlot().getDate().isAfter(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Only future appointments can be cancelled.");
        }

        if (appointment.getTimeSlot() != null) {
            scheduleService.freeSlot(appointment.getTimeSlot());
        }

        appointmentRepository.delete(id);

        if (reminderService != null) {
            appointment.setStatus(AppointmentStatus.CANCELLED);
            reminderService.sendReminder(appointment);
        }
        System.out.println("[DEBUG] cancelAppointment completed.");
    }

    public List<Appointment> getAppointmentsForUser(int userId) {
        return appointmentRepository.findByUserId(userId);
    }

    public List<Appointment> getAllAppointments(User requestingUser) {
        boolean isAdmin = requestingUser instanceof Administrator
                || "ADMIN".equalsIgnoreCase(requestingUser.getRole());
        if (!isAdmin) {
            throw new SecurityException("Only administrators can view all appointments.");
        }
        return appointmentRepository.findAll();
    }
}

package org.example.service;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.repository.AppointmentRepository;
import org.example.strategy.BookingRuleStrategy;

import java.time.LocalDateTime;
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
    }

    
    public void modifyAppointment(Appointment appointment) {

    }

    
    public void cancelAppointment(int id, User requestingUser) {

    }

    
    public List<Appointment> getAppointmentsForUser(int userId) {
        return appointmentRepository.findByUserId(userId);
    }

    
    public List<Appointment> getAllAppointments(User requestingUser) {
        return appointmentRepository.findAll();
    }
}

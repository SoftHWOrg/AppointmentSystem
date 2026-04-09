package org.example.service;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.repository.AppointmentRepository;
import org.example.strategy.BookingRuleStrategy;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentService {

    
    public AppointmentService(AppointmentRepository appointmentRepository,
                              ScheduleService scheduleService,
                              ReminderService reminderService,
                              List<BookingRuleStrategy> rules) {

    }

    
    public void bookAppointment(Appointment appointment) {

    }

    
    public void modifyAppointment(Appointment appointment) {

    }

    
    public void cancelAppointment(int id, User requestingUser) {

    }

    
    public List<Appointment> getAppointmentsForUser(int userId) {

        return null;
    }

    
    public List<Appointment> getAllAppointments(User requestingUser) {

        return null;
    }
}

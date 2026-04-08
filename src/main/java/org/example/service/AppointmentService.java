package org.example.service;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.repository.AppointmentRepository;
import org.example.strategy.BookingRuleStrategy;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Core service for booking, modifying, and cancelling appointments.
 * Covers: US2.1 (book), US4.1 (user modify/cancel), US4.2 (admin manage).
 *
 * Uses the Strategy Pattern — a list of BookingRuleStrategy objects
 * is applied before every booking to enforce all business rules.
 *
 * @author
 * @version 1.0
 */
public class AppointmentService {

    // TODO: Add field: AppointmentRepository appointmentRepository
    // TODO: Add field: ScheduleService scheduleService
    // TODO: Add field: ReminderService reminderService
    // TODO: Add field: List<BookingRuleStrategy> rules   (injected strategies)

    /**
     * Constructor — inject all dependencies.
     *
     * TODO: Assign all fields.
     *
     * @param appointmentRepository data access for appointments
     * @param scheduleService       manages slot availability
     * @param reminderService       sends notifications after booking
     * @param rules                 list of booking validation strategies
     */
    public AppointmentService(AppointmentRepository appointmentRepository,
                              ScheduleService scheduleService,
                              ReminderService reminderService,
                              List<BookingRuleStrategy> rules) {
        // TODO: assign all fields
    }

    /**
     * Books a new appointment (US2.1).
     *
     * TODO:
     *  1. Loop through all rules: call rule.isValid(appointment)
     *     - If any returns false → throw new IllegalArgumentException(rule.getErrorMessage())
     *  2. Set appointment status = AppointmentStatus.CONFIRMED
     *  3. Call appointmentRepository.save(appointment)
     *  4. Call scheduleService.bookSlot(appointment.getTimeSlot().getId())
     *  5. Call reminderService.sendReminder(appointment.getUser(), "Your appointment is confirmed!")
     *
     * @param appointment the appointment to book
     * @throws IllegalArgumentException if any booking rule is violated
     */
    public void bookAppointment(Appointment appointment) {
        // TODO: implement booking with rule validation
    }

    /**
     * Modifies an existing appointment (US4.1).
     * Only future appointments can be modified.
     *
     * TODO:
     *  1. Find existing: appointmentRepository.findById(appointment.getId())
     *  2. Check that the appointment's time slot is in the future
     *     - If not → throw new IllegalStateException("Cannot modify past appointments")
     *  3. Run all booking rules on the updated appointment
     *  4. Call appointmentRepository.update(appointment)
     *  5. Update slot availability if the time slot changed
     *
     * @param appointment the appointment with updated values
     * @throws IllegalStateException    if the appointment is in the past
     * @throws IllegalArgumentException if any rule is violated
     */
    public void modifyAppointment(Appointment appointment) {
        // TODO: implement modification logic
    }

    /**
     * Cancels an appointment (US4.1 for users, US4.2 for admins).
     * Only future appointments can be cancelled.
     *
     * TODO:
     *  1. Find existing: appointmentRepository.findById(id)
     *  2. Check that the slot is in the future
     *  3. Set status = AppointmentStatus.CANCELLED
     *  4. Call appointmentRepository.update(appointment)
     *  5. Call scheduleService.freeSlot(appointment.getTimeSlot().getId())
     *  6. Notify the user: reminderService.sendReminder(user, "Your appointment has been cancelled.")
     *
     * @param id          the appointment ID to cancel
     * @param requestingUser the user requesting the cancellation
     * @throws IllegalStateException if the appointment is in the past
     * @throws SecurityException     if a non-admin tries to cancel another user's appointment
     */
    public void cancelAppointment(int id, User requestingUser) {
        // TODO: implement cancellation logic
    }

    /**
     * Returns all appointments for a given user.
     *
     * TODO: return appointmentRepository.findByUserId(userId);
     *
     * @param userId the user's ID
     * @return list of that user's appointments
     */
    public List<Appointment> getAppointmentsForUser(int userId) {
        // TODO: implement
        return null;
    }

    /**
     * Returns all appointments in the system — admin only (US4.2).
     *
     * TODO:
     *  - Check that requestingUser.getRole().equals("ADMIN")
     *  - If not → throw new SecurityException("Admin access required")
     *  - Return appointmentRepository.findAll()
     *
     * @param requestingUser the user requesting the list
     * @return all appointments
     * @throws SecurityException if the user is not an admin
     */
    public List<Appointment> getAllAppointments(User requestingUser) {
        // TODO: implement
        return null;
    }
}

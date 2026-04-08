package org.example.domain.entity;

import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;

/**
 * Abstract base class for all appointment types.
 * All 7 concrete appointment types (Urgent, FollowUp, etc.) extend this class.
 *
 * Uses polymorphism (Sprint 5) — each subclass overrides getMaxDuration()
 * and getMaxParticipants() to enforce its own business rules.
 *
 * @author
 * @version 1.0
 */
public abstract class Appointment {

    // TODO: Add field: int id
    // TODO: Add field: User user                  (who booked it)
    // TODO: Add field: TimeSlot timeSlot          (when it is)
    // TODO: Add field: AppointmentType type       (URGENT, FOLLOW_UP, etc.)
    // TODO: Add field: AppointmentStatus status   (CONFIRMED, CANCELLED, PENDING)
    // TODO: Add field: int participants           (number of people attending)

    /**
     * Full constructor.
     *
     * TODO: Add parameters (id, user, timeSlot, type, status, participants)
     *       and assign each to its field.
     */
    public Appointment(int id, User user, TimeSlot timeSlot,
                       AppointmentType type, AppointmentStatus status, int participants) {
        // TODO: assign all fields
    }

    // TODO: Add getters and setters for all fields.

    /**
     * Returns the maximum allowed duration in minutes for this appointment type.
     * Each subclass must define its own limit (Sprint 5 — US5.2).
     *
     * Example: UrgentAppointment returns 30, GroupAppointment returns 120.
     *
     * @return max duration in minutes
     */
    public abstract int getMaxDuration();

    /**
     * Returns the maximum number of participants allowed for this appointment type.
     * Each subclass must define its own limit (Sprint 5 — US5.2).
     *
     * Example: IndividualAppointment returns 1, GroupAppointment returns 20.
     *
     * @return max participants allowed
     */
    public abstract int getMaxParticipants();

    /**
     * TODO: Override toString() to return a readable summary,
     *       e.g. "Appointment{id=1, type=URGENT, status=CONFIRMED, user=John}"
     */
    @Override
    public String toString() {
        // TODO: implement
        return "";
    }
}

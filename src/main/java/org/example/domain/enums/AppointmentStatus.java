package org.example.domain.enums;

/**
 * Represents the current status of an appointment.
 * Used in {@link org.example.domain.entity.Appointment}.
 *
 * @author
 * @version 1.0
 */
public enum AppointmentStatus {

    /**
     * Appointment has been successfully booked (US2.1).
     */
    CONFIRMED,

    /**
     * Appointment was cancelled by the user or admin (US4.1, US4.2).
     */
    CANCELLED,

    /**
     * Appointment is awaiting confirmation — optional intermediate state.
     * TODO: Use this if you want a two-step booking process (book → confirm).
     *       You can remove it if your flow goes directly to CONFIRMED.
     */
    PENDING
}

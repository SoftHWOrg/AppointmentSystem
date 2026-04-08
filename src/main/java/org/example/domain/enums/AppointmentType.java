package org.example.domain.enums;

/**
 * Represents the type of an appointment.
 * Each type maps to a concrete subclass of {@link org.example.domain.entity.Appointment}
 * and has its own rules for duration and participants (Sprint 5 — US5.1, US5.2).
 *
 * @author
 * @version 1.0
 */
public enum AppointmentType {

    /** Urgent/emergency appointment — short duration, single participant. */
    URGENT,

    /** Follow-up appointment after a previous visit. */
    FOLLOW_UP,

    /** Assessment or evaluation appointment. */
    ASSESSMENT,

    /** Remote/online appointment (no physical location needed). */
    VIRTUAL,

    /** Physical, face-to-face appointment. */
    IN_PERSON,

    /** One-on-one appointment — only 1 participant allowed. */
    INDIVIDUAL,

    /** Group appointment — multiple participants allowed. */
    GROUP
}

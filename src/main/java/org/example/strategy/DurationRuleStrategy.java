package org.example.strategy;

import org.example.domain.entity.Appointment;

/**
 * Validates that an appointment's duration does not exceed the allowed maximum.
 * Implements the Strategy Pattern (US2.2 — Enforce visit duration rule).
 *
 * The max duration is fetched from the appointment itself via getMaxDuration(),
 * which each appointment subclass overrides (polymorphism — Sprint 5).
 *
 * @author
 * @version 1.0
 */
public class DurationRuleStrategy implements BookingRuleStrategy {

    /**
     * Checks if the appointment's time slot duration is within the allowed limit.
     *
     * TODO:
     *  1. Get the TimeSlot from appointment.getTimeSlot()
     *  2. Calculate duration: slot.getDurationInMinutes()
     *  3. Get max allowed: appointment.getMaxDuration()
     *  4. Return true if duration <= maxDuration, false otherwise
     *
     * @param appointment the appointment to validate
     * @return true if duration is within limit
     */
    @Override
    public boolean isValid(Appointment appointment) {
        // TODO: implement duration check
        return false;
    }

    /**
     * TODO: Return a clear error message like:
     *       "Appointment duration exceeds the maximum allowed for this type."
     *
     * @return error message shown to the user
     */
    @Override
    public String getErrorMessage() {
        // TODO: return "Appointment duration exceeds the maximum allowed for this type.";
        return "";
    }
}

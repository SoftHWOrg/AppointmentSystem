package org.example.strategy;

import org.example.domain.entity.Appointment;

/**
 * Validates that the number of participants does not exceed the allowed maximum.
 * Implements the Strategy Pattern (US2.3 — Enforce participant limit).
 *
 * The max participants is fetched from appointment.getMaxParticipants(),
 * which each subclass overrides (polymorphism — Sprint 5).
 *
 * @author
 * @version 1.0
 */
public class ParticipantLimitRuleStrategy implements BookingRuleStrategy {

    /**
     * Checks if the participant count is within the allowed limit.
     *
     * TODO:
     *  1. Get participants count: appointment.getParticipants()
     *  2. Get max allowed: appointment.getMaxParticipants()
     *  3. Return true if participants <= maxParticipants, false otherwise
     *
     * @param appointment the appointment to validate
     * @return true if participant count is within limit
     */
    @Override
    public boolean isValid(Appointment appointment) {
        // TODO: implement participant limit check
        return false;
    }

    /**
     * TODO: Return a clear error message like:
     *       "Number of participants exceeds the maximum allowed for this appointment type."
     *
     * @return error message shown to the user
     */
    @Override
    public String getErrorMessage() {
        // TODO: return "Number of participants exceeds the maximum allowed for this appointment type.";
        return "";
    }
}

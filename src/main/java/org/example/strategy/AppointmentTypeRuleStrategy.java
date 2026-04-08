package org.example.strategy;

import org.example.domain.entity.Appointment;
import org.example.domain.enums.AppointmentType;

/**
 * Validates type-specific business rules for an appointment.
 * Implements the Strategy Pattern (US5.2 — Apply different rules per type).
 *
 * Examples of type-specific rules:
 *  - URGENT: must be booked within the next 24 hours
 *  - GROUP: must have at least 2 participants
 *  - VIRTUAL: no location required (informational only)
 *
 * @author
 * @version 1.0
 */
public class AppointmentTypeRuleStrategy implements BookingRuleStrategy {

    /**
     * Applies type-specific rules based on the appointment type.
     *
     * TODO:
     *  1. Get the type: appointment.getType()
     *  2. Use a switch statement on AppointmentType
     *  3. For each case, apply the relevant rule:
     *     - URGENT   → check that the slot is within 24 hours from now
     *     - GROUP    → check that participants >= 2
     *     - INDIVIDUAL → check that participants == 1
     *     - others   → return true (no special rule)
     *
     * @param appointment the appointment to validate
     * @return true if the appointment passes the type-specific rule
     */
    @Override
    public boolean isValid(Appointment appointment) {
        // TODO: implement type-specific validation using switch on appointment.getType()
        return false;
    }

    /**
     * TODO: Return an appropriate message depending on which rule failed.
     *       For now, return a general message:
     *       "Appointment does not meet the rules for its type."
     *
     * @return error message shown to the user
     */
    @Override
    public String getErrorMessage() {
        // TODO: return "Appointment does not meet the rules for its type.";
        return "";
    }
}

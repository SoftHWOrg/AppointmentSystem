package org.example.strategy;

import org.example.domain.entity.Appointment;

/**
 * Strategy Pattern interface for booking rule enforcement.
 * Each concrete strategy implements one specific rule (duration, participants, type).
 *
 * How it works:
 *  - AppointmentService holds a List<BookingRuleStrategy>
 *  - Before saving a booking, it calls isValid() on each strategy
 *  - If any strategy returns false, the booking is rejected
 *
 * Adding a new rule = create a new class implementing this interface.
 * No changes needed to AppointmentService. (Open/Closed Principle)
 *
 * @author
 * @version 1.0
 */
public interface BookingRuleStrategy {

    /**
     * Validates whether the given appointment satisfies this rule.
     *
     * @param appointment the appointment to validate
     * @return true if the appointment is valid, false if the rule is violated
     */
    boolean isValid(Appointment appointment);

    /**
     * Returns a human-readable error message when isValid() returns false.
     * Displayed to the user in the GUI.
     *
     * @return error message string
     */
    String getErrorMessage();
}

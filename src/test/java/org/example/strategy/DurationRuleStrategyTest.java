package org.example.strategy;

import org.example.domain.appointment.UrgentAppointment;
import org.example.domain.appointment.GroupAppointment;
import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.valueobject.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link DurationRuleStrategy}.
 * US2.2 — Enforce visit duration rule.
 *
 * @author
 * @version 1.0
 */
class DurationRuleStrategyTest {

    // TODO: Add field: DurationRuleStrategy strategy

    /**
     * TODO: strategy = new DurationRuleStrategy();
     */
    @BeforeEach
    void setUp() {
        // TODO: initialize strategy
    }

    /**
     * An UrgentAppointment with 20-minute slot (max=30) should be valid.
     *
     * TODO:
     *  1. Create TimeSlot: date=today, start=09:00, end=09:20 (20 minutes)
     *  2. Create UrgentAppointment with that slot
     *  3. assertTrue(strategy.isValid(appointment))
     */
    @Test
    void testIsValid_urgentWithin30min_returnsTrue() {
        // TODO: implement test
    }

    /**
     * An UrgentAppointment with 45-minute slot (max=30) should be INVALID.
     *
     * TODO:
     *  1. Create TimeSlot: start=09:00, end=09:45 (45 minutes)
     *  2. Create UrgentAppointment with that slot
     *  3. assertFalse(strategy.isValid(appointment))
     */
    @Test
    void testIsValid_urgentExceeds30min_returnsFalse() {
        // TODO: implement test
    }

    /**
     * A GroupAppointment with 90-minute slot (max=120) should be valid.
     *
     * TODO:
     *  1. Create TimeSlot: start=09:00, end=10:30 (90 minutes)
     *  2. Create GroupAppointment with that slot
     *  3. assertTrue(strategy.isValid(appointment))
     */
    @Test
    void testIsValid_groupWithin120min_returnsTrue() {
        // TODO: implement test
    }
}

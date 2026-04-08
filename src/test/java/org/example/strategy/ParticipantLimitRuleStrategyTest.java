package org.example.strategy;

import org.example.domain.appointment.IndividualAppointment;
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
 * Unit tests for {@link ParticipantLimitRuleStrategy}.
 * US2.3 — Enforce participant limit.
 *
 * @author
 * @version 1.0
 */
class ParticipantLimitRuleStrategyTest {

    // TODO: Add field: ParticipantLimitRuleStrategy strategy

    /**
     * TODO: strategy = new ParticipantLimitRuleStrategy();
     */
    @BeforeEach
    void setUp() {
        // TODO: initialize strategy
    }

    /**
     * IndividualAppointment with 1 participant (max=1) should be valid.
     *
     * TODO:
     *  1. Create IndividualAppointment with participants=1
     *  2. assertTrue(strategy.isValid(appointment))
     */
    @Test
    void testIsValid_individual1Participant_returnsTrue() {
        // TODO: implement test
    }

    /**
     * IndividualAppointment with 2 participants (max=1) should be INVALID.
     *
     * TODO:
     *  1. Create IndividualAppointment with participants=2
     *  2. assertFalse(strategy.isValid(appointment))
     */
    @Test
    void testIsValid_individualExceedsLimit_returnsFalse() {
        // TODO: implement test
    }

    /**
     * GroupAppointment with 15 participants (max=20) should be valid.
     *
     * TODO:
     *  1. Create GroupAppointment with participants=15
     *  2. assertTrue(strategy.isValid(appointment))
     */
    @Test
    void testIsValid_groupWithin20_returnsTrue() {
        // TODO: implement test
    }

    /**
     * GroupAppointment with 25 participants (max=20) should be INVALID.
     *
     * TODO:
     *  1. Create GroupAppointment with participants=25
     *  2. assertFalse(strategy.isValid(appointment))
     */
    @Test
    void testIsValid_groupExceeds20_returnsFalse() {
        // TODO: implement test
    }
}

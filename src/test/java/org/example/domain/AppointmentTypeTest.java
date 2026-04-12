package org.example.domain;

import org.example.domain.appointment.*;
import org.example.domain.enums.AppointmentType;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.entity.User;
import org.example.domain.valueobject.TimeSlot;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTypeTest {

    private final User testUser = new User(1, "Test", "test@email.com", "pass", "USER");
    private final TimeSlot testSlot = new TimeSlot(1, LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0), true);

    @Test
    void testUrgentAppointment_properties() {
        UrgentAppointment appt = new UrgentAppointment(1, testUser, testSlot, AppointmentStatus.CONFIRMED, 1);
        assertEquals(AppointmentType.URGENT, appt.getType());
        assertEquals(30, appt.getMaxDuration());
        assertEquals(1, appt.getMaxParticipants());
    }

    @Test
    void testGroupAppointment_properties() {
        GroupAppointment appt = new GroupAppointment(1, testUser, testSlot, AppointmentStatus.CONFIRMED, 10);
        assertEquals(AppointmentType.GROUP, appt.getType());
        assertEquals(120, appt.getMaxDuration());
        assertEquals(20, appt.getMaxParticipants());
    }

    @Test
    void testAssessmentAppointment_properties() {
        AssessmentAppointment appt = new AssessmentAppointment(1, testUser, testSlot, AppointmentStatus.CONFIRMED, 1);
        assertEquals(AppointmentType.ASSESSMENT, appt.getType());
        assertEquals(60, appt.getMaxDuration());
        assertEquals(1, appt.getMaxParticipants());
    }

    @Test
    void testInPersonAppointment_properties() {
        InPersonAppointment appt = new InPersonAppointment(1, testUser, testSlot, AppointmentStatus.CONFIRMED, 1);
        assertEquals(AppointmentType.IN_PERSON, appt.getType());
        assertEquals(60, appt.getMaxDuration());
        assertEquals(3, appt.getMaxParticipants());
    }

    @Test
    void testIndividualAppointment_properties() {
        IndividualAppointment appt = new IndividualAppointment(1, testUser, testSlot, AppointmentStatus.CONFIRMED, 1);
        assertEquals(AppointmentType.INDIVIDUAL, appt.getType());
        assertEquals(30, appt.getMaxDuration());
        assertEquals(1, appt.getMaxParticipants());
    }

    @Test
    void testDefaultAppointment_properties() {
        DefaultAppointment appt = new DefaultAppointment(1, testUser, testSlot, AppointmentStatus.CONFIRMED, 1);
        assertEquals(AppointmentType.DEFAULT, appt.getType());
        assertEquals(60, appt.getMaxDuration());
        assertEquals(5, appt.getMaxParticipants());
    }


}


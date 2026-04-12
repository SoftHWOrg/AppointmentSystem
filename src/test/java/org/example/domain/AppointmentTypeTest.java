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
    void testVirtualAppointment_properties() {
        VirtualAppointment appt = new VirtualAppointment(1, testUser, testSlot, AppointmentStatus.CONFIRMED, 1);
        assertEquals(AppointmentType.VIRTUAL, appt.getType());
        assertEquals(60, appt.getMaxDuration());
        assertEquals(5, appt.getMaxParticipants());
    }

    @Test
    void testFollowUpAppointment_properties() {
        FollowUpAppointment appt = new FollowUpAppointment(1, testUser, testSlot, AppointmentStatus.CONFIRMED, 1);
        assertEquals(AppointmentType.FOLLOW_UP, appt.getType());
        assertEquals(30, appt.getMaxDuration());
        assertEquals(1, appt.getMaxParticipants());
    }
}

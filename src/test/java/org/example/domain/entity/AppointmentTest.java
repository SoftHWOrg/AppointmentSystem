package org.example.domain.entity;

import org.example.domain.appointment.DefaultAppointment;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    @Test
    void testGettersAndSetters() {
        User user = new User(1, "Test", "t@e.com", "p", "USER");
        TimeSlot slot = new TimeSlot(1, LocalDate.now(), LocalTime.NOON, LocalTime.MIDNIGHT, true);
        
        Appointment appt = new DefaultAppointment(1, user, slot, AppointmentStatus.PENDING, 1);
        
        appt.setId(10);
        appt.setUser(null);
        appt.setTimeSlot(null);
        appt.setStatus(AppointmentStatus.CONFIRMED);
        appt.setParticipants(5);
        appt.setType(AppointmentType.URGENT);

        assertEquals(10, appt.getId());
        assertNull(appt.getUser());
        assertNull(appt.getTimeSlot());
        assertEquals(AppointmentStatus.CONFIRMED, appt.getStatus());
        assertEquals(5, appt.getParticipants());
        assertEquals(AppointmentType.URGENT, appt.getType());
    }

    @Test
    void testToString() {
        User user = new User(1, "John", "j@e.com", "p", "USER");
        Appointment appt = new DefaultAppointment(1, user, null, AppointmentStatus.CONFIRMED, 1);
        String expected = "Appointment{id=1, type=DEFAULT, status=CONFIRMED, user=John}";
        assertEquals(expected, appt.toString());
    }

    @Test
    void testToString_NullUser() {
        Appointment appt = new DefaultAppointment(1, null, null, AppointmentStatus.CONFIRMED, 1);
        String expected = "Appointment{id=1, type=DEFAULT, status=CONFIRMED, user=null}";
        assertEquals(expected, appt.toString());
    }
}

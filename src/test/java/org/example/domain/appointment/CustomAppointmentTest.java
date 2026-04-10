package org.example.domain.appointment;

import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomAppointmentTest {

    @Test
    void testCustomAppointmentLimits() {
        TimeSlot slot = new TimeSlot(1, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), true);
        User user = new User(1, "Test", "test@test.com", "pass", "USER");
        CustomAppointment appt = new CustomAppointment(1, user, slot, AppointmentStatus.PENDING, 2);
        
        assertEquals(60, appt.getMaxDuration());
        assertEquals(5, appt.getMaxParticipants());
        assertEquals(AppointmentType.CUSTOM, appt.getType());
    }
}

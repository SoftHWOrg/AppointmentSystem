package org.example.domain.appointment;

import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentSubclassesTest {

    private User mockUser;
    private TimeSlot mockTimeSlot;

    @BeforeEach
    void setUp() {
        mockUser = Mockito.mock(User.class);
        mockTimeSlot = new TimeSlot(1, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), true);
    }

    @Test
    void testAssessmentAppointment() {
        AssessmentAppointment app = new AssessmentAppointment(1, mockUser, mockTimeSlot, AppointmentStatus.PENDING, 1);
        assertEquals(AppointmentType.ASSESSMENT, app.getType());
        assertEquals(60, app.getMaxDuration());
        assertEquals(1, app.getMaxParticipants());
    }

    @Test
    void testDefaultAppointment() {
        DefaultAppointment app = new DefaultAppointment(1, mockUser, mockTimeSlot, AppointmentStatus.PENDING, 1);
        assertEquals(AppointmentType.DEFAULT, app.getType());
        assertEquals(60, app.getMaxDuration());
        assertEquals(5, app.getMaxParticipants());
    }

    @Test
    void testFollowUpAppointment() {
        FollowUpAppointment app = new FollowUpAppointment(1, mockUser, mockTimeSlot, AppointmentStatus.PENDING, 1);
        assertEquals(AppointmentType.FOLLOW_UP, app.getType());
        assertEquals(30, app.getMaxDuration());
        assertEquals(1, app.getMaxParticipants());
    }

    @Test
    void testGroupAppointment() {
        GroupAppointment app = new GroupAppointment(1, mockUser, mockTimeSlot, AppointmentStatus.PENDING, 10);
        assertEquals(AppointmentType.GROUP, app.getType());
        assertEquals(120, app.getMaxDuration());
        assertEquals(20, app.getMaxParticipants());
    }

    @Test
    void testInPersonAppointment() {
        InPersonAppointment app = new InPersonAppointment(1, mockUser, mockTimeSlot, AppointmentStatus.PENDING, 1);
        assertEquals(AppointmentType.IN_PERSON, app.getType());
        assertEquals(60, app.getMaxDuration());
        assertEquals(3, app.getMaxParticipants());
    }

    @Test
    void testIndividualAppointment() {
        IndividualAppointment app = new IndividualAppointment(1, mockUser, mockTimeSlot, AppointmentStatus.PENDING, 1);
        assertEquals(AppointmentType.INDIVIDUAL, app.getType());
        assertEquals(30, app.getMaxDuration());
        assertEquals(1, app.getMaxParticipants());
    }

    @Test
    void testUrgentAppointment() {
        UrgentAppointment app = new UrgentAppointment(1, mockUser, mockTimeSlot, AppointmentStatus.PENDING, 1);
        assertEquals(AppointmentType.URGENT, app.getType());
        assertEquals(30, app.getMaxDuration());
        assertEquals(1, app.getMaxParticipants());
    }

    @Test
    void testVirtualAppointment() {
        VirtualAppointment app = new VirtualAppointment(1, mockUser, mockTimeSlot, AppointmentStatus.PENDING, 1);
        assertEquals(AppointmentType.VIRTUAL, app.getType());
        assertEquals(60, app.getMaxDuration());
        assertEquals(5, app.getMaxParticipants());
    }
}

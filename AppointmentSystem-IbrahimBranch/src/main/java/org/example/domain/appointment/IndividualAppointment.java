package org.example.domain.appointment;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;

public class IndividualAppointment extends Appointment {

    public IndividualAppointment(int id, User user, TimeSlot timeSlot, AppointmentStatus status, int participants) {
        super(id, user, timeSlot, AppointmentType.INDIVIDUAL, status, participants);
    }

    @Override
    public int getMaxDuration() { return 30; }

    @Override
    public int getMaxParticipants() { return 1; }
}

package org.example.domain.appointment;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;

public class DefaultAppointment extends Appointment {

    public DefaultAppointment(int id, User user, TimeSlot timeSlot, AppointmentStatus status, int participants) {
        super(id, user, timeSlot, AppointmentType.DEFAULT, status, participants);
    }

    @Override
    public int getMaxDuration() { return Integer.MAX_VALUE; }

    @Override
    public int getMaxParticipants() { return Integer.MAX_VALUE; }
}

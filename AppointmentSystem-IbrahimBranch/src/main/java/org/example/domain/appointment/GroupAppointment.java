package org.example.domain.appointment;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;

public class GroupAppointment extends Appointment {

    public GroupAppointment(int id, User user, TimeSlot timeSlot, AppointmentStatus status, int participants) {
        super(id, user, timeSlot, AppointmentType.GROUP, status, participants);
    }

    @Override
    public int getMaxDuration() { return 120; }

    @Override
    public int getMaxParticipants() { return 20; }
}

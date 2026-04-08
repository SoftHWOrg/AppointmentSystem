package org.example.domain.appointment;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;

/**
 * Represents a virtual/remote appointment (online meeting).
 *
 * @author
 * @version 1.0
 */
public class VirtualAppointment extends Appointment {

    public VirtualAppointment(int id, User user, TimeSlot timeSlot, AppointmentStatus status, int participants) {
        super(id, user, timeSlot, AppointmentType.VIRTUAL, status, participants);
    }

    @Override
    public int getMaxDuration() { return 60; }

    @Override
    public int getMaxParticipants() { return 5; }
}

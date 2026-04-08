package org.example.domain.appointment;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;

/**
 * Represents a follow-up appointment after a previous visit.
 *
 * @author
 * @version 1.0
 */
public class FollowUpAppointment extends Appointment {

    public FollowUpAppointment(int id, User user, TimeSlot timeSlot, AppointmentStatus status, int participants) {
        super(id, user, timeSlot, AppointmentType.FOLLOW_UP, status, participants);
    }

    @Override
    public int getMaxDuration() { return 30; }

    @Override
    public int getMaxParticipants() { return 1; }
}

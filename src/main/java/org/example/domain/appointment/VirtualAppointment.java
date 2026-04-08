package org.example.domain.appointment;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;

/**
 * Represents a virtual/remote appointment (online meeting).
 * No physical location required.
 *
 * @author
 * @version 1.0
 */
public class VirtualAppointment extends Appointment {

    /**
     * TODO: Call super(...) with AppointmentType.VIRTUAL
     */
    public VirtualAppointment(int id, User user, TimeSlot timeSlot, AppointmentStatus status, int participants) {
        super(id, user, timeSlot, AppointmentType.VIRTUAL, status, participants);
    }

    /**
     * Virtual appointments — max 60 minutes.
     * TODO: return 60
     *
     * @return max duration in minutes (60)
     */
    @Override
    public int getMaxDuration() {
        // TODO: return 60;
        return 0;
    }

    /**
     * Virtual allows up to 5 participants.
     * TODO: return 5
     *
     * @return max participants allowed (5)
     */
    @Override
    public int getMaxParticipants() {
        // TODO: return 5;
        return 0;
    }
}

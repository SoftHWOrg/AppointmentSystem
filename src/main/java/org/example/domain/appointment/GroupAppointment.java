package org.example.domain.appointment;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;

/**
 * Represents a group appointment with multiple participants.
 * Has the longest duration and highest participant cap.
 *
 * @author
 * @version 1.0
 */
public class GroupAppointment extends Appointment {

    /**
     * TODO: Call super(...) with AppointmentType.GROUP
     */
    public GroupAppointment(int id, User user, TimeSlot timeSlot, AppointmentStatus status, int participants) {
        super(id, user, timeSlot, AppointmentType.GROUP, status, participants);
    }

    /**
     * Group appointments — max 120 minutes (2 hours).
     * TODO: return 120
     *
     * @return max duration in minutes (120)
     */
    @Override
    public int getMaxDuration() {
        // TODO: return 120;
        return 0;
    }

    /**
     * Group allows up to 20 participants.
     * TODO: return 20
     *
     * @return max participants allowed (20)
     */
    @Override
    public int getMaxParticipants() {
        // TODO: return 20;
        return 0;
    }
}

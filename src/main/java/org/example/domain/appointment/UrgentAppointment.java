package org.example.domain.appointment;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;

/**
 * Represents an urgent/emergency appointment.
 * Short duration, single participant.
 *
 * @author
 * @version 1.0
 */
public class UrgentAppointment extends Appointment {

    /**
     * TODO: Call the parent constructor using super(...)
     *       Pass all parameters up to Appointment.
     *       Force type = AppointmentType.URGENT
     */
    public UrgentAppointment(int id, User user, TimeSlot timeSlot, AppointmentStatus status, int participants) {
        super(id, user, timeSlot, AppointmentType.URGENT, status, participants);
    }

    /**
     * Urgent appointments are short — max 30 minutes.
     *
     * TODO: return 30
     *
     * @return max duration in minutes (30)
     */
    @Override
    public int getMaxDuration() {
        // TODO: return 30;
        return 0;
    }

    /**
     * Urgent appointments are individual — max 1 participant.
     *
     * TODO: return 1
     *
     * @return max participants allowed (1)
     */
    @Override
    public int getMaxParticipants() {
        // TODO: return 1;
        return 0;
    }
}

package org.example.domain.appointment;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;

/**
 * Represents a one-on-one individual appointment.
 * Strictly 1 participant only.
 *
 * @author
 * @version 1.0
 */
public class IndividualAppointment extends Appointment {

    /**
     * TODO: Call super(...) with AppointmentType.INDIVIDUAL
     */
    public IndividualAppointment(int id, User user, TimeSlot timeSlot, AppointmentStatus status, int participants) {
        super(id, user, timeSlot, AppointmentType.INDIVIDUAL, status, participants);
    }

    /**
     * Individual appointments — max 30 minutes.
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
     * Individual is strictly 1-on-1 — max 1 participant.
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

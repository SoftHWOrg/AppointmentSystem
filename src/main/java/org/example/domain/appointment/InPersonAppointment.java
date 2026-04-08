package org.example.domain.appointment;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;

/**
 * Represents a physical face-to-face appointment.
 *
 * @author
 * @version 1.0
 */
public class InPersonAppointment extends Appointment {

    /**
     * TODO: Call super(...) with AppointmentType.IN_PERSON
     */
    public InPersonAppointment(int id, User user, TimeSlot timeSlot, AppointmentStatus status, int participants) {
        super(id, user, timeSlot, AppointmentType.IN_PERSON, status, participants);
    }

    /**
     * In-person appointments — max 60 minutes.
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
     * In-person allows up to 3 participants.
     * TODO: return 3
     *
     * @return max participants allowed (3)
     */
    @Override
    public int getMaxParticipants() {
        // TODO: return 3;
        return 0;
    }
}

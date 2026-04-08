package org.example.domain.appointment;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;

/**
 * Represents an assessment or evaluation appointment.
 * Longer duration allowed to accommodate thorough evaluations.
 *
 * @author
 * @version 1.0
 */
public class AssessmentAppointment extends Appointment {

    /**
     * TODO: Call super(...) with AppointmentType.ASSESSMENT
     */
    public AssessmentAppointment(int id, User user, TimeSlot timeSlot, AppointmentStatus status, int participants) {
        super(id, user, timeSlot, AppointmentType.ASSESSMENT, status, participants);
    }

    /**
     * Assessments can be longer — max 60 minutes.
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
     * Assessment allows 1 participant.
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

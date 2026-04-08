package org.example.domain.appointment;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;

/**
 * Represents an assessment or evaluation appointment.
 *
 * @author
 * @version 1.0
 */
public class AssessmentAppointment extends Appointment {

    public AssessmentAppointment(int id, User user, TimeSlot timeSlot, AppointmentStatus status, int participants) {
        super(id, user, timeSlot, AppointmentType.ASSESSMENT, status, participants);
    }

    @Override
    public int getMaxDuration() { return 60; }

    @Override
    public int getMaxParticipants() { return 1; }
}

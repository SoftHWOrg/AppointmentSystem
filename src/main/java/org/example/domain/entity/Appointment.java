package org.example.domain.entity;

import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;

/**
 * Abstract base class for all appointment types.
 * All 7 concrete appointment types extend this class.
 *
 * @author
 * @version 1.0
 */
public abstract class Appointment {

    private int id;
    private User user;
    private TimeSlot timeSlot;
    private AppointmentType type;
    private AppointmentStatus status;
    private int participants;

    public Appointment(int id, User user, TimeSlot timeSlot,
                       AppointmentType type, AppointmentStatus status, int participants) {
        this.id = id;
        this.user = user;
        this.timeSlot = timeSlot;
        this.type = type;
        this.status = status;
        this.participants = participants;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public TimeSlot getTimeSlot() { return timeSlot; }
    public void setTimeSlot(TimeSlot timeSlot) { this.timeSlot = timeSlot; }

    public AppointmentType getType() { return type; }
    public void setType(AppointmentType type) { this.type = type; }

    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }

    public int getParticipants() { return participants; }
    public void setParticipants(int participants) { this.participants = participants; }

    public abstract int getMaxDuration();
    public abstract int getMaxParticipants();

    @Override
    public String toString() {
        return "Appointment{id=" + id + ", type=" + type + ", status=" + status
                + ", user=" + (user != null ? user.getName() : "null") + "}";
    }
}

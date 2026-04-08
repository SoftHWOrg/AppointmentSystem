package org.example.repository;

import org.example.domain.entity.Appointment;

import java.util.List;

/**
 * Repository interface for Appointment persistence.
 * The PostgreSQL implementation will use JDBC to execute SQL queries.
 *
 * @author
 * @version 1.0
 */
public interface AppointmentRepository {

    /**
     * Saves a new appointment to the database.
     * TODO: INSERT INTO appointments (...) VALUES (...)
     *
     * @param appointment the appointment to save
     */
    void save(Appointment appointment);

    /**
     * Updates an existing appointment (status, time slot, participants).
     * TODO: UPDATE appointments SET ... WHERE id = ?
     *
     * @param appointment the appointment with updated values
     */
    void update(Appointment appointment);

    /**
     * Deletes an appointment by ID.
     * TODO: DELETE FROM appointments WHERE id = ?
     *
     * @param id the appointment ID to delete
     */
    void delete(int id);

    /**
     * Finds a single appointment by its ID.
     * TODO: SELECT * FROM appointments WHERE id = ?
     *
     * @param id the appointment ID
     * @return the found Appointment, or null if not found
     */
    Appointment findById(int id);

    /**
     * Returns all appointments belonging to a specific user.
     * TODO: SELECT * FROM appointments WHERE user_id = ?
     *
     * @param userId the user's ID
     * @return list of the user's appointments
     */
    List<Appointment> findByUserId(int userId);

    /**
     * Returns all appointments in the system (admin use).
     * TODO: SELECT * FROM appointments
     *
     * @return list of all appointments
     */
    List<Appointment> findAll();
}

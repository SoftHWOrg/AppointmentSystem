package org.example.repository;

import org.example.config.DatabaseConnection;
import org.example.domain.entity.Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * PostgreSQL implementation of {@link AppointmentRepository}.
 * Uses JDBC and HikariCP to execute queries against the appointments table.
 *
 * @author
 * @version 1.0
 */
public class PostgresAppointmentRepository implements AppointmentRepository {

    /**
     * Inserts a new appointment record into the database.
     *
     * TODO:
     *  1. Get a connection: DatabaseConnection.getConnection()
     *  2. Write SQL: "INSERT INTO appointments (user_id, time_slot_id, type, status, participants) VALUES (?, ?, ?, ?, ?)"
     *  3. Create a PreparedStatement and set all parameters
     *  4. Execute with executeUpdate()
     *  5. Close resources in a finally block (or use try-with-resources)
     *
     * @param appointment the appointment to save
     */
    @Override
    public void save(Appointment appointment) {
        // TODO: implement INSERT query
    }

    /**
     * Updates an existing appointment record.
     *
     * TODO:
     *  1. Write SQL: "UPDATE appointments SET time_slot_id=?, type=?, status=?, participants=? WHERE id=?"
     *  2. Use PreparedStatement to set parameters
     *  3. Execute with executeUpdate()
     *
     * @param appointment the appointment with updated values
     */
    @Override
    public void update(Appointment appointment) {
        // TODO: implement UPDATE query
    }

    /**
     * Deletes an appointment by ID.
     *
     * TODO:
     *  1. Write SQL: "DELETE FROM appointments WHERE id = ?"
     *  2. Use PreparedStatement, set id parameter
     *  3. Execute with executeUpdate()
     *
     * @param id the appointment ID to delete
     */
    @Override
    public void delete(int id) {
        // TODO: implement DELETE query
    }

    /**
     * Finds an appointment by its ID.
     *
     * TODO:
     *  1. Write SQL: "SELECT * FROM appointments WHERE id = ?"
     *  2. Execute query, get ResultSet
     *  3. If ResultSet has a row: call mapRow(rs) to build Appointment object
     *  4. Return the Appointment or null if not found
     *
     * @param id the appointment ID
     * @return the Appointment or null
     */
    @Override
    public Appointment findById(int id) {
        // TODO: implement SELECT by id
        return null;
    }

    /**
     * Returns all appointments for a specific user.
     *
     * TODO:
     *  1. Write SQL: "SELECT * FROM appointments WHERE user_id = ?"
     *  2. Execute query, loop through ResultSet
     *  3. For each row: call mapRow(rs) and add to list
     *  4. Return list
     *
     * @param userId the user ID
     * @return list of appointments for this user
     */
    @Override
    public List<Appointment> findByUserId(int userId) {
        // TODO: implement SELECT by user_id
        return new ArrayList<>();
    }

    /**
     * Returns all appointments (for admin view).
     *
     * TODO:
     *  1. Write SQL: "SELECT * FROM appointments"
     *  2. Loop through ResultSet, map each row to an Appointment
     *  3. Return the full list
     *
     * @return all appointments in the system
     */
    @Override
    public List<Appointment> findAll() {
        // TODO: implement SELECT all
        return new ArrayList<>();
    }

    /**
     * Maps a single ResultSet row to an Appointment object.
     * Called internally by findById, findByUserId, and findAll.
     *
     * TODO:
     *  1. Read columns: rs.getInt("id"), rs.getString("type"), rs.getString("status"), etc.
     *  2. Build the correct Appointment subclass based on AppointmentType
     *     (use a switch/if on the type string)
     *  3. Return the constructed Appointment
     *
     * @param rs the current ResultSet row
     * @return a fully constructed Appointment object
     */
    private Appointment mapRow(ResultSet rs) {
        // TODO: implement row mapping
        return null;
    }
}

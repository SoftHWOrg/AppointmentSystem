package org.example.repository;

import org.example.config.DatabaseConnection;
import org.example.domain.entity.User;
import org.example.domain.entity.Administrator;

import java.sql.ResultSet;

/**
 * PostgreSQL implementation of {@link UserRepository}.
 * Uses JDBC and HikariCP to execute queries against the users table.
 *
 * @author
 * @version 1.0
 */
public class PostgresUserRepository implements UserRepository {

    /**
     * Inserts a new user into the database.
     *
     * TODO:
     *  1. Get connection: DatabaseConnection.getConnection()
     *  2. SQL: "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)"
     *  3. Set all parameters and executeUpdate()
     *
     * @param user the user to save
     */
    @Override
    public void save(User user) {
        // TODO: implement INSERT query
    }

    /**
     * Finds a user by email — used for login (US1.1).
     *
     * TODO:
     *  1. SQL: "SELECT * FROM users WHERE email = ?"
     *  2. Execute, check if ResultSet has a row
     *  3. Call mapRow(rs) to build User or Administrator object
     *  4. Return it, or null if not found
     *
     * @param email the user's email
     * @return the User or null
     */
    @Override
    public User findByEmail(String email) {
        // TODO: implement SELECT by email
        return null;
    }

    /**
     * Finds a user by ID.
     *
     * TODO:
     *  1. SQL: "SELECT * FROM users WHERE id = ?"
     *  2. Execute, call mapRow(rs) if found
     *  3. Return User or null
     *
     * @param id the user ID
     * @return the User or null
     */
    @Override
    public User findById(int id) {
        // TODO: implement SELECT by id
        return null;
    }

    /**
     * Updates user information.
     *
     * TODO:
     *  1. SQL: "UPDATE users SET name=?, email=?, password=? WHERE id=?"
     *  2. Set parameters, executeUpdate()
     *
     * @param user the user with updated values
     */
    @Override
    public void update(User user) {
        // TODO: implement UPDATE query
    }

    /**
     * Maps a single ResultSet row to a User or Administrator object.
     *
     * TODO:
     *  1. Read: rs.getInt("id"), rs.getString("name"), rs.getString("email"),
     *           rs.getString("password"), rs.getString("role")
     *  2. If role == "ADMIN" → return new Administrator(id, name, email, password)
     *  3. Otherwise         → return new User(id, name, email, password, role)
     *
     * @param rs the current ResultSet row
     * @return a User or Administrator object
     */
    private User mapRow(ResultSet rs) {
        // TODO: implement row mapping
        return null;
    }
}

package org.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Manages the PostgreSQL database connection using HikariCP connection pooling.
 * This is a singleton — only one pool is created for the whole application.
 *
 * @author
 * @version 1.0
 */
public class DatabaseConnection {

    // TODO: Set your PostgreSQL credentials here
    private static final String URL      = "jdbc:postgresql://localhost:5432/appointmentsys";
    private static final String USER     = "postgres";
    private static final String PASSWORD = "your_password_here";

    private static HikariDataSource dataSource;

    /**
     * Private constructor — prevents instantiation.
     * All access is through getConnection().
     */
    private DatabaseConnection() {}

    /**
     * Initializes the HikariCP connection pool on first call.
     *
     * TODO:
     *  - Create a HikariConfig object
     *  - Set the JDBC URL, username, and password
     *  - Set pool size (e.g. maximumPoolSize = 10)
     *  - Create a HikariDataSource from the config
     *  - Store it in the static `dataSource` field
     */
    private static void init() {
        // TODO: implement pool initialization
    }

    /**
     * Returns a connection from the pool.
     * Calls init() if the pool has not been created yet.
     *
     * TODO:
     *  - Check if dataSource is null, call init() if so
     *  - Return dataSource.getConnection()
     *
     * @return a live {@link Connection} from the pool
     * @throws SQLException if a connection cannot be obtained
     */
    public static Connection getConnection() throws SQLException {
        // TODO: implement
        return null;
    }

    /**
     * Closes the connection pool on application shutdown.
     *
     * TODO:
     *  - Check if dataSource is not null and not already closed
     *  - Call dataSource.close()
     */
    public static void close() {
        // TODO: implement
    }
}

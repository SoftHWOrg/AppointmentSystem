-- ============================================================
-- Appointment Scheduling System - PostgreSQL Schema
-- ============================================================
-- TODO: Run this file in your PostgreSQL database before starting the app.
--       You can run it via: psql -U postgres -d appointmentsys -f schema.sql
-- ============================================================

-- TODO: Create the database first (run this line separately):
-- CREATE DATABASE appointmentsys;

-- ============================================================
-- Table: users
-- Stores both regular users and administrators.
-- The 'role' column distinguishes between USER and ADMIN.
-- ============================================================
CREATE TABLE IF NOT EXISTS users (
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(100)        NOT NULL,
    email      VARCHAR(150) UNIQUE NOT NULL,
    password   VARCHAR(255)        NOT NULL,  -- TODO: store hashed passwords (e.g. BCrypt)
    role       VARCHAR(20)         NOT NULL DEFAULT 'USER'  -- values: 'USER' or 'ADMIN'
);

-- ============================================================
-- Table: time_slots
-- Represents available time windows that can be booked.
-- ============================================================
CREATE TABLE IF NOT EXISTS time_slots (
    id           SERIAL PRIMARY KEY,
    slot_date    DATE        NOT NULL,
    start_time   TIME        NOT NULL,
    end_time     TIME        NOT NULL,
    is_available BOOLEAN     NOT NULL DEFAULT TRUE
);

-- ============================================================
-- Table: appointments
-- Core table — stores all booked appointments.
-- Links a user to a time slot with type, status, and participant count.
-- ============================================================
CREATE TABLE IF NOT EXISTS appointments (
    id               SERIAL PRIMARY KEY,
    user_id          INT          NOT NULL REFERENCES users(id),
    time_slot_id     INT          NOT NULL REFERENCES time_slots(id),
    type             VARCHAR(50)  NOT NULL,  -- e.g. URGENT, FOLLOW_UP, VIRTUAL, etc.
    status           VARCHAR(20)  NOT NULL DEFAULT 'CONFIRMED',  -- CONFIRMED, CANCELLED, PENDING
    participants     INT          NOT NULL DEFAULT 1,
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- Table: notifications (optional - for tracking sent reminders)
-- TODO: Use this table if you want to persist notification history.
-- ============================================================
CREATE TABLE IF NOT EXISTS notifications (
    id          SERIAL PRIMARY KEY,
    user_id     INT          NOT NULL REFERENCES users(id),
    message     TEXT         NOT NULL,
    sent_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    channel     VARCHAR(20)  NOT NULL  -- EMAIL, SMS, CALENDAR
);

-- ============================================================
-- Sample seed data — TODO: change passwords before using in production
-- ============================================================
INSERT INTO users (name, email, password, role)
VALUES
    ('Admin User', 'admin@clinic.com', 'admin123', 'ADMIN'),
    ('John Doe',   'john@email.com',   'user123',  'USER')
ON CONFLICT DO NOTHING;

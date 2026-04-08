package org.example.domain.valueobject;

import java.time.LocalDateTime;

/**
 * Represents a notification message sent to a user.
 * Used by the Observer pattern (Sprint 3 — US3.1).
 *
 * This is a value object — it carries the message data, no logic.
 *
 * @author
 * @version 1.0
 */
public class NotificationMessage {

    // TODO: Add field: String recipient    (user's email or phone number)
    // TODO: Add field: String subject      (short title of the message)
    // TODO: Add field: String body         (full message text)
    // TODO: Add field: String channel      (EMAIL, SMS, or CALENDAR)
    // TODO: Add field: LocalDateTime sentAt

    /**
     * Full constructor.
     *
     * TODO: Add parameters (recipient, subject, body, channel)
     *       Assign them to fields and set sentAt = LocalDateTime.now()
     *
     * @param recipient the target user's contact info
     * @param subject   short title of the notification
     * @param body      full message content
     * @param channel   delivery channel: "EMAIL", "SMS", or "CALENDAR"
     */
    public NotificationMessage(String recipient, String subject, String body, String channel) {
        // TODO: assign fields, set sentAt = LocalDateTime.now()
    }

    // TODO: Add getters for all fields (no setters — value objects are immutable)

    /**
     * TODO: Override toString() to return something like:
     *       "NotificationMessage{to='john@email.com', channel='EMAIL', subject='Reminder'}"
     */
    @Override
    public String toString() {
        // TODO: implement
        return "";
    }
}

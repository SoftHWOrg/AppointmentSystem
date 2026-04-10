package org.example.observer;

import org.example.domain.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CalendarNotificationObserverTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testNotify() {
        CalendarNotificationObserver observer = new CalendarNotificationObserver();
        User user = new User(1, "John", "john@example.com", "pass", "USER");
        observer.notify(user, "Test Message");
        
        System.out.flush();
        String output = outContent.toString();
        assertTrue(output.contains("[CALENDAR] User: John | Syncing event: Test Message"));
    }
}

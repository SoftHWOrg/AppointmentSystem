package org.example.repository;

import org.example.domain.valueobject.TimeSlot;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TxtTimeSlotRepository {

    private static final String FILE_PATH = "C:\\Users\\ibrah\\Desktop\\java code fuck this shit\\AppointmentSystem-main\\data\\timeslots.txt";
    private static final String DELIMITER = "|";
    private static final String DELIMITER_REGEX = "\\|";

    public TxtTimeSlotRepository() {
        ensureFileExists();
    }

    private void ensureFileExists() {
        try {
            Path path = Paths.get(FILE_PATH);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot initialise timeslots file: " + FILE_PATH, e);
        }
    }

    private List<String> readAllLines() {
        try {
            return new ArrayList<>(Files.readAllLines(Paths.get(FILE_PATH)));
        } catch (IOException e) {
            throw new RuntimeException("Error reading timeslots file", e);
        }
    }

    private void writeAllLines(List<String> lines) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH, false))) {
            for (String line : lines) {
                pw.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing timeslots file", e);
        }
    }

    private String toLine(TimeSlot slot) {
        return slot.getId() + DELIMITER
                + slot.getDate() + DELIMITER
                + slot.getStartTime() + DELIMITER
                + slot.getEndTime() + DELIMITER
                + slot.isAvailable();
    }

    private TimeSlot parseLine(String line) {
        if (line == null || line.isBlank())
            return null;
        String[] p = line.split(DELIMITER_REGEX, -1);
        if (p.length < 5)
            return null;

        int id = Integer.parseInt(p[0].trim());
        LocalDate date = LocalDate.parse(p[1].trim());
        LocalTime start = LocalTime.parse(p[2].trim());
        LocalTime end = LocalTime.parse(p[3].trim());
        boolean available = Boolean.parseBoolean(p[4].trim());

        return new TimeSlot(id, date, start, end, available);
    }

    private int nextId(List<String> lines) {
        int max = 0;
        for (String line : lines) {
            if (line.isBlank())
                continue;
            String[] parts = line.split(DELIMITER_REGEX, -1);
            try {
                int id = Integer.parseInt(parts[0].trim());
                if (id > max)
                    max = id;
            } catch (NumberFormatException ignored) {
            }
        }
        return max + 1;
    }

    public void save(TimeSlot slot) {
        List<String> lines = readAllLines();
        slot.setId(nextId(lines));
        lines.add(toLine(slot));
        writeAllLines(lines);
    }

    public List<TimeSlot> findAll() {
        List<TimeSlot> result = new ArrayList<>();
        for (String line : readAllLines()) {
            TimeSlot slot = parseLine(line);
            if (slot != null)
                result.add(slot);
        }
        return result;
    }

    public void update(TimeSlot slot) {
        List<String> lines = readAllLines();
        for (int i = 0; i < lines.size(); i++) {
            TimeSlot existing = parseLine(lines.get(i));
            if (existing != null && existing.getId() == slot.getId()) {
                lines.set(i, toLine(slot));
                writeAllLines(lines);
                return;
            }
        }
    }
}

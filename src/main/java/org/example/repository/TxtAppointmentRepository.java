package org.example.repository;

import org.example.domain.appointment.*;
import org.example.domain.entity.Administrator;
import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TxtAppointmentRepository implements AppointmentRepository {

    private final String filePath;
    private static final String DEFAULT_FILE_PATH = "data/appointments.txt";
    private static final String DELIMITER = "|";
    private static final String DELIMITER_REGEX = "\\|";

    public TxtAppointmentRepository() {
        this(DEFAULT_FILE_PATH);
    }

    public TxtAppointmentRepository(String filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    private void ensureFileExists() {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                if (path.getParent() != null) {
                    Files.createDirectories(path.getParent());
                }
                Files.createFile(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot initialise appointments file: " + filePath, e);
        }
    }

    private List<String> readAllLines() {
        try {
            return new ArrayList<>(Files.readAllLines(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException("Error reading appointments file", e);
        }
    }

    private void writeAllLines(List<String> lines) {
        try {
            Files.write(Paths.get(filePath), lines, java.nio.charset.StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error writing appointments file", e);
        }
    }

    private String toLine(Appointment a) {
        User u = a.getUser();
        TimeSlot s = a.getTimeSlot();
        return a.getId() + DELIMITER
                + u.getId() + DELIMITER
                + u.getName() + DELIMITER
                + u.getEmail() + DELIMITER
                + u.getPassword() + DELIMITER
                + u.getRole() + DELIMITER
                + s.getId() + DELIMITER
                + s.getDate() + DELIMITER
                + s.getStartTime() + DELIMITER
                + s.getEndTime() + DELIMITER
                + s.isAvailable() + DELIMITER
                + a.getType().name() + DELIMITER
                + a.getStatus().name() + DELIMITER
                + a.getParticipants();
    }

    private Appointment parseLine(String line) {
        if (line == null || line.isBlank())
            return null;
        String[] p = line.split(DELIMITER_REGEX, -1);
        if (p.length < 14)
            return null;

        int apptId = Integer.parseInt(p[0].trim());
        int userId = Integer.parseInt(p[1].trim());
        String userName = p[2].trim();
        String userEmail = p[3].trim();
        String userPwd = p[4].trim();
        String userRole = p[5].trim();

        int slotId = Integer.parseInt(p[6].trim());
        LocalDate slotDate = LocalDate.parse(p[7].trim());
        LocalTime slotStart = LocalTime.parse(p[8].trim());
        LocalTime slotEnd = LocalTime.parse(p[9].trim());
        boolean slotAvailable = Boolean.parseBoolean(p[10].trim());

        AppointmentType type;
        try {
            type = AppointmentType.valueOf(p[11].trim());
        } catch (IllegalArgumentException e) {
            type = AppointmentType.DEFAULT;
        }

        AppointmentStatus status = AppointmentStatus.valueOf(p[12].trim());
        int participants = Integer.parseInt(p[13].trim());

        User user = "ADMIN".equals(userRole)
                ? new Administrator(userId, userName, userEmail, userPwd)
                : new User(userId, userName, userEmail, userPwd, userRole);

        TimeSlot slot = new TimeSlot(slotId, slotDate, slotStart, slotEnd, slotAvailable);

        return buildAppointment(apptId, user, slot, type, status, participants);
    }

    private Appointment buildAppointment(int id, User user, TimeSlot slot,
            AppointmentType type, AppointmentStatus status,
            int participants) {
        return switch (type) {
            case URGENT -> new UrgentAppointment(id, user, slot, status, participants);
            case FOLLOW_UP -> new FollowUpAppointment(id, user, slot, status, participants);
            case ASSESSMENT -> new AssessmentAppointment(id, user, slot, status, participants);
            case VIRTUAL -> new VirtualAppointment(id, user, slot, status, participants);
            case IN_PERSON -> new InPersonAppointment(id, user, slot, status, participants);
            case INDIVIDUAL -> new IndividualAppointment(id, user, slot, status, participants);
            case GROUP -> new GroupAppointment(id, user, slot, status, participants);
            case DEFAULT -> new DefaultAppointment(id, user, slot, status, participants);
        };
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

    @Override
    public void save(Appointment appointment) {
        List<String> lines = readAllLines();
        int id = nextId(lines);
        appointment.setId(id);
        lines.add(toLine(appointment));
        writeAllLines(lines);
    }

    @Override
    public void update(Appointment appointment) {
        List<String> lines = readAllLines();
        for (int i = 0; i < lines.size(); i++) {
            Appointment existing = parseLine(lines.get(i));
            if (existing != null && existing.getId() == appointment.getId()) {
                lines.set(i, toLine(appointment));
                writeAllLines(lines);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        List<String> lines = readAllLines();
        lines.removeIf(line -> {
            Appointment a = parseLine(line);
            return a != null && a.getId() == id;
        });
        writeAllLines(lines);
    }

    @Override
    public Appointment findById(int id) {
        for (String line : readAllLines()) {
            Appointment a = parseLine(line);
            if (a != null && a.getId() == id)
                return a;
        }
        return null;
    }

    @Override
    public List<Appointment> findByUserId(int userId) {
        List<Appointment> result = new ArrayList<>();
        for (String line : readAllLines()) {
            Appointment a = parseLine(line);
            if (a != null && a.getUser().getId() == userId)
                result.add(a);
        }
        return result;
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> result = new ArrayList<>();
        for (String line : readAllLines()) {
            Appointment a = parseLine(line);
            if (a != null)
                result.add(a);
        }
        return result;
    }
}

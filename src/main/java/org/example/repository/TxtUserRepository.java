package org.example.repository;

import org.example.domain.entity.Administrator;
import org.example.domain.entity.User;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class TxtUserRepository implements UserRepository {

    private static final String FILE_PATH = "data/users.txt";
    private static final String DELIMITER = "|";
    private static final String DELIMITER_REGEX = "\\|";

    public TxtUserRepository() {
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
            throw new RuntimeException("Cannot initialise users file: " + FILE_PATH, e);
        }
    }

    private List<String> readAllLines() {
        try {
            return Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Error reading users file", e);
        }
    }

    private void writeAllLines(List<String> lines) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH, false))) {
            for (String line : lines) {
                pw.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing users file", e);
        }
    }

    private String toLine(User user) {
        return user.getId() + DELIMITER
                + user.getName() + DELIMITER
                + user.getEmail() + DELIMITER
                + user.getPassword() + DELIMITER
                + user.getRole();
    }

    private User parseLine(String line) {
        if (line == null || line.isBlank())
            return null;
        String[] parts = line.split(DELIMITER_REGEX, -1);
        if (parts.length < 5)
            return null;

        int id = Integer.parseInt(parts[0].trim());
        String name = parts[1].trim();
        String email = parts[2].trim();
        String password = parts[3].trim();
        String role = parts[4].trim();

        if ("ADMIN".equals(role)) {
            return new Administrator(id, name, email, password);
        }
        return new User(id, name, email, password, role);
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
    public void save(User user) {
        List<String> lines = readAllLines();
        int id = nextId(lines);
        user.setId(id);
        lines.add(toLine(user));
        writeAllLines(lines);
    }

    @Override
    public User findByEmail(String email) {
        for (String line : readAllLines()) {
            User user = parseLine(line);
            if (user != null && email.equals(user.getEmail())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User findById(int id) {
        for (String line : readAllLines()) {
            User user = parseLine(line);
            if (user != null && user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void update(User user) {
        List<String> lines = readAllLines();
        for (int i = 0; i < lines.size(); i++) {
            User existing = parseLine(lines.get(i));
            if (existing != null && existing.getId() == user.getId()) {
                lines.set(i, toLine(user));
                writeAllLines(lines);
                return;
            }
        }
    }
}

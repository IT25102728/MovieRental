package service;

import model.User;
import util.AppConfig;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * UserService - handles all user-related business logic (Service layer in MVC)
 */
public class UserService {

    private String getFile() { return AppConfig.getUsersFile(); }

    /**
     * Register a new user - returns error message or null on success
     */
    public String register(String id, String name, String email, String password, String role) {
        List<String> lines = FileUtil.readLines(getFile());
        // Check email uniqueness
        for (String line : lines) {
            String[] parts = line.split(",", -1);
            if (parts.length > 2 && parts[2].trim().equalsIgnoreCase(email)) {
                return "Email already registered.";
            }
        }
        String newId = (id != null && !id.isEmpty()) ? id : FileUtil.generateId("U");
        User user = new User(newId, name, email, password, role == null ? "user" : role);
        FileUtil.appendLine(getFile(), user.toFileString());
        return null; // success
    }

    /**
     * Authenticate user by email + password
     */
    public User login(String email, String password) {
        List<String> lines = FileUtil.readLines(getFile());
        for (String line : lines) {
            User user = User.fromFileString(line);
            if (user != null
                    && user.getEmail().equalsIgnoreCase(email)
                    && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Get all users
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (String line : FileUtil.readLines(getFile())) {
            User u = User.fromFileString(line);
            if (u != null) users.add(u);
        }
        return users;
    }

    /**
     * Find user by ID
     */
    public User findById(String id) {
        String line = FileUtil.findById(FileUtil.readLines(getFile()), id);
        return line != null ? User.fromFileString(line) : null;
    }

    /**
     * Update user profile
     */
    public boolean update(String id, String name, String email, String password) {
        User existing = findById(id);
        if (existing == null) return false;
        existing.setName(name);
        existing.setEmail(email);
        if (password != null && !password.isEmpty()) existing.setPassword(password);
        return FileUtil.updateById(getFile(), id, existing.toFileString());
    }

    /**
     * Delete user by ID
     */
    public boolean delete(String id) {
        return FileUtil.deleteById(getFile(), id);
    }
}

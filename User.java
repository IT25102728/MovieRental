package model;

/**
 * Base User class - demonstrates Encapsulation and serves as parent for Inheritance
 */
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String role; // "admin" or "user"

    public User() {}

    public User(String id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters (Encapsulation)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    /**
     * Polymorphic method - overridden by subclasses
     */
    public String getUserType() {
        return "User";
    }

    /**
     * Convert user to CSV line for file storage
     */
    public String toFileString() {
        return id + "," + name + "," + email + "," + password + "," + role;
    }

    /**
     * Parse a CSV line from users.txt into a User object
     */
    public static User fromFileString(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length < 5) return null;
        String role = parts[4].trim();
        if ("admin".equalsIgnoreCase(role)) {
            return new AdminUser(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(), role);
        } else {
            return new RegularUser(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(), role);
        }
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name=" + name + ", email=" + email + ", role=" + role + "}";
    }
}

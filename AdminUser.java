// AdminUser.java (Inheritance)
public class AdminUser extends User {
    private String role; // e.g., "SuperAdmin", "Editor"
    private String password;

    public AdminUser(String userId, String username, String password, String role) {
        super(userId, username);
        this.password = password;
        this.role = role;
    }

    // Encapsulation
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return getUsername() + "," + password + "," + role;
    }
}
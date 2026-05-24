package model;

/**
 * AdminUser extends User - demonstrates Inheritance and Polymorphism
 */
public class AdminUser extends User {
    private String permission; // e.g., "full", "readonly"

    public AdminUser() {
        super();
        setRole("admin");
    }

    public AdminUser(String id, String name, String email, String password, String role) {
        super(id, name, email, password, role);
        this.permission = "full";
    }

    public AdminUser(String id, String name, String email, String password, String role, String permission) {
        super(id, name, email, password, role);
        this.permission = permission;
    }

    public String getPermission() { return permission; }
    public void setPermission(String permission) { this.permission = permission; }

    /**
     * Polymorphic override
     */
    @Override
    public String getUserType() {
        return "Administrator";
    }

    @Override
    public String toFileString() {
        return super.toFileString();
    }
}

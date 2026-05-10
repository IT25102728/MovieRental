// AdminUser.java
public class AdminUser extends User {
    private String permissions;

    public AdminUser(String id, String name, String permissions) {
        super(id, name);
        this.permissions = permissions;
    }

    public String getPermissions() { return permissions; }
    public void setPermissions(String permissions) { this.permissions = permissions; }

    @Override
    public String getRole() {
        return "Administrator";
    }
}
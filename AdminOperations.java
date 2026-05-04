import java.util.List;

// Abstraction
public interface AdminOperations {
    void createAdmin(AdminUser admin);
    List<AdminUser> readAdmins();
    void updatePermissions(String username, String newRole);
    void deleteAdmin(String username);
}

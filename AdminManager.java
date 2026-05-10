import java.io.*;
import java.util.*;

public class AdminManager {
    private final String FILE_PATH = "admins.txt";

    // Create - Add Admin
    public void saveAdmin(AdminUser admin) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(admin.getId() + "," + admin.getName() + "," + admin.getPermissions());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read - Get all Admins
    public List<AdminUser> getAllAdmins() {
        List<AdminUser> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    list.add(new AdminUser(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Delete Admin by ID
    public void deleteAdmin(String id) {
        List<AdminUser> admins = getAllAdmins();
        admins.removeIf(a -> a.getId().equals(id));
        rewriteFile(admins);
    }

    // Update Permissions
    public void updateAdmin(String id, String newPerm) {
        List<AdminUser> admins = getAllAdmins();
        for (AdminUser a : admins) {
            if (a.getId().equals(id)) {
                a.setPermissions(newPerm);
            }
        }
        rewriteFile(admins);
    }

    private void rewriteFile(List<AdminUser> admins) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (AdminUser a : admins) {
                bw.write(a.getId() + "," + a.getName() + "," + a.getPermissions());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
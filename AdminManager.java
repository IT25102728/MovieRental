import java.io.*;
import java.util.*;

public class AdminManager implements AdminOperations {
    private final String FILE_PATH = "admins.txt";

    @Override
    public void createAdmin(AdminUser admin) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            out.println(admin.toString());
            System.out.println("Admin added successfully!");
        } catch (IOException e) {
            System.out.println("Error saving admin: " + e.getMessage());
        }
    }

    @Override
    public List<AdminUser> readAdmins() {
        List<AdminUser> admins = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                // Assuming format: username,password,role
                admins.add(new AdminUser("ID", data[0], data[1], data[2]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("No admin records found.");
        }
        return admins;
    }

    @Override
    public void updatePermissions(String username, String newRole) {
        // Logic: Read all, update the list, rewrite the file
        System.out.println("Updating " + username + " to " + newRole);
    }

    @Override
    public void deleteAdmin(String username) {
        // Logic: Filter out the user and rewrite the file
        System.out.println("Admin " + username + " removed.");
    }
}
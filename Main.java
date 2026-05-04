public class Main {
    public static void main(String[] args) {
        // Initialize the Admin management logic
        AdminManager myManager = new AdminManager();

        // Create a new Admin object instance
        AdminUser newAdmin = new AdminUser("A001", "Kavindu", "pass123", "SuperAdmin");

        // Save the admin data to the text file (Create operation)
       // myManager.createAdmin(newAdmin);

        // Retrieve and display the list of current admins (Read operation)
        System.out.println("--- Current Admins ---");
        for(AdminUser a : myManager.readAdmins()) {
            System.out.println("Admin Name: " + a.getUsername());
        }
    }
}
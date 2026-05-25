package util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import service.UserService;
import service.AdminService;
import service.MovieService;

/**
 * AppContextListener - runs on server startup to initialize data files
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialize config with the real path
        AppConfig.init(sce.getServletContext());

        // Seed default admin if no admins exist
        AdminService adminService = new AdminService();
        if (adminService.getAllAdmins().isEmpty()) {
            adminService.addAdmin("ADM001", "Super Admin", "admin@movies.com", "admin123", "full");
            System.out.println("[Startup] Default admin seeded: admin@movies.com / admin123");
        }

        // Seed sample movies if none exist
        MovieService movieService = new MovieService();
        if (movieService.getAllMovies().isEmpty()) {
            movieService.addMovie("M001", "The Dark Knight", "Action", 3.99, true);
            movieService.addMovie("M002", "The Hangover", "Comedy", 2.99, true);
            movieService.addMovie("M003", "Get Out", "Horror", 2.49, true);
            movieService.addMovie("M004", "Avengers Endgame", "Action", 4.99, true);
            movieService.addMovie("M005", "Superbad", "Comedy", 2.49, true);
            movieService.addMovie("M006", "It", "Horror", 3.49, true);
            System.out.println("[Startup] Sample movies seeded.");
        }

        // Seed sample user if none exist
        UserService userService = new UserService();
        if (userService.getAllUsers().isEmpty()) {
            userService.register("U001", "John Doe", "user@movies.com", "user123", "user");
            System.out.println("[Startup] Sample user seeded: user@movies.com / user123");
        }

        System.out.println("[Startup] Online Movie Rental System initialized successfully.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[Shutdown] Movie Rental System shutting down.");
    }
}

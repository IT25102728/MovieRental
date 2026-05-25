package service;

import model.RegularRental;
import model.RentalTransaction;
import util.AppConfig;
import util.DateUtil;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * RentalService - rental CRUD, return, and fine calculation
 */
public class RentalService {

    private String getFile() { return AppConfig.getRentalsFile(); }

    /**
     * Rent a movie - creates a new rental record
     */
    public String rentMovie(String userId, String movieId) {
        // Check if user already has active rental for this movie
        for (RentalTransaction r : getAllRentals()) {
            if (r.getUserId().equals(userId)
                    && r.getMovieId().equals(movieId)
                    && "active".equals(r.getStatus())) {
                return "You already have an active rental for this movie.";
            }
        }
        String rentalId = FileUtil.generateId("R");
        RegularRental rental = new RegularRental(rentalId, userId, movieId,
                DateUtil.today(), "", "active", 0.0);
        FileUtil.appendLine(getFile(), rental.toFileString());
        return null; // success
    }

    /**
     * Return a movie - marks rental as returned and calculates fine
     */
    public String returnMovie(String rentalId) {
        List<String> lines = FileUtil.readLines(getFile());
        for (int i = 0; i < lines.size(); i++) {
            RentalTransaction r = RentalTransaction.fromFileString(lines.get(i));
            if (r != null && r.getRentalId().equals(rentalId) && "active".equals(r.getStatus())) {
                double fine = DateUtil.calculateFine(r.getRentDate(), r.getDailyLateFee());
                r.setReturnDate(DateUtil.today());
                r.setStatus("returned");
                r.setFine(fine);
                lines.set(i, r.toFileString());
                FileUtil.writeLines(getFile(), lines);
                return null; // success
            }
        }
        return "Rental not found or already returned.";
    }

    public List<RentalTransaction> getAllRentals() {
        List<RentalTransaction> list = new ArrayList<>();
        for (String line : FileUtil.readLines(getFile())) {
            RentalTransaction r = RentalTransaction.fromFileString(line);
            if (r != null) list.add(r);
        }
        return list;
    }

    /**
     * Get rentals for a specific user
     */
    public List<RentalTransaction> getRentalsByUser(String userId) {
        List<RentalTransaction> list = new ArrayList<>();
        for (RentalTransaction r : getAllRentals()) {
            if (r.getUserId().equals(userId)) list.add(r);
        }
        return list;
    }

    public RentalTransaction findById(String rentalId) {
        String line = FileUtil.findById(FileUtil.readLines(getFile()), rentalId);
        return line != null ? RentalTransaction.fromFileString(line) : null;
    }

    /**
     * Get active rental for a user + movie pair
     */
    public RentalTransaction getActiveRental(String userId, String movieId) {
        for (RentalTransaction r : getAllRentals()) {
            if (r.getUserId().equals(userId)
                    && r.getMovieId().equals(movieId)
                    && "active".equals(r.getStatus())) {
                return r;
            }
        }
        return null;
    }
}

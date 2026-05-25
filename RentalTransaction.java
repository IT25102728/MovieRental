package model;

/**
 * RentalTransaction - abstract base class demonstrating Abstraction
 * Fields: rentalId,userId,movieId,rentDate,returnDate,status,fine
 */
public abstract class RentalTransaction {
    private String rentalId;
    private String userId;
    private String movieId;
    private String rentDate;
    private String returnDate; // empty if not returned
    private String status;    // "active" or "returned"
    private double fine;

    public RentalTransaction() {}

    public RentalTransaction(String rentalId, String userId, String movieId,
                             String rentDate, String returnDate, String status, double fine) {
        this.rentalId = rentalId;
        this.userId = userId;
        this.movieId = movieId;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.status = status;
        this.fine = fine;
    }

    // Getters and Setters
    public String getRentalId() { return rentalId; }
    public void setRentalId(String rentalId) { this.rentalId = rentalId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getMovieId() { return movieId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }

    public String getRentDate() { return rentDate; }
    public void setRentDate(String rentDate) { this.rentDate = rentDate; }

    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getFine() { return fine; }
    public void setFine(double fine) { this.fine = fine; }

    /**
     * Abstract method - Abstraction; each subclass defines rental type
     */
    public abstract String getRentalType();

    /**
     * Abstract method - each subclass defines late fee per day
     */
    public abstract double getDailyLateFee();

    /**
     * Convert to CSV for rentals.txt
     */
    public String toFileString() {
        return rentalId + "," + userId + "," + movieId + "," + rentDate + ","
                + (returnDate == null ? "" : returnDate) + "," + status + "," + fine;
    }

    /**
     * Parse CSV line from rentals.txt
     */
    public static RentalTransaction fromFileString(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length < 7) return null;
        String rentalId = parts[0].trim();
        String userId = parts[1].trim();
        String movieId = parts[2].trim();
        String rentDate = parts[3].trim();
        String returnDate = parts[4].trim();
        String status = parts[5].trim();
        double fine = 0;
        try { fine = Double.parseDouble(parts[6].trim()); } catch (Exception ignored) {}

        // Default to RegularRental; extend logic for premium if needed
        return new RegularRental(rentalId, userId, movieId, rentDate, returnDate, status, fine);
    }
}

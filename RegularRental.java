package model;

/**
 * RegularRental - standard rental with basic late fee
 */
public class RegularRental extends RentalTransaction {
    public RegularRental() { super(); }

    public RegularRental(String rentalId, String userId, String movieId,
                         String rentDate, String returnDate, String status, double fine) {
        super(rentalId, userId, movieId, rentDate, returnDate, status, fine);
    }

    @Override
    public String getRentalType() { return "Regular"; }

    @Override
    public double getDailyLateFee() { return 1.50; } // $1.50 per day late
}

package model;

/**
 * PremiumRental - premium members get lower late fees
 */
public class PremiumRental extends RentalTransaction {
    public PremiumRental() { super(); }

    public PremiumRental(String rentalId, String userId, String movieId,
                         String rentDate, String returnDate, String status, double fine) {
        super(rentalId, userId, movieId, rentDate, returnDate, status, fine);
    }

    @Override
    public String getRentalType() { return "Premium"; }

    @Override
    public double getDailyLateFee() { return 0.75; } // discounted late fee
}

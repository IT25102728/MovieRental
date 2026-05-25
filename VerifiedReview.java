package model;

public class VerifiedReview extends Review {
    public VerifiedReview() { super(); }
    public VerifiedReview(String reviewId, String userId, String movieId, int rating, String comment) {
        super(reviewId, userId, movieId, rating, comment);
    }
    @Override
    public String getReviewType() { return "Verified"; }
}

package model;

public class PublicReview extends Review {
    public PublicReview() { super(); }
    public PublicReview(String reviewId, String userId, String movieId, int rating, String comment) {
        super(reviewId, userId, movieId, rating, comment);
    }
    @Override
    public String getReviewType() { return "Public"; }
}

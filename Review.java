package model;

/**
 * Review base class - reviewId,userId,movieId,rating,comment
 */
public class Review {
    private String reviewId;
    private String userId;
    private String movieId;
    private int rating; // 1-5
    private String comment;

    public Review() {}

    public Review(String reviewId, String userId, String movieId, int rating, String comment) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
        this.comment = comment;
    }

    public String getReviewId() { return reviewId; }
    public void setReviewId(String reviewId) { this.reviewId = reviewId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getMovieId() { return movieId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getReviewType() { return "Public"; }

    public String toFileString() {
        // Escape commas in comment
        String safeComment = comment == null ? "" : comment.replace(",", "&#44;");
        return reviewId + "," + userId + "," + movieId + "," + rating + "," + safeComment;
    }

    public static Review fromFileString(String line) {
        String[] parts = line.split(",", 5);
        if (parts.length < 5) return null;
        int rating = 0;
        try { rating = Integer.parseInt(parts[3].trim()); } catch (Exception ignored) {}
        String comment = parts[4].trim().replace("&#44;", ",");
        return new PublicReview(parts[0].trim(), parts[1].trim(), parts[2].trim(), rating, comment);
    }
}

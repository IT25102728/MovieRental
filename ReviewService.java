package service;

import model.Review;
import util.AppConfig;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ReviewService - handles review CRUD operations
 */
public class ReviewService {

    private String getFile() { return AppConfig.getReviewsFile(); }

    /**
     * Add a new review
     */
    public String addReview(String userId, String movieId, int rating, String comment) {
        if (rating < 1 || rating > 5) return "Rating must be between 1 and 5.";
        String reviewId = FileUtil.generateId("REV");
        Review review = new Review(reviewId, userId, movieId, rating, comment);
        FileUtil.appendLine(getFile(), review.toFileString());
        return null;
    }

    public List<Review> getAllReviews() {
        List<Review> list = new ArrayList<>();
        for (String line : FileUtil.readLines(getFile())) {
            Review r = Review.fromFileString(line);
            if (r != null) list.add(r);
        }
        return list;
    }

    public List<Review> getReviewsByMovie(String movieId) {
        List<Review> list = new ArrayList<>();
        for (Review r : getAllReviews()) {
            if (r.getMovieId().equals(movieId)) list.add(r);
        }
        return list;
    }

    public List<Review> getReviewsByUser(String userId) {
        List<Review> list = new ArrayList<>();
        for (Review r : getAllReviews()) {
            if (r.getUserId().equals(userId)) list.add(r);
        }
        return list;
    }

    public Review findById(String reviewId) {
        String line = FileUtil.findById(FileUtil.readLines(getFile()), reviewId);
        return line != null ? Review.fromFileString(line) : null;
    }

    public boolean update(String reviewId, int rating, String comment) {
        Review existing = findById(reviewId);
        if (existing == null) return false;
        existing.setRating(rating);
        existing.setComment(comment);
        return FileUtil.updateById(getFile(), reviewId, existing.toFileString());
    }

    public boolean delete(String reviewId) {
        return FileUtil.deleteById(getFile(), reviewId);
    }

    /**
     * Calculate average rating for a movie
     */
    public double getAverageRating(String movieId) {
        List<Review> reviews = getReviewsByMovie(movieId);
        if (reviews.isEmpty()) return 0;
        double sum = 0;
        for (Review r : reviews) sum += r.getRating();
        return Math.round((sum / reviews.size()) * 10.0) / 10.0;
    }
}

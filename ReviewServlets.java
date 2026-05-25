package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Movie;
import model.Review;
import model.User;
import service.MovieService;
import service.ReviewService;
import util.SessionUtil;

import java.io.IOException;
import java.util.List;

// ─────────────────────────────────────────────
//  AddReviewServlet
// ─────────────────────────────────────────────
@WebServlet("/addReview")
class AddReviewServlet extends HttpServlet {
    private final ReviewService reviewService = new ReviewService();
    private final MovieService  movieService  = new MovieService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireLogin(req, resp)) return;
        String movieId = req.getParameter("movieId");
        Movie movie = movieService.findById(movieId);
        req.setAttribute("movie", movie);
        req.getRequestDispatcher("/jsp/submitReview.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireLogin(req, resp)) return;
        User user     = SessionUtil.getUser(req);
        String movieId = req.getParameter("movieId");
        String comment = req.getParameter("comment");
        int rating = 0;
        try { rating = Integer.parseInt(req.getParameter("rating")); } catch (Exception ignored) {}

        String error = reviewService.addReview(user.getId(), movieId, rating, comment);
        if (error != null) {
            req.setAttribute("error", error);
            Movie movie = movieService.findById(movieId);
            req.setAttribute("movie", movie);
            req.getRequestDispatcher("/jsp/submitReview.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/reviews?movieId=" + movieId + "&added=true");
        }
    }
}

// ─────────────────────────────────────────────
//  UpdateReviewServlet
// ─────────────────────────────────────────────
@WebServlet("/editReview")
class UpdateReviewServlet extends HttpServlet {
    private final ReviewService reviewService = new ReviewService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireLogin(req, resp)) return;
        String reviewId = req.getParameter("reviewId");
        Review review = reviewService.findById(reviewId);
        req.setAttribute("review", review);
        req.getRequestDispatcher("/jsp/submitReview.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireLogin(req, resp)) return;
        String reviewId = req.getParameter("reviewId");
        String comment  = req.getParameter("comment");
        int rating = 0;
        try { rating = Integer.parseInt(req.getParameter("rating")); } catch (Exception ignored) {}

        reviewService.update(reviewId, rating, comment);
        resp.sendRedirect(req.getContextPath() + "/reviews?updated=true");
    }
}

// ─────────────────────────────────────────────
//  DeleteReviewServlet
// ─────────────────────────────────────────────
@WebServlet("/deleteReview")
class DeleteReviewServlet extends HttpServlet {
    private final ReviewService reviewService = new ReviewService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireLogin(req, resp)) return;
        User user       = SessionUtil.getUser(req);
        String reviewId = req.getParameter("reviewId");
        Review review   = reviewService.findById(reviewId);

        // Only owner or admin can delete
        if (review != null &&
            (review.getUserId().equals(user.getId()) || "admin".equalsIgnoreCase(user.getRole()))) {
            reviewService.delete(reviewId);
        }
        resp.sendRedirect(req.getContextPath() + "/reviews?deleted=true");
    }
}

// ─────────────────────────────────────────────
//  ViewReviewsServlet
// ─────────────────────────────────────────────
@WebServlet("/reviews")
class ViewReviewsServlet extends HttpServlet {
    private final ReviewService reviewService = new ReviewService();
    private final MovieService  movieService  = new MovieService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireLogin(req, resp)) return;
        String movieId = req.getParameter("movieId");
        List<Review> reviews;
        if (movieId != null && !movieId.isEmpty()) {
            reviews = reviewService.getReviewsByMovie(movieId);
            req.setAttribute("filterMovie", movieService.findById(movieId));
        } else {
            reviews = reviewService.getAllReviews();
        }
        req.setAttribute("reviews", reviews);
        req.setAttribute("movieService", movieService);
        req.getRequestDispatcher("/jsp/reviews.jsp").forward(req, resp);
    }
}

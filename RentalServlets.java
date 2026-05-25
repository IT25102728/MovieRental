package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Movie;
import model.RentalTransaction;
import model.User;
import service.MovieService;
import service.PaymentService;
import service.RentalService;
import util.SessionUtil;

import java.io.IOException;
import java.util.List;

// ─────────────────────────────────────────────
//  RentMovieServlet
// ─────────────────────────────────────────────
@WebServlet("/rentMovie")
class RentMovieServlet extends HttpServlet {
    private final RentalService rentalService = new RentalService();
    private final MovieService  movieService  = new MovieService();
    private final PaymentService paymentService = new PaymentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireLogin(req, resp)) return;
        String movieId = req.getParameter("movieId");
        Movie movie = movieService.findById(movieId);
        req.setAttribute("movie", movie);
        req.getRequestDispatcher("/jsp/rentMovie.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireLogin(req, resp)) return;
        User user          = SessionUtil.getUser(req);
        String movieId     = req.getParameter("movieId");
        String payMethod   = req.getParameter("paymentMethod");

        Movie movie = movieService.findById(movieId);
        if (movie == null || !movie.isAvailable()) {
            req.setAttribute("error", "Movie not available.");
            req.setAttribute("movie", movie);
            req.getRequestDispatcher("/jsp/rentMovie.jsp").forward(req, resp);
            return;
        }

        String error = rentalService.rentMovie(user.getId(), movieId);
        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("movie", movie);
            req.getRequestDispatcher("/jsp/rentMovie.jsp").forward(req, resp);
            return;
        }

        // Process payment
        paymentService.processPayment(user.getId(), movie.getPrice(), payMethod);
        // Mark movie unavailable
        movieService.setAvailability(movieId, false);

        resp.sendRedirect(req.getContextPath() + "/rentalHistory?rented=true");
    }
}

// ─────────────────────────────────────────────
//  ReturnMovieServlet
// ─────────────────────────────────────────────
@WebServlet("/returnMovie")
class ReturnMovieServlet extends HttpServlet {
    private final RentalService rentalService = new RentalService();
    private final MovieService  movieService  = new MovieService();
    private final PaymentService paymentService = new PaymentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireLogin(req, resp)) return;
        String rentalId = req.getParameter("rentalId");
        RentalTransaction rental = rentalService.findById(rentalId);
        Movie movie = rental != null ? movieService.findById(rental.getMovieId()) : null;
        req.setAttribute("rental", rental);
        req.setAttribute("movie", movie);
        req.getRequestDispatcher("/jsp/returnMovie.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireLogin(req, resp)) return;
        User user       = SessionUtil.getUser(req);
        String rentalId = req.getParameter("rentalId");

        RentalTransaction rental = rentalService.findById(rentalId);
        if (rental == null || !rental.getUserId().equals(user.getId())) {
            resp.sendRedirect(req.getContextPath() + "/rentalHistory?error=notfound");
            return;
        }

        String error = rentalService.returnMovie(rentalId);
        if (error == null) {
            // Re-fetch to get updated fine
            RentalTransaction updated = rentalService.findById(rentalId);
            if (updated != null && updated.getFine() > 0) {
                paymentService.processPayment(user.getId(), updated.getFine(), "cash");
            }
            // Make movie available again
            movieService.setAvailability(rental.getMovieId(), true);
            resp.sendRedirect(req.getContextPath() + "/rentalHistory?returned=true");
        } else {
            resp.sendRedirect(req.getContextPath() + "/rentalHistory?error=" + error);
        }
    }
}

// ─────────────────────────────────────────────
//  RentalHistoryServlet
// ─────────────────────────────────────────────
@WebServlet("/rentalHistory")
class RentalHistoryServlet extends HttpServlet {
    private final RentalService rentalService = new RentalService();
    private final MovieService  movieService  = new MovieService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireLogin(req, resp)) return;
        User user = SessionUtil.getUser(req);
        List<RentalTransaction> rentals;
        if ("admin".equalsIgnoreCase(user.getRole())) {
            rentals = rentalService.getAllRentals();
        } else {
            rentals = rentalService.getRentalsByUser(user.getId());
        }
        req.setAttribute("rentals", rentals);
        req.setAttribute("movieService", movieService);
        req.getRequestDispatcher("/jsp/rentalHistory.jsp").forward(req, resp);
    }
}

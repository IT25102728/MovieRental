package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import service.MovieService;
import service.PaymentService;
import service.RentalService;
import service.UserService;

import util.SessionUtil;

import java.io.IOException;

@WebServlet("/reports")
public class ReportServlet extends HttpServlet {

    private final PaymentService paymentService =
            new PaymentService();

    private final RentalService rentalService =
            new RentalService();

    private final UserService userService =
            new UserService();

    private final MovieService movieService =
            new MovieService();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        if (SessionUtil.requireAdmin(req, resp)) {
            return;
        }

        req.setAttribute(
                "totalRevenue",
                String.format(
                        "%.2f",
                        paymentService.getTotalRevenue()
                )
        );

        req.setAttribute(
                "totalRentals",
                rentalService.getAllRentals().size()
        );

        req.setAttribute(
                "totalUsers",
                userService.getAllUsers().size()
        );

        req.setAttribute(
                "totalMovies",
                movieService.getAllMovies().size()
        );

        req.setAttribute(
                "allPayments",
                paymentService.getAllPayments()
        );

        req.setAttribute(
                "allRentals",
                rentalService.getAllRentals()
        );

        req.getRequestDispatcher("/jsp/reports.jsp")
                .forward(req, resp);
    }
}
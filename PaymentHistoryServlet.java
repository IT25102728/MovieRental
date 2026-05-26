package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Payment;
import model.User;

import service.PaymentService;

import util.SessionUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/paymentHistory")
public class PaymentHistoryServlet extends HttpServlet {

    private final PaymentService paymentService =
            new PaymentService();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        if (SessionUtil.requireLogin(req, resp)) {
            return;
        }

        User user = SessionUtil.getUser(req);

        List<Payment> payments;

        // Admin sees all payments
        if ("admin".equalsIgnoreCase(user.getRole())) {

            payments =
                    paymentService.getAllPayments();

        } else {

            payments =
                    paymentService.getPaymentsByUser(
                            user.getId()
                    );
        }

        req.setAttribute("payments", payments);

        req.setAttribute(
                "totalSpent",
                String.format(
                        "%.2f",
                        paymentService.getTotalByUser(
                                user.getId()
                        )
                )
        );

        req.getRequestDispatcher("/jsp/paymentHistory.jsp")
                .forward(req, resp);
    }
}
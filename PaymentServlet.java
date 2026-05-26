package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.User;

import service.PaymentService;

import util.SessionUtil;

import java.io.IOException;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

    private final PaymentService paymentService =
            new PaymentService();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        if (SessionUtil.requireLogin(req, resp)) {
            return;
        }

        req.getRequestDispatcher("/jsp/payment.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {

        if (SessionUtil.requireLogin(req, resp)) {
            return;
        }

        User user = SessionUtil.getUser(req);

        String amountStr =
                req.getParameter("amount");

        String paymentMethod =
                req.getParameter("paymentMethod");

        double amount;

        try {

            amount = Double.parseDouble(amountStr);

        } catch (Exception e) {

            req.setAttribute(
                    "error",
                    "Invalid amount."
            );

            req.getRequestDispatcher("/jsp/payment.jsp")
                    .forward(req, resp);

            return;
        }

        String error = paymentService.processPayment(
                user.getId(),
                amount,
                paymentMethod
        );

        if (error != null) {

            req.setAttribute("error", error);

            req.getRequestDispatcher("/jsp/payment.jsp")
                    .forward(req, resp);

        } else {

            resp.sendRedirect(
                    req.getContextPath()
                            + "/paymentHistory?paid=true"
            );
        }
    }
}
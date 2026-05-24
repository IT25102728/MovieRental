package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.UserService;

import java.io.IOException;

/**
 * RegisterServlet - handles GET (show form) and POST (process registration)
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name     = req.getParameter("name");
        String email    = req.getParameter("email");
        String password = req.getParameter("password");
        String confirm  = req.getParameter("confirmPassword");

        // Server-side validation
        if (name == null || name.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            req.setAttribute("error", "All fields are required.");
            req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
            return;
        }
        if (!password.equals(confirm)) {
            req.setAttribute("error", "Passwords do not match.");
            req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
            return;
        }
        if (password.length() < 6) {
            req.setAttribute("error", "Password must be at least 6 characters.");
            req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
            return;
        }

        String error = userService.register(null, name.trim(), email.trim(), password, "user");
        if (error != null) {
            req.setAttribute("error", error);
            req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login?registered=true");
        }
    }
}

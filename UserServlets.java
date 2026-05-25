package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import service.UserService;
import util.SessionUtil;

import java.io.IOException;
import java.util.List;

// ─────────────────────────────────────────────
//  LogoutServlet
// ─────────────────────────────────────────────
@WebServlet("/logout")
class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        SessionUtil.logout(req);
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}

// ─────────────────────────────────────────────
//  UpdateProfileServlet
// ─────────────────────────────────────────────
@WebServlet("/updateProfile")
class UpdateProfileServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireLogin(req, resp)) return;
        req.getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireLogin(req, resp)) return;
        User current = SessionUtil.getUser(req);
        String name     = req.getParameter("name");
        String email    = req.getParameter("email");
        String password = req.getParameter("password");

        if (name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            req.setAttribute("error", "Name and email are required.");
            req.getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);
            return;
        }

        boolean updated = userService.update(current.getId(), name.trim(), email.trim(), password);
        if (updated) {
            // Refresh session with updated data
            User refreshed = userService.findById(current.getId());
            if (refreshed != null) SessionUtil.setUser(req, refreshed);
            req.setAttribute("success", "Profile updated successfully.");
        } else {
            req.setAttribute("error", "Update failed.");
        }
        req.getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);
    }
}

// ─────────────────────────────────────────────
//  DeleteUserServlet  (admin only)
// ─────────────────────────────────────────────
@WebServlet("/deleteUser")
class DeleteUserServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireAdmin(req, resp)) return;
        String id = req.getParameter("id");
        userService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/users");
    }
}

// ─────────────────────────────────────────────
//  ViewUsersServlet  (admin only)
// ─────────────────────────────────────────────
@WebServlet("/users")
class ViewUsersServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireAdmin(req, resp)) return;
        List<User> users = userService.getAllUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/jsp/users.jsp").forward(req, resp);
    }
}

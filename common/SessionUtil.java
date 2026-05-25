package util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import java.io.IOException;

/**
 * SessionUtil - Helper for session-based authentication
 */
public class SessionUtil {

    public static final String SESSION_USER = "loggedUser";

    /**
     * Store the logged-in user in the session
     */
    public static void setUser(HttpServletRequest req, User user) {
        HttpSession session = req.getSession(true);
        session.setAttribute(SESSION_USER, user);
        session.setMaxInactiveInterval(30 * 60); // 30 minutes
    }

    /**
     * Get the logged-in user from session (null if not logged in)
     */
    public static User getUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) return null;
        return (User) session.getAttribute(SESSION_USER);
    }

    /**
     * Check if a user is logged in
     */
    public static boolean isLoggedIn(HttpServletRequest req) {
        return getUser(req) != null;
    }

    /**
     * Check if logged-in user is admin
     */
    public static boolean isAdmin(HttpServletRequest req) {
        User user = getUser(req);
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }

    /**
     * Invalidate the session on logout
     */
    public static void logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
    }

    /**
     * Redirect to login if not authenticated
     * @return true if redirect happened (caller should return immediately)
     */
    public static boolean requireLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!isLoggedIn(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return true;
        }
        return false;
    }

    /**
     * Redirect to home if not admin
     */
    public static boolean requireAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (requireLogin(req, resp)) return true;
        if (!isAdmin(req)) {
            resp.sendRedirect(req.getContextPath() + "/movies");
            return true;
        }
        return false;
    }
}

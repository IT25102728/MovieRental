package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Movie;
import service.MovieService;
import util.SessionUtil;

import java.io.IOException;
import java.util.List;

// ─────────────────────────────────────────────
//  MoviesServlet - list all movies
// ─────────────────────────────────────────────
@WebServlet("/movies")
class MoviesServlet extends HttpServlet {
    private final MovieService movieService = new MovieService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireLogin(req, resp)) return;
        List<Movie> movies = movieService.getAllMovies();
        req.setAttribute("movies", movies);
        req.getRequestDispatcher("/jsp/movies.jsp").forward(req, resp);
    }
}

// ─────────────────────────────────────────────
//  AddMovieServlet (admin only)
// ─────────────────────────────────────────────
@WebServlet("/addMovie")
class AddMovieServlet extends HttpServlet {
    private final MovieService movieService = new MovieService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireAdmin(req, resp)) return;
        req.getRequestDispatcher("/jsp/addMovie.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireAdmin(req, resp)) return;
        String title  = req.getParameter("title");
        String genre  = req.getParameter("genre");
        String priceS = req.getParameter("price");
        boolean avail = "true".equals(req.getParameter("available"));

        if (title == null || title.trim().isEmpty() || genre == null || priceS == null) {
            req.setAttribute("error", "All fields are required.");
            req.getRequestDispatcher("/jsp/addMovie.jsp").forward(req, resp);
            return;
        }
        double price;
        try { price = Double.parseDouble(priceS); }
        catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid price.");
            req.getRequestDispatcher("/jsp/addMovie.jsp").forward(req, resp);
            return;
        }
        movieService.addMovie(null, title.trim(), genre.trim(), price, avail);
        resp.sendRedirect(req.getContextPath() + "/movies?added=true");
    }
}

// ─────────────────────────────────────────────
//  UpdateMovieServlet (admin only)
// ─────────────────────────────────────────────
@WebServlet("/editMovie")
class UpdateMovieServlet extends HttpServlet {
    private final MovieService movieService = new MovieService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireAdmin(req, resp)) return;
        String id = req.getParameter("id");
        Movie movie = movieService.findById(id);
        if (movie == null) { resp.sendRedirect(req.getContextPath() + "/movies"); return; }
        req.setAttribute("movie", movie);
        req.getRequestDispatcher("/jsp/editMovie.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireAdmin(req, resp)) return;
        String id     = req.getParameter("id");
        String title  = req.getParameter("title");
        String genre  = req.getParameter("genre");
        String priceS = req.getParameter("price");
        boolean avail = "true".equals(req.getParameter("available"));

        double price;
        try { price = Double.parseDouble(priceS); }
        catch (NumberFormatException e) { price = 0; }

        movieService.update(id, title, genre, price, avail);
        resp.sendRedirect(req.getContextPath() + "/movies?updated=true");
    }
}

// ─────────────────────────────────────────────
//  DeleteMovieServlet (admin only)
// ─────────────────────────────────────────────
@WebServlet("/deleteMovie")
class DeleteMovieServlet extends HttpServlet {
    private final MovieService movieService = new MovieService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireAdmin(req, resp)) return;
        String id = req.getParameter("id");
        movieService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/movies?deleted=true");
    }
}

// ─────────────────────────────────────────────
//  SearchMovieServlet
// ─────────────────────────────────────────────
@WebServlet("/searchMovie")
class SearchMovieServlet extends HttpServlet {
    private final MovieService movieService = new MovieService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (SessionUtil.requireLogin(req, resp)) return;
        String keyword = req.getParameter("q");
        List<Movie> results = movieService.search(keyword);
        req.setAttribute("movies", results);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/jsp/searchMovie.jsp").forward(req, resp);
    }
}

package service;

import model.Movie;
import util.AppConfig;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * MovieService - handles movie CRUD and search
 */
public class MovieService {

    private String getFile() { return AppConfig.getMoviesFile(); }

    public void addMovie(String id, String title, String genre, double price, boolean available) {
        String newId = (id != null && !id.isEmpty()) ? id : FileUtil.generateId("M");
        Movie m = Movie.fromFileString(newId + "," + title + "," + genre + "," + price + "," + available);
        FileUtil.appendLine(getFile(), m.toFileString());
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        for (String line : FileUtil.readLines(getFile())) {
            Movie m = Movie.fromFileString(line);
            if (m != null) movies.add(m);
        }
        return movies;
    }

    public Movie findById(String id) {
        String line = FileUtil.findById(FileUtil.readLines(getFile()), id);
        return line != null ? Movie.fromFileString(line) : null;
    }

    /**
     * Search movies by title or genre (case-insensitive)
     */
    public List<Movie> search(String keyword) {
        List<Movie> results = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) return getAllMovies();
        String kw = keyword.trim().toLowerCase();
        for (Movie m : getAllMovies()) {
            if (m.getTitle().toLowerCase().contains(kw)
                    || m.getGenre().toLowerCase().contains(kw)) {
                results.add(m);
            }
        }
        return results;
    }

    public boolean update(String id, String title, String genre, double price, boolean available) {
        Movie existing = findById(id);
        if (existing == null) return false;
        existing.setTitle(title);
        existing.setGenre(genre);
        existing.setPrice(price);
        existing.setAvailable(available);
        return FileUtil.updateById(getFile(), id, existing.toFileString());
    }

    public boolean delete(String id) {
        return FileUtil.deleteById(getFile(), id);
    }

    /**
     * Set availability flag
     */
    public boolean setAvailability(String id, boolean available) {
        Movie m = findById(id);
        if (m == null) return false;
        m.setAvailable(available);
        return FileUtil.updateById(getFile(), id, m.toFileString());
    }
}

package com.example.movie.controller;

import com.example.movie.model.Movie;
import com.example.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private MovieService service;

    // ADD
    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "add-movie";
    }

    @PostMapping("/add")
    public String addMovie(@ModelAttribute Movie movie, Model model) {
        service.addMovie(movie);
        model.addAttribute("message", "Movie Added!");
        return "add-movie";
    }

    // READ
    @GetMapping("/list")
    public String listMovies(Model model) {
        List<Movie> movies = service.getAllMovies();
        model.addAttribute("movies", movies);
        return "movie-list";
    }

    // DELETE
    @GetMapping("/delete/{title}")
    public String deleteMovie(@PathVariable String title) {
        service.deleteMovie(title);
        return "redirect:/list";
    }

    // EDIT PAGE
    @GetMapping("/edit/{title}")
    public String editPage(@PathVariable String title, Model model) {
        List<Movie> movies = service.getAllMovies();

        for (Movie m : movies) {
            if (m.getTitle().equalsIgnoreCase(title)) {
                model.addAttribute("movie", m);
                break;
            }
        }

        return "edit-movie";
    }

    // UPDATE
    @PostMapping("/update")
    public String updateMovie(@ModelAttribute Movie movie) {
        service.updateMovie(movie);
        return "redirect:/list";
    }
}

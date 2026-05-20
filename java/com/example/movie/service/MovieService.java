package com.example.movie.service;

import com.example.movie.model.Movie;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private static final String FILE = "movies.txt";

    // CREATE
    public void addMovie(Movie movie) {
        try (FileWriter writer = new FileWriter(FILE, true)) {
            writer.write(movie.getTitle() + "," +
                    movie.getGenre() + "," +
                    movie.getPrice() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // READ
    public List<Movie> getAllMovies() {
        List<Movie> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                list.add(new Movie(data[0], data[1], Double.parseDouble(data[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    // DELETE
    public void deleteMovie(String title) {
        List<Movie> list = getAllMovies();

        try (FileWriter writer = new FileWriter(FILE)) {
            for (Movie m : list) {
                if (!m.getTitle().equalsIgnoreCase(title)) {
                    writer.write(m.getTitle() + "," +
                            m.getGenre() + "," +
                            m.getPrice() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void updateMovie(Movie updatedMovie) {
        List<Movie> list = getAllMovies();

        try (FileWriter writer = new FileWriter(FILE)) {
            for (Movie m : list) {
                if (m.getTitle().equalsIgnoreCase(updatedMovie.getTitle())) {
                    writer.write(updatedMovie.getTitle() + "," +
                            updatedMovie.getGenre() + "," +
                            updatedMovie.getPrice() + "\n");
                } else {
                    writer.write(m.getTitle() + "," +
                            m.getGenre() + "," +
                            m.getPrice() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


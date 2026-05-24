package model;

public class HorrorMovie extends Movie {
    public HorrorMovie() { super(); }
    public HorrorMovie(String id, String title, String genre, double price, boolean available) {
        super(id, title, genre, price, available);
    }
    @Override
    public String getMovieType() { return "Horror"; }
}

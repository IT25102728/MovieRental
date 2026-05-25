package model;

public class ComedyMovie extends Movie {
    public ComedyMovie() { super(); }
    public ComedyMovie(String id, String title, String genre, double price, boolean available) {
        super(id, title, genre, price, available);
    }
    @Override
    public String getMovieType() { return "Comedy"; }
}

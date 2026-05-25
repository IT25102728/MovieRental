package model;

/**
 * ActionMovie extends Movie - Inheritance + Polymorphism
 */
public class ActionMovie extends Movie {
    public ActionMovie() { super(); }
    public ActionMovie(String id, String title, String genre, double price, boolean available) {
        super(id, title, genre, price, available);
    }
    @Override
    public String getMovieType() { return "Action"; }
}

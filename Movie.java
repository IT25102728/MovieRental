package model;

/**
 * Movie base class - Encapsulation + base for Inheritance
 */
public class Movie {
    private String id;
    private String title;
    private String genre;
    private double price;
    private boolean available;

    public Movie() {}

    public Movie(String id, String title, String genre, double price, boolean available) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.available = available;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    /**
     * Polymorphic method - returns movie type label
     */
    public String getMovieType() {
        return "Movie";
    }

    /**
     * Convert to CSV line for movies.txt
     */
    public String toFileString() {
        return id + "," + title + "," + genre + "," + price + "," + available;
    }

    /**
     * Parse CSV line from movies.txt
     */
    public static Movie fromFileString(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length < 5) return null;
        String id = parts[0].trim();
        String title = parts[1].trim();
        String genre = parts[2].trim();
        double price = 0;
        try { price = Double.parseDouble(parts[3].trim()); } catch (Exception ignored) {}
        boolean available = Boolean.parseBoolean(parts[4].trim());

        switch (genre.toLowerCase()) {
            case "action": return new ActionMovie(id, title, genre, price, available);
            case "comedy": return new ComedyMovie(id, title, genre, price, available);
            case "horror": return new HorrorMovie(id, title, genre, price, available);
            default:       return new Movie(id, title, genre, price, available);
        }
    }

    @Override
    public String toString() {
        return "Movie{id=" + id + ", title=" + title + ", genre=" + genre + "}";
    }
}

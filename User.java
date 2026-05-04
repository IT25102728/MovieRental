// User.java (Base Class for Inheritance)
public abstract class User {
    private String userId;
    private String username;

    public User(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    // Getters and Setters (Encapsulation)
    public String getUsername() { return username; }
}
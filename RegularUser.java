package model;

/**
 * RegularUser extends User - demonstrates Inheritance and Polymorphism
 */
public class RegularUser extends User {

    public RegularUser() {
        super();
        setRole("user");
    }

    public RegularUser(String id, String name, String email, String password, String role) {
        super(id, name, email, password, role);
    }

    /**
     * Polymorphic override
     */
    @Override
    public String getUserType() {
        return "Regular Member";
    }
}

package be.uclouvain.lsinf1225.v.bartender.model;

/**
 * A customer who can order drinks.
 */
public class Customer extends User {
    public Customer(String username, String email, String language) {
        super(username, email, language);
    }
}

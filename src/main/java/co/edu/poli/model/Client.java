package co.edu.poli.model;

import java.util.Objects;

/**
 * Represents a customer's information in the management system.
 * This class stores a customer's basic data, such as their unique identifier,
 * their name, and their email address.
 * */
public class Client {
    private String id;
    private String name;
    private String email;

    /**
     * Constructor to create a new instance of {@code Client}.
     *
     * @param id The unique identifier of the client.
     * @param name The full name of the client.
     * @param email The email address of the client.
     * */
    public Client(String id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }

    /**
     * Compares this client object to another client object to verify equality.
     * Equality is determined based solely on the client's unique identifier.
     *
     * @param one The object to compare with this client.
     * @return {@code true} if the objects are the same or have the same ID, {@code false} otherwise.
     * */
    @Override
    public boolean equals(Object one){
        if (this == one) return true;
        if (one == null || getClass() != one.getClass()) return false;
        Client client = (Client) one;
        return Objects.equals(id, client.id);
    }

    /**
     * Returns a string representation of the customer's information.
     * The format is a multi-line string that includes the customer's ID, name, and email address.
     *
     * @return A string representation of the customer.
     * */
    @Override
    public String toString(){
        return String.format("""
                Cliente{
                id: %s
                nombre: %s
                email: %s
                """,id, name, email);
    }
}
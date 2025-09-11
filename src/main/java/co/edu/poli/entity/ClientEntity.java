package co.edu.poli.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a client's entity in the system.
 * <p>
 * This class maps customer information to the 'clients' table in the database.
 * It is the basic for the persistence of customer data.
 * </p>
 * */
@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {

    /**
     * The unique identifier of the client.
     * Mapped as the primary key of the 'clients' table.
     * */
    @Id
    @Column(name = "id")
    private String id;

    /**
     * The client's full name.
     * It is a required field and has a maximum length of 100 characters.
     * */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * The customer's email address.
     * This is a required field and must be unique for each customer.
     * */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * The date the client was registered.
     * */
    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    /**
     * The list of orders belonging to this customer.
     * <p>
     * Establishes a one-yo-many relationship with the {@link OrderEntity} entity.
     * The 'mappedBy' attribute indicates that the relationship is managed by the 'client' field
     * in the OrderEntity class
     * 'CascadeType.ALL' is used to propagate persistence operations.
     * </p>
     * */
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderEntity> orders;
}
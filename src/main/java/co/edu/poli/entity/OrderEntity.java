package co.edu.poli.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

/**
 * Represents the order entity in the system.
 * <p>
 * This class maps order information to the 'orders' table in the database.
 * Includes a relationship to the {@link ClientEntity} entity.
 * </p>
 * */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    /**
     * The unique identifier of the order.
     * Mapped as the primary key of the 'orders' table with a UUID.
     * */
    @Id
    @Column(name = "id")
    private String id;

    /**
     * The order date.
     * Stored as a date in the database.
     * */
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    /**
     * The order total.
     * Mapped to a DOUBLE column
     * */
    @Column(name = "total", nullable = false)
    private Double total;

    /**
     * Order details in text format.
     * */
    @Column(name = "details")
    private String details;

    /**
     * The customer to whom this order belongs.
     * <p>
     * Establishes a many-to-one relationship with the {@link ClientEntity} entity.
     * Joins the 'id' column of the 'clients' table.
     * </p>
     * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private ClientEntity client;
}
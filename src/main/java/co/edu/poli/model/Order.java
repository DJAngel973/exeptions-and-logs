package co.edu.poli.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Represents a customer order within the inventory management system.
 * This class holds the details of a specific order, including a unique identifier,
 * the ID of the client who placed the order, the order date, the total amount,
 * and a list of order details.
 * */
public class Order {
    private String id;
    private String clientId;
    private LocalDate orderDate;
    private Double total;
    private List<String> details;

    /**
     * Constructs a new {@code Order} instance.
     *
     * @param id The unique identifier of the order.
     * @param clientId The unique identifier of the client who placed the order.
     * @param orderDate The date on which the order was placed.
     * @param total The total cost of the order.
     * @param details A list of strings detailing the order items.
     * */
    public Order(String id, String clientId, LocalDate orderDate, Double total, List<String> details) {
        this.id = id;
        this.clientId = clientId;
        this.orderDate = orderDate;
        this.total = total;
        this.details = details;
    }

    public String getId() {
        return id;
    }
    public String getClientId() {
        return clientId;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }
    public Double getTotal() {
        return total;
    }
    public List<String> getDetails() {
        return details;
    }

    /**
     * Compares this order object with another for equality.
     * The comparison is based solely on the unique identifier of the order.
     *
     * @param one The object to compare against.
     * @return {@code true} if the objects are the same or have the same ID, {@code false} otherwise.
     * */
    @Override
    public boolean equals(Object one){
        if (this == one) return true;
        if (one == null || getClass() != one.getClass()) return false;
        Order order = (Order) one;
        return Objects.equals(id, order.id);
    }

    /**
     * Returns a string representation of the order.
     * This representation is a formatted multi-line string containing the order's ID, client ID,
     * order date, total, and a string representation of the details.
     *
     * @return A formatted string representation of the order.
     * */
    @Override
    public String toString(){
        return String.format("""
                Pedido{
                id: %s
                clienteId: %s
                fecha Pedido: %s
                total: %.2f
                detalles: %s
                """,id, clientId, orderDate, total, details);
    }
}
package co.edu.poli.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Order {
    private String id;
    private String clientId;
    private LocalDate orderDate;
    private Double total;
    private List<String> details;

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

    @Override
    public boolean equals(Object one){
        if (this == one) return true;
        if (one == null || getClass() != one.getClass()) return false;
        Order order = (Order) one;
        return Objects.equals(id, order.id);
    }

    //@Override
    //public int hasCode(){
    //    return Objects.hash(id);
    //}

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
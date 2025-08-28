package co.edu.poli.service;

import co.edu.poli.exception.ClientNotFoundException;
import co.edu.poli.exception.IdDuplicadoException;
import co.edu.poli.exception.InvalidDataException;
import co.edu.poli.exception.OrderNotFoundException;
import co.edu.poli.model.Client;
import co.edu.poli.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Service for business logic related to orders.
 * This class is responsible for creating, searching, and listing orders.
 * <p>
 *     The {@code @Service} annotation marks this class as a Spring service component.
 *     This allows Spring to automatically detect it and manage its lifecycle, making dependency injection easier.
 * </p>
 * */
@Service
public class OrderService {
    private Map<String, Order> orders;
    private ClientService clientService;

    @Autowired
    public OrderService(Map<String, Order> orders, ClientService clientService) {
        this.orders = orders;
        this.clientService = clientService;
    }

    public OrderService() {
        this.orders = orders;
        this.clientService = clientService;
    }

    /**
     * Create a new order for an existing customer.
     * @param clientId The customer ID for the order.
     * @param total The order total.
     * @param details The list of order details.
     * @return The created order object.
     * @throws InvalidDataException if the order data is invalid.
     * @throws ClientNotFoundException if the customer cannot be found.
     * @throws IdDuplicadoException if a duplicate order ID is generated.
     * */
    public Order createOrder(String clientId, Double total, List<String> details) throws ClientNotFoundException {
        if (clientId == null || clientId.trim().isEmpty() || total <= 0 || details == null || details.isEmpty()) {
            throw new InvalidDataException("Datos del pedido incompletos o inválidos.");
        }
        Client existingClient = clientService.searchClient(clientId);

        String orderId = UUID.randomUUID().toString(); // Genera un ID único para el pedido.
        if (orders.containsKey(orderId)) { // Buena práctica verificar.
            throw new IdDuplicadoException("ID del pedido generado duplicado. Intente de nuevo.");
        }
        Order newOrder = new Order(orderId, clientId, LocalDate.now(),total, details);
        orders.put(orderId, newOrder);
        System.out.printf("Pedido %s creado exitosamente para el cliente %s.", newOrder.getId(), clientId);
        return newOrder;
    }

    /**
     * Searches for an order by its ID.
     * @param id The ID of the order to search for.
     * @return The found order object.
     * @throws OrderNotFoundException if the order is not found.
     * */
    public Order searchOrder(String id) throws OrderNotFoundException {
        if (id == null || id.trim().isEmpty()) {
            throw new OrderNotFoundException("El ID del pedido no puede ser nulo o vacío.");
        }
        Order order = orders.get(id);
        if (order == null) {
            throw new OrderNotFoundException(String.format("El pedido con ID %s no encontrado.", id));
        }
        return order;
    }

    /**
     * Finds all orders placed by a specific customer.
     * @param clientId The customer ID.
     * @return A list of the customer's orders.
     * @throws ClientNotFoundException if the customer does not exist.
     * */
    public List<Order> searchOrdersClient(String clientId) throws ClientNotFoundException {
        if (clientId == null || clientId.trim().isEmpty()) {
            throw new ClientNotFoundException("El ID del cliente no puede ser nulo o vacío.");
        }
        clientService.searchClient(clientId);

        List<Order> ordersClient = new ArrayList<>();
        for (Order order : orders.values()) {
            if (order.getClientId().equals(clientId)){
                ordersClient.add(order);
            }
        }
        return ordersClient;
    }

    /**
     * List all orders registered in the system.
     * */
    public void listAllOrders(){
        if (orders.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            return;
        }
        System.out.println("\n------ Pedidos registrados ------");
        orders.values().forEach(System.out::println);
        System.out.println("-----------------------------------");
    }
}